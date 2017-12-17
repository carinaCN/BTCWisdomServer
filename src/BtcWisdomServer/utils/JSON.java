/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.utils;

import BtcWisdomServer.BtcwServerInitializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carina
 */
public class JSON {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static byte[] serialize(Object o){
        try{
            return mapper.writeValueAsBytes(o);
        }catch(JsonProcessingException ex){
            return ("{\"error\": \""+ex.getClass().getName() + " - " + ex.getMessage()+"\"}").getBytes();
        }
    }
    
    public static <T> T deserialize(InputStream is, Class<T> classObj){
        try {
            return mapper.readValue(is, classObj);
        } catch (IOException ex) {
            Logger.getLogger(BtcwServerInitializer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
