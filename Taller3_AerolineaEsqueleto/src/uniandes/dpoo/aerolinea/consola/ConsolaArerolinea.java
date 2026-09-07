package uniandes.dpoo.aerolinea.consola;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;

public class ConsolaArerolinea extends ConsolaBasica
{
    private Aerolinea unaAerolinea;

    public void correrAplicacion()
    {
        try
        {
            unaAerolinea = new Aerolinea();

            unaAerolinea.cargarAerolinea(
                    "./datos/aerolinea.json",
                    CentralPersistencia.JSON);

            unaAerolinea.cargarTiquetes(
                    "./datos/tiquetes.json",
                    CentralPersistencia.JSON);

            System.out.println("Aerolinea cargada correctamente");
            System.out.println("Rutas: " + unaAerolinea.getRutas().size());
            System.out.println("Aviones: " + unaAerolinea.getAviones().size());
            System.out.println("Vuelos: " + unaAerolinea.getVuelos().size());
            System.out.println("Clientes: " + unaAerolinea.getClientes().size());
            System.out.println("Tiquetes: " + unaAerolinea.getTiquetes().size());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ConsolaArerolinea ca = new ConsolaArerolinea();
        ca.correrAplicacion();
    }
}