package hu.perit.wsstepbystep.service.api;

import lombok.Data;

@Data
public class TableRowDTO
{
    private Long id;
    private String name;
    private Double weight;
    private String symbol;
}
