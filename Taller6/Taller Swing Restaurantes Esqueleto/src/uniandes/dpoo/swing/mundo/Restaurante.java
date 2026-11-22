package uniandes.dpoo.swing.mundo;

public class Restaurante
{
    private String nombre;

    private int calificacion;

    private int coordenadaX;

    private int coordenadaY;

    public boolean visitado;

    public Restaurante( String nombre, int calificacion, int x, int y, boolean visitado )
    {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.visitado = visitado;
    }

    public String getNombre( )
    {
        return nombre;
    }

    public int getCalificacion( )
    {
        return calificacion;
    }

    public int getX( )
    {
        return coordenadaX;
    }

    public int getY( )
    {
        return coordenadaY;
    }

    public boolean isVisitado( )
    {
        return visitado;
    }

    @Override
    public String toString( )
    {
     // TODO completar para que retorne el nombre del restaurante
    	return nombre;
    }

}
