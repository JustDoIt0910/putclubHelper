package PutclubHelper.pojo;

import lombok.Data;
import java.util.Date;

//对应一篇听力材料
@Data
public class Materials {
    private int     id;
    private String  title; //标题
    private Date    date;  //日期
    private String  type;  //来源或类型(BBC VOA...)
    private String  audioSource; //存放音频的地址
    private String  text; //转出的文本
}
