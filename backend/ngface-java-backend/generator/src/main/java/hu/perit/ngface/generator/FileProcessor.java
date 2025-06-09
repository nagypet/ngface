/*
 * Copyright 2020-2025 the original author or authors.
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class FileProcessor
{
    private final String name;
    private final Map<String, String> config;

    private String content;

    public void process() throws IOException
    {
        this.content = getResourceFileAsString(name);
        for (Map.Entry<String, String> entry : this.config.entrySet())
        {
            String regex = String.format("\\$\\{%s\\}", entry.getKey().toUpperCase());
            //log.debug("Replacing {} by {}", regex, entry.getValue());
            this.content = this.content.replaceAll(regex, entry.getValue());
        }
        //System.out.println(this.content);
    }

    public void save(File file) throws IOException
    {
        Objects.requireNonNull(file, "file parameter cannot be null!");

        String folder = FilenameUtils.getFullPath(file.getAbsolutePath());
        Files.createDirectories(Path.of(folder));
        Files.write(file.toPath(), this.content.getBytes(StandardCharsets.UTF_8));

        System.out.println(file.getName() + " generated");
    }

    private static String getResourceFileAsString(String fileName) throws IOException
    {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName))
        {
            if (is == null)
            {
                return null;
            }
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr))
            {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
