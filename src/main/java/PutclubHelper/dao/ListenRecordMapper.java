package PutclubHelper.dao;

import PutclubHelper.pojo.ListenRecords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("recordMapper")
public interface ListenRecordMapper {
    public void addRecord(@Param("record")ListenRecords record);
    public List<ListenRecords> getRecords(Map<String, Object> page);
}
