package kr.ac.hallym.onlinedataanalyser.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userid;
    private String userpw;
    private String nickname;
}
