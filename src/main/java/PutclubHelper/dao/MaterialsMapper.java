package PutclubHelper.dao;

import PutclubHelper.pojo.Materials;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("materialsMapper")
public interface MaterialsMapper {
    Materials getMaterialById(@Param("id") String id);
    void addMaterial(@Param(("material")) Materials m);
    void updateMaterialText(@Param("id") String id, @Param("text") String text);
}
