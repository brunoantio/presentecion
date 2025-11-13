import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Bodega bodega = new Bodega();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        // Productos Iniciales
        bodega.agregarProducto(new Producto("Leche Entera", 15, 5));
        bodega.agregarProducto(new Producto("Pan de Molde", 8, 3));
        bodega.agregarProducto(new Producto("Harina 1kg", 2, 10)); 
        bodega.agregarProducto(new Producto("Arroz 1kg", 30, 15));
        bodega.agregarProducto(new Producto("Tomates enlatados", 50, 20));

        do {
            mostrarMenu();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
                ejecutarOpcion(opcion, bodega, scanner);
            } else {
                System.out.println("⚠️ Opción no válida. Ingrese un número.");
                scanner.nextLine();
            }
        } while (opcion != 0);

        System.out.println("¡Gracias por usar el sistema de inventario!");
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==== GESTIÓN DE INVENTARIO ====");
        System.out.println("1. Gestionar Productos (Crear/Listar)");
        System.out.println("2. Registrar Entradas (Sumar Stock)");
        System.out.println("3. Registrar Salidas (Restar Stock)");
        System.out.println("4. Generar Reportes (Total/Crítico)");
        System.out.println("5. Abrir Reporte Gráfico (Interacción)"); 
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ejecutarOpcion(int opcion, Bodega bodega, Scanner scanner) {
        switch (opcion) {
            case 1: gestionProductos(bodega, scanner); break;
            case 2: registrarMovimiento(bodega, scanner, "ENTRADA"); break;
            case 3: registrarMovimiento(bodega, scanner, "SALIDA"); break;
            case 4: generarReportes(bodega, scanner); break;
            case 5:
                // Abre la GUI en el hilo de Swing
                SwingUtilities.invokeLater(() -> { new ReporteGUI(bodega); });
                System.out.println("Ventana de reporte lanzada. Vuelva a la consola para continuar.");
                break;
            case 0: break;
            default: System.out.println("Opción inválida. Intente de nuevo.");
        }
    }
    
    private static void gestionProductos(Bodega bodega, Scanner scanner) {
        // ... (Implementación de creación de productos)
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Crear Nuevo Producto");
        System.out.println("2. Ver Inventario Completo");
        System.out.print("Seleccione: ");
        
        if (scanner.hasNextInt()) {
            int subOpcion = scanner.nextInt();
            scanner.nextLine();
            if (subOpcion == 1) {
                System.out.print("Nombre del Producto: ");
                String nombre = scanner.nextLine();
                System.out.print("Stock Inicial: ");
                int stock = scanner.nextInt();
                System.out.print("Stock Mínimo de Alerta: ");
                int stockMinimo = scanner.nextInt();
                scanner.nextLine(); 
                bodega.agregarProducto(new Producto(nombre, stock, stockMinimo));
                System.out.println("✅ Producto '" + nombre + "' agregado con éxito.");
            } else if (subOpcion == 2) {
                bodega.generarReporteTotal();
            } else {
                System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("Opción no válida.");
            scanner.nextLine();
        }
    }

    private static void registrarMovimiento(Bodega bodega, Scanner scanner, String tipo) {
        // ... (Implementación de entradas/salidas)
        System.out.println("\n--- REGISTRAR " + tipo + " ---");
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la cantidad a " + (tipo.equals("ENTRADA") ? "sumar" : "restar") + ": ");
        if (scanner.hasNextInt()) {
            int cantidad = scanner.nextInt();
            scanner.nextLine(); 

            boolean exito;
            if (tipo.equals("ENTRADA")) {
                exito = bodega.agregarStock(nombre, cantidad);
            } else {
                exito = bodega.restarStock(nombre, cantidad);
            }

            if (exito) {
                System.out.println("✅ " + tipo + " de stock registrada con éxito para " + nombre + ".");
            } else {
                System.out.println("❌ ERROR: No se pudo registrar la " + tipo.toLowerCase() + ".");
                System.out.println("Posiblemente el producto no existe o, en caso de SALIDA, el stock es insuficiente.");
            }
        } else {
            System.out.println("Cantidad no válida.");
            scanner.nextLine();
        }
    }

    private static void generarReportes(Bodega bodega, Scanner scanner) {
        // ... (Implementación de reportes)
        System.out.println("\n--- GENERAR REPORTES ---");
        System.out.println("1. Inventario Total");
        System.out.println("2. Stock Crítico");
        System.out.print("Seleccione: ");

        if (scanner.hasNextInt()) {
            int subOpcion = scanner.nextInt();
            scanner.nextLine();
            if (subOpcion == 1) {
                bodega.generarReporteTotal();
            } else if (subOpcion == 2) {
                bodega.generarReporteCritico();
            } else {
                System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("Opción no válida.");
            scanner.nextLine();
        }
    }
}