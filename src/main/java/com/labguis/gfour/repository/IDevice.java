package com.labguis.gfour.repository;

import com.labguis.gfour.modelo.MigratedDevice;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IDevice extends CrudRepository<MigratedDevice, Integer> {
    MigratedDevice findByInvPlate(String inv_plate);
    MigratedDevice findByStandarKey(String standarKey);
    List<MigratedDevice> findAllByOrderByUpdateTimeDesc();
    
    @Query(value = ":sql", nativeQuery = true)
    List<MigratedDevice>filter(@Param("sql")String sql);
}
