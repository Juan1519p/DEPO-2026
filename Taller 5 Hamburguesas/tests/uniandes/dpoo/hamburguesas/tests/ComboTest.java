package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
    private Combo combo;
    private ArrayList<ProductoMenu> items;

    @BeforeEach
    void setUp( )
    {
        // Escenario: combo corral = 14000 + 5500 + 5000 = 24500, con 10% de descuento
        items = new ArrayList<ProductoMenu>( );
        items.add( new ProductoMenu( "corral", 14000 ) );
        items.add( new ProductoMenu( "papas medianas", 5500 ) );
        items.add( new ProductoMenu( "gaseosa", 5000 ) );
        combo = new Combo( "combo corral", 0.10, items );
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "combo corral", combo.getNombre( ), "El nombre del combo no es el esperado." );
    }

    @Test
    void testGetPrecioAplicaDescuento( )
    {
        // 24500 * 0.9 = 22050
        assertEquals( 22050, combo.getPrecio( ), "El precio del combo no aplica bien el descuento." );
    }

    @Test
    void testGetPrecioSinDescuento( )
    {
        Combo sinDescuento = new Combo( "sin descuento", 0, items );
        assertEquals( 24500, sinDescuento.getPrecio( ), "Con descuento 0 el precio debe ser la suma de los productos." );
    }

    @Test
    void testGetPrecioComboVacio( )
    {
        Combo vacio = new Combo( "vacio", 0.10, new ArrayList<ProductoMenu>( ) );
        assertEquals( 0, vacio.getPrecio( ), "Un combo sin productos debe costar 0." );
    }

    @Test
    void testConstructorCopiaLaLista( )
    {
        // Si se modifica la lista original, el combo no debe cambiar
        items.add( new ProductoMenu( "papas grandes", 6900 ) );
        assertEquals( 22050, combo.getPrecio( ), "El combo no debe verse afectado por cambios en la lista original." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        String esperado = "Combo combo corral\n"
                        + " Descuento: 0.1\n"
                        + "            22050\n";
        assertEquals( esperado, combo.generarTextoFactura( ), "El texto de la factura no es el esperado." );
    }
}