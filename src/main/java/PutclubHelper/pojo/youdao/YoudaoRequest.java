package PutclubHelper.pojo.youdao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoudaoRequest {
    private String q;
    private String from;
    private String to;
    private String appKey;
    private String salt;
    private String sign;
    private String signType;
    private String curtime;
}
