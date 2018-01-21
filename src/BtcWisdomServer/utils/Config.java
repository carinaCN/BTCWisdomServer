/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author carina
 */
public class Config {
    
    private static final String DEFAULT_FILE = "conf.ini";
    private static final HashMap<String, Config> CONFIGS = new HashMap<>();
    
    private final LinkedList<String> files;
    private final HashMap<String, String> options;
    
    public Config(){
        this.files = new LinkedList<>();
        this.options = new HashMap<>();
    }
    
    public static Config getInstance(){
        try {
            return Config.getInstance(DEFAULT_FILE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        //If file is not found, register an empty config as a default.
        Config c = new Config();
        Config.CONFIGS.put(DEFAULT_FILE, c);
        return c;
    }
    
    public static Config getInstance(String file) throws FileNotFoundException{
        Config c = Config.CONFIGS.get(file);
        if(c != null) return c;
        c = new Config();
        c.process(file);
        Config.CONFIGS.put(file, c);
        return c;
    }
    
    private void process(String filename) throws FileNotFoundException{
        this.files.addFirst(filename);
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        
        String block = "";
        
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            
            //Elimina el comentario
            int commentIndex = line.indexOf(';');
            if(commentIndex > 0){
                line = line.substring(0, line.indexOf(';'));
            }
            
            //Pasamos si está vacía
            line = line.trim();
            if(line.isEmpty()) continue;
            
            //Detecta un bloque nuevo
            if(line.startsWith("[")){
                int last = line.indexOf(']');
                block = line.substring(1, last)+".";
                continue;
            }
            
            if(!line.contains("=")) continue;
            
            //Recoge opción
            String[] option = line.split("=", 2);
            if(option.length < 2) continue;
            String key = option[0].trim();
            String val = option[1].trim();
            this.options.put(block+key, val);
        }
    }
    
    public String getValue(String key){
        return this.getValue(key, null);
    }
    
    public String getValue(String key, String defaultValue){
        String value = this.options.get(key);
        return value == null ? defaultValue : value;
    }
    
}
