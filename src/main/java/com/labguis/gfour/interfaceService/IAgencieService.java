package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.Agencie;
import java.util.List;

public interface IAgencieService {
    public int save(Agencie a);
    public List<Agencie>listar();
    Agencie findByName(String name);
}
