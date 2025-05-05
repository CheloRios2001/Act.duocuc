package teatromoro;


public class Reserva {
    private int idReserva;
    private int idCliente;
    private String asiento;

    public Reserva(int idReserva, int idCliente, String asiento) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.asiento = asiento;
    }

    //GET
    
    public int getIdReserva() {
        return idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getAsiento() {
        return asiento;
    }
}
