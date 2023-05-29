package hu.perit.ngface.webservice.service.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page<T>
{
    private List<T> list = new ArrayList<>();
    private long totalElements = 0;
}
