package teatromoro;

public class Cliente {
    private int id;
    private String nombre;
    private int edad;

    public Cliente(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public boolean esEstudiante() {
        return edad < 18;
    }

    public boolean esTerceraEdad() {
        return edad >= 60;
    }
}
