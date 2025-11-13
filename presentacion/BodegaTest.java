import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para Bodega.java. 
 * Asegura que los métodos de gestión de stock cumplen con los requerimientos.
 * * NOTA: Requiere tener la librería JUnit 5 (JUnit Jupiter) configurada.
 */
public class BodegaTest {

    private Bodega bodega;
    private final String NOMBRE_PRODUCTO = "Leche Prueba";

    @BeforeEach
    void setUp() {
        // Inicializa la bodega y agrega un producto base antes de cada prueba
        bodega = new Bodega();
        Producto producto = new Producto(NOMBRE_PRODUCTO, 10, 5); // Stock inicial 10, Mínimo 5
        bodega.agregarProducto(producto);
    }

    // Requerimiento 1: Prueba agregarStock (Suma)
    @Test
    void testAgregarStockSumaCorrectamente() {
        // Acción: 10 + 5
        bodega.agregarStock(NOMBRE_PRODUCTO, 5);
        
        // Verificación: El stock debe ser 15
        assertEquals(15, bodega.buscarProducto(NOMBRE_PRODUCTO).getStock(), 
                     "El stock debe ser 15 (10+5)");
    }

    // Requerimiento 2: Prueba restarStock (Éxito)
    @Test
    void testRestarStockDescuentaCorrectamente() {
        // Acción: 10 - 3
        boolean resultado = bodega.restarStock(NOMBRE_PRODUCTO, 3);
        
        // Verificación 1: La operación debe ser exitosa (true)
        assertTrue(resultado, "La operación debe ser exitosa");
        
        // Verificación 2: El stock debe ser 7
        assertEquals(7, bodega.buscarProducto(NOMBRE_PRODUCTO).getStock(), 
                     "El stock debe ser 7 (10-3)");
    }

    // Requerimiento 3: Prueba restarStock (Sin Negativos)
    @Test
    void testRestarStockNoPermiteNegativos() {
        // Acción: Intentar 10 - 11 (Venta excesiva)
        boolean resultado = bodega.restarStock(NOMBRE_PRODUCTO, 11);

        // Verificación 1: La operación debe fallar (retornar false)
        assertFalse(resultado, "Restar stock no debe ser exitoso si resulta en stock negativo");

        // Verificación 2: El stock debe permanecer inalterado (en 10)
        assertEquals(10, bodega.buscarProducto(NOMBRE_PRODUCTO).getStock(), 
                     "El stock debe permanecer en 10 si se intenta vender de más");
    }
}
    

