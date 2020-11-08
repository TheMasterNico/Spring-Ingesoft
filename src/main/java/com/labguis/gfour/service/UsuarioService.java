/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.service;

import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.modelo.User;
import com.labguis.gfour.modelo.WhiteList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.labguis.gfour.repository.UserRepository;
import com.labguis.gfour.repository.WhiteListRepository;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Makot
 */
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UserRepository data;
    @Autowired
    private WhiteListRepository wlr;

    @Override
    public List<User> listar() {
        return (List<User>) data.findAll();
    }

    @Override
    public int save(User u) {
        int res = 0;
        User usuario = data.save(u);
        if (!usuario.equals(null)) {
            res = 1;
        }
        return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }

    @Override
    public User findByName(String nombre) {
        return data.findByName(nombre);
    }

    @Override
    public User findByEmail(String email) {
        return data.findByEmail(email);
    }

    @Override
    public String hashPassword(String password) {
        try {
            String salt = "IngeSoft";
            int iterations = 10000;
            int keyLength = 256;
            char[] passwordChars = password.toCharArray();
            byte[] saltBytes = salt.getBytes();

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return Hex.encodeHexString(res);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isUserLogged(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String mail = "";
            int id = 0;
            for (Cookie cookie : cookies) {
                if ("Hash-2".equals(cookie.getName())) {
                    id = Integer.valueOf(cookie.getValue());
                } else if ("Hash-1".equals(cookie.getName())) {
                    mail = cookie.getValue();
                }
            }
            Optional<User> user = data.findById(id);
            if (user.get() != null) { // the user exist
                if (this.hashPassword(user.get().getEmail()).equals(mail)) { // if all cookies are ok
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkWhiteList(String email) {
        WhiteList wl = wlr.findByEmail(email);
        return wl != null;
    }

    @Override
    public boolean isUserAdmin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String mail = "";
            int id = 0;
            for (Cookie cookie : cookies) {
                if ("Hash-2".equals(cookie.getName())) {
                    id = Integer.valueOf(cookie.getValue());
                } else if ("Hash-1".equals(cookie.getName())) {
                    mail = cookie.getValue();
                }
            }
            Optional<User> user = data.findById(id);
            if (user.get() != null) { // the user exist
                if (this.hashPassword(user.get().getEmail()).equals(mail)) { // if all cookies are ok
                    if (user.get().getEmail().equals("email@testing.com")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public User findByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            int id = 0;
            for (Cookie cookie : cookies) {
                if ("Hash-2".equals(cookie.getName())) {
                    id = Integer.valueOf(cookie.getValue());
                }
            }
            Optional<User> user = data.findById(id);
            return user.get();
        }
        return null;
    }

}
