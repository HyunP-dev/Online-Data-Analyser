package kr.ac.hallym.onlinedataanalyser.toolkit;

import lombok.experimental.StandardException;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class TeXGenerator {
    public String loadTeXTemplate(TeXTemplate template) throws IOException {
        InputStream inputStream = Objects.requireNonNull(
                TeXGenerator.class.getClassLoader().getResource(template.toString())
        ).openStream();
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @param filename filename must include an extension.
     * @param tex
     * @throws IOException
     * @throws InterruptedException
     */
    public void compile(String filename, String tex) throws IOException, InterruptedException {
        String path = System.getProperty("user.home") + "/Online-Data-Analyser-Data/" + filename;
        FileWriter writer = new FileWriter(path);
        writer.write(tex);
        writer.close();

        Runtime runtime = Runtime.getRuntime();

        String outDir = System.getProperty("user.home") + "/Online-Data-Analyser-Data/";
        String cmd = String.format("xelatex -output-directory=%s %s", outDir, path);
        System.out.println("[LOG] TeXGenerator: " + cmd);
        Process p = runtime.exec(cmd);
        p.waitFor();

//        if (debug) {
//            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = "";
//
//            while ((line = b.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            b.close();
//        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

    }
}
