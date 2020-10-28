package com.labguis.gfour.interfaces;

import com.labguis.gfour.modelo.MigratedDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDevice extends CrudRepository<MigratedDevice, Integer> {
    MigratedDevice findByInvPlate(String inv_plate);
}
