/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.model.classes.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Navia
 */
public class BtcwServerInitializer implements HttpApiInitializer{
    
    private ObjectMapper mapper;

    public BtcwServerInitializer() {
        mapper = new ObjectMapper();
    }
    
    private byte[] jsonSerialize(Object o){
        try{
            return this.mapper.writeValueAsBytes(o);
        }catch(JsonProcessingException ex){
            return ("{\"error\": \""+ex.getClass().getName() + " - " + ex.getMessage()+"\"}").getBytes();
        }
    }
    
    private <T> T jsonDeserialize(InputStream is, Class<T> classObj){
        try {
            return this.mapper.readValue(is, classObj);
        } catch (IOException ex) {
            Logger.getLogger(BtcwServerInitializer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public HttpApiServer initilize(HttpApiServer srv) {
        srv.setDefaultHeader("Content-type", "application/json; charset=utf-8");
        
        srv.setHandler("usuario", "GET", (he, params) -> {
            Usuario u = new Usuario("john", "johnm@yopmail.com", "123456");
            return jsonSerialize(u);
        });
        
        srv.setHandler("usuario/echo", "POST", (he, params) -> {
            Usuario u = jsonDeserialize(he.getRequestBody(), Usuario.class);
            System.out.println(u.getNombre() + " - " + u.getCorreo());
            return jsonSerialize(u);
        });
        
        srv.setHandler("moneda", "GET", (he, params) -> {
            return "[\"This will show info for coins.\"]".getBytes();
        });
        
        return srv;
    }
    
}
