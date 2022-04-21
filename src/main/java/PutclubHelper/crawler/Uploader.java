package PutclubHelper.crawler;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Uploader {
    private String bucket;
    private String accessKey;
    private String secretKey;
    private final UploadManager uploadManager;
    private final String token;

    public Uploader() {
        Resource resource = new ClassPathResource("oss.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            bucket = properties.getProperty("qiniu.bucket");
            accessKey = properties.getProperty("qiniu.AccessKey");
            secretKey = properties.getProperty("qiniu.SecretKey");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //华南
        Configuration configuration = new Configuration(Region.region2());
        uploadManager = new UploadManager(configuration);
        Auth auth = Auth.create(accessKey, secretKey);
        token = auth.uploadToken(bucket);
    }

    public static class FileKey {
        public String GetKeyByTime() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
            Date now = new Date(System.currentTimeMillis());
            String time = formatter.format(now);
            return "material-" + time;
        }
    }

    public String Upload(String filepath) {
        try {
            String key = new FileKey().GetKeyByTime();
            uploadManager.put(filepath, key, token);
            return key;
        } catch (QiniuException e) {
            System.out.println(e.response.toString());
        }
        return "";
    }
}
