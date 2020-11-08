     package com.labguis.gfour.repository;

import com.labguis.gfour.modelo.Agencie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgencie extends CrudRepository<Agencie, Integer> {
    Agencie findByName(String name);
}
