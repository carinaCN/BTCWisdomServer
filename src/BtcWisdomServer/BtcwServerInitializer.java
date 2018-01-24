/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.model.DAO.MonedaDAO;
import BtcWisdomServer.model.DAO.UsuarioDAO;
import BtcWisdomServer.model.DAO.base.AbstractDAO;
import BtcWisdomServer.model.classes.*;
import BtcWisdomServer.utils.JSON;
import java.util.List;

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
            AbstractDAO<Usuario> dao = new UsuarioDAO();
            if(params.length == 0){
                List<Usuario> usuarios = dao.readAll();
                return JSON.serialize(usuarios);
            }else if(params.length == 1){
                Usuario usuario = dao.read(params[0]);
                return JSON.serialize(usuario);
            }
            return JSON.serialize("Invalid url");
        });
        
        srv.setHandler("usuario", "POST", (he, params) -> {
            AbstractDAO<Usuario> dao = new UsuarioDAO();
            Usuario u = JSON.deserialize(he.getRequestBody(), Usuario.class);
            dao.create(u);
            return JSON.serialize(u.getId());
        });
        
        srv.setHandler("usuario", "PUT", (he, params) -> {
            AbstractDAO<Usuario> dao = new UsuarioDAO();
            Usuario u = JSON.deserialize(he.getRequestBody(), Usuario.class);
            boolean actualizado = dao.update(u);
            return JSON.serialize(actualizado);
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
