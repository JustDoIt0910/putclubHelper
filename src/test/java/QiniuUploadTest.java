import PutclubHelper.crawler.Uploader;
import org.junit.Test;

public class QiniuUploadTest {
    @Test
    public void upload() {
        Uploader uploader = new Uploader();
        String key = uploader.Upload("UA.txt");
        System.out.println(key);
    }
}
