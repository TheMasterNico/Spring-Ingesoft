package com.labguis.gfour.service;

import com.labguis.gfour.interfaceService.IAgencieService;
import com.labguis.gfour.repository.IAgencie;
import com.labguis.gfour.modelo.Agencie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nicolas Castillo
 */
@Service
public class AgencieService implements IAgencieService{
    @Autowired
    private IAgencie data;
    
    @Override
    public int save(Agencie a) {
        int res = 0;
        Agencie agencie = data.save(a);
        if(!agencie.equals(null)) res = 1;
        return res;
    }

    @Override
    public Agencie findByName(String name) {
        return data.findByName(name);
    }

}
