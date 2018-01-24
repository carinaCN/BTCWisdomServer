/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.DAO;

import BtcWisdomServer.model.DAO.base.AbstractDAO;
import BtcWisdomServer.model.DAO.base.Property;
import BtcWisdomServer.model.classes.Usuario;

/**
 *
 * @author carina
 */
public class UsuarioDAO extends AbstractDAO<Usuario>{

    public UsuarioDAO(){
        this.setProperty("nombre", new Property<Usuario, String>() {

            @Override
            public String getProperty(Usuario obj) {
                return obj.getNombre();
            }

            @Override
            public void setProperty(Usuario obj, String value) {
                obj.setNombre(value);
            }
        });
        
        this.setProperty("correo", new Property<Usuario, String>() {

            @Override
            public String getProperty(Usuario obj) {
                return obj.getCorreo();
            }

            @Override
            public void setProperty(Usuario obj, String value) {
                obj.setCorreo(value);
            }
        });
        this.setProperty("contrasena", new Property<Usuario, String>() {

            @Override
            public String getProperty(Usuario obj) {
                return obj.getContrasena();
            }

            @Override
            public void setProperty(Usuario obj, String value) {
                obj.setContrasena(value);
            }
        });
        this.setProperty("saldo", new Property<Usuario, Double>() {

            @Override
            public Double getProperty(Usuario obj) {
                return obj.getSaldo();
            }

            @Override
            public void setProperty(Usuario obj, Double value) {
                obj.setSaldo(value);
            }
        });
    }
    
    @Override
    protected String getTableName() {
        return "usuario";
    }

    @Override
    protected String getIdField() {
        return "id";
    }

    @Override
    protected Object getIdValue(Usuario obj) {
        return obj.getId();
    }

    @Override
    protected void setIdValue(Usuario obj, Object value) {
        obj.setId((Long)value);
    }

    @Override
    protected Usuario getNewInstace() {
        return new Usuario();
    }
    
}
