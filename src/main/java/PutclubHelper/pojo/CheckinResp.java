package PutclubHelper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckinResp {
    private double accuracy;
    private List<Words> unrecognized;
    private List<Words> mismatch;
}
