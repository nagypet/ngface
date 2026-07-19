package hu.perit.ngface.data.jpa.service.impl;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;

import java.util.List;

public interface FilterConverter<T>
{
    List<T> convertToDatabaseColumn(DataRetrievalParams.Filter filter);

    default List<String> convertToDisplayValue(List<String> dbValue)
    {
        return dbValue;
    }
}
