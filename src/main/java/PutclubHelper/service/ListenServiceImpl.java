package PutclubHelper.service;

import PutclubHelper.dao.ListenRecordMapper;
import PutclubHelper.dao.MaterialsMapper;
import PutclubHelper.pojo.CheckinData;
import PutclubHelper.pojo.CheckinResp;
import PutclubHelper.pojo.ListenRecords;
import PutclubHelper.pojo.Words;
import PutclubHelper.pojo.youdao.Result;
import PutclubHelper.translate.youdaoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class ListenServiceImpl implements ListenService {

    @Autowired
    @Qualifier("recordMapper")
    private ListenRecordMapper recordDao;

    @Autowired
    @Qualifier("materialsMapper")
    private MaterialsMapper materialDao;

    private final youdaoClient translator = new youdaoClient();

    @Override
    public CheckinResp checkin(CheckinData data) {
        String text = materialDao.getMaterialById(data.getMaterialId()).getText();
        int s = 0;
        int length = text.length();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            if(text.charAt(i) == '[' || i == length - 1) {
                if(s < length && i > 0)
                    builder.append(text.substring(s, i));
            }
            else if(text.charAt(i) == ']') {
                s = i + 1;
            }
        }
        String[] words = numberProcess(builder.toString()
                .toLowerCase().strip()).split("[\\n., ]+");
        String[] inputWords = numberProcess(data.getListeningText()
                .toLowerCase().strip()).split("[\\n., ]+");
        int correct = 0;
        //匹配数字的pattern(小数a.b被提前处理为apb, 防止单词分割时被错误分割)
        Pattern numberPattern = Pattern.compile("-?\\d+(p\\d+)?");
        List<Words> unrecognized = new ArrayList<>();
        List<Words> mismatch = new ArrayList<>();
        for(int i = 0; i < Math.min(words.length, inputWords.length); i++) {
            if(words[i].equals(inputWords[i]))
                correct++;
            else {
                String word = words[i];
                if(!numberPattern.matcher(word).matches()) {
                    Result res = translator.translate(word, "en", "zh-CHS");
                    if(res.isWord()) {
                        StringBuilder explains = new StringBuilder();
                        for(String exp : res.getBasic().getExplains()) {
                            explains.append(exp);
                        }
                        Words w = new Words(0, word,
                                explains.toString() , data.getMaterialId(),0);
                        if(inputWords[i].equals("[]") || inputWords[i].equals("()"))
                            unrecognized.add(w);
                        else
                            mismatch.add(w);
                    }
                }
            }
        }
        double accuracy = (double)correct / words.length;
        recordDao.addRecord(new ListenRecords(0,
                new Date(System.currentTimeMillis()),
                data.getStart_at(),
                data.getEnd_at(),
                data.getMaterialId(),
                accuracy));
        return new CheckinResp(accuracy, unrecognized, mismatch);
    }

    @Override
    public List<ListenRecords> getListenRecords(int pageSize, int pageNum) {
        Map<String, Object> page = new HashMap<>();
        //默认一页20条数据
        if(pageSize == -1 || pageNum == -1) {
            pageNum = 1;
            pageSize = 20;
        }
        page.put("offset", pageSize * (pageNum - 1));
        page.put("pageSize", pageSize);
        return recordDao.getRecords(page);
    }

    private String numberProcess(String text) {
        StringBuilder builder = new StringBuilder(text);
        for(int i = 0; i < builder.length(); i++) {
            if(builder.charAt(i) == '.'
                    && isNumber(builder.charAt(i - 1))
                    && isNumber(builder.charAt(i + 1))) {
                builder.replace(i, i + 1, "p");
            }
        }
        return builder.toString();
    }

    private boolean isNumber(char c) {
        return (c >= 48 && c <= 57);
    }
}
