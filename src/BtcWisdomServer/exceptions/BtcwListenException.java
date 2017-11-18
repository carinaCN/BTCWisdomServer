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
public class BtcwListenException extends BtcwException{

    private static final String MESSAGE = "Unable to listen connection.";
    
    public BtcwListenException() {
        super(MESSAGE);
    }

    public BtcwListenException(Throwable thrwbl) {
        super(MESSAGE, thrwbl);
    }
    
}
