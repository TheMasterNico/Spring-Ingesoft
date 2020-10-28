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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class RegisterController {
    
    @Autowired
    private IUsuarioService service;
    
    @PostMapping("/registro")
    public String register(@Validated User user, @RequestParam("password_2") String pass2, Model model) {
        if(!checkName(user.getName())) {
            model.addAttribute("error", "Longitud del nombre incorrecta");
        }
        else if(!checkPass(user.getPassword())) {
            model.addAttribute("error", "Longitud de la contraseña incorrecta");
        }
        else if(!checkPasswords(user.getPassword(), pass2)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
        }
        else {
            int reg = service.save(user); // Save the user in DB      
            if(reg != 0) return "redirect:/login?status=Gracias por registrarse";
        }
        return "registro";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new User());
        return "registro";
    }
    
    public boolean checkPasswords(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
    
    public boolean checkName(String nombre) {
        return (nombre.length() > 1 && nombre.length() <= 8);
    }
    public boolean checkPass(String pass) {
        return (pass.length() > 3 && pass.length() < 6);
    }
    
}
