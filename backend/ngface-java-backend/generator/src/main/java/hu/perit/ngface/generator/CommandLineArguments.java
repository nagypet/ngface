package hu.perit.ngface.generator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

@Slf4j
@Getter
public class CommandLineArguments
{
    public static final Option NAME_OPTION = Option.builder("n")
            .longOpt("name")
            .hasArg(true)
            .argName("component name")
            .optionalArg(false)
            .required(true)
            .desc("name of the component with upper camel case")
            .build();

    public static final Option PACKAGE_OPTION = Option.builder("p")
            .longOpt("package")
            .hasArg(true)
            .argName("package name")
            .optionalArg(false)
            .required(false)
            .desc("name of the containing package")
            .build();

    private final CommandLine commandLine;

    public CommandLineArguments (String[] args) throws ParseException
    {
        Options options = new Options();

        options.addOption(NAME_OPTION);
        options.addOption(PACKAGE_OPTION);

        CommandLineParser parser = new DefaultParser();
        try
        {
            this.commandLine = parser.parse(options, args);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ngf", options);
            throw e;
        }
    }


    public boolean hasOption(Option option)
    {
        Option[] options = this.commandLine.getOptions();

        if (options != null && options.length > 0)
        {
            for (Option o : options)
            {
                if (o.equals(option))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
