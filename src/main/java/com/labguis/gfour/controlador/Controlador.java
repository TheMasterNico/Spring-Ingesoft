package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.modelo.Usuario;
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
        List<Usuario>usuarios =  service.listar();
        model.addAttribute("usuarios", usuarios);
        return "index";
    }
    @GetMapping("/listar/{id}")
    public String listarId(@PathVariable int id,Model model) {
        model.addAttribute("persona", service.listarId(id));
        return "form";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("persona", new Usuario());
        return "form";
    }

    @PostMapping("/save")
    public String save(@Validated Usuario p,Model model) {
            int id=service.save(p);
            if(id==0) {
                    return "form";
            }
            return "redirect:/listar";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id,Model model) {
            service.delete(id);
            return "redirect:/listar";
    }
}
