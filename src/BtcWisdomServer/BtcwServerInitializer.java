/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.model.DAO.MonedaDAO;
import BtcWisdomServer.model.DAO.base.AbstractDAO;
import BtcWisdomServer.model.classes.*;
import BtcWisdomServer.utils.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Navia
 */
public class BtcwServerInitializer implements HttpApiInitializer{

    public BtcwServerInitializer() {
    }

    @Override
    public HttpApiServer initialize(HttpApiServer srv) {
        srv.setDefaultHeader("Content-type", "application/json; charset=utf-8");
        
        srv.setHandler("usuario", "GET", (he, params) -> {
            Usuario u = new Usuario("john", "johnm@yopmail.com", "123456");
            return JSON.serialize(u);
        });
        
        srv.setHandler("usuario/echo", "POST", (he, params) -> {
            Usuario u = JSON.deserialize(he.getRequestBody(), Usuario.class);
            System.out.println(u.getNombre() + " - " + u.getCorreo());
            return JSON.serialize(u);
        });
        
        
        //Métodos de uso para Moneda----------------------
        srv.setHandler("moneda", "GET", (he, params) -> {
            AbstractDAO<Moneda> dao = new MonedaDAO();
            if(params.length == 0){
                List<Moneda> monedas = dao.readAll();
                return JSON.serialize(monedas);
            }else if(params.length == 1){
                Moneda moneda = dao.read(params[0]);
                return JSON.serialize(moneda);
            }
            return JSON.serialize("Invalid url");
        });
        
        srv.setHandler("moneda", "POST", (he, params) -> {
            AbstractDAO<Moneda> dao = new MonedaDAO();
            Moneda m = JSON.deserialize(he.getRequestBody(), Moneda.class);
            boolean insertado = dao.create(m);
            return JSON.serialize(insertado ? m.getCodigo() : false);
        });
        
        srv.setHandler("moneda", "PUT", (he, params) -> {
            AbstractDAO<Moneda> dao = new MonedaDAO();
            Moneda m = JSON.deserialize(he.getRequestBody(), Moneda.class);
            boolean actualizado = dao.update(m);
            return JSON.serialize(actualizado);
        });
        //-----------------------------------------
        
        return srv;
    }
    
}
