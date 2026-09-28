package uniandes.dpoo.aerolinea.modelo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifasTemporadaAlta;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifasTemporadaBaja;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaAerolinea;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaTiquetes;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Aerolinea
{
    private List<Avion> aviones;

    private Map<String, Ruta> rutas;

    private List<Vuelo> vuelos;

    private Map<String, Cliente> clientes;

    public Aerolinea()
    {
        aviones = new LinkedList<Avion>();
        rutas = new HashMap<String, Ruta>();
        vuelos = new LinkedList<Vuelo>();
        clientes = new HashMap<String, Cliente>();
    }

    public void agregarRuta(Ruta ruta)
    {
        this.rutas.put(ruta.getCodigoRuta(), ruta);
    }

    public void agregarAvion(Avion avion)
    {
        this.aviones.add(avion);
    }

    public void agregarCliente(Cliente cliente)
    {
        this.clientes.put(cliente.getIdentificador(), cliente);
    }

    public boolean existeCliente(String identificadorCliente)
    {
        return this.clientes.containsKey(identificadorCliente);
    }

    public Cliente getCliente(String identificadorCliente)
    {
        return this.clientes.get(identificadorCliente);
    }

    public Collection<Avion> getAviones()
    {
        return aviones;
    }

    public Collection<Ruta> getRutas()
    {
        return rutas.values();
    }

    public Ruta getRuta(String codigoRuta)
    {
        return rutas.get(codigoRuta);
    }

    public Collection<Vuelo> getVuelos()
    {
        return vuelos;
    }

    public Vuelo getVuelo(String codigoRuta, String fechaVuelo)
    {
        for (Vuelo vuelo : vuelos)
        {
            if (vuelo.getRuta().getCodigoRuta().equals(codigoRuta)
                    && vuelo.getFecha().equals(fechaVuelo))
            {
                return vuelo;
            }
        }

        return null;
    }

    public Collection<Cliente> getClientes()
    {
        return clientes.values();
    }

    public Collection<Tiquete> getTiquetes()
    {
        Collection<Tiquete> todosLosTiquetes = new ArrayList<Tiquete>();

        for (Vuelo vuelo : vuelos)
        {
            todosLosTiquetes.addAll(vuelo.getTiquetes());
        }

        return todosLosTiquetes;
    }

    public void cargarAerolinea(String archivo, String tipoArchivo)
            throws TipoInvalidoException, IOException,
            InformacionInconsistenteException
    {
        IPersistenciaAerolinea cargador =
                CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);

        cargador.cargarAerolinea(archivo, this);
    }

    public void salvarAerolinea(String archivo, String tipoArchivo)
            throws TipoInvalidoException, IOException
    {
        IPersistenciaAerolinea salvador =
                CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);

        salvador.salvarAerolinea(archivo, this);
    }

    public void cargarTiquetes(String archivo, String tipoArchivo)
            throws TipoInvalidoException, IOException,
            InformacionInconsistenteException
    {
        IPersistenciaTiquetes cargador =
                CentralPersistencia.getPersistenciaTiquetes(tipoArchivo);

        cargador.cargarTiquetes(archivo, this);
    }

    public void salvarTiquetes(String archivo, String tipoArchivo)
            throws TipoInvalidoException, IOException
    {
        IPersistenciaTiquetes cargador =
                CentralPersistencia.getPersistenciaTiquetes(tipoArchivo);

        cargador.salvarTiquetes(archivo, this);
    }

    public void programarVuelo(String fecha, String codigoRuta,
            String nombreAvion) throws Exception
    {
        Ruta ruta = rutas.get(codigoRuta);

        if (ruta == null)
        {
            throw new Exception("No existe una ruta con el código "
                    + codigoRuta);
        }

        Avion avionSeleccionado = null;

        for (Avion avion : aviones)
        {
            if (avion.getNombre().equals(nombreAvion))
            {
                avionSeleccionado = avion;
                break;
            }
        }

        if (avionSeleccionado == null)
        {
            throw new Exception("No existe el avión " + nombreAvion);
        }

        if (getVuelo(codigoRuta, fecha) != null)
        {
            throw new Exception(
                    "Ya existe un vuelo para esa ruta en esa fecha");
        }

        LocalDate fechaNueva = LocalDate.parse(fecha);

        LocalTime horaSalidaNueva =
                LocalTime.of(
                        Ruta.getHoras(ruta.getHoraSalida()),
                        Ruta.getMinutos(ruta.getHoraSalida()));

        LocalTime horaLlegadaNueva =
                LocalTime.of(
                        Ruta.getHoras(ruta.getHoraLlegada()),
                        Ruta.getMinutos(ruta.getHoraLlegada()));

        LocalDateTime inicioNuevo =
                LocalDateTime.of(fechaNueva, horaSalidaNueva);

        LocalDateTime finNuevo =
                LocalDateTime.of(fechaNueva, horaLlegadaNueva);

        if (!finNuevo.isAfter(inicioNuevo))
        {
            finNuevo = finNuevo.plusDays(1);
        }

        for (Vuelo vuelo : vuelos)
        {
            if (vuelo.getAvion().getNombre().equals(nombreAvion))
            {
                Ruta otraRuta = vuelo.getRuta();

                LocalDate otraFecha =
                        LocalDate.parse(vuelo.getFecha());

                LocalTime otraSalida =
                        LocalTime.of(
                                Ruta.getHoras(
                                        otraRuta.getHoraSalida()),
                                Ruta.getMinutos(
                                        otraRuta.getHoraSalida()));

                LocalTime otraLlegada =
                        LocalTime.of(
                                Ruta.getHoras(
                                        otraRuta.getHoraLlegada()),
                                Ruta.getMinutos(
                                        otraRuta.getHoraLlegada()));

                LocalDateTime otroInicio =
                        LocalDateTime.of(otraFecha, otraSalida);

                LocalDateTime otroFin =
                        LocalDateTime.of(otraFecha, otraLlegada);

                if (!otroFin.isAfter(otroInicio))
                {
                    otroFin = otroFin.plusDays(1);
                }

                boolean seCruzan =
                        inicioNuevo.isBefore(otroFin)
                                && otroInicio.isBefore(finNuevo);

                if (seCruzan)
                {
                    throw new Exception(
                            "El avión ya está ocupado en ese horario");
                }
            }
        }

        Vuelo nuevoVuelo =
                new Vuelo(ruta, fecha, avionSeleccionado);

        vuelos.add(nuevoVuelo);
    }

    public int venderTiquetes(String identificadorCliente,
            String fecha, String codigoRuta, int cantidad)
            throws VueloSobrevendidoException, Exception
    {
        Cliente cliente = getCliente(identificadorCliente);

        if (cliente == null)
        {
            throw new Exception(
                    "No existe el cliente "
                            + identificadorCliente);
        }

        Vuelo vuelo = getVuelo(codigoRuta, fecha);

        if (vuelo == null)
        {
            throw new Exception(
                    "No existe el vuelo indicado");
        }

        if (cantidad <= 0)
        {
            throw new Exception(
                    "La cantidad de tiquetes debe ser mayor que cero");
        }

        int mes = LocalDate.parse(fecha).getMonthValue();

        CalculadoraTarifas calculadora;

        if ((mes >= 1 && mes <= 5)
                || (mes >= 9 && mes <= 11))
        {
            calculadora =
                    new CalculadoraTarifasTemporadaBaja();
        }
        else
        {
            calculadora =
                    new CalculadoraTarifasTemporadaAlta();
        }

        return vuelo.venderTiquetes(
                cliente, calculadora, cantidad);
    }

    public void registrarVueloRealizado(String fecha,
            String codigoRuta)
    {
        Vuelo vuelo = getVuelo(codigoRuta, fecha);

        if (vuelo != null)
        {
            for (Cliente cliente : clientes.values())
            {
                cliente.usarTiquetes(vuelo);
            }
        }
    }

    public String consultarSaldoPendienteCliente(
            String identificadorCliente)
    {
        Cliente cliente =
                getCliente(identificadorCliente);

        if (cliente == null)
        {
            return "0";
        }

        int saldo = 0;

        for (Tiquete tiquete : getTiquetes())
        {
            if (tiquete.getCliente().equals(cliente)
                    && !tiquete.esUsado())
            {
                saldo += tiquete.getTarifa();
            }
        }

        return String.valueOf(saldo);
    }
}
