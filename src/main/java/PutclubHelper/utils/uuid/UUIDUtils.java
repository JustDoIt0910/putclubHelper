package PutclubHelper.utils.uuid;

import java.util.UUID;

public class UUIDUtils {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
