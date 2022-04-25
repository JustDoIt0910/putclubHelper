package PutclubHelper.service;

import PutclubHelper.pojo.CheckinData;
import PutclubHelper.pojo.CheckinResp;
import PutclubHelper.pojo.ListenRecords;

import java.util.List;

public interface ListenService {
    public CheckinResp checkin(CheckinData data);
    public List<ListenRecords> getListenRecords(int pageSize, int pageNum);
}
