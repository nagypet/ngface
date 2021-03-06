/*
 * Copyright 2020-2022 the original author or authors.
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

package hu.perit.wsstepbystep.ngface.democomponent;

import hu.perit.ngface.formating.CurrencyFormat;
import hu.perit.ngface.formating.NumericFormat;
import hu.perit.ngface.view.ComponentView;
import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.DateInput;
import hu.perit.ngface.widget.input.DateRangeInput;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.Select;
import hu.perit.ngface.widget.input.TextInput;
import hu.perit.ngface.widget.input.validator.Email;
import hu.perit.ngface.widget.input.validator.Max;
import hu.perit.ngface.widget.input.validator.Min;
import hu.perit.ngface.widget.input.validator.Pattern;
import hu.perit.ngface.widget.input.validator.Required;
import hu.perit.ngface.widget.input.validator.Size;
import hu.perit.ngface.widget.table.Action;
import hu.perit.ngface.widget.table.Column;
import hu.perit.ngface.widget.table.Row;
import hu.perit.ngface.widget.table.Table;
import hu.perit.ngface.widget.table.cell.ActionCell;
import hu.perit.wsstepbystep.config.Constants;
import hu.perit.wsstepbystep.service.api.TableRowDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DemoComponentView implements ComponentView
{
    private final DemoComponentDTO data;

    @Override
    public Form getForm()
    {
        return new Form("demo-form")
                .addWidget(new TextInput(DemoComponentDTO.OWNERS_NAME_ID)
                        .value(this.data.getOwnersName())
                        .label("Owner's name")
                        .placeholder("Your name")
                        .hint("Name of the account owner")
                        .addValidator(new Required("Name is required!"))
                        .addValidator(new Pattern("[A-Z]{1}[a-z]*", "Name has to begin with an uppercase letter and may contain only letters!"))
                        .addValidator(new Size("The name must be max 15 chars long!").max(15))
                )
                .addWidget(new TextInput(DemoComponentDTO.PLACE_OF_BIRTH_ID)
                        .value(this.data.getPlaceOfBirth())
                        .label("Place of birth")
                        .hint("Very long tooltip to see how it is rendered. kjh gkjhg kjhg kjhg kjgh kjhg kjhg kjhg kjhg kjhg kjhg kjg khjg kjhg kjh")
                )
                .addWidget(new TextInput(DemoComponentDTO.EMAIL_ID)
                        .value(this.data.getEmail())
                        .label("Email")
                        .addValidator(new Required("Email address is required!"))
                        .addValidator(new Email("Wrong email format!"))
                )
                .addWidget(new TextInput(DemoComponentDTO.ROLE_ID)
                        .value(this.data.getRole())
                        .label("User's role")
                        .hint("Disabled control")
                        .enabled(false)
                )
                .addWidget(new NumericInput(DemoComponentDTO.AMOUNT_ID)
                        .label("Amount")
                        .hint("Must be between 0 and 99.999,99 EUR")
                        .placeholder("Type in the amount")
                        .format(CurrencyFormat.EUR)
                        .value(this.data.getAmount())
                        .addValidator(new Required("Amount is required!"))
                        .addValidator(new Min(0.01, "The min amount is 0,01 EUR"))
                        .addValidator(new Max(99999.99, "The max amount is 99.999,99 EUR"))
                )
                .addWidget(new NumericInput(DemoComponentDTO.COUNT_SAMPLES_ID)
                        .label("Count of samples")
                        .hint("Must be between 1-99")
                        .placeholder("Type in the count of samples, you want to print")
                        .format(new NumericFormat().precision(0).suffix("pcs"))
                        .value(this.data.getCountSamples())
                        .addValidator(new Required("Count of samples is required!"))
                        .addValidator(new Min(1.0, "The count have to be between 1 and 99!"))
                        .addValidator(new Max(99.0, "The count have to be between 1 and 99!"))
                )
                .addWidget(new TextInput(DemoComponentDTO.ACCOUNT_NO)
                        .label("Account number")
                        //.value("alma")
                        .placeholder("00000000-00000000-00000000")
                )
                .addWidget(new DateInput(DemoComponentDTO.CHECK_IN_DATE_ID)
                        .value(this.data.getCheckInDate())
                        .label("Check-in")
                        .hint("DateInput")
                )
                .addWidget(new DateInput(DemoComponentDTO.CHECK_OUT_DATE_ID)
                        .value(this.data.getCheckOutDate())
                        .label("Check-out")
                        .hint("DateInput")
                )
                .addWidget(new DateRangeInput(DemoComponentDTO.DATE_RANGE_ID)
                        .label("Check-in - Check-out")
                        .data(this.data.getDateRange())
                        .placeholder("Start date")
                        .addValidator(new Required("Start date is required!"))
                        .placeholder2("End date")
                        .addValidator2(new Required("End date is required!"))
                        .hint("DateRangeInput - date-range")
                )
                .addWidget(new DateRangeInput("date-range2")
                        .label("Check-in - Check-out uninitialized")
                        .hint("DateRangeInput - date-range2")
                        .enabled(false)
                )
                .addWidget(new Select(DemoComponentDTO.SELECT_ID)
                        .label("Select")
                        .data(this.data.getSelectData())
                        .placeholder("Select an option!")
                        .hint("no default, required")
                        .addValidator(new Required("Must select an option!"))
                )
                .addWidget(new Select(DemoComponentDTO.SELECT2_ID)
                        .label("Select")
                        .data(this.data.getSelect2Data())
                        .placeholder("Select an option!")
                        .hint("no default, not required")
                )
                .addWidget(new Select("select3")
                        .label("Select")
                        .data(this.data.getSelect3Data())
                        .placeholder("Select an option!")
                        .hint("First option is default")
                )
//                .addWidget(getTable("table", Table.SelectMode.NONE))
//                .addWidget(getTable("table-singleselect", Table.SelectMode.SINGLE))
//                .addWidget(new Button("button-details").label("Details"))
                .addWidget(getTable("table-multiselect", Table.SelectMode.CHECKBOX))
                .addWidget(Button.OK.hint("OK button :-)"))
                .addWidget(Button.CANCEL)
                .addWidget(Button.DELETE)
                ;
    }


    private Table getTable(String id, Table.SelectMode selectMode)
    {
        Table table = new Table(id)
                .label("Elements")
                .selectMode(selectMode)
                .addColumn(new Column("id").text("Id").sortable(true).size(Column.Size.S))
                .addColumn(new Column("name").text("Name").sortable(true).size(Column.Size.L))
                .addColumn(new Column("weight").text("Weight").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
                .addColumn(new Column("symbol").text("Symbol").size(Column.Size.S))
                .addColumn(new Column("price-eur").text("Price EUR").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
                .addColumn(new Column("price-huf").text("Price HUF").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
                .addColumn(new Column("actions").text("Actions"));

        for (TableRowDTO item : this.data.getTableDTO().getRows())
        {
            table.addRow(new Row(item.getId().toString())
                    .putCell("id", item.getId().toString())
                    .putCell("name", item.getName())
                    .putCell("weight", item.getWeight(), Constants.ATOMIC_WEIGHT_FORMAT)
                    .putCell("symbol", item.getSymbol())
                    .putCell("price-eur", item.getWeight(), CurrencyFormat.EUR)
                    .putCell("price-huf", item.getWeight() * 370, CurrencyFormat.HUF)
                    .putCell("actions", new ActionCell(List.of(
                            new Action("edit").label("Edit").icon("edit"),
                            new Action("delete").label("Delete").icon("delete").enabled(false)
                    )))
            );
        }

        table.data(this.data.getTableData());

        return table;
    }
}
