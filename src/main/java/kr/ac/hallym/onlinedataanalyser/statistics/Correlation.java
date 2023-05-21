package kr.ac.hallym.onlinedataanalyser.statistics;

import lombok.Getter;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;

import java.lang.reflect.InvocationTargetException;

public class Correlation {
    @Getter
    private final double estimate;

    @Getter
    private final double statistic;

    @Getter
    private final double pValue;

    public Correlation(double[] v1, double[] v2) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, REngineException, REXPMismatchException {
        REngine engine = REngine.engineForClass("org.rosuda.REngine.JRI.JRIEngine");
        engine.assign("v1", v1);
        engine.assign("v2", v2);
        engine.assign("t", engine.parseAndEval("cor.test(v1, v2)"));
        estimate = engine.parseAndEval("t$estimate").asDouble();
        statistic = engine.parseAndEval("t$statistic").asDouble();
        pValue = engine.parseAndEval("t$p.value").asDouble();
        engine.close();
    }

    public void generateReport() {

    }
}
