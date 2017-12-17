/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import BtcWisdomServer.exceptions.BtcwListenException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import BtcWisdomServer.utils.Pair;

/**
 *
 * @author Navia
 */
public class HttpApiServer implements HttpHandler{
    
    private HttpServer srv;
    private HashMap<Pair<String, String>, RequestHandler> handlers;
    private String entryPoint;
    private HashMap<String, List<String>> defaultHeaders;
    
    //Configuración por defecto
    public final static int DEFAULT_PORT = 8080;
    public final static String DEFAULT_BINDING_IP = "127.0.0.1";
    public final static String DEFAULT_ENTRY_POINT = "/api";

    public HttpApiServer() throws BtcwListenException{
        this(DEFAULT_BINDING_IP, DEFAULT_PORT, DEFAULT_ENTRY_POINT);
    }
    
    public HttpApiServer(String ip, int port) throws BtcwListenException{
        this(ip, port, DEFAULT_ENTRY_POINT);
    }
    
    public HttpApiServer(String ip, int port, String entryPoint) throws BtcwListenException{
        try {
            if(entryPoint.endsWith("/")){
                entryPoint = entryPoint.substring(0, entryPoint.length()-1);
            }
            if(!entryPoint.startsWith("/")){
                entryPoint = "/"+entryPoint;
            }
            InetSocketAddress address = new InetSocketAddress(ip, port);
            this.srv = HttpServer.create(address, 50);
            this.srv.createContext(entryPoint, this);
            this.entryPoint = entryPoint;
            this.handlers = new HashMap<>();
            this.defaultHeaders = new HashMap<>();
        } catch (IOException ex) {
            Logger.getLogger(HttpApiServer.class.getName()).log(Level.SEVERE, null, ex);
            throw new BtcwListenException(ex);
        }
    }
    
    public void start(){
        this.srv.start();
    }
    
    public void setHandler(String path, String method, RequestHandler handler){
        if(!path.startsWith("/")){
            path = "/"+path;
        }
        if(path.endsWith("/")){
            path = path.substring(0, path.length()-1);
        }
        this.srv.createContext(this.entryPoint+path, this);
        this.handlers.put(new Pair<>(this.entryPoint+path, method), handler);
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        this.setUpDefaultResposneHeaders(he);
        String path = he.getHttpContext().getPath();
        String method = he.getRequestMethod();
        RequestHandler handler = this.handlers.get(new Pair<>(path, method));
        byte[] response = null;
        if(handler != null){
            String paramsString = he.getRequestURI().getPath().replaceAll(path+"/", "");
            String[] params = paramsString.equals("") ? new String[0] : paramsString.split("/");
            response = handler.handle(he, params);
            he.sendResponseHeaders(200, response.length);
        }else{
            response = "404 - Not found".getBytes();
            he.sendResponseHeaders(404, response.length);
        }
        OutputStream os = he.getResponseBody();
        os.write(response);
        os.flush();
        os.close();
        he.close();
    }
    
    private void setUpDefaultResposneHeaders(HttpExchange he){
        Headers h = he.getResponseHeaders();
        Iterator<String> keyIterator = defaultHeaders.keySet().iterator();
        String key = null;
        while(keyIterator.hasNext()){
            key = keyIterator.next();
            List<String> values = this.defaultHeaders.get(key);
            for (String value : values) {
                h.add(key, value);
            }
        }
    }
    
    public void addDefaultHeader(String key, String value){
        List<String> values = this.defaultHeaders.get(key);
        values.add(value);
    }
    
    public void setDefaultHeader(String key, String value){
        List<String> values = new LinkedList<>();
        values.add(value);
        this.defaultHeaders.put(key, values);
    }
    
    public interface RequestHandler{
        public byte[] handle(HttpExchange he, String[] params);
    }
    
}
