package teatromoro;

public class Entrada {
    private int numero;
    private String tipoEntrada;
    private String tipoTarifa;
    private String asiento;
    private int precioBase;
    private int precioFinal;
    private double descuento;

    private static int contador = 1;

    public Entrada(String tipoEntrada, String tipoTarifa, String asiento, int precioBase, int precioFinal, double descuento){
        this.numero = contador++;
        this.tipoTarifa = tipoTarifa;
        this.tipoEntrada = tipoEntrada;
        this.asiento = asiento;
        this.precioBase = precioBase;
        this.precioFinal = precioFinal;
        this.descuento = descuento;
    }

    public void mostrarInfo(){
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Entrada N° " + numero);
        System.out.println("Tipo de entrada: " + tipoEntrada);
        System.out.println("Tipo de tarifa: " + tipoTarifa);
        System.out.println("Asiento: " + asiento);
        System.out.println("Precio Base: $" + precioBase);
        System.out.println("Descuento aplicado: " + (int)(descuento * 100)+ "%");
        System.out.println("Precio a pagar: $" + precioFinal);
        System.out.println("---------------------------------------------------------------------------");
    }

    public int getNumero(){
        return numero;
    }

    public String getTipoEntrada(){
        return tipoEntrada;
    }

    public String getTipoTarifa(){
        return tipoTarifa;
    }
    
}

