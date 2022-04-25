package PutclubHelper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

//生词表
@Data
@AllArgsConstructor
public class Words {
    private int id;
    private String word;
    private String explanation;
    private String materialId;
    private int position;
}
