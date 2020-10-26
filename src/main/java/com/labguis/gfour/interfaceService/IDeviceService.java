package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.MigratedDevice;
import java.util.List;
import java.util.Optional;


public interface IDeviceService {
    public List<MigratedDevice>listar();
    public Optional<MigratedDevice>listarId(int id);
    public int save(MigratedDevice u);
    public void delete(int id);
}
