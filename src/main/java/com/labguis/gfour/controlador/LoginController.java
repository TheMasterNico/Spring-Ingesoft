/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.modelo.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/ingreso")
    public String login(@Validated User user, Model model, HttpServletResponse response) {
        int can_login = checkLogin(user);
        switch (can_login) {
            case 1:
                model.addAttribute("error", "No se encontro el usuario");
                break;
            case 2:
                model.addAttribute("error", "Datos incorrectos");
                break;
            default:
                Cookie cookie = new Cookie("Hash-1", service.hashPassword(user.getEmail()));
                cookie.setMaxAge(1 * 24 * 60 * 60); // expire in 1 day
                Cookie cookie2 = new Cookie("Hash-2", Long.toString(service.findByEmail(user.getEmail()).getId()));
                cookie2.setMaxAge(1 * 24 * 60 * 60); // expire in 1 day

                response.addCookie(cookie);
                response.addCookie(cookie2);
                //model.addAttribute("info", "Datos correctos. Bienvenido!");
                return "redirect:/equipos";
        }
        return "login";
    }

    public int checkLogin(User user) {
        User user_to_check = service.findByEmail(user.getEmail());
        if (user_to_check == null) {
            return 1;
        }
        if (!user_to_check.getPassword().equals(service.hashPassword(user.getPassword()))) {
            return 2;
        }
        return 0; // Al ok
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        if (service.isUserLogged(request)) {
            return "redirect:/equipos";
        }
        model.addAttribute("usuario", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (service.isUserLogged(request)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0); // delete the cookies
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/login";
    }
}
