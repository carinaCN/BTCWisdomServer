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
import java.sql.ResultSet;
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
    
    public T read(Object id, T newObj){
        String select = null;
        Connection c = ConnectionPool.getConnection();
        try{
            Set<String> fields = this.properties.keySet();
            select = "SELECT "+String.join(", ", fields);
            select += " FROM "+this.getTableName();
            select += " WHERE "+this.getIdField()+" = ?";
            
            PreparedStatement ps = c.prepareStatement(select);
            this.setParameter(ps, 1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                this.setIdValue(newObj, id);
                for(String field : fields){
                    Object value = rs.getObject(field);
                    Property<T, Object> prop = this.properties.get(field);
                    prop.setProperty(newObj, value);
                }
            }
        } catch (SQLException ex) {
            throw new BtcwDaoException(select, ex);
        }finally{
            ConnectionPool.releaseConnection(c);
        }
        return newObj;
    }
    
    public void update(T obj){
        Connection c = ConnectionPool.getConnection();
        String update = null;
        try {
            String tblName = this.getTableName();
            Set<String> keys = this.properties.keySet();
            
            update = "UPDATE "+tblName+" SET ";
            
            String coma = "";
            for(String field : keys){
                update += coma+field+" = ?";
                if(coma.isEmpty()) coma = ",";
            }
            update += " WHERE "+this.getIdField()+" = ?";
            
            PreparedStatement ps = c.prepareStatement(update);
            int index = 1;
            for(String field : keys){
                Property<T, Object> p = this.properties.get(field);
                this.setParameter(ps, index++, p.getProperty(obj));
            }
            this.setParameter(ps, index++, this.getIdValue(obj));
            
            ps.execute();
        } catch (SQLException ex) {
            throw new BtcwDaoException(update, ex);
        }finally{
            ConnectionPool.releaseConnection(c);
        }
    }
    
    protected abstract String getTableName();
    
    protected abstract String getIdField();
    
    protected abstract Object getIdValue(T obj);
    
    protected abstract void setIdValue(T obj, Object value);
    
}
