package com.labguis.gfour.service;

import com.labguis.gfour.interfaceService.InterfaceWhiteList;
import com.labguis.gfour.modelo.WhiteList;
import com.labguis.gfour.repository.WhiteListRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nicolas Castillo
 */
@Service
public class WhiteListService implements InterfaceWhiteList {
    @Autowired
    private WhiteListRepository data;

    @Override
    public WhiteList findByEmail(String email) {
        return data.findByEmail(email);
    }

    @Override
    public List<WhiteList> listar() {
        return (List<WhiteList>)data.findAll();
    }

    @Override
    public int save(WhiteList w) {
        int res = 0;
        WhiteList wl = data.save(w);
        if (!wl.equals(null)) {
            res = 1;
        }
        return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }

   
}
