package uniandes.dpoo.swing.interfaz.agregar;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelEditarRestaurante extends JPanel
{
    /**
     * El campo para que el usuario ingrese el nombre del restaurante
     */
    private JTextField txtNombre;

    /**
     * Un selector (JComboBox) para que el usuario seleccione la calificación (1 a 5) del restaurante
     */
    private JComboBox<String> cbbCalificacion;

    /**
     * Un selector (JComboBox) para que el usuario indique si ya visitó el restaurante o no
     */
    private JComboBox<String> cbbVisitado;

    public PanelEditarRestaurante( )
    {
    	txtNombre = new JTextField( 10 );
    	JPanel pNombre = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
    	pNombre.add( new JLabel( "Nombre" ) );
    	pNombre.add( txtNombre );

    	cbbCalificacion = new JComboBox<String>( new String[]{ "1", "2", "3", "4", "5" } );
    	JPanel pCalificacion = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
    	pCalificacion.add( new JLabel( "Calificación" ) );
    	pCalificacion.add( cbbCalificacion );

    	cbbVisitado = new JComboBox<String>( new String[]{ "Si", "No" } );
    	JPanel pVisitado = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
    	pVisitado.add( new JLabel( "Visitado" ) );
    	pVisitado.add( cbbVisitado );

    	setLayout( new GridLayout( 3, 1 ) );
    	add( pNombre );
    	add( pCalificacion );
    	add( pVisitado );

    }

    /**
     * Indica si en el selector se seleccionó la opción que dice que el restaurante fue visitado
     * @return
     */
    public boolean getVisitado( )
    {
    	return "Si".equals( cbbVisitado.getSelectedItem( ) );
        
    }

    /**
     * Indica la calificación marcada en el selector
     * @return
     */
    public int getCalificacion( )
    {
        String calif = ( String )cbbCalificacion.getSelectedItem( );
        return Integer.parseInt( calif );
    }

    /**
     * Indica el nombre digitado para el restaurante
     * @return
     */
    public String getNombre( )
    {
        // TODO completar
    	return txtNombre.getText( );
    }
}
