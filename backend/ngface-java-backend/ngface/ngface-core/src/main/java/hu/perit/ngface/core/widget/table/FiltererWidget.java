package hu.perit.ngface.core.widget.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.ngface.core.widget.base.VoidWidgetData;
import hu.perit.ngface.core.widget.base.Widget;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.BooleanUtils;

import java.text.MessageFormat;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FiltererWidget extends Widget<VoidWidgetData, FiltererWidget> implements FiltererPanelItem
{
    @JsonIgnore
    private final Filterer filterer;


    public FiltererWidget(String id, Filterer filterer)
    {
        super(id);
        Objects.requireNonNull(filterer, MessageFormat.format("filterer is null! id={0}", id));
        this.filterer = filterer;
    }


    // Json
    private FiltererWidget()
    {
        super(null);
        this.filterer = null;
    }


    public String getColumn()
    {
        return this.filterer.getColumn();
    }


    @Override
    public Long getCountActiveFilters()
    {
        if (this.filterer == null)
        {
            return 0L;
        }
        return BooleanUtils.isTrue(this.filterer.getActive()) ? 1L : 0L;
    }
}
