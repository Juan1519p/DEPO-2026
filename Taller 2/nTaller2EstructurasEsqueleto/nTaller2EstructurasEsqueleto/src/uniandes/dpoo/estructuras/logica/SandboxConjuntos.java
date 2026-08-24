package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class SandboxConjuntos
{
    private NavigableSet<String> arbolCadenas;

    public SandboxConjuntos()
    {
        arbolCadenas = new TreeSet<String>();
    }

    public List<String> getCadenasComoLista()
    {
        return new ArrayList<String>(arbolCadenas);
    }

    public List<String> getCadenasComoListaInvertida()
    {
        return new ArrayList<String>(arbolCadenas.descendingSet());
    }

    public String getPrimera()
    {
        if (arbolCadenas.isEmpty())
        {
            return null;
        }

        return arbolCadenas.first();
    }

    public String getUltima()
    {
        if (arbolCadenas.isEmpty())
        {
            return null;
        }

        return arbolCadenas.last();
    }

    public Collection<String> getSiguientes(String cadena)
    {
        return new ArrayList<String>(arbolCadenas.tailSet(cadena, true));
    }

    public int getCantidadCadenas()
    {
        return arbolCadenas.size();
    }

    public void agregarCadena(String cadena)
    {
        arbolCadenas.add(cadena);
    }

    public void eliminarCadena(String cadena)
    {
        arbolCadenas.remove(cadena);
    }

    public void eliminarCadenaSinMayusculasOMinusculas(String cadena)
    {
        String encontrada = null;

        for (String actual : arbolCadenas)
        {
            if (actual.equalsIgnoreCase(cadena))
            {
                encontrada = actual;
                break;
            }
        }

        if (encontrada != null)
        {
            arbolCadenas.remove(encontrada);
        }
    }

    public void eliminarPrimera()
    {
        if (!arbolCadenas.isEmpty())
        {
            arbolCadenas.pollFirst();
        }
    }

    public void reiniciarConjuntoCadenas(List<Object> objetos)
    {
        arbolCadenas.clear();

        for (Object objeto : objetos)
        {
            arbolCadenas.add(objeto.toString());
        }
    }

    public void volverMayusculas()
    {
        NavigableSet<String> nuevoConjunto = new TreeSet<String>();

        for (String cadena : arbolCadenas)
        {
            nuevoConjunto.add(cadena.toUpperCase());
        }

        arbolCadenas = nuevoConjunto;
    }

    public TreeSet<String> invertirCadenas()
    {
        TreeSet<String> invertido = new TreeSet<String>(Comparator.reverseOrder());

        invertido.addAll(arbolCadenas);

        return invertido;
    }

    public boolean compararElementos(String[] otroArreglo)
    {
        for (String cadena : otroArreglo)
        {
            if (!arbolCadenas.contains(cadena))
            {
                return false;
            }
        }

        return true;
    }
}