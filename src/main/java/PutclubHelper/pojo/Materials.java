package PutclubHelper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//对应一篇听力材料
@Data
@AllArgsConstructor
public class Materials implements Serializable {
    private String  id;
    private String  title; //标题
    private Date    date;  //日期
    private String  type;  //来源或类型(BBC VOA...)
    private String  audioSource; //存放音频的地址
    private String  text; //转出的文本
}
