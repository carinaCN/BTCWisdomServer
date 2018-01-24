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
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    
    public boolean create(T obj){
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
            
            if(BtcWisdomServer.BTCWisdomSrvApp.DEBUG){
                System.err.println("GOING TO INSERT: \n"+insert);
            }
            
            PreparedStatement ps = c.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for(String field : keys){
                Property<T, Object> p = this.properties.get(field);
                Object value = p.getProperty(obj);
                this.setParameter(ps, index++, value);
            }
            ps.executeUpdate();
            ResultSet resId = ps.getGeneratedKeys();
            if(resId.next()){
                Object id = resId.getObject(1);
                this.setIdValue(obj, id);
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }finally{
            ConnectionPool.releaseConnection(c);
        }
    }
    
    public T read(Object id){
        T newObj = null;
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
                newObj = this.getNewInstace();
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
    
    public List<T> readAll(){
        List<T> listaObj = new LinkedList<>();
        String select = null;
        Connection c = ConnectionPool.getConnection();
        try{
            Set<String> fields = this.properties.keySet();
            select = "SELECT "+this.getIdField()+", "+String.join(", ", fields);
            select += " FROM "+this.getTableName();
            
            PreparedStatement ps = c.prepareStatement(select);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                T newObj = this.getNewInstace();
                this.setIdValue(newObj, rs.getObject(this.getIdField()));
                for(String field : fields){
                    Object value = rs.getObject(field);
                    Property<T, Object> prop = this.properties.get(field);
                    prop.setProperty(newObj, value);
                }
                listaObj.add(newObj);
            }
        } catch (SQLException ex) {
            throw new BtcwDaoException(select, ex);
        }finally{
            ConnectionPool.releaseConnection(c);
        }
        return listaObj;
    }
    
    public boolean update(T obj){
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
            
            if(BtcWisdomServer.BTCWisdomSrvApp.DEBUG){
                System.err.println("GOING TO UPDATE: \n"+update);
            }
            
            PreparedStatement ps = c.prepareStatement(update);
            int index = 1;
            for(String field : keys){
                Property<T, Object> p = this.properties.get(field);
                this.setParameter(ps, index++, p.getProperty(obj));
            }
            this.setParameter(ps, index++, this.getIdValue(obj));
            
            ps.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }finally{
            ConnectionPool.releaseConnection(c);
        }
    }
    
    public boolean delete(T obj){
        Connection c = ConnectionPool.getConnection();
        String delete = null;
        try {
            String tblName = this.getTableName();
            Set<String> keys = this.properties.keySet();
            
            delete = "DELETE FROM "+tblName+" WHERE ";
            
            delete += this.getIdField()+" = ?";
            
            if(BtcWisdomServer.BTCWisdomSrvApp.DEBUG){
                System.err.println("GOING TO DELETE: \n"+delete);
            }
            
            PreparedStatement ps = c.prepareStatement(delete);
            this.setParameter(ps, 1, this.getIdValue(obj));
            
            ps.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }finally{
            ConnectionPool.releaseConnection(c);
        }
    }
    
    protected abstract String getTableName();
    
    protected abstract String getIdField();
    
    protected abstract Object getIdValue(T obj);
    
    protected abstract void setIdValue(T obj, Object value);
    
    protected abstract T getNewInstace();
    
}
