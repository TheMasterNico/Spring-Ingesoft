package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.WhiteList;
import java.util.List;

/**
 *
 * @author Nicolas Castillo
 */
public interface InterfaceWhiteList {
    WhiteList findByEmail(String email);
    public List<WhiteList>listar();
    public int save(WhiteList u);
    public void delete(int id);
}
