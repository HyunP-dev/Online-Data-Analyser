package kr.ac.hallym.onlinedataanalyser.statistics;

import kr.ac.hallym.onlinedataanalyser.model.RawDataset;
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
import java.util.Scanner;

public class KNearestNeighbors implements Closeable {
    private final REngine engine;

    public KNearestNeighbors(RawDataset raw) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, REngineException, REXPMismatchException {
        engine = REngine.engineForClass("org.rosuda.REngine.JRI.JRIEngine");
        engine.assign("content", raw.getContent());
        engine.assign("title", raw.getTitle());
        engine.assign("df", engine.parseAndEval("read.table(text=content, sep=\",\", header=T, stringsAsFactors=F)"));


        engine.assign("rdsidx", engine.parseAndEval("sample(2, nrow(df), replace=T, prob=c(0.67, 0.33))"));

        engine.assign("df.train.X", engine.parseAndEval("df[rdsidx==1, 1:(ncol(df)-1)]"));
        engine.assign("df.train.y", engine.parseAndEval("df[rdsidx==1, ncol(df)]"));

        engine.assign("df.test.X", engine.parseAndEval("df[rdsidx==2, 1:(ncol(df)-1)]"));
        engine.assign("df.test.y", engine.parseAndEval("df[rdsidx==2, ncol(df)]"));


        engine.parseAndEval("library(class)");
//        engine.parseAndEval("knn(train = df.train.X, test = df.test.X, cl = df.train.y, k =3)");
    }

    public double score(int k) throws REngineException, REXPMismatchException {
        String cmd = "knn(train=df.train.X, test=df.test.X, cl=df.train.y, k="+k+")";
        engine.assign("df.test.yhat", engine.parseAndEval(cmd));
        return engine.parseAndEval("mean(df.test.yhat==df.test.y)").asDouble();
    }

    public static void main(String[] args) throws FileNotFoundException, REngineException, REXPMismatchException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Scanner scanner = new Scanner(new File("/home/researcher/Downloads/iris.csv"));
        StringBuilder rawIrisBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            rawIrisBuilder.append(scanner.nextLine() + "\n");
        }
        String rawIris = rawIrisBuilder.toString();
        RawDataset rawDataset = new RawDataset("iris", rawIris);
        KNearestNeighbors kNearestNeighbors = new KNearestNeighbors(rawDataset);

        for (int i = 1; i < 100; i++) {
            System.out.println("when k=" + i + ": " + kNearestNeighbors.score(i));
        }

        System.out.println("end");
    }

    @Override
    public void close() {
        engine.close();
    }
}
