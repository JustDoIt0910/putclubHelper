package PutclubHelper.controller;

import PutclubHelper.cache.RedisCacheManager;
import PutclubHelper.dao.MaterialsMapper;
import PutclubHelper.pojo.Materials;
import PutclubHelper.pojo.TencentCallback;
import PutclubHelper.service.MaterialService;
import PutclubHelper.utils.http.BaseResponse;
import PutclubHelper.utils.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/material")
public class MaterialController {

    @Autowired
    private RedisCacheManager redisClient;
    @Autowired
    MaterialService materialService;

    @PostMapping("/tencentCallback")
    public BaseResponse TencentCallback(TencentCallback callback) {
        String id = redisClient.get(String.valueOf(callback.getRequestId()));
        materialService.save(id, callback.getText());
        return new BaseResponse(0, "success");
    }

    @GetMapping("/{id}")
    public Response<Materials> getMaterial(@PathVariable("id") String id) {
        Materials material = materialService.getMaterialById(id);
        System.out.println(material);
        return new Response<>(200, "ok", material);
    }
}
