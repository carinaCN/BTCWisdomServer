/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.model.classes.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Navia
 */
public class BtcwServerInitializer implements HttpApiInitializer{
    
    private byte[] jsonSerialize(Object o){
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writeValueAsBytes(o);
        }catch(IOException ex){
            return ("{\"error\": \""+ex.getClass().getName() + " - " + ex.getMessage()+"\"}").getBytes();
        }
    }

    @Override
    public HttpApiServer initilize(HttpApiServer srv) {
        srv.setDefaultHeader("Content-type", "application/json; charset=utf-8");
        
        srv.setHandler("usuario", "GET", (he, params) -> {
            Usuario u = new Usuario("john", "johnm@yopmail.com", "123456");
            return jsonSerialize(u);
        });
        
        srv.setHandler("moneda", "GET", (he, params) -> {
            return "This will show info for coins.".getBytes();
        });
        
        return srv;
    }
    
}
