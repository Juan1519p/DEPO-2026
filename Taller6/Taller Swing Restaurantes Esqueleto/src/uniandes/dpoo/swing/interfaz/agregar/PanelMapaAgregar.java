package uniandes.dpoo.swing.interfaz.agregar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class PanelMapaAgregar extends JPanel implements MouseListener
{
    /**
     * La etiqueta donde se muestra el mapa y donde se debe marcar la ubicación del nuevo restaurante
     */
    private JLabel labMapa;

    /**
     * La coordenada X del nuevo restaurante
     */
    private int x;

    /**
     * La coordenada Y del nuevo restaurante
     */
    private int y;

    public PanelMapaAgregar( )
    {
        this.labMapa = new JLabel( new ImageIcon( "./imagenes/mapa.png" ) );
        labMapa.setBorder( new LineBorder( Color.DARK_GRAY ) );
        labMapa.addMouseListener( this );
        add( labMapa );

        this.x = 0;
        this.y = 0;
    }

    /**
     * Retorna las coordenadas del restaurante
     * @return
     */
    public int[] getCoordenadas( )
    {
        return new int[]{ x, y };
    }

    @Override
    public void paint( Graphics g )
    {
        super.paint( g );

        // Pinta un círculo rojo en la posición designada para el restaurante
        Graphics2D g2d = ( Graphics2D )g;
        g2d.setColor( Color.red );
        g2d.fillOval( x - 3, y - 3, 7, 7 );
    }

    @Override
    public void mouseClicked( MouseEvent e )
    {
        this.x = e.getX( );
        this.y = e.getY( );
        repaint( );
    }

    @Override
    public void mousePressed( MouseEvent e )
    {
    }

    @Override
    public void mouseReleased( MouseEvent e )
    {
    }

    @Override
    public void mouseEntered( MouseEvent e )
    {
    }

    @Override
    public void mouseExited( MouseEvent e )
    {
    }

}
