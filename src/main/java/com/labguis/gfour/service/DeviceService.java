package com.labguis.gfour.service;

import com.labguis.gfour.interfaceService.IDeviceService;
import com.labguis.gfour.repository.IDevice;
import com.labguis.gfour.modelo.MigratedDevice;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService implements IDeviceService {
   
    @Autowired
    private IDevice data;

    @Override
    public List<MigratedDevice> listar() {
        return (List<MigratedDevice>)data.findAll();
    }

    @Override
    public Optional<MigratedDevice> listarId(int id) {
        return data.findById(id);
    }

    @Override
    public int save(MigratedDevice u) {
        int res = 0;
        MigratedDevice md = data.save(u);
        if(!md.equals(null)) res = 1;
        return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }

    @Override
    public MigratedDevice findByInvPlate(String inv_plate) {
        return data.findByInvPlate(inv_plate);
    }
    
    
}
