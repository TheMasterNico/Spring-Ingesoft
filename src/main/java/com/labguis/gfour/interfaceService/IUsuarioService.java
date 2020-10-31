/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.User;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * DAO
 */
public interface IUsuarioService {
    public List<User>listar();
    public int save(User u);
    public void delete(int id);
    User findByName(String nombre);
    User findByEmail(String email);
    String hashPassword(String password);
    boolean isUserLogged(HttpServletRequest request);
}
