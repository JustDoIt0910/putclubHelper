package PutclubHelper.dao;

import PutclubHelper.pojo.Words;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("wordsMapper")
public interface WordsMapper {
    public void addWords(List<Words> words);
    public List<String> getWordsByMaterialId(@Param("mid") String materialId);
}
