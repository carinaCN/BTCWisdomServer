/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Navia
 */
public class Usuario {
    
    private int id;
    private String nombre;
    private String correo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;
    private double saldo;
    @JsonIgnore
    private List<Usuario> seguidos;
    
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
    
}
