/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BtcWisdomServer.model.classes;

/**
 *
 * @author Navia
 */
public class Configuracion {
    
    private int id;
    private Usuario usuario;
    private Moneda moneda;
    private double importeCompra;
    private double importeVenta;
    private double precioMaxVenta;
    private double precioMinVenta;
    private double precioMaxCompra;
    
    public Configuracion(Usuario u, Moneda m){
        this(u, m, 0d, 0d, 0d, 0d, 0d);
    }
    
    public Configuracion(Usuario u, Moneda m, double impCompra, double impVenta, double pMaxVenta, double pMinVenta, double pMaxCompra){
        this.usuario = u;
        this.moneda = m;
        this.importeCompra = impCompra;
        this.importeVenta = impVenta;
        this.precioMaxVenta = pMaxVenta;
        this.precioMinVenta = pMinVenta;
        this.precioMaxCompra = pMaxCompra;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
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

    public double getImporteCompra() {
        return importeCompra;
    }

    public void setImporteCompra(double importeCompra) {
        this.importeCompra = importeCompra;
    }

    public double getImporteVenta() {
        return importeVenta;
    }

    public void setImporteVenta(double importeVenta) {
        this.importeVenta = importeVenta;
    }

    public double getPrecioMaxVenta() {
        return precioMaxVenta;
    }

    public void setPrecioMaxVenta(double precioMaxVenta) {
        this.precioMaxVenta = precioMaxVenta;
    }

    public double getPrecioMinVenta() {
        return precioMinVenta;
    }

    public void setPrecioMinVenta(double precioMinVenta) {
        this.precioMinVenta = precioMinVenta;
    }

    public double getPrecioMaxCompra() {
        return precioMaxCompra;
    }

    public void setPrecioMaxCompra(double precioMaxCompra) {
        this.precioMaxCompra = precioMaxCompra;
    }
    
}
