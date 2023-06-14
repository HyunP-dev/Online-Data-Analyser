package kr.ac.hallym.onlinedataanalyser.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawDataset {
    private String title;
    private String content;
}
