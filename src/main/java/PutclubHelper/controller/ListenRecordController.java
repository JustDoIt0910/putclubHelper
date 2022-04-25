package PutclubHelper.controller;

import PutclubHelper.pojo.CheckinData;
import PutclubHelper.pojo.CheckinResp;
import PutclubHelper.pojo.ListenRecords;
import PutclubHelper.service.ListenService;
import PutclubHelper.utils.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listen")
public class ListenRecordController {

    @Autowired
    private ListenService service;

    @PostMapping("/checkin")
    public Response<CheckinResp> Checkin(@RequestBody CheckinData record) {
        CheckinResp resp = service.checkin(record);
        return new Response<>(200, "ok", resp);
    }

    @GetMapping("/records")
    public Response<List<ListenRecords>> getListenRecords(
            @RequestParam(name = "pageSize", required = true, defaultValue = "-1") int pageSize,
            @RequestParam(name = "pageNum", required = true, defaultValue = "-1") int pageNum) {
        List<ListenRecords> records = service.getListenRecords(pageSize, pageNum);
        return new Response<>(200, "ok", records);
    }
}
