package com.labguis.gfour.repository;

import com.labguis.gfour.modelo.TypeDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nicolas Castillo
 */
@Repository
public interface ITypeDevice extends CrudRepository<TypeDevice, Integer> {
    TypeDevice findByName(String name);  
}
