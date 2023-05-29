package hu.perit.ngface.webservice.service.api;

import hu.perit.ngface.data.DataRetrievalParams;
import hu.perit.ngface.widget.table.Filterer;

import java.util.Optional;

public interface DemoTableDataProvider
{
    Optional<TableRowDTO> getTableRowById(Long id);

    Page<TableRowDTO> getTableRows(DataRetrievalParams dataRetrievalParams);

    Filterer getNameFilter();

    Filterer getSymbolFilter();
}
