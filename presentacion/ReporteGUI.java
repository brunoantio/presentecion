import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.util.List;

public class ReporteGUI extends JFrame {

    private Bodega bodega;
    private JTextArea textAreaReporte;
    private JComboBox<String> comboProductos; 

    public ReporteGUI(Bodega bodega) {
        this.bodega = bodega;
        
        setTitle("Reporte de Inventario (GUI)");
        setSize(650, 450); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout(10, 10));
        
        JLabel labelTitulo = new JLabel("  Inventario Total de Bodega (Interactivo)", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        textAreaReporte = new JTextArea();
        textAreaReporte.setEditable(false); 
        textAreaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        JScrollPane scrollPane = new JScrollPane(textAreaReporte);
        
        // Panel de Controles (Abajo)
        JPanel panelControles = new JPanel(new BorderLayout());
        
        // Panel para selección de producto
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboProductos = new JComboBox<>();
        panelSeleccion.add(new JLabel("Seleccionar Producto:"));
        panelSeleccion.add(comboProductos);

        // Panel para botones de acción (3 Botones)
        JPanel panelAcciones = new JPanel(new FlowLayout());
        
        JButton btnActualizar = new JButton("Actualizar Reporte");
        btnActualizar.addActionListener(e -> actualizarReporte());
        
        JButton btnVentaRapida = new JButton("Venta Rápida (-1)");
        btnVentaRapida.addActionListener(e -> {
            realizarVentaRapida(); 
            actualizarReporte(); 
        });
        
        JButton btnAñadirStock = new JButton("Añadir Stock (+5)");
        btnAñadirStock.addActionListener(e -> {
            añadirStockDePrueba(5); 
            actualizarReporte(); 
        });
        
        panelAcciones.add(btnActualizar);
        panelAcciones.add(btnVentaRapida);
        panelAcciones.add(btnAñadirStock);

        panelControles.add(panelSeleccion, BorderLayout.NORTH);
        panelControles.add(panelAcciones, BorderLayout.CENTER);

        add(labelTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);
        
        actualizarReporte();
        
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    // Método auxiliar para poblar el JComboBox
    private void actualizarListaProductos(List<Producto> productos) {
        Object selectedItem = comboProductos.getSelectedItem();
        comboProductos.removeAllItems();
        if (productos != null) {
            for (Producto p : productos) {
                comboProductos.addItem(p.getNombre());
            }
        }
        if (selectedItem != null && comboProductos.getItemCount() > 0) {
            comboProductos.setSelectedItem(selectedItem);
        }
    }
    
    private void realizarVentaRapida() {
        String nombreProducto = (String) comboProductos.getSelectedItem();
        if (nombreProducto == null) {
             JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }
        if (bodega.restarStock(nombreProducto, 1)) {
            JOptionPane.showMessageDialog(this, "Venta de 1 unidad de " + nombreProducto + " registrada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo realizar la venta rápida. Stock insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void añadirStockDePrueba(int cantidad) {
        String nombreProducto = (String) comboProductos.getSelectedItem();
        if (nombreProducto == null) {
             JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        if (bodega.agregarStock(nombreProducto, cantidad)) {
            JOptionPane.showMessageDialog(this, "Entrada de " + cantidad + " unidades de " + nombreProducto + " registrada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir stock.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Genera el reporte y actualiza el JComboBox
    private void actualizarReporte() {
        StringBuilder reporte = new StringBuilder();
        List<Producto> productos = bodega.getInventario();

        actualizarListaProductos(productos);
        
        reporte.append("------------------------------------------------------------------------\n");
        reporte.append(String.format("%-25s | %-10s | %-15s\n", "PRODUCTO", "STOCK", "STOCK MÍNIMO"));
        reporte.append("------------------------------------------------------------------------\n");

        if (productos.isEmpty()) {
            reporte.append("No hay productos en la bodega.\n");
        } else {
            for (Producto p : productos) { 
                reporte.append(p.toString()).append("\n");
            }
        }
        reporte.append("------------------------------------------------------------------------\n");

        textAreaReporte.setText(reporte.toString());
    }
}