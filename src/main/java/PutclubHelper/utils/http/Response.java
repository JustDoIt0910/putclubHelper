package PutclubHelper.utils.http;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Response<E> extends BaseResponse implements Serializable {
    private E data;

    public Response(int code, String message, E data) {
        super(code, message);
        this.data = data;
    }
}
