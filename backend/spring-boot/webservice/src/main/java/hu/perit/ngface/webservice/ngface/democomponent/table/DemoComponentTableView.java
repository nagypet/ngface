/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.ngface.webservice.ngface.democomponent.table;

import hu.perit.ngface.core.formating.CurrencyFormat;
import hu.perit.ngface.core.formating.NumericFormat;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.base.VoidWidgetData;
import hu.perit.ngface.core.widget.base.Widget;
import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Action;
import hu.perit.ngface.core.widget.table.Column;
import hu.perit.ngface.core.widget.table.Row;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.ngface.core.widget.table.cell.ActionCell;
import hu.perit.ngface.webservice.model.AddressTableRow;
import hu.perit.spvitamin.core.typehelpers.LongUtils;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class DemoComponentTableView implements ComponentView
{
    public static final String BUTTON_SELECT_ALL = "button-select-all";
    public static final String BUTTON_CLEAR_ALL = "button-clear-all";

    private final DemoComponentTableDTO data;


    @Override
    public Form getForm()
    {
        Form form = new Form("demo-form-table")
//                .addWidget(getTable("table", Table.SelectMode.NONE))
//                .addWidget(getTable("table-singleselect", Table.SelectMode.SINGLE))
//                .addWidget(new Button("button-details").label("Details"))
            .addWidget(getTable(DemoComponentTableDTO.TABLE_MULTISELECT, Table.SelectMode.CHECKBOX));
        getActionBar().values().stream().filter(Objects::nonNull).forEach(form::addWidget);
        return form;
    }


    private Table<Long> getTable(String id, Table.SelectMode selectMode)
    {
        Table<Long> table = new Table<Long>(id)
            .label("Multiselect table demo")
            .selectMode(selectMode)
            .addColumn(new Column(AddressTableRow.COL_ID).text("ID").sortable(true).size(Column.Size.NUMBER).textAlign(Column.TextAlign.RIGHT))
            .addColumn(new Column(AddressTableRow.COL_POSTCODE).text("Post code").sortable(true).size(Column.Size.S))
            .addColumn(new Column(AddressTableRow.COL_CITY).text("City").sortable(true).size(Column.Size.L))
            .addColumn(new Column(AddressTableRow.COL_STREET).text("Street").sortable(true).size(Column.Size.L))
            .addColumn(new Column(AddressTableRow.COL_DISTRICT).text("District").sortable(true).size(Column.Size.S))
            .addColumn(new Column("price-huf").text("Price HUF").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
            .addColumn(new Column("price-eur").text("Price EUR").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
            .addColumn(new Column("actions").text("Actions"));

        for (AddressTableRow tableRow : this.data.getTableDTO().getContent().getRows())
        {
            table.addRow(new Row<>(tableRow.getId())
                .selected(tableRow.getSelected())
                .putCell(AddressTableRow.COL_ID, tableRow.getId(), NumericFormat.UNGROUPED)
                .putCell(AddressTableRow.COL_POSTCODE, tableRow.getPostCode(), NumericFormat.UNGROUPED)
                .putCell(AddressTableRow.COL_CITY, tableRow.getCity())
                .putCell(AddressTableRow.COL_STREET, tableRow.getStreet())
                .putCell(AddressTableRow.COL_DISTRICT, tableRow.getDistrict())
                .putCell("price-huf", tableRow.getPostCode() * 1_000, CurrencyFormat.HUF)
                .putCell("price-eur", tableRow.getPostCode() * 1_000 / 380, CurrencyFormat.EUR)
                .putCell("actions", new ActionCell(List.of(
                    new Action("edit").label("Edit").icon("edit"),
                    new Action("delete").label("Delete").icon("delete").enabled(false)
                )))
            );
        }

        table.data(this.data.getTableDTO().getData());

        // Notification
        table.notification(this.data.getTableDTO().getContent().getNotification());

        return table;
    }


    public Map<String, Widget<VoidWidgetData, Button>> getActionBar()
    {
        Map<String, Widget<VoidWidgetData, Button>> buttons = new HashMap<>();
        buttons.put(BUTTON_CLEAR_ALL, null);

        Button buttonSelectAll = new Button(BUTTON_SELECT_ALL)
            .label("Select All")
            .style(Button.Style.PRIMARY)
            .badge((String) null)
            .enabled(false);
        buttons.put(BUTTON_SELECT_ALL, buttonSelectAll);

        Long countSelectedRows = this.data.getTableDTO().getContent().getCountSelectedRows();

        Button buttonClearAll = new Button(BUTTON_CLEAR_ALL)
            .label("Clear All")
            .style(Button.Style.PRIMARY)
            .badge((String) null)
            .enabled(false);
        buttons.put(BUTTON_CLEAR_ALL, buttonClearAll);

        if (countSelectedRows > 0)
        {
            buttonClearAll.enabled(true).badge(countSelectedRows);
        }

        long paginatorLength = this.data.getTableDTO().getData().getPaginatorLength();
        if (LongUtils.compare(paginatorLength, countSelectedRows) > 0)
        {
            buttonSelectAll.enabled(true).badge(paginatorLength - countSelectedRows);
        }

        return buttons;
    }

}
