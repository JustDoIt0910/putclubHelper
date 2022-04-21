package PutclubHelper.pojo;

import lombok.Data;
import java.util.Date;

//对应一次听力练习记录
@Data
public class ListenRecords {
    private int id;
    private Date date; //日期
    private long startTime; //开始时间
    private long endTime; //结束时间
    private int materialId; //听的哪篇材料
    private double accuracy; //听写准确率
}
