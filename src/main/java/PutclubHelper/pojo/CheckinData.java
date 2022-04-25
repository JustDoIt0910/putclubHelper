package PutclubHelper.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class CheckinData {
    private String materialId;
    private long start_at;
    private long end_at;
    private String listeningText;
}
