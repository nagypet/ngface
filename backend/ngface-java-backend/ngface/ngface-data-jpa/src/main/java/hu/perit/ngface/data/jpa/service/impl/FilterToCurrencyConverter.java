package hu.perit.ngface.data.jpa.service.impl;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.spvitamin.core.util.Currency;

import java.util.List;

public class FilterToCurrencyConverter implements FilterConverter<Currency>
{
    @Override
    public List<Currency> convertToDatabaseColumn(DataRetrievalParams.Filter filter)
    {
        return filter.getValueSet().stream()
                .filter(i -> i != null && i.getText() != null)
                .map(item -> Currency.fromString(item.getText()))
                .filter(i -> i.isValid())
                .toList();
    }
}
