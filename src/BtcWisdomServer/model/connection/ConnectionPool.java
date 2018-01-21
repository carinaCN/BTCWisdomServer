/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.connection;

import BtcWisdomServer.BTCWisdomSrvApp;
import BtcWisdomServer.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carina
 */
public class ConnectionPool {
    
    private static LinkedList<Connection> connections = new LinkedList<>();
    private static final int MAX_CONNECTIONS = 10;
    private static int ACTIVE_CONNECTIONS = 0;
    
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static synchronized Connection getConnection(){
        if(connections.size() > 0){
            return connections.pop();
        }
        if(ACTIVE_CONNECTIONS < MAX_CONNECTIONS){
            Connection c = null;
            try {
                Config conf = Config.getInstance();
                String address = conf.getValue("BD.address", "localhost");
                String port = conf.getValue("BD.port", "3306");
                String user = conf.getValue("BD.user", "root");
                String pass = conf.getValue("BD.password", "root");
                String database = conf.getValue("BD.database", "btc_wisdom");
                if(BTCWisdomSrvApp.DEBUG){
                    System.out.println("New DB connection to: "+address+":"+port+"/"+database);
                }
                c = DriverManager.getConnection("jdbc:mysql://"+address+":"+port+"/"+database, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error on creating connection!");
            }
            ACTIVE_CONNECTIONS++;
            return c;
        }
        
        while(connections.isEmpty()){
            try {
                ConnectionPool.class.wait();
            } catch (InterruptedException ex) {
                //Pensar que hace si se interrumpe la espera.
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Interrupted wait for connection!");
            }
        }
        return connections.pop();
        
    }
    
    public static synchronized void releaseConnection(Connection c){
        connections.push(c);
        ConnectionPool.class.notify();
    }
    
}
