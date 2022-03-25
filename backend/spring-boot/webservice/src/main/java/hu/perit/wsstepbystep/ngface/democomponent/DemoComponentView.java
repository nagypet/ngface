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

import hu.perit.ngface.formating.FormatCurrency;
import hu.perit.ngface.view.ComponentView;
import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.*;
import hu.perit.ngface.widget.input.validator.*;
import hu.perit.ngface.widget.table.*;
import hu.perit.ngface.widget.table.cell.ActionCell;
import hu.perit.wsstepbystep.config.Constants;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class DemoComponentView implements ComponentView
{
    public static final String OWNERS_NAME_ID = "name";
    public static final String PLACE_OF_BIRTH_ID = "place-of-birth";
    public static final String EMAIL_ID = "email";
    public static final String ROLE_ID = "role";
    public static final String AMOUNT_ID = "amount";
    public static final String COUNT_SAMPLES_ID = "count-samples";
    public static final String ACCOUNT_NO = "account-no";
    public static final String CHECK_IN_DATE_ID = "check-in-date";
    public static final String CHECK_OUT_DATE_ID = "check-out-date";
    public static final String DATE_RANGE_ID = "date-range";
    public static final String SELECT_ID = "select";
    public static final String SELECT2_ID = "select2";
    public static final String SELECT3_ID = "select3";

    private final DemoComponentData data;

    public Form getForm()
    {
        return new Form("demo-form")
                .addWidget(new TextInput(OWNERS_NAME_ID)
                        .value(this.data.getOwnersName())
                        .label("Owner's name")
                        .placeholder("Your name")
                        .hint("Name of the account owner")
                        .addValidator(new Required("Name is required!"))
                        .addValidator(new Pattern("[A-Z]{1}[a-z]*", "Name has to begin with an uppercase letter and may contain only letters!"))
                        .addValidator(new Size("The name must be max 15 chars long!").max(15))
                )
                .addWidget(new TextInput(PLACE_OF_BIRTH_ID)
                        .value(this.data.getPlaceOfBirth())
                        .label("Place of birth")
                        .hint("Very long tooltip to see how it is rendered. kjh gkjhg kjhg kjhg kjgh kjhg kjhg kjhg kjhg kjhg kjhg kjg khjg kjhg kjh")
                )
                .addWidget(new TextInput(EMAIL_ID)
                        .value(this.data.getEmail())
                        .label("Email")
                        .addValidator(new Required("Email address is required!"))
                        .addValidator(new Email("Wrong email format!"))
                )
                .addWidget(new TextInput(ROLE_ID)
                        .value(this.data.getRole())
                        .label("User's role")
                        .hint("Disabled control")
                        .enabled(false)
                )
                .addWidget(new NumericInput(AMOUNT_ID)
                        .label("Amount")
                        .hint("Must be between 0 and 99.999,99 EUR")
                        .placeholder("Type in the amount")
                        .format(FormatCurrency.EUR)
                        .value(this.data.getAmount())
                        .addValidator(new Required("Amount is required!"))
                        .addValidator(new Min(0.01, "The min amount is 0,01 EUR"))
                        .addValidator(new Max(99999.99, "The max amount is 99.999,99 EUR"))
                )
                .addWidget(new NumericInput(COUNT_SAMPLES_ID)
                        .label("Count of samples")
                        .hint("Must be between 1-99")
                        .placeholder("Type in the count of samples, you want to print")
                        .precision(0)
                        .value(this.data.getCountSamples())
                        .suffix("pcs")
                        .addValidator(new Required("Count of samples is required!"))
                        .addValidator(new Min(1.0, "The count have to be between 1 and 99!"))
                        .addValidator(new Max(99.0, "The count have to be between 1 and 99!"))
                )
                .addWidget(new TextInput(ACCOUNT_NO)
                        .label("Account number")
                        //.value("alma")
                        .placeholder("00000000-00000000-00000000")
                )
                .addWidget(new DateInput(CHECK_IN_DATE_ID)
                        .value(this.data.getCheckInDate())
                        .label("Check-in")
                        .hint("DateInput")
                )
                .addWidget(new DateInput(CHECK_OUT_DATE_ID)
                        .value(this.data.getCheckOutDate())
                        .label("Check-out")
                        .hint("DateInput")
                )
                .addWidget(new DateRangeInput(DATE_RANGE_ID)
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
                .addWidget(new Select(SELECT_ID)
                        .label("Select")
                        .data(this.data.getSelectData())
                        .placeholder("Select an option!")
                        .hint("no default, required")
                        .addValidator(new Required("Must select an option!"))
                )
                .addWidget(new Select(SELECT2_ID)
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
                .addColumn(new Column("id").text("Id").sortable(true).size(Column.Size.S))
                .addColumn(new Column("name").text("Name").sortable(true).size(Column.Size.L))
                .addColumn(new Column("weight").text("Weight").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
                .addColumn(new Column("symbol").text("Symbol").size(Column.Size.S))
                .addColumn(new Column("price-eur").text("Price EUR").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
                .addColumn(new Column("price-huf").text("Price HUF").size(Column.Size.M).textAlign(Column.TextAlign.RIGHT))
                .addColumn(new Column("actions").text("Actions"));

        for (DemoTableDataProvider.DataRow item : this.data.getTableRows())
        {
            table.addRow(new Row(item.getId().toString())
                    .putCell("id", item.getId().toString())
                    .putCell("name", item.getName())
                    .putCell("weight", BigDecimal.valueOf(item.getWeight()), Constants.ATOMIC_WEIGHT_FORMAT)
                    .putCell("symbol", item.getSymbol())
                    .putCell("price-eur", BigDecimal.valueOf(item.getWeight()), FormatCurrency.EUR)
                    .putCell("price-huf", BigDecimal.valueOf(item.getWeight() * 370), FormatCurrency.HUF)
                    .putCell("actions", new ActionCell(List.of(
                            new Action("edit").label("Edit").icon("edit"),
                            new Action("delete").label("Delete").icon("delete").enabled(false)
                    )))
            );
        }

        table
                .paginator(new Paginator(Constants.DEFAULT_PAGESIZE, this.data.getTotalTableRowCount(), Arrays.asList(3, 5, 10, 20)))
                .selectMode(selectMode);
        return table;
    }
}
