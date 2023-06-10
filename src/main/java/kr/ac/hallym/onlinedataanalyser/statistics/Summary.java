package kr.ac.hallym.onlinedataanalyser.statistics;

import kr.ac.hallym.onlinedataanalyser.model.RawDataset;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;

import java.lang.reflect.InvocationTargetException;

public class Summary {
    private final REngine engine;
    private final String resultTeX;

    public Summary(RawDataset raw) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, REngineException, REXPMismatchException {
        engine = REngine.engineForClass("org.rosuda.REngine.JRI.JRIEngine");
        engine.assign("content", raw.getContent());
        engine.assign("title", raw.getTitle());
        engine.assign("df", engine.parseAndEval("read.table(text=content, sep=\",\", header=T, stringsAsFactors=F)"));
        engine.assign("res", engine.parseAndEval("summary(df)"));
        engine.parseAndEval("library(xtable)");
        engine.assign("res.tex", engine.parseAndEval("xtable(res) |> print()"));
        resultTeX = engine.parseAndEval("res.tex").asString();
    }

    public String getTeX() {
        return resultTeX;
    }
}
