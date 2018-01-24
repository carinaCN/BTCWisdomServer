/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Navia
 */
public class Usuario {
    
    private int id;
    private String nombre;
    private String correo;
    @JsonIgnore
    private String contrasena;
    private double saldo;
    @JsonIgnore
    private List<Usuario> seguidos;
    private Map<Moneda, Double> monedas;
    
    public Usuario(){
        
    }
    
    public Usuario(String nombre, String correo, String contrasena){
        this(nombre, correo, contrasena, 0d);
    }
    
    public Usuario(String nombre, String correo, String contrasena, double saldo){
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.saldo = saldo;
        this.seguidos = new LinkedList<>();
        this.monedas = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public void seguir (Usuario u){
        this.seguidos.add(u);
    }
    
    public void dejarDeSeguir (Usuario u){
        this.seguidos.remove(u);
    }
    
    public void dejarDeSeguir (int id){
        Usuario encontrado = null;
        //Buscamos usuario para dejar de seguir en la lista.
        for (Usuario seguido : this.seguidos) {
            if(seguido.getId() == id){
                encontrado = seguido;
                break;
            }
        }
        //Si hay encontrado, pues lo borramos de la lista.
        if(encontrado != null){
            this.seguidos.remove(encontrado);
        }
    }
    
    public void setMoneda(Moneda m, double importe){
        this.monedas.put(m, importe);
    }
    
    public void addToMoneda(Moneda m, double importe){
        Double importeAntiguo = this.monedas.get(m);
        if(importeAntiguo == null){
            importeAntiguo = 0d;
        }
        this.monedas.put(m, importeAntiguo+importe);
    }
    
    public void deleteMoneda(Moneda m){
        this.monedas.remove(m);
    }
    
    public void deleteMoneda(String codigo){
        //Recorremos monedas del usuario
        Moneda found = null;
        Set<Moneda> monedasUsuario = this.monedas.keySet();
        Iterator<Moneda> it = monedasUsuario.iterator();
        
        //Comprobamos cual tiene el mismo codigo
        while(it.hasNext()){
            Moneda m = it.next();
            if(m.getCodigo().equals(codigo)){
                found = m;
                break;
            }
        }
        
        //Si la hemos encontrado, la borramos
        if(found != null){
            this.monedas.remove(found);
        }
    }
    
}
