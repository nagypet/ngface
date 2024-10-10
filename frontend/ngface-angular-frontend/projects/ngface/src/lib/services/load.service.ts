import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadService
{
  public loadingSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  public spinnerEnabled = true;

  constructor()
  {
  }

  startLoading(): void
  {
    if (this.spinnerEnabled)
    {
      this.loadingSubject.next(true);
    }
  }

  endLoading(): void
  {
    if (this.spinnerEnabled)
    {
      this.loadingSubject.next(false);
    }
  }
}
