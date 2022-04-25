package PutclubHelper.service;

import PutclubHelper.dao.MaterialsMapper;
import PutclubHelper.pojo.Materials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService{

    @Autowired
    @Qualifier("materialsMapper")
    private MaterialsMapper mapper;

    @Override
    public void save(String id, String text) {
        mapper.updateMaterialText(id, text);
    }

    @Override
    public Materials getMaterialById(String id) {
        return mapper.getMaterialById(id);
    }

}
