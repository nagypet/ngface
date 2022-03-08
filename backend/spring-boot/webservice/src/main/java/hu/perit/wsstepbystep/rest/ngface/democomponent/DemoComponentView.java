package hu.perit.wsstepbystep.rest.ngface.democomponent;

import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.*;
import hu.perit.ngface.widget.input.validator.*;
import hu.perit.ngface.widget.table.Column;
import hu.perit.ngface.widget.table.Paginator;
import hu.perit.ngface.widget.table.Row;
import hu.perit.ngface.widget.table.Table;
import hu.perit.wsstepbystep.config.Constants;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@RequiredArgsConstructor
public class DemoComponentView
{
    public static final String OWNERS_NAME_ID = "name";
    public static final String PLACE_OF_BIRTH_ID = "place-of-birth";
    public static final String EMAIL_ID = "email";
    public static final String ROLE_ID = "role";

    private final DemoComponentData data;

    public Form getDemoForm()
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
                .addWidget(new NumericInput("amount")
                        .label("Amount")
                        .hint("Must be between 0 and 99.999 EUR")
                        .placeholder("Type in the amount")
                        .precision(2)
                        .value(new BigDecimal("2.123456789"))
                        .prefix("EUR")
                        .addValidator(new Required("Amount is required!"))
                        .addValidator(new Min(0.01, "The min amount is 0,01 EUR"))
                        .addValidator(new Max(99999.99, "The max amount is 99.999,99 EUR"))
                )
                .addWidget(new NumericInput("count-samples")
                        .label("Count of samples")
                        .hint("Must be between 1-99")
                        .placeholder("Type in the count of samples, you want to print")
                        .precision(0)
                        .value(new BigDecimal("10"))
                        .suffix("pcs")
                        .addValidator(new Required("Count of samples is required!"))
                        .addValidator(new Min(1.0, "The count have to be between 1 and 99!"))
                        .addValidator(new Max(99.0, "The count have to be between 1 and 99!"))
                )
                .addWidget(new DateInput("check-in-date")
                        .label("Check-in")
                        .value(LocalDate.now())
                        .hint("DateInput")
                )
                .addWidget(new DateInput("check-out-date")
                        .label("Check-out")
                        .hint("DateInput")
                )
                .addWidget(new DateRangeInput("date-range")
                        .label("Check-in - Check-out")
                        .data(new DateRangeInput.Data(LocalDate.now(), LocalDate.now().plusDays(1)))
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
                .addWidget(new Select("select")
                        .label("Select")
                        .data(new Select.Data()
                                .addOption(new Select.Option("id_first", "First option"))
                                .addOption(new Select.Option("id_second", "Second option"))
                        )
                        .placeholder("Select an option!")
                        .hint("no default, required")
                        .addValidator(new Required("Must select an option!"))
                )
                .addWidget(new Select("select2")
                        .label("Select")
                        .data(new Select.Data()
                                .addOption(new Select.Option("id_first", "First option"))
                                .addOption(new Select.Option("id_second", "Second option"))
                        )
                        .placeholder("Select an option!")
                        .hint("no default, not required")
                )
                .addWidget(new Select("select3")
                        .label("Select")
                        .data(new Select.Data()
                                .addOption(new Select.Option("id_first", "First option"))
                                .addOption(new Select.Option("id_second", "Second option"))
                                .selected("id_first")
                        )
                        .placeholder("Select an option!")
                        .hint("First option is default")
                )
                .addWidget(getTable())
                .addWidget(Button.OK.hint("OK button :-)"))
                .addWidget(Button.CANCEL)
                .addWidget(Button.DELETE)
                ;
    }


    private Table getTable()
    {
        Table table = new Table("table")
                .addColumn(new Column("id").text("Id").sortable(true).size(Column.Size.S))
                .addColumn(new Column("name").text("Name").sortable(true).size(Column.Size.L))
                .addColumn(new Column("weight").text("Weight").size(Column.Size.S))
                .addColumn(new Column("symbol").text("Symbol"));

        for (DemoTableDataProvider.DataRow item : this.data.getTableRows())
        {
            table.addRow(new Row(item.getId().toString())
                    .putCell("id", item.getId().toString())
                    .putCell("name", item.getName())
                    .putCell("weight", item.getWeight().toString())
                    .putCell("symbol", item.getSymbol()));
        }

        table.paginator(new Paginator(Constants.DEFAULT_PAGESIZE, this.data.getTotalTableRowCount(), Arrays.asList(3, 5, 10, 20)));
        return table;
    }
}
