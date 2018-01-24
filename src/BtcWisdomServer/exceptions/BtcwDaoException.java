/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.exceptions;

/**
 *
 * @author Navia
 */
public class BtcwDaoException extends BtcwException{
    
    private static final String MESSAGE = "Exception during DAO execution with query:\n";
    
    public BtcwDaoException(String sql, Throwable thr){
        super(MESSAGE+sql+"\n", thr);
    }
    
}
