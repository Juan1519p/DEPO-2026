package uniandes.dpoo.swing.interfaz.principal;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uniandes.dpoo.swing.mundo.Restaurante;

@SuppressWarnings("serial")
public class PanelLista extends JPanel implements ListSelectionListener
{
    /**
     * El widget donde se muestra la lista de restaurantes
     */
    private JList<Restaurante> listaDeRestaurantes;

    /**
     * El modelo de datos que se muestra en la lista de restaurantes
     */
    private DefaultListModel<Restaurante> dataModel;

    /**
     * La ventana principal que contiene a este panel
     */
    private VentanaPrincipal ventanaPrincipal;

    public PanelLista( VentanaPrincipal ventanaPrincipal )
    {
        this.ventanaPrincipal = ventanaPrincipal;
        setBorder( new TitledBorder( "Restaurantes" ) );
        setLayout( new BorderLayout( ) );

        // Crea la lista con un modelo de datos en el que puede haber sólo restaurantes
        dataModel = new DefaultListModel<>( );
        listaDeRestaurantes = new JList<>( dataModel );
        listaDeRestaurantes.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        listaDeRestaurantes.addListSelectionListener( this );

        // Crear un panel con barras de desplazamiento para la lista
        JScrollPane scroll = new JScrollPane( listaDeRestaurantes );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

        add( scroll );
    }

    /**
     * Actualiza la lista de restaurantes que se muestran en la lista.
     * 
     * Para esto, lo que se modifica es el model (y no el JList)
     * @param restaurantes
     */
    public void actualizarRestaurantes( List<Restaurante> restaurantes )
    {
        List<Restaurante> nuevosRestaurantes = new ArrayList<Restaurante>( );
        for( Restaurante q : restaurantes )
        {
            if( !dataModel.contains( q ) )
                nuevosRestaurantes.add( q );
        }
        dataModel.addAll( nuevosRestaurantes );
    }

    @Override
    public void valueChanged( ListSelectionEvent e )
    {
        // Revisa cuál es el restaurante seleccionado actualmente
        Restaurante seleccionado = listaDeRestaurantes.getSelectedValue( );

        // Le envía la ventana principal el restaurante seleccionado para que se actualice el resto de la interfaz
        this.ventanaPrincipal.cambiarRestauranteSeleccionado( seleccionado );
    }

    /**
     * Cambia el restaurante seleccionado en la lista
     * @param restaurante
     */
    public void seleccionarRestaurante( Restaurante restaurante )
    {
        listaDeRestaurantes.setSelectedValue( restaurante, true );
    }

}
