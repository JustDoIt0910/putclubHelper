package PutclubHelper.audio2text;

import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskRequest;
import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class AudioTransform {
    AsrClient client;
    String callbackUrl;

    public AudioTransform() {
        try{
            Properties ps = PropertiesLoaderUtils.loadProperties(new ClassPathResource("tencent.properties"));
            Credential cred = new Credential(ps.getProperty("tencent.secretId"), ps.getProperty("tencent.secretKey"));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("asr.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            client = new AsrClient(cred, "", clientProfile);
            callbackUrl = ps.getProperty("tencent.callbackUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CreateRecTaskResponse post(String audioUrl) throws TencentCloudSDKException {
        CreateRecTaskRequest req = new CreateRecTaskRequest();
        req.setEngineModelType("16k_en");
        req.setChannelNum(1L);
        req.setResTextFormat(0L);
        req.setSourceType(0L);
        req.setUrl(audioUrl);
        req.setCallbackUrl(callbackUrl);
        return client.CreateRecTask(req);
    }
}
