package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SandboxMapas
{
    private Map<String, String> mapaCadenas;

    public SandboxMapas()
    {
        mapaCadenas = new HashMap<String, String>();
    }

    public List<String> getValoresComoLista()
    {
        List<String> valores = new ArrayList<String>(mapaCadenas.values());
        Collections.sort(valores);
        return valores;
    }

    public List<String> getLlavesComoListaInvertida()
    {
        List<String> llaves = new ArrayList<String>(mapaCadenas.keySet());
        Collections.sort(llaves, Collections.reverseOrder());
        return llaves;
    }

    public String getPrimera()
    {
        if (mapaCadenas.isEmpty())
        {
            return null;
        }

        String primera = null;

        for (String llave : mapaCadenas.keySet())
        {
            if (primera == null || llave.compareTo(primera) < 0)
            {
                primera = llave;
            }
        }

        return primera;
    }

    public String getUltima()
    {
        if (mapaCadenas.isEmpty())
        {
            return null;
        }

        String ultima = null;

        for (String valor : mapaCadenas.values())
        {
            if (ultima == null || valor.compareTo(ultima) > 0)
            {
                ultima = valor;
            }
        }

        return ultima;
    }

    public Collection<String> getLlaves()
    {
        Collection<String> llavesMayusculas = new ArrayList<String>();

        for (String llave : mapaCadenas.keySet())
        {
            llavesMayusculas.add(llave.toUpperCase());
        }

        return llavesMayusculas;
    }

    public int getCantidadCadenasDiferentes()
    {
        Set<String> valoresDiferentes = new HashSet<String>(mapaCadenas.values());
        return valoresDiferentes.size();
    }

    public void agregarCadena(String cadena)
    {
        String llave = invertirCadena(cadena);
        mapaCadenas.put(llave, cadena);
    }

    public void eliminarCadenaConLLave(String llave)
    {
        mapaCadenas.remove(llave);
    }

    public void eliminarCadenaConValor(String valor)
    {
        String llaveAEliminar = null;

        for (Map.Entry<String, String> entrada : mapaCadenas.entrySet())
        {
            if (entrada.getValue().equals(valor))
            {
                llaveAEliminar = entrada.getKey();
                break;
            }
        }

        if (llaveAEliminar != null)
        {
            mapaCadenas.remove(llaveAEliminar);
        }
    }

    public void reiniciarMapaCadenas(List<Object> objetos)
    {
        mapaCadenas.clear();

        for (Object objeto : objetos)
        {
            String cadena = objeto.toString();
            String llave = invertirCadena(cadena);

            mapaCadenas.put(llave, cadena);
        }
    }

    public void volverMayusculas()
    {
        Map<String, String> nuevoMapa = new HashMap<String, String>();

        for (Map.Entry<String, String> entrada : mapaCadenas.entrySet())
        {
            nuevoMapa.put(entrada.getKey().toUpperCase(), entrada.getValue());
        }

        mapaCadenas = nuevoMapa;
    }

    public boolean compararValores(String[] otroArreglo)
    {
        for (String cadena : otroArreglo)
        {
            if (!mapaCadenas.containsValue(cadena))
            {
                return false;
            }
        }

        return true;
    }

    private String invertirCadena(String cadena)
    {
        String invertida = "";

        for (int i = cadena.length() - 1; i >= 0; i--)
        {
            invertida += cadena.charAt(i);
        }

        return invertida;
    }
}