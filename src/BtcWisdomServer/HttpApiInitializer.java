/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer;

import com.sun.net.httpserver.HttpServer;

/**
 *
 * @author Navia
 */
public interface HttpApiInitializer {
    public HttpApiServer initialize(HttpApiServer srv);
}
