package com.labguis.gfour.repository;

import com.labguis.gfour.modelo.WhiteList;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Nicolas Castillo
 */
@Repository
public interface WhiteListRepository extends CrudRepository<WhiteList, Integer> {
    WhiteList findByEmail(String email);
}
