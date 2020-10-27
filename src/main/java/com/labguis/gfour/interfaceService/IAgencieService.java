package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.Agencie;

public interface IAgencieService {
    public int save(Agencie a);
    Agencie findByName(String name);
}
