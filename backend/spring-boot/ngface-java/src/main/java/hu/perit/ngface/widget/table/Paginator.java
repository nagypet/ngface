package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@ToString
@Getter
public class Paginator
{
    private final Integer pageSize;
    private final Integer length;
    private final List<Integer> pageSizeOptions;
}
