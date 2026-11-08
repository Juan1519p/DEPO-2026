package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest
{
    private static final String RUTA_TEST = "data/test/";

    private Restaurante restaurante;

    @BeforeAll
    static void crearCarpetaFacturas( )
    {
        // cerrarYGuardarPedido escribe en ./facturas/, que no existe en el proyecto
        new File( "./facturas/" ).mkdirs( );
    }

    @BeforeEach
    void setUp( )
    {
        restaurante = new Restaurante( );
    }

    /**
     * Método auxiliar para no repetir la creación de los File en cada prueba
     */
    private void cargar( String ingredientes, String menu, String combos ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( new File( RUTA_TEST + ingredientes ), new File( RUTA_TEST + menu ),
                new File( RUTA_TEST + combos ) );
    }

    // ---------- Constructor ----------

    @Test
    void testRestauranteNuevoEstaVacio( )
    {
        assertTrue( restaurante.getIngredientes( ).isEmpty( ), "No debería haber ingredientes." );
        assertTrue( restaurante.getMenuBase( ).isEmpty( ), "No debería haber productos." );
        assertTrue( restaurante.getMenuCombos( ).isEmpty( ), "No debería haber combos." );
        assertTrue( restaurante.getPedidos( ).isEmpty( ), "No debería haber pedidos." );
        assertNull( restaurante.getPedidoEnCurso( ), "No debería haber pedido en curso." );
    }

    // ---------- Carga de archivos ----------

    @Test
    void testCargarArchivosValidos( ) throws Exception
    {
        cargar( "ingredientes.txt", "menu.txt", "combos.txt" );

        // Las líneas vacías deben ignorarse
        assertEquals( 2, restaurante.getIngredientes( ).size( ), "Número de ingredientes incorrecto." );
        assertEquals( 3, restaurante.getMenuBase( ).size( ), "Número de productos incorrecto." );
        assertEquals( 2, restaurante.getMenuCombos( ).size( ), "Número de combos incorrecto." );

        assertEquals( "lechuga", restaurante.getIngredientes( ).get( 0 ).getNombre( ) );
        assertEquals( 2500, restaurante.getIngredientes( ).get( 1 ).getCostoAdicional( ) );

        ProductoMenu primero = restaurante.getMenuBase( ).get( 0 );
        assertEquals( "corral", primero.getNombre( ) );
        assertEquals( 14000, primero.getPrecio( ) );

        // combo corral: (14000 + 5500 + 5000) * 0.90 = 22050
        assertEquals( "combo corral", restaurante.getMenuCombos( ).get( 0 ).getNombre( ) );
        assertEquals( 22050, restaurante.getMenuCombos( ).get( 0 ).getPrecio( ) );
        // combo papas: (5500 + 5000) * 0.95 = 9975
        assertEquals( 9975, restaurante.getMenuCombos( ).get( 1 ).getPrecio( ) );
    }

    @Test
    void testCargarArchivosRealesDelRestaurante( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( new File( "data/ingredientes.txt" ), new File( "data/menu.txt" ),
                new File( "data/combos.txt" ) );

        assertEquals( 15, restaurante.getIngredientes( ).size( ) );
        assertEquals( 22, restaurante.getMenuBase( ).size( ) );
        assertEquals( 4, restaurante.getMenuCombos( ).size( ) );
    }

    @Test
    void testIngredienteRepetido( )
    {
        IngredienteRepetidoException e = assertThrows( IngredienteRepetidoException.class,
                ( ) -> cargar( "ingredientes_repetidos.txt", "menu.txt", "combos.txt" ) );
        assertTrue( e.getMessage( ).contains( "lechuga" ), "El mensaje debe mencionar el ingrediente repetido." );
    }

    @Test
    void testProductoRepetido( )
    {
        ProductoRepetidoException e = assertThrows( ProductoRepetidoException.class,
                ( ) -> cargar( "ingredientes.txt", "menu_repetido.txt", "combos.txt" ) );
        assertTrue( e.getMessage( ).contains( "corral" ), "El mensaje debe mencionar el producto repetido." );
    }

    @Test
    void testPrecioInvalido( )
    {
        assertThrows( NumberFormatException.class,
                ( ) -> cargar( "ingredientes.txt", "menu_precio_invalido.txt", "combos.txt" ) );
    }

    @Test
    void testComboRepetido( )
    {
        ProductoRepetidoException e = assertThrows( ProductoRepetidoException.class,
                ( ) -> cargar( "ingredientes.txt", "menu.txt", "combos_repetidos.txt" ) );
        assertTrue( e.getMessage( ).contains( "combo corral" ), "El mensaje debe mencionar el combo repetido." );
    }

    @Test
    void testComboConProductoFaltante( )
    {
        ProductoFaltanteException e = assertThrows( ProductoFaltanteException.class,
                ( ) -> cargar( "ingredientes.txt", "menu.txt", "combos_producto_faltante.txt" ) );
        assertTrue( e.getMessage( ).contains( "malteada" ), "El mensaje debe mencionar el producto faltante." );
    }

    @Test
    void testArchivoInexistente( )
    {
        assertThrows( FileNotFoundException.class,
                ( ) -> cargar( "no_existe.txt", "menu.txt", "combos.txt" ) );
    }

    // ---------- Pedidos ----------

    @Test
    void testIniciarPedido( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 1" );
        assertNotNull( restaurante.getPedidoEnCurso( ), "Debería haber un pedido en curso." );
        assertEquals( "Juan", restaurante.getPedidoEnCurso( ).getNombreCliente( ) );
    }

    @Test
    void testIniciarPedidoConPedidoEnCurso( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 1" );
        YaHayUnPedidoEnCursoException e = assertThrows( YaHayUnPedidoEnCursoException.class,
                ( ) -> restaurante.iniciarPedido( "Ana", "Calle 2" ) );
        assertTrue( e.getMessage( ).contains( "Juan" ) && e.getMessage( ).contains( "Ana" ),
                "El mensaje debe mencionar a ambos clientes." );
    }

    @Test
    void testCerrarSinPedidoEnCurso( )
    {
        NoHayPedidoEnCursoException e = assertThrows( NoHayPedidoEnCursoException.class,
                ( ) -> restaurante.cerrarYGuardarPedido( ) );
        assertNotNull( e.getMessage( ), "La excepción debe tener un mensaje." );
    }

    @Test
    void testCerrarYGuardarPedido( ) throws Exception
    {
        cargar( "ingredientes.txt", "menu.txt", "combos.txt" );
        restaurante.iniciarPedido( "Juan", "Calle 1" );
        Pedido pedido = restaurante.getPedidoEnCurso( );
        pedido.agregarProducto( restaurante.getMenuBase( ).get( 0 ) );
        pedido.agregarProducto( restaurante.getMenuCombos( ).get( 0 ) );

        restaurante.cerrarYGuardarPedido( );

        // 1. Ya no hay pedido en curso
        assertNull( restaurante.getPedidoEnCurso( ), "Después de cerrar no debe haber pedido en curso." );

        // 2. La factura quedó en ./facturas/factura_<id>.txt con el contenido correcto
        File archivo = new File( "./facturas/factura_" + pedido.getIdPedido( ) + ".txt" );
        assertTrue( archivo.exists( ), "No se creó el archivo de la factura." );
        String contenido = Files.readString( archivo.toPath( ), Charset.defaultCharset( ) );
        assertEquals( pedido.generarTextoFactura( ), contenido, "El contenido de la factura no es el esperado." );

        // 3. El pedido quedó en el histórico
        assertTrue( restaurante.getPedidos( ).contains( pedido ), "El pedido cerrado debe quedar en la lista de pedidos." );

        archivo.delete( ); // limpiar
    }

    @Test
    void testIniciarOtroPedidoDespuesDeCerrar( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 1" );
        restaurante.cerrarYGuardarPedido( );
        File archivo = new File( "./facturas/factura_" + restaurante.getPedidos( ).get( 0 ).getIdPedido( ) + ".txt" );

        restaurante.iniciarPedido( "Ana", "Calle 2" );
        assertEquals( "Ana", restaurante.getPedidoEnCurso( ).getNombreCliente( ) );

        archivo.delete( );
    }
}