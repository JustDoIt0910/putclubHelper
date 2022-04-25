package PutclubHelper.translate;

import PutclubHelper.pojo.youdao.Result;
import PutclubHelper.pojo.youdao.YoudaoRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class youdaoClient {
    private  String appKey;
    private  String appSecret;
    private final HttpClient client;
    private  String api;

    public youdaoClient() {
        try {
            Properties ps = PropertiesLoaderUtils.loadProperties(new ClassPathResource("youdao.properties"));
            appKey = ps.getProperty("youdao.appKey");
            appSecret = ps.getProperty("youdao.appSecret");
            api = ps.getProperty("youdao.api");
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public Result translate(String text, String from, String to) {
        long curtime = System.currentTimeMillis() / 1000;
        String curtimeStr = String.valueOf(curtime);
        String salt = String.valueOf(System.currentTimeMillis());
        String s = appKey + text + salt + curtimeStr + appSecret;
        YoudaoRequest request = new YoudaoRequest(text, from, to,
                appKey, salt, getSign(s), "v3", curtimeStr);
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI(api)).header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(ofFormData(request)).build();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(response.body(), Result.class);
        } catch(URISyntaxException | IllegalAccessException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSign(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(s.getBytes(StandardCharsets.UTF_8));
            return byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    private static BodyPublisher ofFormData(Object data) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (first)
                first = false;
            else
                builder.append("&");
            String key = URLEncoder.encode(f.getName(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode((String) f.get(data), StandardCharsets.UTF_8);
            builder.append(key);
            builder.append("=");
            builder.append(value);
        }
        return BodyPublishers.ofString(builder.toString());
    }
}
