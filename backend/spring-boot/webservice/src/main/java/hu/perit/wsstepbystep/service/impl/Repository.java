package hu.perit.wsstepbystep.service.impl;

import hu.perit.ngface.data.DataRetrievalParams;
import hu.perit.spvitamin.core.typehelpers.LongUtils;
import hu.perit.wsstepbystep.service.api.TableRowDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Repository
{
    private List<TableRowDTO> rows;

    public Optional<TableRowDTO> findById(Long id)
    {
        return this.rows.stream().filter(r -> LongUtils.equals(r.getId(), id)).findFirst();
    }


    public List<String> getDistinctNames()
    {
        return this.rows.stream().map(TableRowDTO::getName).distinct().sorted().collect(Collectors.toList());
    }

    public List<String> getDistinctSymbols()
    {
        return this.rows.stream().map(TableRowDTO::getSymbol).distinct().sorted().collect(Collectors.toList());
    }

    public List<TableRowDTO> filterBy(List<DataRetrievalParams.Filter> filters)
    {
        List<TableRowDTO> result = new ArrayList<>(this.rows);

        if (filters == null)
        {
            return result;
        }

        for (DataRetrievalParams.Filter filter : filters)
        {
            result = filterBy(result, filter);
        }

        return result;
    }

    private List<TableRowDTO> filterBy(List<TableRowDTO> input, DataRetrievalParams.Filter filter)
    {
        return input.stream().filter(i -> contains(filter, i)).collect(Collectors.toList());
    }

    private boolean contains(DataRetrievalParams.Filter filter, TableRowDTO row)
    {
        List<String> stringList = filter.getValueSet().stream().map(DataRetrievalParams.Filter.Item::getText).collect(Collectors.toList());
        if ("name".equals(filter.getColumn()))
        {
            return stringList.contains(row.getName());
        }
        else if ("symbol".equals(filter.getColumn()))
        {
            return stringList.contains(row.getSymbol());
        }

        throw new IllegalStateException(String.format("Cannot filter by column '%s'", filter.getColumn()));
    }
}
