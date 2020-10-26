package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IDeviceService;
import com.labguis.gfour.modelo.MigratedDevice;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nicolas Castillo
 */
@Controller
@RequestMapping
public class DeviceController {
    
    @Autowired
    private IDeviceService ids;
    
    @GetMapping("/equipos")
    public String equipos(Model model) {
        List<MigratedDevice>devices =  ids.listar();
        model.addAttribute("datos", devices);
        return "equipos";
    }
    
}
