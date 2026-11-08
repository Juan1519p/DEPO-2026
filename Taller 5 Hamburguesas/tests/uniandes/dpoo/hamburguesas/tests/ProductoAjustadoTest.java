package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
    private ProductoMenu base;
    private ProductoAjustado ajustado;
    private Ingrediente queso;
    private Ingrediente tomate;

    @BeforeEach
    void setUp( )
    {
        base = new ProductoMenu( "corral", 14000 );
        ajustado = new ProductoAjustado( base );
        queso = new Ingrediente( "queso americano", 2500 );
        tomate = new Ingrediente( "tomate", 1000 );
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", ajustado.getNombre( ), "El nombre debe ser el del producto base." );
    }

    @Test
    void testGetPrecioSinAjustes( )
    {
        assertEquals( 14000, ajustado.getPrecio( ), "Sin ajustes, el precio debe ser el del producto base." );
    }

    @Test
    void testGetPrecioConAgregados( )
    {
        // El mismo ingrediente puede agregarse varias veces
        ajustado.agregarIngrediente( queso );
        ajustado.agregarIngrediente( queso );
        assertEquals( 19000, ajustado.getPrecio( ), "El precio debe sumar cada ingrediente agregado." );
    }

    @Test
    void testGetPrecioConEliminados( )
    {
        // Quitar ingredientes NO reduce el precio
        ajustado.eliminarIngrediente( tomate );
        assertEquals( 14000, ajustado.getPrecio( ), "Eliminar un ingrediente no debe cambiar el precio." );
    }

    @Test
    void testGenerarTextoFacturaSinAjustes( )
    {
        String esperado = "corral\n"
                        + "            14000\n";
        assertEquals( esperado, ajustado.generarTextoFactura( ), "La factura sin ajustes no es la esperada." );
    }

    @Test
    void testGenerarTextoFacturaConAjustes( )
    {
        ajustado.agregarIngrediente( queso );
        ajustado.agregarIngrediente( queso );
        ajustado.eliminarIngrediente( tomate );

        String esperado = "corral\n"
                        + "    +queso americano                2500\n"
                        + "    +queso americano                2500\n"
                        + "    -tomate\n"
                        + "            19000\n";
        assertEquals( esperado, ajustado.generarTextoFactura( ), "La factura con ajustes no es la esperada." );
    }
}