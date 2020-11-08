package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.modelo.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Makot
 */
@Controller
@RequestMapping
public class Controlador {
    
    @Autowired
    private IUsuarioService service;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        List<User>usuarios =  service.listar();
        model.addAttribute("usuarios", usuarios);
        return "index";
    }
}
