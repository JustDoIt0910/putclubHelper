package PutclubHelper.pojo.youdao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basic {
    @JsonProperty("exam_type")
    private String[] exam_type;
    private String[] explains;
}