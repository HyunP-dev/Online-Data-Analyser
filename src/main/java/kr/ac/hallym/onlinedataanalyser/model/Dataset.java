package kr.ac.hallym.onlinedataanalyser.model;

import lombok.Getter;

public class Dataset {
    @Getter
    private final String title;

    @Getter
    private final String[] columns;

    private final String[][] values;

    private Dataset(RawDataset raw) {
        title = raw.getTitle();
        final String content = raw.getContent();
        String[] lines = content.split("\n");
        columns = lines[0].split(",");
        values = new String[lines.length - 1][columns.length];
        for (int i = 1; i < lines.length; i++) {
            values[i - 1] = lines[i].split(",");
        }
    }

    public String getValue(String column, int index) {
        return values[index][column.indexOf(column)];
    }

    public static Dataset from(RawDataset raw) {
        return new Dataset(raw);
    }
}
