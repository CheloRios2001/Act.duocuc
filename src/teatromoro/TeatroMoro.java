
package teatromoro;

import java.util.ArrayList;
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
     static ArrayList<Entrada> entradasVendidas = new ArrayList<>();
     static ArrayList<Entrada> entradasReservadas = new ArrayList<>();


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

    public static int pedirCantidadEntradas(Scanner input){
        int cantidad = -1;

        do{
            System.out.println("\nIngrese la cantidad de entradas que desea reservar:");

            while (!input.hasNextInt()) {
                System.out.println("Cantidad invalida. Debe ingresar un numero entero positivo.");
                input.next();
            }
            cantidad = input.nextInt();

            if (cantidad <= 0) {
                System.out.println("Debe ingresar una cantidad mayor a 0.");
            }
        } while (cantidad <= 0); 

        return cantidad;
    }

    public static int pedirTipoEntrada(Scanner input){
        System.out.println("\nSeleccione el tipo de entrada:");
        System.out.println("1. VIP");
        System.out.println("2. Platea Alta");
        System.out.println("3. Platea Baja");
        System.out.println("4. Palco");

        int tipoElegido = -1;

        do{
            while (!input.hasNextInt()) {
                System.out.println("Opcion no valida. Ingrese un numero del 1 al 4.");
                input.next();
            }
            tipoElegido = input.nextInt();

            if (tipoElegido < 1 || tipoElegido > 4) {
                System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (tipoElegido < 1 || tipoElegido > 4);

        return tipoElegido;

    }

    public static String elegirAsientoReserva(Scanner input){
        String asientoElegido = "";
        boolean asientoValido = false;

        do { 
            System.out.println("\nIngrese el asiento deseado a reservar (Ej: A1):");
            asientoElegido = input.next().toUpperCase();

            for (int fila = 0; fila < asientos.length; fila++) {
                for (int asi = 0; asi < asientos[fila].length; asi++) {
                    if (asientos[fila][asi].equalsIgnoreCase(asientoElegido)) {
                        asientoValido = true;
                        break;
                    }
                }
                if (asientoValido) {
                    break;
                }   
            }
            if (!asientoValido) {
                System.out.println("Asiento no valido o ya reservado/comprado. Intente nuevamente.");
            }
        } while (!asientoValido);

        return asientoElegido;
    }

    public static void marcarAsientoReservado(String asientoElegido){
        for (int fila = 0; fila < asientos.length; fila++) {
            for (int asi = 0; asi < asientos[fila].length; asi++) {
                if (asientos[fila][asi].equalsIgnoreCase(asientoElegido)) {
                    asientos[fila][asi] = "RR";
                    return;
                }
            }
        }
    }

    public static void marcarAsientoVendido(String asientoElegido){
        for (int fila = 0; fila < asientos.length; fila++) {
            for (int asi = 0; asi < asientos[fila].length; asi++) {
                if (asientos[fila][asi].equalsIgnoreCase("RR")){
                    String asientoOriginal = filaLetra(fila) + (asi + 1);
                    if (asientoOriginal.equalsIgnoreCase(asientoElegido)) {
                        asientos[fila][asi] = "XX";
                        return;
                    }
                }
            }
        }
    }



    public static void mostrarEntradas(double descuento){
        System.out.println("\nSeleccione el tipo de entrada:");

        for (int i = 0; i < entradas.length; i++) {
            int precioBase = precioGeneral[i];
            int precioConDescuento = (int) Math.round(precioBase * (1 - descuento));
            System.out.println((i+1)+ ". "+ entradas[i]+ " - $" + precioConDescuento);
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
            System.out.println("1. Reservar entrada");
            System.out.println("2. Comprar entrada");
            System.out.println("3. Comprar reserva");
            System.out.println("4. Modificar venta");
            System.out.println("5. Imprimir boleta");
            System.out.println("6. Buscar entrada");
            System.out.println("7. Eliminar entrada");
            System.out.println("8. Ver promociones");
            System.out.println("9. Salir");
            System.out.println("Seleccione una opcion: ");
            System.out.println("---------------------------------------------------------------------------");

            while (!input.hasNextInt()){
                System.out.println("Opcion no valida. Debe ingresar un numero positivo entre 1 y 9.");
                input.next();
            }

            opcionesMenu = input.nextInt();

            switch (opcionesMenu) {
                case 1:
                    reservarEntrada(input);
                    break;
                case 2:
                    seguirComprando = venderEntrada(input);
                    break;
                case 3:
                    comprarReserva(input);
                    break;
                case 4:
                    modificarVenta();
                    break;   
                case 5:
                    imprimirBoleta();
                    break;
                case 6:
                    buscarEntradas(input);
                    break;  
                case 7:
                    eliminarEntrada(input);
                    break;    
                case 8:
                    System.out.println("Promociones actuales:");
                    System.out.println("Hay un 10% de descuento para estudiantes!!");
                    System.out.println("Contamos con un 15% de descuento para la Tercera edad!!");
                    System.out.println("Por la compra de dos o mas entradas contamos con descuentos especiales!!");
                    break;  
                case 9:
                    seguirComprando = false;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
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

    public static String filaLetra(int fila){
        switch (fila) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 3:
                return "C";
            default:
                return "";
        }
    }

    public static String elegirAsiento(Scanner input){
        String asientoElegido = "";
        boolean asientoValido = false;

        do {
            System.out.println("\nIngrese el asiento deseado (Ej: A1):");
            asientoElegido = input.next().toUpperCase();

            for (int fila = 0; fila < asientos.length; fila++) {
                for (int asi = 0; asi < asientos[fila].length; asi++) {
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

    public static boolean venderEntrada(Scanner input) {
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

            mostrarEntradas(descuento);

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

            Entrada nueva = new Entrada(entradaElegida, tipoTarifa, asiento, precioBase, precioFinal, descuento );

            entradasVendidas.add(nueva);

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
            
        return (respuesta == 1);
    }

    public static void mostrarEntradasVendidas(){
        if (entradasVendidas.isEmpty()) {
            System.out.println("\nNo se han vendido entradas aun.");
        } else {
            System.out.println("\nLista de entradas vendidas:");
            for (Entrada entrada : entradasVendidas) {
                entrada.mostrarInfo();
            }
        }
    }

    public static void buscarEntradas(Scanner input){
        if (entradasVendidas.isEmpty()) {
            System.out.println("\nNo hay entradas para buscar.");
            return;
        }

        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("1. Buscar por numero");
        System.out.println("2. Buscar por tipo de entrada");
        System.out.println("3. Buscar por tipo de tarifa");
        System.out.println("4. Ver todas las entradas vendidas");
        System.out.println("Seleccione una opcion: ");
        System.out.println("---------------------------------------------------------------------------");

        int opcion = -1;

        do {
            while (!input.hasNextInt()) {
                System.out.println("Entrada no valida. Ingrese 1, 2 o 3.");
                input.next();
            }
            opcion = input.nextInt();

            if (opcion < 1 || opcion > 4) {
                System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion < 1 || opcion > 4);

        switch (opcion) {
            case 1:
                buscarPorNumero(input);
                break;
            case 2:
                buscarPorTipoDeEntrada(input);
                break;
            case 3:
                buscarPorTarifa(input);
                break;
            case 4:
                mostrarEntradasVendidas();
            default:
                break;
        }
    }

    public static void buscarPorNumero(Scanner input){
        System.out.println("\nIngrese el numero de la entrada deseada:");
        int numeroBuscado = -1;

        while (!input.hasNextInt()) {
            System.out.println("Numero invalido. Intente nuevamente.");
            input.next();
        }
        numeroBuscado = input.nextInt();

        boolean encontrado = false;

        for (Entrada entrada : entradasVendidas){
            if (entrada.getNumero() == numeroBuscado) {
                System.out.println("\nEntrada encontrada:");
                entrada.mostrarInfo();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontro una entrada asociada al numero.");
        }
    }

    public static void buscarPorTipoDeEntrada(Scanner input){
        System.out.println("\nSeleccione el tipo de entrada:");
        System.out.println("1. VIP");
        System.out.println("2. Platea Alta");
        System.out.println("3. Platea Baja");
        System.out.println("4. Palco");

        int tipoElegido = -1;

        do {
            while (!input.hasNextInt()) {
                System.out.println("Opcion no valida. Ingrese un numero del 1 al 4.");
                input.next();
            }
            tipoElegido = input.nextInt();

            if (tipoElegido < 1 || tipoElegido > 4) {
                System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (tipoElegido < 1 || tipoElegido > 4);

        String tipoBuscado = entradas[tipoElegido - 1];
        boolean encontrado = false;

        System.out.println("\nEntradas del tipo: " + tipoBuscado);

        for (Entrada entrada : entradasVendidas){
            if (entrada.getTipoEntrada().equalsIgnoreCase(tipoBuscado)) {
                entrada.mostrarInfo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron entradas.");
        }
    }

    public static void buscarPorTarifa(Scanner input){
        System.out.println("\nSeleccione el tipo de entrada:");
        System.out.println("1. Estudiante");
        System.out.println("2. Tercera edad");
        System.out.println("3. General");

        int opcion = -1;

        do {
            while (!input.hasNextInt()) {
                System.out.println("Opcion no valida. Ingrese 1,2 o 3 segun corresponda.");
                input.next();
            }
            opcion = input.nextInt();

            if (opcion < 1 || opcion > 3) {
                System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion < 1 || opcion > 3);

        String tipoBuscado = "";

        switch (opcion){
            case 1:
                tipoBuscado = "Estudiante";
                break;
            case 2:
                tipoBuscado = "Tercera edad";
                break;
            case 3:
                tipoBuscado = "General";
                break;
        }

        boolean encontrado = false;

        for (Entrada entrada : entradasVendidas ){
            if (entrada.getTipoTarifa().equalsIgnoreCase(tipoBuscado)) {
                if (!encontrado) {
                    System.out.println("\nEntradas encontradas: ");
                }
                entrada.mostrarInfo();
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron entradas.");
        }
    }

    public static void eliminarEntrada(Scanner input){
        if (entradasVendidas.isEmpty()) {
            System.out.println("\nNo hay entradas para eliminar.");
            return;
        }
        System.out.println("\nIngrese el numero de la entrada que desea eliminar.");
        int numeroBuscado = -1;
        while (!input.hasNextInt()) {
            System.out.println("Opcion no valida. Intente nuevamente.");
            input.next();
        }
        numeroBuscado = input.nextInt();

        Entrada entradaAEliminar = null;

        for (Entrada entrada : entradasVendidas){
            if (entrada.getNumero() == numeroBuscado) {
                entradaAEliminar = entrada;
                break;
            }
        }

        if (entradaAEliminar == null) {
            System.out.println("Entrada no encontrada.");
            return;
        }

        System.out.println("\nEntrada encontrada:.");
        entradaAEliminar.mostrarInfo();
        System.out.println("Desea eliminar esta entrada? (1. Si / 2. No):");

        int opcion = -1;

        do {
            while (!input.hasNextInt()) {
                System.out.println("Opcion no valida. Ingrese 1 o 2: ");  
                input.next();
            }
            opcion = input.nextInt();
        } while(opcion != 1 && opcion != 2);

        if (opcion == 1) {
            entradasVendidas.remove(entradaAEliminar);
            System.out.println("Entrada eliminada correctamente. ");
        } else {
            System.out.println("Operacion cancelada. ");
        }
    }

    public static void reservarEntrada(Scanner input){
        System.out.println("\nRESERVA DE ENTRADAS");
        asientosTeatro();

        System.out.println("Cuantas entradas desea reservar?");
        int cantidad = pedirCantidadEntradas(input);

        for (int i = 0; i < cantidad; i++) {
            String asiento = elegirAsientoReserva(input);

            Entrada nuevaReserva = new Entrada("Reservado", "Sin tarifa", asiento, 0, 0, 0.0);
            entradasReservadas.add(nuevaReserva);

            marcarAsientoReservado(asiento);

            System.out.println("Asiento "+ asiento + " Registrado exitosamente.\n");

            asientosTeatro();
        }

        System.out.println("Gracias por su reserva!");
    }

    public static void comprarReserva(Scanner input){
        if (entradasReservadas.isEmpty()) {
            System.out.println("\nNo hay reservas.");
            return;
        }

        System.out.println("\nReservas disponibles:");
        for (int i = 0; i < entradasReservadas.size(); i++) {
            System.out.println((i + 1) + ". Asiento " + entradasReservadas.get(i).getAsiento());
        }

        int numeroReserva = -1;
        do { 
            System.out.println("\nIngrese el numero de la reserva que desea comprar:");
            while (!input.hasNextInt()) {
                System.out.println("Opcion no valida. Ingrese un numero.");
                input.next();
            }
            numeroReserva = input.nextInt();

            if (numeroReserva < 1 || numeroReserva > entradasReservadas.size()) {
                System.out.println("Numero ingresado no valido. Intente nuevamente.");
            }
        } while (numeroReserva < 1 || numeroReserva > entradasReservadas.size());

        Entrada reservaElegida = entradasReservadas.get(numeroReserva - 1);
        String asiento = reservaElegida.getAsiento();
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

        mostrarEntradas(descuento);

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

        int indice = tipoEntrada - 1;
        String entradaElegida = entradas[indice];
        int precioBase = precioGeneral[indice];
        int precioFinal = (int) Math.round(precioBase * (1 - descuento));

        Entrada nueva = new Entrada(entradaElegida, tipoTarifa, asiento, precioBase, precioFinal, descuento);
        entradasVendidas.add(nueva);

        marcarAsientoVendido(asiento);
        entradasReservadas.remove(reservaElegida);

        resumenFinal(tipoTarifa, entradaElegida, precioFinal, asiento, descuento, precioBase);

    }

    public static void modificarVenta(){
        System.out.println("Funcion de venta");
    }

    public static void imprimirBoleta(){
        if (entradasVendidas.isEmpty()) {
            System.out.println("\nNo hay entradas vendidas.");
            return;
        }

        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("                      BOLETA DE COMPRA");
        System.out.println("\n---------------------------------------------------------------------------");

        int contador = 1;
        int total = 0;

        for (Entrada entrada : entradasVendidas){
            System.out.println("Entrada N°" + contador + " - Tipo: " + entrada.getTipoEntrada() + " - Asiento: " + entrada.getAsiento() + " - Precio: $" + entrada.getPrecioFinal());
            total += entrada.getPrecioFinal();
            contador++;
        }

        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Cantidad de entradas: " + entradasVendidas.size());
        System.out.println("Total a pagar: $" + total);
        System.out.println("\n---------------------------------------------------------------------------"); 
        System.out.println("Gracias por su compra. Disfrute el espectaculo!");
    }
}



