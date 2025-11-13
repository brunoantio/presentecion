public class Producto {
    private String nombre;
    private int stock;
    private int stockMinimo;

    public Producto(String nombre, int stock, int stockMinimo) {
        this.nombre = nombre;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    // Getters y Setter
    public String getNombre() { return nombre; }
    public int getStock() { return stock; }
    public int getStockMinimo() { return stockMinimo; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%-25s | %-10d | %-15d", nombre, stock, stockMinimo);
    }
}