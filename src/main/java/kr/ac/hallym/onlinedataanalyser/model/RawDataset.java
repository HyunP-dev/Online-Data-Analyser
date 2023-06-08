package kr.ac.hallym.onlinedataanalyser.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RawDataset {
    private String title;
    private String content;
}
