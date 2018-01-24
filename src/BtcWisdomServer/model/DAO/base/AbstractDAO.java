/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.DAO.base;

import BtcWisdomServer.exceptions.BtcwDaoException;
import BtcWisdomServer.model.connection.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Navia
 */
public abstract class AbstractDAO<T> {
    
    private Map<String, Property> properties = new HashMap<>();
    
    protected void setProperty(String name, Property prop){
        this.properties.put(name, prop);
    }
    
    protected void setParameter(PreparedStatement ps, int index, Object value) throws SQLException{
        if(value instanceof Integer){
            ps.setInt(index, ((Integer) value));
        }else if(value instanceof Double){
            ps.setDouble(index, (Double) value);
        }else if(value instanceof String){
            ps.setString(index, (String) value);
        }else{
            ps.setObject(index, value);
        }
    }
    
    public void create(T obj){
        Connection c = ConnectionPool.getConnection();
        String insert = null;
        try {
            String tblName = this.getTableName();
            Set<String> keys = this.properties.keySet();
            
            insert = "INSERT INTO "+tblName;
            
            insert += " ("+String.join(", ", keys)+") VALUES (";
            
            for(int i = 0; i < keys.size(); i++){
                insert += i < keys.size()-1 ? "?, " : "?";
            }
            insert += ")";
            
            PreparedStatement ps = c.prepareStatement(insert);
            int index = 1;
            for(String field : keys){
                Property<T, Object> p = this.properties.get(field);
                this.setParameter(ps, index++, p.getProperty(obj));
            }
            ps.execute();
        } catch (SQLException ex) {
            throw new BtcwDaoException(insert, ex);
        }finally{
            ConnectionPool.releaseConnection(c);
        }
    }
    
    protected abstract String getTableName();
    
    protected abstract String getIdField();
    
    protected abstract Object getIdValue(T obj);
    
}
