package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea
{
    public PersistenciaAerolineaJson()
    {
    }

    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea)
            throws IOException, InformacionInconsistenteException
    {
        String contenido = new String(
                Files.readAllBytes(Paths.get(archivo)),
                StandardCharsets.UTF_8);

        try
        {
            JSONObject raiz = new JSONObject(contenido);

            Map<String, Aeropuerto> aeropuertos = new HashMap<String, Aeropuerto>();
            Map<String, Avion> aviones = new HashMap<String, Avion>();

            // Cargar aeropuertos
            JSONArray arregloAeropuertos = raiz.getJSONArray("aeropuertos");

            for (int i = 0; i < arregloAeropuertos.length(); i++)
            {
                JSONObject objeto = arregloAeropuertos.getJSONObject(i);

                String nombre = objeto.getString("nombre");
                String codigo = objeto.getString("codigo");
                String ciudad = objeto.getString("ciudad");
                double latitud = objeto.getDouble("latitud");
                double longitud = objeto.getDouble("longitud");

                Aeropuerto aeropuerto = new Aeropuerto(
                        nombre,
                        codigo,
                        ciudad,
                        latitud,
                        longitud);

                aeropuertos.put(codigo, aeropuerto);
            }

            // Cargar aviones
            JSONArray arregloAviones = raiz.getJSONArray("aviones");

            for (int i = 0; i < arregloAviones.length(); i++)
            {
                JSONObject objeto = arregloAviones.getJSONObject(i);

                String nombre = objeto.getString("nombre");
                int capacidad = objeto.getInt("capacidad");

                Avion avion = new Avion(nombre, capacidad);

                aviones.put(nombre, avion);
                aerolinea.agregarAvion(avion);
            }

            // Cargar rutas
            JSONArray arregloRutas = raiz.getJSONArray("rutas");

            for (int i = 0; i < arregloRutas.length(); i++)
            {
                JSONObject objeto = arregloRutas.getJSONObject(i);

                String codigoRuta = objeto.getString("codigoRuta");
                String codigoOrigen = objeto.getString("origen");
                String codigoDestino = objeto.getString("destino");
                String horaSalida = objeto.getString("horaSalida");
                String horaLlegada = objeto.getString("horaLlegada");

                Aeropuerto origen = aeropuertos.get(codigoOrigen);
                Aeropuerto destino = aeropuertos.get(codigoDestino);

                if (origen == null || destino == null)
                {
                    throw new IOException(
                            "La ruta " + codigoRuta +
                            " usa un aeropuerto que no existe");
                }

                Ruta ruta = new Ruta(
                        origen,
                        destino,
                        horaSalida,
                        horaLlegada,
                        codigoRuta);

                aerolinea.agregarRuta(ruta);
            }

            // Cargar vuelos
            JSONArray arregloVuelos = raiz.getJSONArray("vuelos");

            for (int i = 0; i < arregloVuelos.length(); i++)
            {
                JSONObject objeto = arregloVuelos.getJSONObject(i);

                String codigoRuta = objeto.getString("codigoRuta");
                String fecha = objeto.getString("fecha");
                String nombreAvion = objeto.getString("avion");

                if (aerolinea.getRuta(codigoRuta) == null)
                {
                    throw new IOException(
                            "No existe la ruta " + codigoRuta);
                }

                if (!aviones.containsKey(nombreAvion))
                {
                    throw new IOException(
                            "No existe el avion " + nombreAvion);
                }

                aerolinea.programarVuelo(
                        fecha,
                        codigoRuta,
                        nombreAvion);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new IOException(
                    "Error cargando la informacion de la aerolinea",
                    e);
        }
    }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea)
            throws IOException
    {
        JSONObject raiz = new JSONObject();

        JSONArray arregloAeropuertos = new JSONArray();
        JSONArray arregloAviones = new JSONArray();
        JSONArray arregloRutas = new JSONArray();
        JSONArray arregloVuelos = new JSONArray();

        // Evita guardar dos veces el mismo aeropuerto
        Map<String, Aeropuerto> aeropuertos =
                new LinkedHashMap<String, Aeropuerto>();

        for (Ruta ruta : aerolinea.getRutas())
        {
            aeropuertos.put(
                    ruta.getOrigen().getCodigo(),
                    ruta.getOrigen());

            aeropuertos.put(
                    ruta.getDestino().getCodigo(),
                    ruta.getDestino());
        }

        // Guardar aeropuertos
        for (Aeropuerto aeropuerto : aeropuertos.values())
        {
            JSONObject objeto = new JSONObject();

            objeto.put("nombre", aeropuerto.getNombre());
            objeto.put("codigo", aeropuerto.getCodigo());
            objeto.put("ciudad", aeropuerto.getNombreCiudad());
            objeto.put("latitud", aeropuerto.getLatitud());
            objeto.put("longitud", aeropuerto.getLongitud());

            arregloAeropuertos.put(objeto);
        }

        // Guardar aviones
        for (Avion avion : aerolinea.getAviones())
        {
            JSONObject objeto = new JSONObject();

            objeto.put("nombre", avion.getNombre());
            objeto.put("capacidad", avion.getCapacidad());

            arregloAviones.put(objeto);
        }

        // Guardar rutas
        for (Ruta ruta : aerolinea.getRutas())
        {
            JSONObject objeto = new JSONObject();

            objeto.put("codigoRuta", ruta.getCodigoRuta());
            objeto.put("origen", ruta.getOrigen().getCodigo());
            objeto.put("destino", ruta.getDestino().getCodigo());
            objeto.put("horaSalida", ruta.getHoraSalida());
            objeto.put("horaLlegada", ruta.getHoraLlegada());

            arregloRutas.put(objeto);
        }

        // Guardar vuelos
        for (Vuelo vuelo : aerolinea.getVuelos())
        {
            JSONObject objeto = new JSONObject();

            objeto.put(
                    "codigoRuta",
                    vuelo.getRuta().getCodigoRuta());

            objeto.put(
                    "fecha",
                    vuelo.getFecha());

            objeto.put(
                    "avion",
                    vuelo.getAvion().getNombre());

            arregloVuelos.put(objeto);
        }

        raiz.put("aeropuertos", arregloAeropuertos);
        raiz.put("aviones", arregloAviones);
        raiz.put("rutas", arregloRutas);
        raiz.put("vuelos", arregloVuelos);

        Files.write(
                Paths.get(archivo),
                raiz.toString(4).getBytes(StandardCharsets.UTF_8));
    }
}