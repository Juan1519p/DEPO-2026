package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SandboxListas
{
    private List<Integer> listaEnteros;

    private List<String> listaCadenas;

    public SandboxListas()
    {
        listaEnteros = new ArrayList<Integer>();
        listaCadenas = new LinkedList<String>();
    }

    public List<Integer> getCopiaEnteros()
    {
        return new ArrayList<Integer>(listaEnteros);
    }

    public List<String> getCopiaCadenas()
    {
        return new LinkedList<String>(listaCadenas);
    }

    public int[] getEnterosComoArreglo()
    {
        int[] arreglo = new int[listaEnteros.size()];

        for (int i = 0; i < listaEnteros.size(); i++)
        {
            arreglo[i] = listaEnteros.get(i);
        }

        return arreglo;
    }

    public int getCantidadEnteros()
    {
        return listaEnteros.size();
    }

    public int getCantidadCadenas()
    {
        return listaCadenas.size();
    }

    public void agregarEntero(int entero)
    {
        listaEnteros.add(entero);
    }

    public void agregarCadena(String cadena)
    {
        listaCadenas.add(cadena);
    }

    public void eliminarEntero(int valor)
    {
        Iterator<Integer> iterador = listaEnteros.iterator();

        while (iterador.hasNext())
        {
            if (iterador.next() == valor)
            {
                iterador.remove();
            }
        }
    }

    public void eliminarCadena(String cadena)
    {
        Iterator<String> iterador = listaCadenas.iterator();

        while (iterador.hasNext())
        {
            String actual = iterador.next();

            if (actual.equals(cadena))
            {
                iterador.remove();
            }
        }
    }

    public void insertarEntero(int entero, int posicion)
    {
        if (posicion < 0)
        {
            posicion = 0;
        }
        else if (posicion > listaEnteros.size())
        {
            posicion = listaEnteros.size();
        }

        listaEnteros.add(posicion, entero);
    }

    public void eliminarEnteroPorPosicion(int posicion)
    {
        if (posicion >= 0 && posicion < listaEnteros.size())
        {
            listaEnteros.remove(posicion);
        }
    }

    public void reiniciarArregloEnteros(double[] valores)
    {
        listaEnteros.clear();

        for (double valor : valores)
        {
            listaEnteros.add((int) valor);
        }
    }

    public void reiniciarArregloCadenas(List<Object> objetos)
    {
        listaCadenas.clear();

        for (Object objeto : objetos)
        {
            listaCadenas.add(objeto.toString());
        }
    }

    public void volverPositivos()
    {
        for (int i = 0; i < listaEnteros.size(); i++)
        {
            int valor = listaEnteros.get(i);

            if (valor < 0)
            {
                listaEnteros.set(i, valor * -1);
            }
        }
    }

    public void organizarEnteros()
    {
        listaEnteros.sort((a, b) -> b.compareTo(a));
    }

    public void organizarCadenas()
    {
        listaCadenas.sort(null);
    }

    public int contarApariciones(int valor)
    {
        int contador = 0;

        for (Integer entero : listaEnteros)
        {
            if (entero == valor)
            {
                contador++;
            }
        }

        return contador;
    }

    public int contarApariciones(String cadena)
    {
        int contador = 0;

        for (String actual : listaCadenas)
        {
            if (actual.equalsIgnoreCase(cadena))
            {
                contador++;
            }
        }

        return contador;
    }

    public int contarEnterosRepetidos()
    {
        int contador = 0;

        for (int i = 0; i < listaEnteros.size(); i++)
        {
            int valor = listaEnteros.get(i);

            if (contarApariciones(valor) > 1)
            {
                boolean yaContado = false;

                for (int j = 0; j < i; j++)
                {
                    if (listaEnteros.get(j) == valor)
                    {
                        yaContado = true;
                        break;
                    }
                }

                if (!yaContado)
                {
                    contador++;
                }
            }
        }

        return contador;
    }

    public boolean compararArregloEnteros(int[] otroArreglo)
    {
        if (otroArreglo == null)
        {
            return false;
        }

        if (listaEnteros.size() != otroArreglo.length)
        {
            return false;
        }

        for (int i = 0; i < listaEnteros.size(); i++)
        {
            if (listaEnteros.get(i) != otroArreglo[i])
            {
                return false;
            }
        }

        return true;
    }

    public void generarEnteros(int cantidad, int minimo, int maximo)
    {
        listaEnteros.clear();

        for (int i = 0; i < cantidad; i++)
        {
            int valor = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
            listaEnteros.add(valor);
        }
    }
}