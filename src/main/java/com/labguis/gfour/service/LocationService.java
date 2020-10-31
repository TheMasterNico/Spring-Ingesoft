package com.labguis.gfour.service;

import com.labguis.gfour.interfaceService.ILocationService;
import com.labguis.gfour.repository.ILocation;
import com.labguis.gfour.modelo.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nicolas Castillo
 */
@Service
public class LocationService implements ILocationService {
    @Autowired
    private ILocation data;

    @Override
    public int save(Location l) {
        int res = 0;
        Location loc = data.save(l);
        if(!loc.equals(null)) res = 1;
        return res;
    }

    @Override
    public Location findByName(String name) {
       return data.findByName(name);
    }
    
    
}
