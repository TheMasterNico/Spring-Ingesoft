package com.labguis.gfour.service;

import com.labguis.gfour.interfaceService.ITypeDeviceService;
import com.labguis.gfour.interfaces.ITypeDevice;
import com.labguis.gfour.modelo.TypeDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nicolas Castillo
 */
@Service
public class TypeDeviceService implements ITypeDeviceService{

    @Autowired
    private ITypeDevice data;
    @Override
    public int save(TypeDevice td) {
        int res = 0;
        TypeDevice type = data.save(td);
        if(!type.equals(null)) res = 1;
        return res;
    }

    @Override
    public TypeDevice findByName(String name) {
        return data.findByName(name);
    }

}
