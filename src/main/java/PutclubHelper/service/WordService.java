package PutclubHelper.service;

import PutclubHelper.pojo.Words;

import java.util.List;

public interface WordService {
    public void addWords(List<Words> words);
    public List<String> getWordsInMaterial(String materialId);
}
