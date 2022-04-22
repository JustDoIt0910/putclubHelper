package PutclubHelper.pojo;

import lombok.Data;

@Data
public class TencentCallback {
    private int code;
    private String message;
    private int requestId;
    private int appid;
    private int projectId;
    private String audioUrl;
    private String text;
    private String resultDetail;
    private double audioTime;
}
