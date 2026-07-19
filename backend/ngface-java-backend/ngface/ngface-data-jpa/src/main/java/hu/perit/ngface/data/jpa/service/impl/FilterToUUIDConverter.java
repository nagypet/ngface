package hu.perit.ngface.data.jpa.service.impl;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;

import java.util.List;
import java.util.UUID;

public class FilterToUUIDConverter implements FilterConverter<UUID>
{
    @Override
    public List<UUID> convertToDatabaseColumn(DataRetrievalParams.Filter filter)
    {
        return filter.getValueSet().stream()
                .filter(i -> i != null && i.getText() != null)
                .map(item -> UUID.fromString(item.getText()))
                .toList();
    }
}
