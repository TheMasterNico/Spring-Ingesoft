package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.Location;

/**
 *
 * @author Nicolas Castillo
 */
public interface ILocationService {
    public int save(Location l);
    Location findByName(String name);
}
