/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.DAO.base;

/**
 *
 * @author Navia
 */
public interface Property<T, R> {
    
    public R getProperty(T obj);
    
    public void setProperty(T obj, R value);
    
}
