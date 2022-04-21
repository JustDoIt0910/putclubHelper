package PutclubHelper.dao;

import PutclubHelper.pojo.Materials;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("materialsMapper")
public interface MaterialsMapper {
    Materials getMaterialById(@Param("id") int id);
    int addMaterial(@Param(("material")) Materials m);
}
