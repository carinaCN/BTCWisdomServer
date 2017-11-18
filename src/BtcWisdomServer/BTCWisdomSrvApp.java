/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.exceptions.BtcwException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Navia
 */
public class BTCWisdomSrvApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Endpoint.publish("http://localhost:8080/ws/TestSrv", new TestSrv());
        try {
            HttpApiServer srv = new HttpApiServer();
            srv = new BtcwServerInitializer().initilize(srv);
            srv.start();
        } catch (BtcwException ex) {
            Logger.getLogger(BTCWisdomSrvApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
