# SSE - Server sent event

Ez a technológia lehetővé teszi, hogy szerver oldalról tudjunk a frontendnek értesítéseket küldeni, ezzel a pollozás elkerülhetővé válik. Ez jelentős szerver terhelés és hálózati forgalom csökkenést eredményez, különösen nagyszámú kliens esetén. 

A feladat az volt, hogy a megosztás események végrehajtása után az eredményről jelenjen meg a frontend-en egy toast. Mivel a megosztások aszinkron módon hajtódnak végre, a legjobb megoldás az, ha a backend értesíti a frontend-et a művelet eredményéről. Korábban a kilevelezés tab frissítése is pollozás által valósult meg, a tervem az volt, hogy a technológiát nem csak a toast megjelenítésére fogom használni, hanem a pollozást is kiváltom.

Itt van egy jó kis [bevezető](https://golb.hplar.ch/2017/03/Server-Sent-Events-with-Spring.html) a témába.

Ezt a fajta kommunikációt is a kliens kezdeményezi egy SSE csatorna megnyitásával. Ilyenkor a backend egy `SseEmitter` objektumot ad vissza. Ezután a backend aszinkron eseményeket tud küldeni a csatornát nyitó kliens-nek. Egy-egy ilyen csatorna alapértelmezetten 30 mp-ig él, utána a backend oldal lezárja. Ezt érzékeli a frontend, és elindít egy újracsatlakozási kísérletet. Első pillanásra fura ez a rövid timeout, de mégis van értelme, ugyanis a frontend-ről nem lehet zárni a csatornát, csak nyitni, így a rövid timeout miatt a fölösleges csatornák hamar automatikusan kipucolódnak a rendszerből. Pontosabban le lehet zárni a csatornát frontend oldalon, de erről a backend nem értesül csak úgy, hogy ha küldeni akar egy lezárt csatornába, akkor `ClientAbortException` keletkezik.

Frontend oldalon az `EventSource` osztályt használjuk.

Mind backend, mint frontend oldalon úgy oldottam meg a feladatot, hogy elkülönítettem egymástól az alacsonyabb szintű, újrafelhasználható kommunikációs réteget az üzleti logikától.

## Backend

A controller kialakításánál a lényeg a media type és a return value. 
```java
@GetMapping(path = BASE_URL + "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
SseEmitter subscribe(); 
```
Az `SseEmitter` objektumok létrehozását a `ServerSentEvent` osztály kezeli. Ez lényegében az observer pattern-t valósítja meg, úgy csináltam meg, hogy teljesen analóg legyen a backend-en belüli esemény alapú kommunikációt kezelő `hu.perit.spvitamin.core.event.Event` osztállyal. [GitHub](https://github.com/nagypet/spvitamin/tree/master/spvitamin-core)

A `ServerSentEvent` osztály lényege:
```java
public class ServerSentEvent<T, F> {

    public Subscription<F> subscribe(String lastReceivedEventIdAsString, F filterKey) {...}
    public synchronized void fire(T args) {...}
    public synchronized void fire(T args, F filterCriteria, BiFunction<F, F, Boolean> filterFunction) {...}

    @Getter
    public static class Subscription<F> extends SseEmitter {
        private final int id;
        private final F filterKey;

        public Subscription(int id, Long timeout, F filterKey) {
            super(timeout);
            this.id = id;
            this.filterKey = filterKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Subscription<?> that = (Subscription<?>) o;

            return new EqualsBuilder()
                    .append(id, that.id)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .toHashCode();
        }
    }
}
```
A feliratkozás a `subscribe()` metódussal történik, amelyik egy `Subscription` osztályt ad vissza. (Memento pattern) A `Subscription` osztály az `SseEmitter`-ből származik, és pluszban tárol két változót: a subscription azonosítóját és a `filterKey` adatot.   

A `subscribe()` metódus két paramétert vár: 
- `lastReceivedEventIdAsString`: ez a paraméter egyfajta minőségbiztosítást tesz lehetővé az üzenetküldő rendszerben (__Quality of Service__). A kapcsolat a frontend és a backend között labilis, 30 mp-enként lebomlik, majd újraépül. Ezért a frontend minden kapcsolódáskor visszaküldi a backend-nek a legutoljára fogadott esemény azonosítóját. Így a backend újra tudja küldeni a kimaradt eseményeket. De mit küldjünk újra? A QoS __delivery__ minőség tekintetében három alapesetet különböztetünk meg: __legalább egyszer__, __legföljebb egyszer__, illetve __pontosan egyszer__. A jelenleg implementált változat a felsoroltak közül egyiknek sem felel meg, mégis tökéletesen megfelel a célnak, mert itt nem az a fontos, hogy a frontend minden változásra vonatkozó értesítést megkapjon, hanem az, hogy a frontend mindig a backend aktuális állapotát jelezze ki, amihez elegendő a legutolsó eseményt újraküldeni egy sikeres csatlakozás után, amennyiben a backend érzékeli, hogy a frontend le van maradva. Ez azért jó, mert ha a kapcsolat megszakadt állapotában elmulasztott összes eseményt újraküldenénk, akkor az indokolatlanul sok frissítést eredményezne a frontend oldalon, ráadásul zavaró lenne a felhasználó számára, ha a múltban, talán több perccel korábban megtörtént összes állapotváltozásról egyszerre értesülne. A jövőbeni fejlesztés feladata lehet, a delivery minőség paraméterezhetőségét megoldani. 
- `filterKey`: Mivel ez egy generikus típus a `filterKey` bármilyen objektum lehet, amit a `fire()` metódusban a targetek leválogatására szeretnénk használni.

A `fire()` metódusnak két variációja létezik, az egyik feltétel nélkül továbbítja a paraméterben átadott eseményt az összes feliratkozónak, a másiknak pedig paraméterben adhatunk egy lambda kifejezést, amely boolean értéket ad vissza, és azt vezérli, hogy az adott előfizetőnek ki kell-e küldeni az eseményt. Ez akkor hasznos, ha a feliratkozókat nem különítjük el HTTP session szinten, hanem másfajta session kezelést szeretnénk megvalósítani.

Például:

```java
if (!eventToSend.isEmpty()) {
    serverSentEvent.fire(eventToSend, megrendeloAzon, String::equals);
}
```

## Frontend
Frontend oldalon a funkcionalitást az `SseChannel` osztályban valósítottam meg. Ez gondoskodik a csatorna megnyitásáról, lezárásáról, illetve hiba esetén az újracsatlakozásról.

```java
export class SseChannel {
  private eventSource: EventSource;
  private url: string;
  private reconnectFrequencySec: number;
  private reconnectTimeout: number;
  private lastEventId: string;
  private onMessage: (MessageEvent) => any;
  private debugLogEnabled: boolean;
  private zone: NgZone;

  constructor(
    url: string,
    onMessage: (MessageEvent) => any,
    zone: NgZone,
    debugLogEnabled: boolean = false,
  ) {
    this.url = url;
    this.onMessage = onMessage;
    this.zone = zone;
    this.debugLogEnabled = debugLogEnabled;
  }

  public open(): boolean {
    this.createSseEventSource();
    return !!this.eventSource;
  }

  public close(onError: boolean = false) {
    if (this.eventSource) {
      this.eventSource.close();
      this.eventSource = null;
      if (!onError) {
        this.lastEventId = undefined;
        if (this.debugLogEnabled) { console.log('SSE channel closed and lastEventId discarded!'); }
      } else {
        if (this.debugLogEnabled) { console.log('SSE channel closed!'); }
      }
    }
  }

  private createSseEventSource(): void {
    // Close event source if current instance of SSE service has some
    if (this.eventSource) {
      this.close();
    }
    // Open new channel, create new EventSource
    if (this.lastEventId) {
      this.eventSource = new EventSource(this.url + '&lastEventId=' + this.lastEventId);
    } else {
      this.eventSource = new EventSource(this.url);
    }

    // Process default event
    this.eventSource.onmessage = (event: MessageEvent) => {
      this.handleServerEvent(event);
    };

    // Process connection opened
    this.eventSource.onopen = () => {
      if (this.debugLogEnabled) { console.log('SSE channel opened!'); }
      this.reconnectFrequencySec = 1;
    };

    // Process error
    this.eventSource.onerror = (error: any) => {
      if (this.debugLogEnabled) { console.log(error); }
      this.reconnectOnError();
    };
  }

  // Handles reconnect attempts when the connection fails for some reason.
  private reconnectOnError(): void {
    this.close(true);
    clearTimeout(this.reconnectTimeout);
    if (this.debugLogEnabled) { console.log('Reconnecting in ' + this.reconnectFrequencySec + ' sec...'); }
    this.reconnectTimeout = setTimeout(() => {
      this.open();
      this.reconnectFrequencySec *= 2;
      if (this.reconnectFrequencySec >= 30) {
        this.reconnectFrequencySec = 30;
      }
    }, this.reconnectFrequencySec * 1000);
  }

  private handleServerEvent(event: MessageEvent) {
    if (this.debugLogEnabled) { console.log(event); }
    this.lastEventId = event.lastEventId;
    this.zone.run(() => this.onMessage(event));
  }
}
```
Az újracsatlakozás úgy van megoldva, hogy hiba esetén 1 mp-ről indul, majd duplázódik a timeout, egészen 30 mp-ig. Ha lelőjük a backend-et, akkor 30 mp próbál újracsatlakozni, amíg nem sikerül.

# Megvalósítás a megosztás központban

A session kezelés kialakításánál több megoldás is szóba jöhet. Egyrészt a service layert megvalósító osztály scope paraméterét választhatjuk session scope-ra, így backend oldalon pontosan leképződik a kliensek strukturája, és a feliratkozók teljesen el volnának egymástól szeparálva. Ha csak a toast megjelenítése lett volna a feladat, akkor ezt választottam volna, de a mi esetünkben a kilevelezés tab frissítése is feladat. Amennyiben több kliens is megnyitja ugyanazt a megrendelést, és a kilevelezés tabra navigál, akkor azt várjuk el, hogy mindegyik böngészőben frissüljün a megosztások állapotát mutató táblázat. Így a HTTP session nem jön szóba. 

## Controller
```java
@GetMapping(path = BASE_URL + "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
SseEmitter subscribe(
        @RequestParam("megrendeloAzon") String megrendeloAzon,
        @RequestParam(value = "lastEventId", required = false) String lastEventId
) throws UnsupportedEncodingException {
    String decodedMegrendeloAzon = URLDecoder.decode(megrendeloAzon, "UTF-8");
    log.debug("subscribe({}, {})", decodedMegrendeloAzon, lastEventId);

    return this.megosztasKozpontFrontendNotificationService.subscribe(decodedMegrendeloAzon, lastEventId);
}
```
A `subscribe()` metódus két paramétert kap a frontend-től: 
- `megrendeloAzon`: egyfajta session key-ként funkcionál. Ahhoz kell, hogy majd az értesítést csak azoknak a frontend-eknek küldjök ki, amelyeket ez érdekel. Már a backend oldalon is van egy erre vonatkozó szűrés, de a frontend is ellenőrzi. 
- `lastEventId`: az utoljára sikeresen fogadott esemény azonosítója. 

## Service layer

A service layert a `MegosztasKozpontFrontendNotificationService` kezeli backend oldalon. A feladata a backend oldalon keletkező nyers események feldolgozása a frontend igényeinek megfelelően. Mindjárt a tervezés elején tudtam, hogy a már most is kialakított mechanizmust szeretném használni, amelyik a megposztás események állapotváltozásai esetén eseményeket küld. Ezekre fel lehet iratkozni, és továbbítani lehet őket a frontend-nek. Ugyanakkor nem lett volna jó ezeket az eseményeket egy az egyben továbbküldeni, mert a frontend-en egy zavaró vibrálást okozott volna. Ezért úgy döntöttem, hogy a keletkező állapotváltozás eseményeket összevárom, és csak bizonyos időközönként továbbítom a frontend-nek. Ez az időköz kellően nagy legyen ahhoz, hogy megakadályozza a frontend-en a toast villogását, de elég kicsi legyen ahhoz, hogy az állapotváltozás után viszonylag gyorsan megjelenjen a frontend-en a frissítés. 3 mp timeout jónak tűnik.

Azt kellett még megoldani, hogy ez a service layer megrendelő azonosítónként gyűjtse a beérkező eseményeket, hogy könnyen ki tudjuk válogatni azokat a feliratkozókat, akik érdeklődnek az állapotváltozás iránt.

## Frontend
A frontend kialakítása már egyszerű:
```typescript
  ngOnInit() {
    // Sse channel
    const baseUrl = this.configService.getConfiguration().baseServerProtocol +
      this.configService.getConfiguration().baseServerAddress + ':' +
      this.configService.getConfiguration().baseServerPort + this.configService.getConfiguration().baseServerPath;
    const sseChannelUrl = 'https:' + baseUrl + 'megosztasok/subscribe?megrendeloAzon=' + this.megrendeloId;
    console.log(sseChannelUrl);

    this.sseChannel = new SseChannel(sseChannelUrl, (event: MessageEvent) => this.handleServerEvent(event), this.zone);
    this.sseChannel.open();
  }

  ngOnDestroy() {
    this.sseChannel.close();
  }

  private handleServerEvent(event: MessageEvent) {
    const data: MegosztasEsemenyStatusChangedEventDto = JSON.parse(event.data);
    // Megnézzük, hogy releváns-e nekünk az üzenet?
    if (data.megrendelesAzon === this.megrendeloId) {
      this.load();

      let message;
      if (data.countFailed === 0) {
        message = 'Kilevelezés sikeres!';
      } else {
        message = 'Kilevelezés hiba!';
      }
      if (data.countDelivered > 0) {
        message += '<br>' + data.countDelivered + ' esemény kiküldve.';
      }
      if (data.countReadyToDeliver > 0) {
        message += '<br>' + data.countReadyToDeliver + ' esemény kiküldése visszavonva.';
      }
      if (data.countInProgress > 0) {
        message += '<br>' + data.countInProgress + ' esemény kiküldése folyamatban.';
      }
      if (data.countFailed > 0) {
        message += '<br>' + data.countFailed + ' esemény kiküldése nem sikerült.';
      }

      const actModal: Modal = {
        modalClass: 'modal-info',
        message,
        disableFadeIn: false,
        disableFadeOut: true,
      };
      this.modalService.setModal(actModal);
    }
  }
```
Egy eddig szokatlan jelenség, amire figyelni kell, hogy mivel a DOM változása az angular keretrendszeren kívül keletkezik, a framework nem frissíti automatikusan. Ezt a problémát kezeli az `NgZone` osztály. 
