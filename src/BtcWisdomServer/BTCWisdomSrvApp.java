/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.exceptions.BtcwException;
import BtcWisdomServer.model.DAO.MonedaDAO;
import BtcWisdomServer.model.classes.Moneda;
import BtcWisdomServer.utils.Config;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Navia
 */
public class BTCWisdomSrvApp {

    public static boolean DEBUG;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Set debug flag
            DEBUG = args.length != 0 && args[0].equals("--debug");
            
            //Get config data
            Config conf = Config.getInstance();
            String ip = conf.getValue("server.bind-address", "localhost");
            int port = Integer.parseInt(conf.getValue("server.bind-port", "8080"));
            String entryPoint = conf.getValue("server.entry-point", "/api");

            //Show info on debug mode
            if(DEBUG){
                System.out.println("Server address: "+ip);
                System.out.println("Server port: "+port);
                System.out.println("Server entry point: "+entryPoint);
            }
            
            //Initate server
            HttpApiServer srv = new HttpApiServer(ip, port, entryPoint);
            srv = new BtcwServerInitializer().initilize(srv);
            srv.start();
            
            /*Moneda m = new Moneda("EUR", "Euro", "€", 1);
            MonedaDAO dao = new MonedaDAO();
            dao.create(m);*/
            /*Moneda m = new Moneda();
            MonedaDAO dao = new MonedaDAO();
            dao.read("EUR", m);
            System.out.println(m.getCodigo());
            System.out.println(m.getNombre());
            System.out.println(m.getSimbolo());
            System.out.println(m.getValor());*/
        } catch (BtcwException ex) {
            Logger.getLogger(BTCWisdomSrvApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
