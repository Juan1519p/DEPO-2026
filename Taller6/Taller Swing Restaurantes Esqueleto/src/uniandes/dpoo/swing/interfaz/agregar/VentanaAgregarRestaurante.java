package uniandes.dpoo.swing.interfaz.agregar;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uniandes.dpoo.swing.interfaz.principal.VentanaPrincipal;

@SuppressWarnings("serial")
public class VentanaAgregarRestaurante extends JFrame
{
    /**
     * El panel donde se editan los detalles del restaurante
     */
    private PanelEditarRestaurante panelDetalles;

    /**
     * El panel con los botones para agregar un restaurante o cerrar la ventana
     */
    private PanelBotonesAgregar panelBotones;

    /**
     * El panel para marcar la ubicación del restaurante
     */
    private PanelMapaAgregar panelMapa;

    /**
     * La ventana principal de la aplicación
     */
    private VentanaPrincipal ventanaPrincipal;

    public VentanaAgregarRestaurante( VentanaPrincipal principal )
    {
        this.ventanaPrincipal = principal;
        setLayout( new BorderLayout( ) );

        panelMapa = new PanelMapaAgregar( );
        add( panelMapa, BorderLayout.CENTER );

        panelDetalles = new PanelEditarRestaurante( );
        panelBotones = new PanelBotonesAgregar( this );
        JPanel panelSur = new JPanel( new BorderLayout( ) );
        panelSur.add( panelDetalles, BorderLayout.CENTER );
        panelSur.add( panelBotones, BorderLayout.SOUTH );
        add( panelSur, BorderLayout.SOUTH );
        
        pack( );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setResizable( false );
    }

    /**
     * Le pide al panelDetalles los datos del nuevo restaurante y se los envía a la ventana principal para que cree el nuevo restaurante, y luego cierra la ventana.
     */
    public void agregarRestaurante( )
    {
    	int[] coordenadas = panelMapa.getCoordenadas( );
    	ventanaPrincipal.agregarRestaurante( panelDetalles.getNombre( ), panelDetalles.getCalificacion( ),
    	        coordenadas[ 0 ], coordenadas[ 1 ], panelDetalles.getVisitado( ) );
    	dispose( );
    }

    /**
     * Cierra la ventana sin crear un nuevo restaurante
     */
    public void cerrarVentana( )
    {
        dispose( );
    }

}
