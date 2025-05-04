package teatromoro;


public class Venta {
    private int idVenta;
    private int idCliente;
    private String tipoEntrada;
    private String asiento;
    private double precioBase;
    private double descuento;
    private double precioFinal;

    public Venta(int idVenta, int idCliente, String tipoEntrada, String asiento, double precioBase, double descuento) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.tipoEntrada = tipoEntrada;
        this.asiento = asiento;
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.precioFinal = Math.round(precioBase * (1 - descuento));
    }
}
