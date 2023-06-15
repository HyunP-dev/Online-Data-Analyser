package kr.ac.hallym.onlinedataanalyser.toolkit;

import lombok.experimental.StandardException;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class TeXGenerator {
    private final ClassLoader classLoader = TeXGenerator.class.getClassLoader();
    private final HashMap<TeXTemplate, String> cache = new HashMap<>();

    public String loadTeXTemplate(TeXTemplate template) throws IOException {
        if (cache.containsKey(template)) return cache.get(template);
        String templateFilename = template.toString();
        InputStream inputStream = Objects.requireNonNull(
                classLoader.getResource(templateFilename)
        ).openStream();
        String templateTeX = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));
        cache.put(template, templateTeX);
        return templateTeX;
    }

    /**
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
