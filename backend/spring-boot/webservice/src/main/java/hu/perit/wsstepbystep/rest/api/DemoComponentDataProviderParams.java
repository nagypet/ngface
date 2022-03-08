package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.data.DataProviderParams;
import lombok.Data;

@Data
public class DemoComponentDataProviderParams implements DataProviderParams
{
    private final Long pageNumber;
    private final Long pageSize;
    private final String sortColumn;
    private final String sortDirection;
}
