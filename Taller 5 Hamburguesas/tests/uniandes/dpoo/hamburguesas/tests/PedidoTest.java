package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest
{
    private Pedido pedido;
    private ProductoMenu corral;
    private ProductoMenu gaseosa;

    // JUnit crea una carpeta temporal para cada prueba y la borra al terminar
    @TempDir
    File carpetaTemporal;

    @BeforeEach
    void setUp( )
    {
        pedido = new Pedido( "Juan", "Calle 1" );
        corral = new ProductoMenu( "corral", 14000 );
        gaseosa = new ProductoMenu( "gaseosa", 5000 );
    }

    // ---------- Constructor y getters ----------

    @Test
    void testGetNombreCliente( )
    {
        assertEquals( "Juan", pedido.getNombreCliente( ), "El nombre del cliente no es el esperado." );
    }

    @Test
    void testIdsConsecutivos( )
    {
        // numeroPedidos es static: no se puede suponer que el id sea 0
        Pedido otro = new Pedido( "Ana", "Calle 2" );
        assertEquals( pedido.getIdPedido( ) + 1, otro.getIdPedido( ), "Los ids deben ser consecutivos." );
    }

    // ---------- Precios ----------

    @Test
    void testPrecioPedidoVacio( )
    {
        assertEquals( 0, pedido.getPrecioTotalPedido( ), "Un pedido vacío debe costar 0." );
    }

    @Test
    void testPrecioTotalConProductos( )
    {
        pedido.agregarProducto( corral );
        pedido.agregarProducto( gaseosa );
        // Neto 19000 + IVA 3610 = 22610
        assertEquals( 22610, pedido.getPrecioTotalPedido( ), "El precio total no es el esperado." );
    }

    @Test
    void testPrecioTotalConTodosLosTiposDeProducto( )
    {
        // Producto ajustado: 14000 + 2500 = 16500
        ProductoAjustado ajustado = new ProductoAjustado( corral );
        ajustado.agregarIngrediente( new Ingrediente( "queso americano", 2500 ) );

        // Combo: (14000 + 5000) * 0.9 = 17100
        ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>( );
        items.add( corral );
        items.add( gaseosa );
        Combo combo = new Combo( "combo prueba", 0.10, items );

        pedido.agregarProducto( corral );    // 14000
        pedido.agregarProducto( ajustado );  // 16500
        pedido.agregarProducto( combo );     // 17100

        // Neto 47600, IVA (int)(47600 * 0.19) = 9044, total 56644
        assertEquals( 56644, pedido.getPrecioTotalPedido( ), "El total con productos de distinto tipo no es el esperado." );
    }

    // ---------- Factura ----------

    @Test
    void testGenerarTextoFactura( )
    {
        pedido.agregarProducto( corral );
        pedido.agregarProducto( gaseosa );

        String esperado = "Cliente: Juan\n"
                        + "Dirección: Calle 1\n"
                        + "----------------\n"
                        + "corral\n"
                        + "            14000\n"
                        + "gaseosa\n"
                        + "            5000\n"
                        + "----------------\n"
                        + "Precio Neto:  19000\n"
                        + "IVA:          3610\n"
                        + "Precio Total: 22610\n";
        assertEquals( esperado, pedido.generarTextoFactura( ), "El texto de la factura no es el esperado." );
    }

    @Test
    void testGenerarTextoFacturaPedidoVacio( )
    {
        String factura = pedido.generarTextoFactura( );
        assertTrue( factura.contains( "Cliente: Juan" ), "La factura debe incluir el nombre del cliente." );
        assertTrue( factura.contains( "Precio Total: 0" ), "Un pedido vacío debe tener total 0." );
    }

    @Test
    void testGuardarFactura( ) throws Exception
    {
        pedido.agregarProducto( corral );
        pedido.agregarProducto( gaseosa );
        File archivo = new File( carpetaTemporal, "factura.txt" );

        pedido.guardarFactura( archivo );

        assertTrue( archivo.exists( ), "El archivo de la factura debió crearse." );
        String contenido = Files.readString( archivo.toPath( ), Charset.defaultCharset( ) );
        assertEquals( pedido.generarTextoFactura( ), contenido, "El archivo no contiene la factura correcta." );
    }

    @Test
    void testGuardarFacturaRutaInvalida( )
    {
        // La carpeta "no_existe" no existe, así que no se puede crear el archivo
        File archivo = new File( carpetaTemporal, "no_existe/factura.txt" );
        assertThrows( FileNotFoundException.class, ( ) -> pedido.guardarFactura( archivo ),
                "Debió lanzarse FileNotFoundException." );
    }
}