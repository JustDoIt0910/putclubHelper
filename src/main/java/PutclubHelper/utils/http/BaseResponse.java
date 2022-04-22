package PutclubHelper.utils.http;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {
    private int code;
    private String message;
    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
