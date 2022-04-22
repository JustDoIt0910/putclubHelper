package PutclubHelper.pojo;

import lombok.Data;
import java.util.Date;

//对应一篇听力材料
@Data
public class Materials {
    private String  id;
    private String  title; //标题
    private Date    date;  //日期
    private String  type;  //来源或类型(BBC VOA...)
    private String  audioSource; //存放音频的地址
    private String  text; //转出的文本

    public Materials(String id, String title, Date date, String type, String audioSource, String text) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.type = type;
        this.audioSource = audioSource;
        this.text = text;
    }
}
