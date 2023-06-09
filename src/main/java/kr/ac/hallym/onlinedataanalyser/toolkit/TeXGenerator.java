package kr.ac.hallym.onlinedataanalyser.toolkit;

import lombok.experimental.StandardException;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class TeXGenerator {
    public String loadTeXTemplate() throws IOException {
        InputStream inputStream = Objects.requireNonNull(
                TeXGenerator.class.getClassLoader().getResource("template.tex")
        ).openStream();
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public void generate(String filename, String title, String section, String content) throws IOException, InterruptedException {
        generate(filename, title, section, content, false);
    }

    public void generate(String filename, String title, String section, String content, boolean debug) throws IOException, InterruptedException {
        String path = System.getProperty("user.home") + "/Online-Data-Analyser-Data/" + filename;
        FileWriter writer = new FileWriter(path);
        writer.write(
                loadTeXTemplate()
                        .replace("@title", title)
                        .replace("@section", section)
                        .replace("@content", content)
        );
        writer.close();


        Runtime runtime = Runtime.getRuntime();

        String outDir = System.getProperty("user.home") + "/Online-Data-Analyser-Data/";
        String cmd = String.format("xelatex -output-directory=%s %s", outDir, path);
        Process p = runtime.exec(cmd);
        p.waitFor();

        if (debug) {
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

    }
}
