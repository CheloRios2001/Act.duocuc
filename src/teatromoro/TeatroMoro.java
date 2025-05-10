
package teatromoro;

import java.util.ArrayList;
import java.util.List;
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
    //VARIABLES GLOBALES

    //CONSTANTES

    private static final double DESCUENTO_ESTUDIANTE = 0.10;
    private static final double DESCUENTO_TERCERA_EDAD = 0.15;

    //DATOS FIJOS DEL TEATRO

     static String[] entradas = {"VIP", "Platea Alta", "Platea Baja", "Palco"};
     static int[] precioGeneral = {30000, 18000, 15000, 13000};
     static String[][] asientos = {
        {"A1","A2","A3","A4","A5"},
        {"B1","B2","B3","B4","B5"},
        {"C1","C2","C3","C4","C5"}
     };

     //DATOS DINAMICOS DEL TEATRO

     static ArrayList<Entrada> entradasVendidas = new ArrayList<>();
     static ArrayList<Entrada> entradasReservadas = new ArrayList<>();
     static Cliente[] clientes = new Cliente[100];
     static Venta[] ventas = new Venta[100];
     static List<String> promociones = new ArrayList<>();
     static List<Reserva> reservas = new ArrayList<>();
     static int totalEntradasVendidas = 0;
     static int totalIngresos = 0;

     //CONTADORES

     static int totalClientes = 0;
     static int totalVentas = 0;

    public static void main(String[] args) {

        
        Scanner input = new Scanner(System.in);

        bienvenida();
        
        menuPrincipal(input);
        
        despedida();


    } 

    //METODOS FLUJO PRINCIPAL

    public static void bienvenida() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Bienvenidos al teatro Moro");
        System.out.println("---------------------------------------------------------------------------");
    }

    public static void menuPrincipal(Scanner input){

        boolean seguirComprando = true;
        
        while (seguirComprando){

            mostrarMenu();

            int opcionesMenu = pedirOpcion(input, 1, 10, "Opción no válida. Debe ser un número entre 1 y 10.");

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
                    modificarVenta(input);
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
                    mostrarIngresosTotales();
                    break;
                case 10:
                    gestionarClientes(input);
                    break;
                case 11:
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

    public static void mostrarMenu(){
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Reservar entrada");
        System.out.println("2. Comprar entrada");
        System.out.println("3. Comprar reserva");
        System.out.println("4. Modificar venta");
        System.out.println("5. Imprimir boleta");
        System.out.println("6. Buscar entrada");
        System.out.println("7. Eliminar entrada");
        System.out.println("8. Ver promociones");
        System.out.println("9. Ingresos totales");
        System.out.println("10. Gestionar clientes");
        System.out.println("11. Salir");
        System.out.println("Seleccione una opcion: ");
        System.out.println("---------------------------------------------------------------------------");
    }

    //METODOS DE INTERACCION CON EL USUARIO

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

    public static int pedirOpcion(Scanner input, int minimo, int maximo, String mensajeError){
        int opcion = -1;
        boolean entradaValida = false;

        do { 
            if (input.hasNextInt()) {
               opcion = input.nextInt();
               if (opcion >= minimo && opcion <= maximo) {
                    entradaValida = true;
               } else {
                System.out.println(mensajeError);
               }
            } else {
                System.out.println(mensajeError);
                input.nextLine();
            }
        } while (!entradaValida);
        return opcion;
    }

    public static int pedirTipoEntrada(Scanner input) {
        System.out.println("\nSeleccione el tipo de entrada:");
        System.out.println("1. VIP");
        System.out.println("2. Platea Alta");
        System.out.println("3. Platea Baja");
        System.out.println("4. Palco");

        return pedirOpcion(input, 1, 4, "Opcion no valida. Ingrese un numero del 1 al 4.");
    }

    public static void mostrarEntradas(double descuento){
        System.out.println("\nSeleccione el tipo de entrada:");

        for (int i = 0; i < entradas.length; i++) {
            int precioBase = precioGeneral[i];
            int precioConDescuento = (int) Math.round(precioBase * (1 - descuento));
            System.out.println((i+1)+ ". "+ entradas[i]+ " - $" + precioConDescuento);
        }
    }

    public static Cliente crearCliente(Scanner input){
        System.out.println("\n--- Registro de cliente ---");
        input.nextLine();

        String nombre = "";

        while (nombre.isEmpty()) {
            System.out.println("Ingrese nombre del cliente:");
            nombre = input.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Intente nuevamente.");
            }
        }

        int edad;
        do { 
            System.out.println("Ingrese edad del cliente: ");
            while (!input.hasNextInt()){
                System.out.println("Edad no valida. Debe ingresar un numero entero.");
                input.next();
            }
            edad = input.nextInt();
            input.nextLine();
            if (edad <= 0){
                System.out.println("La edad debe ser mayor que cero.");
            }
        } while (edad <= 0);

        String genero = "";
        do {
            System.out.println("Ingrese el genero del cliente (M/F):");
            genero = input.nextLine().trim().toUpperCase();
            if (!genero.equals("M") && !genero.equals("F")) {
                System.out.println("Genero invalido. Solo se permite M o F.");
            }
        } while (!genero.equals("M") && !genero.equals("F"));

        System.out.println("[DEBUG] Datos capturados - Nombre: " + nombre + ", Edad: " + edad);

        if (totalClientes < clientes.length){
            int idCliente = totalClientes + 1;
            Cliente nuevoCliente = new Cliente(idCliente, nombre, edad, genero);
            clientes[totalClientes++] = nuevoCliente;
            System.out.println("[DEBUG] Cliente registrado con ID: " + idCliente + " | Total clientes ahora: " + totalClientes);
            return nuevoCliente;
        } else {
            System.out.println("ERROR: No se pudo registrar mas clientes.");
            return null;
        }
    }

    //METODOS DE OPERACIONES PRINCIPALES

    public static boolean venderEntrada(Scanner input) {

        System.out.println("\n--- Venta de entrada ---");

        Cliente cliente = crearCliente(input);
        if  (cliente == null){
            System.out.println("No se pudo registrar al cliente. Operacion Cancelada.");
            return true;
        }

        String[] datosDescuento = calcularDescuento(cliente);
        String tipoTarifa = datosDescuento[0];
        double descuento = Double.parseDouble(datosDescuento[1]);

        if (!tipoTarifa.equals("General")) {
            System.out.println("Por ser " + tipoTarifa.toLowerCase() + " tienes un " + (int)(descuento * 100) + "% de descuento en tu entrada!");
        }

        mostrarEntradas(descuento);

        int tipoEntrada = pedirOpcion(input, 1, entradas.length, "Opcion no valida. Ingrese un numero valido.");
        int indice = tipoEntrada - 1;
        String nombreEntrada = entradas[indice];
        int precioBase = precioGeneral[indice];

        asientosTeatro();
        String asiento = elegirAsiento(input);

        Entrada entrada = new Entrada(nombreEntrada, tipoTarifa, asiento, precioBase, (int)Math.round(precioBase * (1 - descuento)), descuento);

        entradasVendidas.add(entrada);
        totalEntradasVendidas++;
        totalIngresos += entrada.getPrecioFinal();

        Venta venta = Venta.crearDesdeObjetos(totalVentas + 1, cliente, entrada);
        ventas[totalVentas++] = venta;

        entrada.mostrarInfo();
        System.out.println("Gracias por su compra. Disfrute su funcion!");

        System.out.println("\nDesea volver al menu principal?");
        System.out.println("1. Si");
        System.out.println("2.No");

        int respuesta = pedirOpcion(input, 1, 2, "Opcion no valida. Ingrese 1 para Si o 2 para No.");
            
        return (respuesta == 1);
    }

    public static void reservarEntrada(Scanner input){
        System.out.println("\n--- Reserva de entradas ---");

        Cliente cliente = crearCliente(input);
        if (cliente == null) {
            System.out.println("No se pudo registrar al cliente. Operacion Cancelada.");
            return;
        }

        asientosTeatro();
        int cantidad = pedirCantidadEntradas(input);

        for (int i = 0; i < cantidad; i++) {
            String asiento = elegirAsientoReserva(input);

            int idReserva = reservas.size() + 1;
            Reserva nuevaReserva = new Reserva(idReserva, cliente.getIdCliente(), asiento);
            reservas.add(nuevaReserva);

            marcarAsientoReservado(asiento);

            System.out.println("Reserva registrada para el asiento: " + asiento + " [ID de reserva: " + idReserva + "]");
        }

        System.out.println("\nResumen de la reserva para " + cliente.getNombre() + ":");
        for(Reserva r : reservas){
            if (r.getIdCliente() == cliente.getIdCliente()) {
                System.out.println(" - Asiento: " + r.getAsiento() + " (ID de la reserva: " + r.getIdReserva() + ")");
            }
        }
        System.out.println("Gracias por su reserva. No olvide comprar su entrada antes de la funcion!");
    }

    public static void comprarReserva(Scanner input){
        if (reservas.isEmpty()) {
            System.out.println("\nNo hay reservas.");
            return;
        }

        System.out.println("\nReservas disponibles:");
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            System.out.println((i + 1) + ". ID de la reserva: " + r.getIdReserva() + " - Asiento: " + r.getAsiento() + " - ID del cliente: " + r.getIdCliente());
        }

        int Seleccion = pedirOpcion(input, 1, reservas.size(), "Opcion no valida. Intente nuevamente.");
        Reserva reservaElegida = reservas.get(Seleccion - 1);

        Cliente cliente = buscarClientePorId(reservaElegida.getIdCliente());
        if (cliente == null) {
            System.out.println("Cliente asociado a la reserva no ubicado.");
            return;
        }

        String[] datosDescuento = calcularDescuento(cliente);
        String tipoTarifa = datosDescuento[0];
        double descuento = Double.parseDouble(datosDescuento[1]);

        if (!tipoTarifa.equals("General")) {
            System.out.println("Por ser " + tipoTarifa.toLowerCase() + " tienes un " + (int)(descuento * 100) + "% de descuento en tu entrada!");
        }

        mostrarEntradas(descuento);

        int tipoEntrada = pedirOpcion(input, 1, entradas.length, "Opcion no valida. Intente nuevamente.");
        int indice = tipoEntrada - 1;

        String entradaElegida = entradas[indice];
        int precioBase = precioGeneral[indice];

        Entrada nueva = crearEntrada(entradaElegida, tipoTarifa, reservaElegida.getAsiento(), precioBase, descuento);
        entradasVendidas.add(nueva);
        totalEntradasVendidas++;
        totalIngresos += nueva.getPrecioFinal();
        
        marcarAsientoVendido(reservaElegida.getAsiento());

        Venta nuevaVenta = Venta.crearDesdeObjetos(totalVentas + 1, cliente, nueva);
        ventas[totalVentas++] = nuevaVenta;

        reservas.remove(reservaElegida);

        System.out.println("[DEBUG] Reserva convertida en compra y eliminada correctamente");

        nueva.mostrarInfo();

        System.out.println("Gracias por su compra. Disfrute su funcion!");
    }

    public static void modificarVenta(Scanner input){
        if (entradasVendidas.isEmpty()) {
            System.out.println("\nNo hay entradas.");
            return;
        }

        mostrarEntradasVendidas();

        System.out.println("\nIngrese el numero de la entrada que desea modificar:");
        int numeroBuscado = -1;

        while (!input.hasNextInt()) {
            System.out.println("Numero invalido. Intente nuevamente");
            input.next();
        }
        numeroBuscado = input.nextInt();

        Entrada entradaAModificar = null;
        for (Entrada entrada : entradasVendidas){
            if (entrada.getNumero() == numeroBuscado) {
                entradaAModificar = entrada;
                break;
            }
        }

        if (entradaAModificar == null) {
            System.out.println("No hay entradas asociadas al numero ingresado.");
            return;
        }

        boolean seguirModificando = true;
        while (seguirModificando) {
            System.out.println("\nQue desea modificar?");
            System.out.println("1. Cambiar tipo de entrada");
            System.out.println("2. Cambiar asiento");
            System.out.println("3. Volver al menu principal");

            int opcion = pedirOpcion(input, 1, 3, "Opcion invalida. Ingrese 1,2 o 3.");

            switch (opcion) {
                case 1:
                    cambiarTipoEntrada(entradaAModificar, input);
                    break;
                case 2:
                    cambiarAsiento(entradaAModificar, input);
                    break;
                case 3:
                    seguirModificando = false;
                    break;
            
                default:
                    break;
            }
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

        int opcion = pedirOpcion(input, 1, 2, "Opcion no valida. Ingrese 1 o 2: ");

        if (opcion == 1) {
            entradasVendidas.remove(entradaAEliminar);
            System.out.println("Entrada eliminada correctamente. ");
            totalIngresos -= entradaAEliminar.getPrecioFinal();
            totalEntradasVendidas--;
        } else {
            System.out.println("Operacion cancelada. ");
        }
    }

    public static void imprimirBoleta(){
        if (entradasVendidas.isEmpty()) {
            System.out.println("\nNo hay entradas vendidas.");
            return;
        }

        System.out.println("\n--------------------------------   BOLETAS GENERADAS   -------------------------------------------");

        for (Entrada entrada : entradasVendidas){
            entrada.mostrarInfo();
        }
    }

    public static void mostrarIngresosTotales(){
        int total = 0;

        for (Entrada entrada : entradasVendidas){
            total += entrada.getPrecioFinal();
        }

        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Ingresos totales acumulados: $" + total);
        System.out.println("---------------------------------------------------------------------------");
    }

    public static void gestionarClientes(Scanner input){
        boolean seguir = true;
        while (seguir) {
            System.out.println("\n--- Gestion de Clientes ---");
            System.out.println("1. Editar cliente");
            System.out.println("2. Eliminar Cliente");
            System.out.println("3. Volver al menu principal");
            System.out.println("Seleccione una opcion: ");

            int opcion = pedirOpcion(input, 1, 3, "Opcion no valida. Intentelo de nuevo.");

            switch (opcion) {
                case 1:
                    editarCliente(input);
                    break;
                case 2:
                    eliminarCliente(input);
                    break;
                case 3:
                    seguir = false;
                    break;
            }
        }
    }

    //METODOS AUXILIARES

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
            case 2:
                return "C";
            default:
                return "";
        }
    }

    public static void liberarAsientoAnterior(String asientoAnterior){
        for (int fila = 0; fila < asientos.length; fila++) {
            for (int asi = 0; asi < asientos[fila].length; asi++) {
                String asientoActual = filaLetra(fila) + (asi + 1);
                if (asientoActual.equalsIgnoreCase(asientoAnterior) && asientos[fila][asi].equals("XX")) {
                    asientos[fila][asi] = asientoAnterior.toUpperCase();
                    System.out.println("[DEBUG] Asiento liberado: " + asientoAnterior);
                    return;
                }
            }
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
                        System.out.println("[DEBUG] Asiento seleccionado para compra: " + asientoElegido);
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
                        System.out.println("[DEBUG] Asiento seleccionado para reserva: " + asientoElegido);
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
                    System.out.println("[DEBUG] Asiento reservado: " + asientoElegido);
                    return;
                }
            }
        }
    }

    public static void marcarAsientoVendido(String asientoElegido){
        for (int fila = 0; fila < asientos.length; fila++) {
            for (int asi = 0; asi < asientos[fila].length; asi++) {
                String asientoActual = asientos[fila][asi];
                String nombreOriginal = filaLetra(fila) + (asi + 1);

                if ((asientoActual.equalsIgnoreCase("RR") || asientoActual.equalsIgnoreCase(nombreOriginal)) && nombreOriginal.equalsIgnoreCase(asientoElegido)) {
                    asientos[fila][asi] = "XX";
                    System.out.println("[DEBUG] Asiento marcado como vendido: " + asientoElegido);
                    return;
                }
            }
        }
    }

    public static Entrada crearEntrada(String tipoEntrada, String tipoTarifa, String asiento, int precioBase, double descuento){
        int precioFinal = (int) Math.round(precioBase * (1 - descuento));
        return new Entrada(tipoEntrada, tipoTarifa, asiento, precioBase, precioFinal, descuento);
    }

    public static Cliente buscarClientePorId(int idCliente){
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i] != null && clientes[i].getIdCliente() == idCliente) {
                return clientes[i];
            }
        }
        return null;
    }

    public static String[] calcularDescuento(Cliente cliente){
        double mayorDescuento = 0.0;
        String tipoTarifa = "General";

        if (cliente.getEdad() < 13) {
            mayorDescuento = 0.10;
            tipoTarifa = "Niño";
        }

        if (cliente.getGenero().equalsIgnoreCase("F")) {
            if (0.20 > mayorDescuento) {
                mayorDescuento = 0.20;
                tipoTarifa= "Mujer";
            }
        }

        if (cliente.getEdad() < 18) {
            if (0.15 > mayorDescuento) {
                mayorDescuento = 0.15;
                tipoTarifa = "Estudiante";
            }
        }

        if (cliente.getEdad() >= 60) {
            if (0.25 > mayorDescuento) {
                mayorDescuento = 0.25;
                tipoTarifa = "Tercera edad";
            }
        }

        return new String[]{tipoTarifa, Double.toString(mayorDescuento)};
    }

    //METODOS DE BUSQUEDA 

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

        int opcion = pedirOpcion(input, 1, 4, "Opcion no valida. Ingrese un numero entre 1 y 4.");

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

        int tipoElegido = pedirOpcion(input, 1, 4, "Opcion no valida. Ingrese un numero del 1 al 4.");

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

        int opcion = pedirOpcion(input, 1, 3, "Opcion no valida. Ingrese 1,2 o 3 segun corresponda.");

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

    //METODOS DE MODIFICACION

    public static void cambiarTipoEntrada(Entrada entrada, Scanner input){
        System.out.println("\nSeleccione el nuevo tipo de entrada:");

        for (int i = 0; i < entradas.length; i++) {
            System.out.println((i + 1) + ". " + entradas[i] + " - $" + precioGeneral[i]);
        }

        int nuevaOpcion = -1;

        do { 
            System.out.println("\nIngrese el numero de la nueva entrada:");
            while (!input.hasNextInt()) {
                System.out.println("Opcion no valida. Ingrese un numero válido.");
                input.next();
            }
            nuevaOpcion = input.nextInt();

            if (nuevaOpcion < 1 || nuevaOpcion > entradas.length) {
                System.out.println("Numero fuera de rango. Intente nuevamente.");
            }
        } while (nuevaOpcion < 1 || nuevaOpcion > entradas.length);

        int nuevoIndice = nuevaOpcion -1;
        String nuevoTipo = entradas[nuevoIndice];
        int nuevoPrecioBase = precioGeneral[nuevoIndice];
        double descuento = entrada.getDescuento();
        int nuevoPrecioFinal = (int) Math.round(nuevoPrecioBase * (1 - descuento));

        entrada.setTipoEntrada(nuevoTipo);
        entrada.setPrecioBase(nuevoPrecioBase);
        entrada.setPrecioFinal(nuevoPrecioFinal);

        System.out.println("\nTipo de entrada modificada correctamente.");
    }

    public static void cambiarAsiento(Entrada entrada, Scanner input){
        asientosTeatro();

        String nuevoAsiento = "";
        boolean asientoValido = false;
        String asientoAnterior = entrada.getAsiento();

        do { 
            System.out.println("\nIngrese el nuevo asiento (Ej: A1)");
            nuevoAsiento = input.next().toUpperCase();

            for (int fila = 0; fila < asientos.length; fila++) {
                for (int asi = 0; asi < asientos[fila].length; asi++) {
                    if (asientos[fila][asi].equalsIgnoreCase(nuevoAsiento)) {
                        asientos[fila][asi] = "XX";
                        asientoValido = true;
                        break;
                    }
                }
                if (asientoValido) {
                    break;
                }
            }
            if (!asientoValido) {
                System.out.println("Asiento no valido o ocupado. Intente nuevamente.");
            }
        } while (!asientoValido);

        liberarAsientoAnterior(asientoAnterior);
        entrada.setAsiento(nuevoAsiento);
        System.out.println("[DEBUG] Cambio de asiento exitoso: de " + asientoAnterior + " a " + nuevoAsiento);

        System.out.println("\nAsiento cambiado exitosamente.");
    }

    public static void editarCliente(Scanner input){
        if (totalClientes == 0) {
            System.out.println("No hay clientes registrados actualmente.");
            return;
        }
        
        System.out.println("\nIngrese el ID del cliente a editar:");
        int idCliente = pedirOpcion(input, 1, totalClientes, "ID invalido.");

        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("Cliente actual: " + cliente.getNombre() + ", Edad: " + cliente.getEdad());

        input.nextLine();
        System.out.println("Ingrese nuevo nombre:");
        String nuevoNombre = input.nextLine().trim();

        while (nuevoNombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio. Intente nuevamente");
            nuevoNombre = input.nextLine().trim();
        }

        System.out.println("Ingrese nueva edad:");
        int nuevaEdad = pedirOpcion(input, 1, 150, "Edad invalida. Intente nuevamente.");

        cliente.setNombre(nuevoNombre);
        cliente.setEdad(nuevaEdad);

        System.out.println("Datos actualizados.");
    }

    public static void eliminarCliente(Scanner input) {
        if (totalClientes == 0) {
            System.out.println("No hay clientes registrados actualmente.");
            return;
        }
        
        System.out.println("\nIngrese el ID del cliente a eliminar:");
        int idCliente = pedirOpcion(input, 1, totalClientes, "ID inválido.");
    
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
    
        for (int i = 0; i < totalVentas; i++) {
            if (ventas[i] != null && ventas[i].getIdCliente() == idCliente) {
                System.out.println("No se puede eliminar. El cliente tiene ventas registradas.");
                return;
            }
        }
    
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].getIdCliente() == idCliente) {
                for (int j = i; j < totalClientes - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                clientes[--totalClientes] = null;
                System.out.println("Cliente eliminado correctamente.");
                return;
            }
        }
    }
    
}



