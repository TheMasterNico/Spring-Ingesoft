/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.modelo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class LoginController {
    
    @Autowired
    private IUsuarioService service;
    
    @PostMapping("/login")
    public String login(@Validated User user, Model model) {
        int can_login = checkLogin(user);
        switch (can_login) {
            case 1:
                model.addAttribute("error", "No se encontro el usuario");
                break;
            case 2:
                model.addAttribute("error", "Datos incorrectos");
                break;
            default:
                //model.addAttribute("info", "Datos correctos. Bienvenido!");
                return "redirect:/equipos";
        }        
        return "login";
    }
    
    public int checkLogin(User user) {
        User user_to_check = service.findByEmail(user.getEmail());
        if(user_to_check == null) return 1;
        if(!user_to_check.getPassword().equals(user.getPassword())) return 2;
        return 0; // Al ok
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new User());
        return "login";
    }
}
