package teatromoro;


public class Venta {
    private int idVenta;
    private int idCliente;
    private String tipoEntrada;
    private String asiento;
    private int precioBase;
    private double descuento;
    private double precioFinal;

    public Venta(int idVenta, int idCliente, String tipoEntrada, String asiento, int precioBase, double descuento) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.tipoEntrada = tipoEntrada;
        this.asiento = asiento;
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.precioFinal = Math.round(precioBase * (1 - descuento));
    }

    public static Venta crearDesdeObjetos(int idVenta, Cliente cliente, Entrada entrada){
        return new Venta(idVenta, cliente.getIdCliente(), entrada.getTipoEntrada(), entrada.getAsiento(), entrada.getPrecioBase(), entrada.getDescuento());
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public String getAsiento() {
        return asiento;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public int getIdVenta() {
        return idVenta;
    }
}
