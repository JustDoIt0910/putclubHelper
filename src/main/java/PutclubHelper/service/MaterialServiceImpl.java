package PutclubHelper.service;

import PutclubHelper.dao.MaterialsMapper;
import PutclubHelper.pojo.Materials;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService{

    private MaterialsMapper mapper;

    @Override
    public void save(Materials m) {
        System.out.println(mapper);
    }
}
