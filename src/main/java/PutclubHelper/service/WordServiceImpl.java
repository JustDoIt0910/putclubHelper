package PutclubHelper.service;

import PutclubHelper.dao.WordsMapper;
import PutclubHelper.pojo.Words;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    @Qualifier("wordsMapper")
    private WordsMapper mapper;

    @Override
    public void addWords(List<Words> words) {
        mapper.addWords(words);
    }

    @Override
    public List<String> getWordsInMaterial(String materialId) {
        return mapper.getWordsByMaterialId(materialId);
    }
}
