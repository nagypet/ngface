package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.widget.base.Widget;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.*;
import hu.perit.ngface.widget.input.validator.*;
import hu.perit.ngface.widget.table.Column;
import hu.perit.ngface.widget.table.Row;
import hu.perit.ngface.widget.table.Table;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import hu.perit.spvitamin.spring.json.JsonSerializable;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class DemoController implements DemoApi
{
    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Form getDemoForm(Long pageNumber, Long pageSize)
    {
        log.debug("getDemoForm(pageNumber: {}, pageSize: {})", pageNumber, pageSize);

        return new Form("demo-form")
                .addWidget(new TextInput("name")
                        .value("Peter")
                        .label("Owner's name")
                        .placeholder("Your name")
                        .hint("Name of the account owner")
                        .addValidator(new Required("Name is required!"))
                        .addValidator(new Pattern("[A-Z]{1}[a-z]*", "Name has to begin with an uppercase letter and may contain only letters!"))
                        .addValidator(new Size("The name must be max 15 chars long!").max(15))
                )
                .addWidget(new TextInput("place-of-birth")
                        .label("Place of birth")
                        .hint("Very long tooltip to see how it is rendered. kjh gkjhg kjhg kjhg kjgh kjhg kjhg kjhg kjhg kjhg kjhg kjg khjg kjhg kjh")
                )
                .addWidget(new TextInput("email")
                        .label("Email")
                        .addValidator(new Required("Email address is required!"))
                        .addValidator(new Email("Wrong email format!"))
                )
                .addWidget(new TextInput("role")
                        .value("Admin")
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
                .addWidget(getTable(pageNumber, pageSize))
                .addWidget(Button.OK.hint("OK button :-)"))
                .addWidget(Button.CANCEL)
                .addWidget(Button.DELETE)
                ;
    }


    @Data
    public static class TableData
    {
        private List<Item> items;

        @Data
        public static class Item
        {
            private Long id;
            private String name;
            private Double weight;
            private String symbol;
        }
    }

    
    private static final String TABLE_DATA = "{\"items\": [" +
            "{\"id\": 1, \"name\": \"Hydrogen\", \"weight\": 1.0079, \"symbol\": \"H\"}," +
            "{\"id\": 2, \"name\": \"Helium\", \"weight\": 4.0026, \"symbol\": \"He\"}," +
            "{\"id\": 3, \"name\": \"Lithium\", \"weight\": 6.941, \"symbol\": \"Li\"}," +
            "{\"id\": 4, \"name\": \"Beryllium\", \"weight\": 9.0122, \"symbol\": \"Be\"}," +
            "{\"id\": 5, \"name\": \"Boron\", \"weight\": 10.811, \"symbol\": \"B\"}," +
            "{\"id\": 6, \"name\": \"Carbon\", \"weight\": 12.0107, \"symbol\": \"C\"}," +
            "{\"id\": 7, \"name\": \"Nitrogen\", \"weight\": 14.0067, \"symbol\": \"N\"}," +
            "{\"id\": 8, \"name\": \"Oxygen\", \"weight\": 15.9994, \"symbol\": \"O\"}," +
            "{\"id\": 9, \"name\": \"Fluorine\", \"weight\": 18.9984, \"symbol\": \"F\"}," +
            "{\"id\": 10, \"name\": \"Neon\", \"weight\": 20.1797, \"symbol\": \"Ne\"}," +
            "{\"id\": 11, \"name\": \"Sodium\", \"weight\": 22.9897, \"symbol\": \"Na\"}," +
            "{\"id\": 12, \"name\": \"Magnesium\", \"weight\": 24.305, \"symbol\": \"Mg\"}," +
            "{\"id\": 13, \"name\": \"Aluminum\", \"weight\": 26.9815, \"symbol\": \"Al\"}," +
            "{\"id\": 14, \"name\": \"Silicon\", \"weight\": 28.0855, \"symbol\": \"Si\"}," +
            "{\"id\": 15, \"name\": \"Phosphorus\", \"weight\": 30.9738, \"symbol\": \"P\"}," +
            "{\"id\": 16, \"name\": \"Sulfur\", \"weight\": 32.065, \"symbol\": \"S\"}," +
            "{\"id\": 17, \"name\": \"Chlorine\", \"weight\": 35.453, \"symbol\": \"Cl\"}," +
            "{\"id\": 18, \"name\": \"Argon\", \"weight\": 39.948, \"symbol\": \"Ar\"}," +
            "{\"id\": 19, \"name\": \"Potassium\", \"weight\": 39.0983, \"symbol\": \"K\"}," +
            "{\"id\": 20, \"name\": \"Calcium\", \"weight\": 40.078, \"symbol\": \"Ca\"}]}";

    private Widget<?, ?> getTable(Long pageNumber, Long pageSize)
    {
        TableData tableData = null;
        try
        {
            tableData = JsonSerializable.fromJson(TABLE_DATA, TableData.class);
        }
        catch (IOException e)
        {
            // do nothing
        }

        Table table = new Table("table")
                .data(new Table.Data()
                        .addColumn(new Column("id").text("Id"))
                        .addColumn(new Column("name").text("Name"))
                        .addColumn(new Column("weight").text("Weight"))
                        .addColumn(new Column("symbol").text("Symbol"))
                );

        for (TableData.Item item: tableData.getItems())
        {
            table.getData().addRow(new Row(item.id.toString())
                    .addCell(item.id.toString())
                    .addCell(item.name)
                    .addCell(item.weight.toString())
                    .addCell(item.symbol));
        }
        return table;
    }


    @Override
    public void submitDemoForm(SubmitFormData submitFormData)
    {
        log.debug("submitDemoForm()");

        if (submitFormData.getWidgetDataMap() != null)
        {
            for (Map.Entry<String, WidgetData> entry : submitFormData.getWidgetDataMap().entrySet())
            {
                log.debug(String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
            }
        }
    }
}
