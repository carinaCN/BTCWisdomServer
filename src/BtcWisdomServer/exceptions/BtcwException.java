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
public abstract class BtcwException extends Exception{

    public BtcwException() {
        super();
    }

    public BtcwException(String string) {
        super(string);
    }

    public BtcwException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public BtcwException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
