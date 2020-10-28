package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.TypeDevice;

/**
 *
 * @author Nicolas Castillo
 */
public interface ITypeDeviceService {
    public int save(TypeDevice td);
    TypeDevice findByName(String name);
}
