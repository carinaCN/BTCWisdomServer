/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.classes;

/**
 *
 * @author carina
 */
public class SaldoMoneda {
    
    private Usuario usuario;
    private Moneda moneda;
    private double saldo;
    
    public SaldoMoneda(){
        
    }
    
    public SaldoMoneda(Usuario u, Moneda m, double saldo){
        this.usuario = u;
        this.moneda = m;
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
}
