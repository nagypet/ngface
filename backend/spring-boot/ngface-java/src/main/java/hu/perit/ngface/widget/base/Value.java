package hu.perit.ngface.widget.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@RequiredArgsConstructor
@Getter
public class Value<V> extends WidgetData
{
    private final V value;
}
