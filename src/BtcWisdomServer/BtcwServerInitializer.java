/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

/**
 *
 * @author Navia
 */
public class BtcwServerInitializer implements HttpApiInitializer{
    

    @Override
    public HttpApiServer initilize(HttpApiServer srv) {
        srv.setDefaultHeader("Content-type", "application/json; charset=utf-8");
        
        srv.setHandler("usuario", "GET", (he, params) -> {
            String id = params.length > 0 ? params[0] : null;
            String message = id != null ? "This will show the user info of user with id "+id :
                    "This will show the user info specified with ../usuario/{id}";
            return message.getBytes();
        });
        
        srv.setHandler("moneda", "GET", (he, params) -> {
            return "This will show info for coins.".getBytes();
        });
        
        return srv;
    }
    
}
