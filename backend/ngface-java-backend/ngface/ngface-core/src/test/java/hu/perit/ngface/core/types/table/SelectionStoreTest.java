/*
 * Copyright (c) 2025. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.core.types.table;

import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.spvitamin.json.JSonSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SelectionStoreTest
{

    @Test
    void testSingleRowsSelected()
    {
        // Test with NONE select mode
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.NONE);
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2, 3);
        selectionStore.singleRowsSelected(rows);

        // No rows should be selected in NONE mode
        assertThat(selectionStore.getSelectedCount()).isZero();

        // Test with SINGLE select mode
        selectionStore = new SelectionStore<>(Table.SelectMode.SINGLE);
        selectionStore.singleRowsSelected(rows);

        // Only one row should be selected in SINGLE mode
        assertThat(selectionStore.getSelectedCount()).isEqualTo(1L);
        assertThat(selectionStore.isSelected(1)).isTrue();
        assertThat(selectionStore.isSelected(2)).isFalse();
        assertThat(selectionStore.isSelected(3)).isFalse();

        // Test with MULTI select mode
        selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);
        selectionStore.singleRowsSelected(rows);

        // All rows should be selected in MULTI mode
        assertThat(selectionStore.getSelectedCount()).isEqualTo(3L);
        assertThat(selectionStore.isSelected(1)).isTrue();
        assertThat(selectionStore.isSelected(2)).isTrue();
        assertThat(selectionStore.isSelected(3)).isTrue();
    }


    @Test
    void testClearSingleSelections()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2, 3);
        selectionStore.singleRowsSelected(rows);

        // Verify rows are selected
        assertThat(selectionStore.getSelectedCount()).isEqualTo(3L);

        // Clear selections
        selectionStore.clearSingleSelections();

        // Verify no rows are selected
        assertThat(selectionStore.getSelectedCount()).isZero();
    }


    @Test
    void testSetSelectMode()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2, 3);
        selectionStore.singleRowsSelected(rows);

        // Verify rows are selected
        assertThat(selectionStore.getSelectedCount()).isEqualTo(3L);

        // Change select mode to ALL_UNCHECKED
        selectionStore.setSelectMode(RowSelectParams.SelectMode.ALL_UNCHECKED);

        // Verify no rows are selected
        assertThat(selectionStore.getSelectedCount()).isZero();

        // Change select mode to ALL_CHECKED
        selectionStore.setSelectMode(RowSelectParams.SelectMode.ALL_CHECKED);
        selectionStore.setTotalElements(10L);

        // Verify all rows are selected
        assertThat(selectionStore.getSelectedCount()).isEqualTo(10L);
    }


    @Test
    void testGetSelectedCount()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);

        // Test with ALL_UNCHECKED mode
        selectionStore.setSelectMode(RowSelectParams.SelectMode.ALL_UNCHECKED);
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2, 3);
        selectionStore.singleRowsSelected(rows);

        // Verify selected count
        assertThat(selectionStore.getSelectedCount()).isEqualTo(3L);

        // Test with ALL_CHECKED mode
        selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);
        selectionStore.setSelectMode(RowSelectParams.SelectMode.ALL_CHECKED);
        selectionStore.setTotalElements(10L);

        // Deselect some rows
        List<RowSelectParams.Row<Integer>> deselectedRows = new ArrayList<>();
        RowSelectParams.Row<Integer> row1 = new RowSelectParams.Row<>();
        row1.setId(1);
        row1.setSelected(false);
        deselectedRows.add(row1);

        RowSelectParams.Row<Integer> row2 = new RowSelectParams.Row<>();
        row2.setId(2);
        row2.setSelected(false);
        deselectedRows.add(row2);

        selectionStore.singleRowsSelected(deselectedRows);

        // Verify selected count (10 total - 2 deselected = 8 selected)
        assertThat(selectionStore.getSelectedCount()).isEqualTo(8L);
    }


    @Test
    void testUpdateRowSelectionStates()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);

        // Select some rows
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2);
        selectionStore.singleRowsSelected(rows);

        // Create test rows
        List<TestRow> testRows = new ArrayList<>();
        testRows.add(new TestRow(1));
        testRows.add(new TestRow(2));
        testRows.add(new TestRow(3));

        // Update row selection states
        selectionStore.updateRowSelectionStates(testRows);

        // Verify row selection states
        assertThat(testRows.get(0).isSelected()).isTrue();
        assertThat(testRows.get(1).isSelected()).isTrue();
        assertThat(testRows.get(2).isSelected()).isFalse();
    }


    @Test
    void testIsSelected()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);

        // Test with ALL_UNCHECKED mode
        selectionStore.setSelectMode(RowSelectParams.SelectMode.ALL_UNCHECKED);
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2);
        selectionStore.singleRowsSelected(rows);

        // Verify selection state
        assertThat(selectionStore.isSelected(1)).isTrue();
        assertThat(selectionStore.isSelected(2)).isTrue();
        assertThat(selectionStore.isSelected(3)).isFalse();
        assertThat(selectionStore.isSelected(null)).isFalse();

        // Test with ALL_CHECKED mode
        selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);
        selectionStore.setSelectMode(RowSelectParams.SelectMode.ALL_CHECKED);

        // Deselect some rows
        List<RowSelectParams.Row<Integer>> deselectedRows = new ArrayList<>();
        RowSelectParams.Row<Integer> row1 = new RowSelectParams.Row<>();
        row1.setId(1);
        row1.setSelected(false);
        deselectedRows.add(row1);
        selectionStore.singleRowsSelected(deselectedRows);

        // Verify selection state
        assertThat(selectionStore.isSelected(1)).isFalse();
        assertThat(selectionStore.isSelected(2)).isTrue();
        assertThat(selectionStore.isSelected(3)).isTrue();
    }


    @Test
    void testGetSelectedRowIds()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);

        // Select some rows
        List<RowSelectParams.Row<Integer>> rows = new ArrayList<>();

        RowSelectParams.Row<Integer> row1 = new RowSelectParams.Row<>();
        row1.setId(1);
        row1.setSelected(true);
        rows.add(row1);

        RowSelectParams.Row<Integer> row2 = new RowSelectParams.Row<>();
        row2.setId(2);
        row2.setSelected(false);
        rows.add(row2);

        RowSelectParams.Row<Integer> row3 = new RowSelectParams.Row<>();
        row3.setId(3);
        row3.setSelected(true);
        rows.add(row3);

        selectionStore.singleRowsSelected(rows);

        // Verify selected row IDs
        List<Integer> selectedRowIds = selectionStore.getSelectedRowIds();
        assertThat(selectedRowIds).containsExactlyInAnyOrder(1, 3);
    }


    @Test
    void testGetUnselectedRowIds()
    {
        SelectionStore<TestRow, Integer> selectionStore = new SelectionStore<>(Table.SelectMode.MULTI);

        // Select some rows
        List<RowSelectParams.Row<Integer>> rows = new ArrayList<>();

        RowSelectParams.Row<Integer> row1 = new RowSelectParams.Row<>();
        row1.setId(1);
        row1.setSelected(true);
        rows.add(row1);

        RowSelectParams.Row<Integer> row2 = new RowSelectParams.Row<>();
        row2.setId(2);
        row2.setSelected(false);
        rows.add(row2);

        RowSelectParams.Row<Integer> row3 = new RowSelectParams.Row<>();
        row3.setId(3);
        row3.setSelected(true);
        rows.add(row3);

        selectionStore.singleRowsSelected(rows);

        // Verify unselected row IDs
        List<Integer> unselectedRowIds = selectionStore.getUnselectedRowIds();
        assertThat(unselectedRowIds).containsExactly(2);
    }


    private List<RowSelectParams.Row<Integer>> createTestRows(Integer... ids)
    {
        List<RowSelectParams.Row<Integer>> rows = new ArrayList<>();
        for (Integer id : ids)
        {
            RowSelectParams.Row<Integer> row = new RowSelectParams.Row<>();
            row.setId(id);
            row.setSelected(true);
            rows.add(row);
        }
        return rows;
    }


    @Test
    void testJsonSerialization() throws IOException
    {
        SelectionStore<TestRow, Integer> expected = new SelectionStore<>(Table.SelectMode.MULTI);
        List<RowSelectParams.Row<Integer>> rows = createTestRows(1, 2, 3);
        expected.singleRowsSelected(rows);
        expected.setTotalElements(10L);
        log.debug(expected.toString());

        String json = JSonSerializer.toJson(expected);
        SelectionStore<?, ?> deserialized = JSonSerializer.fromJson(json, SelectionStore.class);
        assertThat(deserialized).hasToString(expected.toString());
    }


    // Test implementation of AbstractTableRow
    @Getter
    @EqualsAndHashCode
    private static class TestRow implements AbstractTableRow<Integer>
    {
        private boolean selected;
        private final Integer id;


        public TestRow(Integer id)
        {
            this.id = id;
        }


        @Override
        public Integer getId()
        {
            return id;
        }


        @Override
        public void setSelected(Boolean selected)
        {
            this.selected = BooleanUtils.isTrue(selected);
        }
    }
}
