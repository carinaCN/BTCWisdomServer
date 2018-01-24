/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.DAO;

import BtcWisdomServer.model.DAO.base.AbstractDAO;
import BtcWisdomServer.model.DAO.base.Property;
import BtcWisdomServer.model.classes.Moneda;

/**
 *
 * @author Navia
 */
public class MonedaDAO extends AbstractDAO<Moneda>{
    
    public MonedaDAO(){
        this.setProperty("codigo", new Property<Moneda, String>() {
            @Override
            public String getProperty(Moneda obj) {
                return obj.getCodigo();
            }

            @Override
            public void setProperty(Moneda obj, String value) {
                obj.setCodigo(value);
            }
        });
        
        this.setProperty("nombre", new Property<Moneda, String>() {
            @Override
            public String getProperty(Moneda obj) {
                return obj.getNombre();
            }

            @Override
            public void setProperty(Moneda obj, String value) {
                obj.setNombre(value);
            }
        });
        
        this.setProperty("simbolo", new Property<Moneda, String>() {
            @Override
            public String getProperty(Moneda obj) {
                return obj.getSimbolo();
            }

            @Override
            public void setProperty(Moneda obj, String value) {
                obj.setSimbolo(value);
            }
        });
        
        this.setProperty("valor", new Property<Moneda, Double>() {
            @Override
            public Double getProperty(Moneda obj) {
                return obj.getValor();
            }

            @Override
            public void setProperty(Moneda obj, Double value) {
                obj.setValor(value);
            }
        });
    }

    @Override
    protected String getTableName() {
        return "moneda";
    }

    @Override
    protected String getIdField() {
        return "codigo";
    }

    @Override
    protected Object getIdValue(Moneda obj) {
        return obj.getCodigo();
    }
    
}
