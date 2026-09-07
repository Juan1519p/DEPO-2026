package uniandes.dpoo.estructuras.logica;

import java.util.HashMap;

public class SandboxArreglos
{
    private int[] arregloEnteros;
    private String[] arregloCadenas;

    public SandboxArreglos()
    {
        arregloEnteros = new int[] {};
        arregloCadenas = new String[] {};
    }

    public int[] getCopiaEnteros()
    {
        int[] copia = new int[arregloEnteros.length];

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            copia[i] = arregloEnteros[i];
        }

        return copia;
    }

    public String[] getCopiaCadenas()
    {
        String[] copia = new String[arregloCadenas.length];

        for (int i = 0; i < arregloCadenas.length; i++)
        {
            copia[i] = arregloCadenas[i];
        }

        return copia;
    }

    public int getCantidadEnteros()
    {
        return arregloEnteros.length;
    }

    public int getCantidadCadenas()
    {
        return arregloCadenas.length;
    }

    public void agregarEntero(int entero)
    {
        int[] nuevoArreglo = new int[arregloEnteros.length + 1];

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            nuevoArreglo[i] = arregloEnteros[i];
        }

        nuevoArreglo[nuevoArreglo.length - 1] = entero;
        arregloEnteros = nuevoArreglo;
    }

    public void agregarCadena(String cadena)
    {
        String[] nuevoArreglo = new String[arregloCadenas.length + 1];

        for (int i = 0; i < arregloCadenas.length; i++)
        {
            nuevoArreglo[i] = arregloCadenas[i];
        }

        nuevoArreglo[nuevoArreglo.length - 1] = cadena;
        arregloCadenas = nuevoArreglo;
    }

    public void eliminarEntero(int valor)
    {
        int cantidad = contarApariciones(valor);

        if (cantidad == 0)
        {
            return;
        }

        int[] nuevoArreglo = new int[arregloEnteros.length - cantidad];
        int posicion = 0;

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            if (arregloEnteros[i] != valor)
            {
                nuevoArreglo[posicion] = arregloEnteros[i];
                posicion++;
            }
        }

        arregloEnteros = nuevoArreglo;
    }

    public void eliminarCadena(String cadena)
    {
        int cantidad = contarApariciones(cadena);

        if (cantidad == 0)
        {
            return;
        }

        String[] nuevoArreglo = new String[arregloCadenas.length - cantidad];
        int posicion = 0;

        for (int i = 0; i < arregloCadenas.length; i++)
        {
            if (!arregloCadenas[i].equalsIgnoreCase(cadena))
            {
                nuevoArreglo[posicion] = arregloCadenas[i];
                posicion++;
            }
        }

        arregloCadenas = nuevoArreglo;
    }

    public void insertarEntero(int entero, int posicion)
    {
        if (posicion < 0)
        {
            posicion = 0;
        }

        if (posicion > arregloEnteros.length)
        {
            posicion = arregloEnteros.length;
        }

        int[] nuevoArreglo = new int[arregloEnteros.length + 1];

        for (int i = 0; i < posicion; i++)
        {
            nuevoArreglo[i] = arregloEnteros[i];
        }

        nuevoArreglo[posicion] = entero;

        for (int i = posicion; i < arregloEnteros.length; i++)
        {
            nuevoArreglo[i + 1] = arregloEnteros[i];
        }

        arregloEnteros = nuevoArreglo;
    }

    public void eliminarEnteroPorPosicion(int posicion)
    {
        if (posicion < 0 || posicion >= arregloEnteros.length)
        {
            return;
        }

        int[] nuevoArreglo = new int[arregloEnteros.length - 1];

        for (int i = 0; i < posicion; i++)
        {
            nuevoArreglo[i] = arregloEnteros[i];
        }

        for (int i = posicion + 1; i < arregloEnteros.length; i++)
        {
            nuevoArreglo[i - 1] = arregloEnteros[i];
        }

        arregloEnteros = nuevoArreglo;
    }

    public void reiniciarArregloEnteros(double[] valores)
    {
        arregloEnteros = new int[valores.length];

        for (int i = 0; i < valores.length; i++)
        {
            arregloEnteros[i] = (int) valores[i];
        }
    }

    public void reiniciarArregloCadenas(Object[] objetos)
    {
        arregloCadenas = new String[objetos.length];

        for (int i = 0; i < objetos.length; i++)
        {
            arregloCadenas[i] = objetos[i].toString();
        }
    }

    public void volverPositivos()
    {
        for (int i = 0; i < arregloEnteros.length; i++)
        {
            if (arregloEnteros[i] < 0)
            {
                arregloEnteros[i] = arregloEnteros[i] * -1;
            }
        }
    }

    public void organizarEnteros()
    {
        for (int i = 0; i < arregloEnteros.length - 1; i++)
        {
            for (int j = i + 1; j < arregloEnteros.length; j++)
            {
                if (arregloEnteros[i] > arregloEnteros[j])
                {
                    int temporal = arregloEnteros[i];
                    arregloEnteros[i] = arregloEnteros[j];
                    arregloEnteros[j] = temporal;
                }
            }
        }
    }

    public void organizarCadenas()
    {
        for (int i = 0; i < arregloCadenas.length - 1; i++)
        {
            for (int j = i + 1; j < arregloCadenas.length; j++)
            {
                if (arregloCadenas[i].compareTo(arregloCadenas[j]) > 0)
                {
                    String temporal = arregloCadenas[i];
                    arregloCadenas[i] = arregloCadenas[j];
                    arregloCadenas[j] = temporal;
                }
            }
        }
    }

    public int contarApariciones(int valor)
    {
        int contador = 0;

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            if (arregloEnteros[i] == valor)
            {
                contador++;
            }
        }

        return contador;
    }

    public int contarApariciones(String cadena)
    {
        int contador = 0;

        for (int i = 0; i < arregloCadenas.length; i++)
        {
            if (arregloCadenas[i].equalsIgnoreCase(cadena))
            {
                contador++;
            }
        }

        return contador;
    }

    public int[] buscarEntero(int valor)
    {
        int cantidad = contarApariciones(valor);
        int[] posiciones = new int[cantidad];
        int posicion = 0;

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            if (arregloEnteros[i] == valor)
            {
                posiciones[posicion] = i;
                posicion++;
            }
        }

        return posiciones;
    }

    public int[] calcularRangoEnteros()
    {
        if (arregloEnteros.length == 0)
        {
            return new int[] {};
        }

        int minimo = arregloEnteros[0];
        int maximo = arregloEnteros[0];

        for (int i = 1; i < arregloEnteros.length; i++)
        {
            if (arregloEnteros[i] < minimo)
            {
                minimo = arregloEnteros[i];
            }

            if (arregloEnteros[i] > maximo)
            {
                maximo = arregloEnteros[i];
            }
        }

        return new int[] { minimo, maximo };
    }

    public HashMap<Integer, Integer> calcularHistograma()
    {
        HashMap<Integer, Integer> histograma = new HashMap<Integer, Integer>();

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            int valor = arregloEnteros[i];

            if (histograma.containsKey(valor))
            {
                histograma.put(valor, histograma.get(valor) + 1);
            }
            else
            {
                histograma.put(valor, 1);
            }
        }

        return histograma;
    }

    public int contarEnterosRepetidos()
    {
        HashMap<Integer, Integer> histograma = calcularHistograma();
        int contador = 0;

        for (Integer valor : histograma.keySet())
        {
            if (histograma.get(valor) > 1)
            {
                contador++;
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

        if (arregloEnteros.length != otroArreglo.length)
        {
            return false;
        }

        for (int i = 0; i < arregloEnteros.length; i++)
        {
            if (arregloEnteros[i] != otroArreglo[i])
            {
                return false;
            }
        }

        return true;
    }

    public boolean mismosEnteros(int[] otroArreglo)
    {
        if (otroArreglo == null)
        {
            return false;
        }

        if (arregloEnteros.length != otroArreglo.length)
        {
            return false;
        }

        HashMap<Integer, Integer> histograma1 = calcularHistograma();
        HashMap<Integer, Integer> histograma2 = new HashMap<Integer, Integer>();

        for (int i = 0; i < otroArreglo.length; i++)
        {
            int valor = otroArreglo[i];

            if (histograma2.containsKey(valor))
            {
                histograma2.put(valor, histograma2.get(valor) + 1);
            }
            else
            {
                histograma2.put(valor, 1);
            }
        }

        return histograma1.equals(histograma2);
    }

    public void generarEnteros(int cantidad, int minimo, int maximo)
    {
        arregloEnteros = new int[cantidad];

        for (int i = 0; i < cantidad; i++)
        {
            arregloEnteros[i] = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
        }
    }
}