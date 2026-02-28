package hu.perit.ngface.core.widget.table;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaginatorTest
{
    @Test
    @DisplayName("getLastPageIndex returns 0 when length is null or zero, or pageSize is null or zero")
    void lastPageIndex_whenZeroOrNull()
    {
        assertThat(Paginator.of(0, 10, 0L, List.of()).getLastPageIndex()).isZero();
        assertThat(Paginator.of(0, 0, 10L, List.of()).getLastPageIndex()).isZero();
        assertThat(Paginator.of(0, null, 10L, List.of()).getLastPageIndex()).isZero();
        assertThat(Paginator.of(0, 10, null, List.of()).getLastPageIndex()).isZero();
        assertThat(Paginator.of(0, null, null, List.of()).getLastPageIndex()).isZero();
    }


    @Test
    @DisplayName("getLastPageIndex is zero-based and equals ceil(length/pageSize)-1 for positive values")
    void lastPageIndex_basicCases()
    {
        // length < pageSize => one page => last index = 0
        assertThat(Paginator.of(0, 10, 1L, List.of()).getLastPageIndex()).isZero();
        assertThat(Paginator.of(0, 10, 9L, List.of()).getLastPageIndex()).isZero();

        // exact multiple => last index = pages-1
        assertThat(Paginator.of(0, 10, 10L, List.of()).getLastPageIndex()).isZero();
        assertThat(Paginator.of(0, 10, 20L, List.of()).getLastPageIndex()).isEqualTo(1);
        assertThat(Paginator.of(0, 10, 30L, List.of()).getLastPageIndex()).isEqualTo(2);

        // non-exact multiple
        assertThat(Paginator.of(0, 10, 11L, List.of()).getLastPageIndex()).isEqualTo(1); // 2 pages -> last=1
        assertThat(Paginator.of(0, 7, 20L, List.of()).getLastPageIndex()).isEqualTo(2);  // ceil(20/7)=3 -> last=2
        assertThat(Paginator.of(103, 100, 2592L, List.of()).getLastPageIndex()).isEqualTo(25);
    }


    @Test
    @DisplayName("getLastPageIndex handles large numbers without overflow")
    void lastPageIndex_largeNumbers()
    {
        assertThat(Paginator.of(0, 1_000, 1_000_000L, List.of()).getLastPageIndex()).isEqualTo(999); // 1000 pages => last=999
        assertThat(Paginator.of(0, Integer.MAX_VALUE, 1L, List.of()).getLastPageIndex()).isZero();
    }
}
