package teatromoro;

public class Cliente {
    private int idCliente;
    private String nombre;
    private int edad;

    public Cliente(int idCliente, String nombre, int edad) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.edad = edad;
    }

    public boolean esEstudiante() {
        return edad < 18;
    }

    public boolean esTerceraEdad() {
        return edad >= 60;
    }

    //GET
    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    //SET

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
