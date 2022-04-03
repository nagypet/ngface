
/*
 * Copyright 2020-2021 the original author or authors.
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

package hu.perit.ngface.generator;


import hu.perit.ngface.generator.config.Constants;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Application
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) // NOSONAR
    {
        try
        {
            CommandLineArguments commandLineArguments = new CommandLineArguments(args);
            Map<String, String> config = new HashMap<>();

            Iterator<Option> iterator = commandLineArguments.getCommandLine().iterator();
            while (iterator.hasNext())
            {
                Option option = iterator.next();
                switch (option.getLongOpt())
                {
                    case "name":
                        config.put(Constants.NAME, option.getValue());
                        break;
                }

            }

            config.put(Constants.PACKAGE, getPackageName(config));

            processFile("DTO.java", config);
            processFile("Controller.java", config);
            processFile("View.java", config);
        }
        catch (ParseException e)
        {
            // Just do nothing
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Trying to get package name from working directory name
     * e.g. c:\np\github\ngface\backend\spring-boot\webservice\src\main\java\hu\perit\wsstepbystep\ngface =>
     * hu.perit.wsstepbystep.ngface.${NAME}component
     *
     * @param config
     * @return
     */
    private static String getPackageName(Map<String, String> config)
    {
        File wd = new File(".");
        String basePackageName = "";
        String absolutePath = FilenameUtils.getFullPath(wd.getAbsolutePath()).replaceAll("\\\\", "/").toLowerCase();
        int index = absolutePath.indexOf("src/main/java");
        if (index > 0)
        {
            String substring = absolutePath.substring(index + 14);
            basePackageName = substring.replaceAll("/", ".");
        }
        return basePackageName + config.get(Constants.NAME).toLowerCase() + "component";
    }


    private static void processFile(String filename, Map<String, String> config) throws IOException
    {
        FileProcessor controller = new FileProcessor(String.format("templates/%s", filename), config);
        controller.process();

        String packagefolder = String.format("%scomponent", config.get(Constants.NAME).toLowerCase());
        File file = new File(String.format("%s/%sComponent%s", packagefolder, config.get(Constants.NAME), filename));
        controller.save(file);
    }
}
