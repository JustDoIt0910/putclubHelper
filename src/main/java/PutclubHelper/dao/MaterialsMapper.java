package PutclubHelper.dao;

import PutclubHelper.pojo.Materials;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("materialsMapper")
public interface MaterialsMapper {
    Materials getMaterialById(@Param("id") String id);
    int addMaterial(@Param(("material")) Materials m);
    int updateMaterialText(@Param("id") String id, @Param("text") String text);
}
