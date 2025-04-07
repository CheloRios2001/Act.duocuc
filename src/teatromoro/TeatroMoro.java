
package teatromoro;

import java.util.Scanner;




//apuntes
//Math.round redondea al entero mas cercano
/**
 *
 * @author marce
 */
public class TeatroMoro {

    /**
     * @param args the command line arguments
     */
    //Variables globales dentro de la clase pero fuera del metodo


     static String[] entradas = {"VIP", "Platea Alta", "Platea Baja", "Palco"};
     static int[] precioGeneral = {30000, 18000, 15000, 13000};
     static double[] descuentoEstudiante = {0.33, 0.5, 0.33, 0.5};
     static String[][] asientos = {
        {"A1","A2","A3","A4","A5"},
        {"B1","B2","B3","B4","B5"},
        {"C1","C2","C3","C4","C5"}
     };


    public static void main(String[] args) {

        
        Scanner input = new Scanner(System.in);

        bienvenida();
        
        menuPrincipal(input);
        
        despedida();


    } 

    public static void bienvenida() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Bienvenidos al teatro Moro");
        System.out.println("---------------------------------------------------------------------------");
    }

    public static int pedirEdad(Scanner input){
        int edad;

        do {
            System.out.println("Ingrese su edad:");

            while (!input.hasNextInt()){
                System.out.println("Edad no valida. Debe ingresar un numero entero.");
                input.next();
            }
            edad = input.nextInt();

            if (edad <= 0) {
                System.out.println("Edad no valida. Debe ingresar un numero mayor a 0.");
            }
        } while (edad <= 0);

        return edad;

    }

    public static void mostrarEntradas(boolean esEstudiante){
        System.out.println("\nSeleccione el tipo de entrada:");

        for (int i = 0; i < entradas.length; i++) {
            int precio = precioGeneral[i];

            if (esEstudiante) {
                precio = (int) Math.round(precio * (1 - descuentoEstudiante[i]));
            }
            System.out.println((i+1)+ ". "+ entradas[i]+ " - $" + precio);
        }
    }

    public static void resumenFinal(String tipoTarifa, String entradaElegida, int precioFinal, String asiento, double descuento, int precioBase) {
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Resumen de la compra:");
        System.out.println("Tipo de tarifa: " + tipoTarifa);
        System.out.println("Entrada elegida: " + entradaElegida);
        System.out.println("Asiento: " + asiento);
        System.out.println("Precio Base: $" + precioBase);
        System.out.println("Descuento aplicado: " + (int)(descuento * 100)+ "%");
        System.out.println("Precio a pagar: $" + precioFinal);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Gracias por su compra, Disfrute su funcion.");
    }

    public static void menuPrincipal(Scanner input){

        boolean seguirComprando = true;
        
        while (seguirComprando){
            int opcionesMenu = -1;

            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.println("Seleccione una opcion: ");
            System.out.println("---------------------------------------------------------------------------");

            while (!input.hasNextInt()){
                System.out.println("Opcion no valida. Debe ingresar 1 o 2.");
                input.next();
            }

            opcionesMenu = input.nextInt();

            if (opcionesMenu == 1) {
                int edad = pedirEdad(input);
                boolean esEstudiante = (edad < 18);
                boolean terceraEdad = (edad >= 60);
                double descuento = 0.0;
                String tipoTarifa;

                if (esEstudiante) {
                    descuento = 0.10;
                    tipoTarifa = "Estudiante";
                    System.out.println("Por ser estudiante tienes un 10% de descuento en tu entrada!");
                } else if (terceraEdad) {
                    descuento = 0.15;
                    tipoTarifa ="Tercera Edad";
                    System.out.println("Por ser adulto mayor tienes un 15% de descuento en tu entrada!");
                } else {
                    tipoTarifa = "General";
                }

                mostrarEntradas(esEstudiante);

                int tipoEntrada = -1;

                do {
                    System.out.println("\nIngrese el número de la entrada que desea:");

                    while (!input.hasNextInt()) {
                        System.out.println("Opcion no valida. Ingrese un numero válido.");
                        input.next();
                    }

                    tipoEntrada = input.nextInt();

                    if (tipoEntrada < 1 || tipoEntrada > 4) {
                        System.out.println("Opcion no válida. Intente nuevamente.");
                    }
                } while (tipoEntrada < 1 || tipoEntrada > 4);

                asientosTeatro();

                String asiento = elegirAsiento(input);

                int indice = tipoEntrada - 1;
                String entradaElegida = entradas[indice];
                int precioBase = precioGeneral[indice];
                int precioFinal = (int)Math.round(precioBase* (1 - descuento));

                resumenFinal( tipoTarifa, entradaElegida, precioFinal, asiento, descuento, precioBase);

                System.out.println("\nDesea comprar otra entrada?");
                System.out.println("1. Si");
                System.out.println("2.No");

                int respuesta = -1;

                do {
                    while (!input.hasNextInt()) {
                        System.out.println("Opcion no valida. Ingrese 1 para Si o 2 para No.");
                        input.next();
                    }
                    respuesta = input.nextInt();

                    if (respuesta != 1 && respuesta != 2) {
                        System.out.println("Opcion no valida. Ingrese 1 para Si o 2 para No.");
                    }
                } while (respuesta != 1 && respuesta != 2);

                if (respuesta == 2) {
                    seguirComprando = false;
                }
                
            } else if (opcionesMenu == 2) {
                seguirComprando = false;
            }
                
        }
    }

    public static void despedida(){
        System.out.println("\nGracias por visitar el Teatro Moro. ¡Hasta pronto!");
    }

    public static void asientosTeatro(){
        System.out.println("\nAsientos del teatro: ");
        for (int fila = 0; fila < asientos.length; fila++) {
            for (int asi = 0; asi < asientos[fila].length; asi++) {
                System.out.print(asientos[fila][asi] + "\t");
            }
            System.out.println();
        }
    }

    public static String elegirAsiento(Scanner input){
        String asientoElegido = "";
        boolean asientoValido = false;

        do {
            System.out.println("\nIngrese el asiento deseado (Ej: A1):");
            asientoElegido = input.next().toUpperCase();

            for (int fila = 0; fila < asientos.length; fila++) {
                for (int asi = 0; asi < asientos.length; asi++) {
                    if (asientos[fila][asi].equals(asientoElegido)) {
                        asientos[fila][asi] = "XX";
                        asientoValido = true;
                        break;
                    }
                }
            }
            if (!asientoValido) {
                System.out.println("Asiento no valido o ocupado. Intente nuevamente.");
            }
        } while (!asientoValido);
        return asientoElegido;
    }
}



