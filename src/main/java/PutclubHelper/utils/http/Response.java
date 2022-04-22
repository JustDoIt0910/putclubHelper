package PutclubHelper.utils.http;

import lombok.Data;
import lombok.EqualsAndHashCode;


public class Response<E> extends BaseResponse {
    private int code;
    private String message;
    private E data;

    public Response(int code, String message, E data) {
        super(code, message);
        this.data = data;
    }
}
