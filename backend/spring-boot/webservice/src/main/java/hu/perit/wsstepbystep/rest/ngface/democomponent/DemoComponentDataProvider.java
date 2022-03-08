package hu.perit.wsstepbystep.rest.ngface.democomponent;

import hu.perit.ngface.data.ComponentDataProvider;
import hu.perit.ngface.data.DataProviderParams;
import hu.perit.wsstepbystep.rest.api.DemoComponentDataProviderParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Peter Nagy
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoComponentDataProvider implements ComponentDataProvider<DemoComponentData>
{
    private final DemoTableDataProvider demoTableDataProvider;

    @Override
    public DemoComponentData getData(DataProviderParams params)
    {
        if (!(params instanceof DemoComponentDataProviderParams))
        {
            throw new RuntimeException("DataProviderParams type mismatch!");
        }
        DemoComponentDataProviderParams p = (DemoComponentDataProviderParams) params;

        DemoComponentData data = new DemoComponentData();
        data.setOwnersName("Peter");
        data.setRole("Admin");
        data.setTableRows(this.demoTableDataProvider.getTableRows(p.getPageNumber(), p.getPageSize(), p.getSortColumn(), p.getSortDirection()));
        data.setTotalTableRowCount(this.demoTableDataProvider.getLength());
        return data;
    }

    @Override
    public void setData(DemoComponentData data)
    {
        log.debug(data.toString());
    }
}
