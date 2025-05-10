package teatromoro;

public class Cliente {
    private int idCliente;
    private String nombre;
    private int edad;
    private String genero;

    public Cliente(int idCliente, String nombre, int edad, String genero) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public boolean esEstudiante() {
        return edad < 18;
    }

    public boolean esTerceraEdad() {
        return edad >= 60;
    }

    public boolean esNino(){
       return edad < 12; 
    }

    public boolean esMujer(){
        return genero.equalsIgnoreCase("F");
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

    public String getGenero(){
        return genero;
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
