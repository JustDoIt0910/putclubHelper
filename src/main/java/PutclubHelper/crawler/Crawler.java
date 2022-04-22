package PutclubHelper.crawler;

import PutclubHelper.audio2text.AudioTransform;
import PutclubHelper.cache.RedisCacheManager;
import PutclubHelper.dao.MaterialsMapper;
import PutclubHelper.pojo.Materials;
import PutclubHelper.utils.uuid.UUIDUtils;
import cn.wanghaomiao.xpath.model.JXDocument;
import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


//用于获取当天更新的材料
@Component
public class Crawler {
    private Material_Type type;
    private static List<String> UAPool;
    private HttpClient client;
    private Uploader uploader;
    private Map<String, String> xpathRules;
    private String baseURL;
    private String mainURLPattern;
    AudioTransform transformer;
    MaterialsMapper mapper;
    RedisCacheManager redis;

    @Autowired
    public Crawler(@Qualifier("materialsMapper") MaterialsMapper mapper,
    @Qualifier("redisCache") RedisCacheManager redis) {
        this.mapper = mapper;
        this.redis = redis;
        this.transformer = new AudioTransform();
        Resource resource = new ClassPathResource("crawler.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            this.baseURL = properties.getProperty("crawler.baseURL");
            this.mainURLPattern = properties.getProperty("crawler.mainURLPattern");
            this.xpathRules = new HashMap<>();
            addXPathRule("date", properties);
            addXPathRule("detail", properties);
            addXPathRule("resource1", properties);
            addXPathRule("resource2", properties);
            addXPathRule("title", properties);
            var uaReader = new UAReader(properties.getProperty("crawler.ua"));
            uaReader.Read();
            this.client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10)).build();
            this.uploader = new Uploader();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.type = Material_Type.Special;
    }

    private void addXPathRule(String key, Properties ps) {
        xpathRules.put(key, ps.getProperty("crawler.xpath." + key));
    }

    private String getRandomUA() {
        Random rnd = new Random();
        return UAPool.get(rnd.nextInt(UAPool.size()));
    }

    private String getPage(String url) {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET().header("User-Agent", getRandomUA()).build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            return new String(response.body(), "gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean getResource(String url, String filename) throws IOException {
        File audio = new File(filename);
        try(FileOutputStream fs = new FileOutputStream(audio)){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET().header("User-Agent", getRandomUA()).build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            fs.write(response.body());
            fs.flush();
        } catch (Exception e) {
            audio.delete();
            return false;
        }
        return true;
    }

    @Scheduled(cron = "0/60 * * * * ?")
    public void Craw() {
        System.out.println("updating.........");
        String mainURL = mainURLPattern.replace("{type}", type.toString())
                .replace("{page}", "1");
        String mainPage = getPage(mainURL);
        JXDocument document = new JXDocument(mainPage);
        try {
            List<Object> detailUrls = document.sel(xpathRules.get("detail"));
            List<Object> dates = document.sel(xpathRules.get("date"));

            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
            //String today = format.format(now);
            String today = "2022-4-22";
            for(int i = 0; i < dates.size(); i++) {
                String date = (String) dates.get(i);
                //判断今日有无更新
                if(date.equals(today)) {
                    Thread.sleep(3 * 1000);
                    String detailUrl = baseURL + detailUrls.get(i);
                    String detailPage = getPage(detailUrl);

                    document = new JXDocument(detailPage);
                    List<Object> title = document.sel(xpathRules.get("title"));
                    List<Object> resourceUrl = document.sel(xpathRules.get("resource1"));
                    if(resourceUrl.size() == 0)
                        resourceUrl = document.sel(xpathRules.get("resource2"));
                    if(title.size() > 0 && resourceUrl.size() > 0) {
                        String filename = ((String)title.get(0)).replace(" ", "") + ".mp3";
                        if(getResource((String)resourceUrl.get(0), filename)) {
                            System.out.println("download audio success: " + filename);
                            UploadResult res = uploader.Upload(filename);
                            boolean ok =  (new File(filename)).delete();

                            String id = UUIDUtils.uuid();
                            mapper.addMaterial(new Materials(id, filename,
                                    new Date(System.currentTimeMillis()),
                                    "", res.getUrl(), ""));
                            CreateRecTaskResponse taskResponse =  transformer.post(res.getUrl());
                            System.out.println("post send success, TaskId: " + taskResponse.getData().getTaskId());
                            redis.set(String.valueOf(taskResponse.getData().getTaskId()), id);
                            System.out.println(taskResponse.getData().getTaskId() + " -----> " + id);
                        }
                    }
                }
                else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class UAReader {
        private final String uaFile;
        public UAReader(String uaFile) {this.uaFile = uaFile;}

        public void Read() {
            try {
                String path =Thread.currentThread().getContextClassLoader().getResource(uaFile).getPath();
                BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
                UAPool = new ArrayList<>();
                while(true) {
                    String line = reader.readLine();
                    if (line == null)
                        break;
                    UAPool.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
