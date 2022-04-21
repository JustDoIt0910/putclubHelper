package PutclubHelper.pojo;

import lombok.Data;

//生词表
@Data
public class Words {
    private int id;
    private String word;
    private String explanation;
    private int materialId;
    private int position;
}
