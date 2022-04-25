package PutclubHelper.controller;

import PutclubHelper.pojo.Words;
import PutclubHelper.service.WordService;
import PutclubHelper.utils.http.BaseResponse;
import PutclubHelper.utils.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/words")
public class WordController {
    @Autowired
    private WordService service;

    @PostMapping("/add")
    public BaseResponse addWords(@RequestBody List<Words> words) {
        service.addWords(words);
        return new BaseResponse(200, "ok");
    }

    @GetMapping("/words/{mid}")
    public Response<List<String>> getWordsInMaterial(@PathVariable("mid") String materialId) {
        List<String> words = service.getWordsInMaterial(materialId);
        return new Response<>(200, "ok", words);
    }
}
