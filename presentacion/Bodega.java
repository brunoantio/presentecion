import java.util.ArrayList;
import java.util.List;

public class Bodega {
    private List<Producto> inventario = new ArrayList<>();

    public Producto buscarProducto(String nombre) {
        for (Producto p : inventario) { // Uso de iterador
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> getInventario() { return inventario; }
    public void agregarProducto(Producto producto) { inventario.add(producto); }

    public boolean agregarStock(String nombreProducto, int cantidad) {
        Producto producto = buscarProducto(nombreProducto);
        if (producto != null && cantidad > 0) {
            producto.setStock(producto.getStock() + cantidad);
            return true;
        }
        return false;
    }

    public boolean restarStock(String nombreProducto, int cantidad) {
        Producto producto = buscarProducto(nombreProducto);
        if (producto != null && cantidad > 0) {
            int nuevoStock = producto.getStock() - cantidad;
            if (nuevoStock >= 0) { // No permite stock en negativo
                producto.setStock(nuevoStock);
                return true;
            }
        }
        return false;
    }

    public void generarReporteTotal() {
        System.out.println("\n--- INVENTARIO TOTAL ---");
        // ... (lógica de reporte)
        System.out.println(String.format("%-25s | %-10s | %-15s", "PRODUCTO", "STOCK", "STOCK MÍNIMO"));
        System.out.println("---------------------------------------------------------");
        for (Producto p : inventario) { 
            System.out.println(p.toString());
        }
        System.out.println("---------------------------------------------------------");
    }

    public void generarReporteCritico() {
        System.out.println("\n--- PRODUCTOS CON STOCK CRÍTICO ---");
        boolean hayCriticos = false;
        for (Producto p : inventario) {
            if (p.getStock() <= p.getStockMinimo()) {
                System.out.println("🔴 " + p.getNombre() + " - ¡ATENCIÓN! Stock actual: " + p.getStock() + " (Mínimo: " + p.getStockMinimo() + ")");
                hayCriticos = true;
            }
        }
        if (!hayCriticos) {
            System.out.println("✅ Todos los productos tienen stock suficiente.");
        }
    }
}
