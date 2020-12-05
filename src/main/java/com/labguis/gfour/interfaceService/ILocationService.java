package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.Location;
import java.util.List;

/**
 *
 * @author Nicolas Castillo
 */
public interface ILocationService {
    public int save(Location l);
    Location findByName(String name);
    public List<Location>listar();

    public Location findByID(String value);

    public Location findByNumberID(String value);
}
