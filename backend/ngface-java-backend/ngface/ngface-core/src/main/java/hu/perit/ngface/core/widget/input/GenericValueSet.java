package hu.perit.ngface.core.widget.input;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public class GenericValueSet<T extends AbstractOption> implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1943622900162277749L;

    // If there are more then MAX_SIZE criteria, then they will not be provided, but must be searched for
    private static final int MAX_SIZE = 100;

    // Remote means, the list of criteria is way too large, cannot be provided without a filter pattern. The client must
    // narrow down the list of distinct items by providing some filter patterns. The client must fetch the value set from
    // the backend each time the searchText changes.
    private Boolean remote;
    private Boolean truncated = Boolean.FALSE;
    private List<Item<T>> values = new ArrayList<>();


    public GenericValueSet(Boolean remote)
    {
        this.remote = remote;
    }


    public GenericValueSet<T> values(Collection<T> valueSet)
    {
        this.values = new ArrayList<>();
        if (valueSet == null)
        {
            this.values.add(new GenericValueSet.Item<T>().value(null));
            return this;
        }
        List<T> sortedValueSet = valueSet.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList();
        for (T value : sortedValueSet)
        {
            this.values.add(new GenericValueSet.Item<T>().value(value));
            if (this.values.size() >= MAX_SIZE)
            {
                this.truncated = Boolean.TRUE;
                this.remote = Boolean.TRUE;
                break;
            }
        }
        return this;
    }


    public void selected(String id, Boolean value)
    {
        if (id == null)
        {
            return;
        }
        this.values.stream().filter(i -> id.equalsIgnoreCase(i.getValue().getId())).forEach(i -> i.selected(BooleanUtils.isTrue(value)));
    }


    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Item<T extends AbstractOption> implements Serializable
    {
        @Serial
        private static final long serialVersionUID = -4579785670689051036L;

        private T value;
        private Boolean selected = Boolean.TRUE;


        public Item<T> value(T value)
        {
            this.value = value;
            return this;
        }


        public Item<T> selected(Boolean selected)
        {
            this.selected = selected;
            return this;
        }
    }
}
