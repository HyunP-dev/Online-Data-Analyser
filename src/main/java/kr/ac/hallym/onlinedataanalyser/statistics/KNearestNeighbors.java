package kr.ac.hallym.onlinedataanalyser.statistics;

import kr.ac.hallym.onlinedataanalyser.model.RawDataset;
import kr.ac.hallym.onlinedataanalyser.toolkit.TeXGenerator;
import kr.ac.hallym.onlinedataanalyser.toolkit.TeXTemplate;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class KNearestNeighbors implements Closeable {
    private final REngine engine;
    private final RawDataset rawDataset;
    private final String userid;

    public KNearestNeighbors(String userid, RawDataset raw, String[] describe, String target) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, REngineException, REXPMismatchException {
        System.out.println("[LOG] KNearestNeighbors: " + "Start creating an object.");

        rawDataset = raw;
        this.userid = userid;
        engine = REngine.engineForClass("org.rosuda.REngine.JRI.JRIEngine");
        engine.assign("content", raw.getContent());
        engine.assign("title", raw.getTitle());
        engine.assign("df", engine.parseAndEval("read.table(text=content, sep=\",\", header=T, stringsAsFactors=F)"));
        engine.assign("desc", describe);
        engine.assign("target", target);
        engine.assign("df", engine.parseAndEval("df[c(desc, target)]"));

        engine.assign("rdsidx", engine.parseAndEval("sample(2, nrow(df), replace=T, prob=c(0.67, 0.33))"));

        engine.assign("df.train.X", engine.parseAndEval("df[rdsidx==1, 1:(ncol(df)-1)]"));
        engine.assign("df.train.y", engine.parseAndEval("df[rdsidx==1, ncol(df)]"));

        engine.assign("df.test.X", engine.parseAndEval("df[rdsidx==2, 1:(ncol(df)-1)]"));
        engine.assign("df.test.y", engine.parseAndEval("df[rdsidx==2, ncol(df)]"));


        engine.parseAndEval("library(class)");
//        engine.parseAndEval("knn(train = df.train.X, test = df.test.X, cl = df.train.y, k =3)");

        System.out.println("[LOG] KNearestNeighbors: " + "ended an object.");
    }

    public double score(int k) throws REngineException, REXPMismatchException {
        String cmd = "knn(train=df.train.X, test=df.test.X, cl=df.train.y, k="+k+")";
        engine.assign("df.test.yhat", engine.parseAndEval(cmd));
        return engine.parseAndEval("mean(df.test.yhat==df.test.y)").asDouble();
    }

    public static void main(String[] args) throws IOException, REngineException, REXPMismatchException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InterruptedException {
        Scanner scanner = new Scanner(new File("/home/researcher/Downloads/iris.csv"));
        StringBuilder rawIrisBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            rawIrisBuilder
                    .append(scanner.nextLine())
                    .append("\n");
        }
        String rawIris = rawIrisBuilder.toString();
        RawDataset rawDataset = new RawDataset("iris", rawIris);
//        KNearestNeighbors kNearestNeighbors = new KNearestNeighbors("alice", rawDataset);

//        for (int i = 1; i < 100; i++) {
//            System.out.println("when k=" + i + ": " + kNearestNeighbors.score(i));
//        }
//        kNearestNeighbors.generateReport();

        System.out.println("end");
    }

    public void generateReport() throws IOException, REngineException, REXPMismatchException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InterruptedException {
        System.out.println("[LOG] KNearestNeighbors: " + "started generating a report.");
        String template = TeXGenerator.loadTeXTemplate(TeXTemplate.KNN);
        StringBuilder stringBuilder = new StringBuilder();
        int argmaxK = -1;
        double maxS = -1;
        for (int i = 1; i < 50; i++) {
//            System.out.println("[LOG] KNearestNeighbors: " + "scoring " + i);
            double score = score(i);
            if (score > maxS) {
                argmaxK = i;
                maxS = score;
            }
            stringBuilder.append(String.format("when k=%2d: %f\\\\", i, score));
        }
        String tex = template
                .replace("@title", "kNN 분석 결과")
                .replace("@dataset-summary", new Summary(rawDataset).getTeX())
                .replace("@knn-result", stringBuilder.toString())
                .replace("@argmax-k", String.valueOf(argmaxK));
        String filename = userid + "-knn-" + System.currentTimeMillis() + ".pdf";
        TeXGenerator.compile(filename, tex);
    }

    @Override
    public void close() {
        engine.close();
    }
}
