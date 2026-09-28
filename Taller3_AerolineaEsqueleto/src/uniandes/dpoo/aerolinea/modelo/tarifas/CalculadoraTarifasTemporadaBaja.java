package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas
{
    protected final int COSTO_POR_KM_NATURAL = 600;
    protected final int COSTO_POR_KM_CORPORATIVO = 900;

    protected final double DESCUENTO_PEQ = 0.02;
    protected final double DESCUENTO_MEDIANAS = 0.10;
    protected final double DESCUENTO_GRANDES = 0.20;

    public CalculadoraTarifasTemporadaBaja()
    {
        super();
    }

    @Override
    public int calcularCostoBase(Vuelo vuelo, Cliente cliente)
    {
        int distancia = calcularDistanciaVuelo(vuelo.getRuta());

        if (cliente instanceof ClienteCorporativo)
        {
            return COSTO_POR_KM_CORPORATIVO * distancia;
        }
        else
        {
            return COSTO_POR_KM_NATURAL * distancia;
        }
    }

    @Override
    public double calcularPorcentajeDescuento(Cliente cliente)
    {
        if (cliente instanceof ClienteCorporativo)
        {
            ClienteCorporativo clienteCorporativo =
                    (ClienteCorporativo) cliente;

            if (clienteCorporativo.getTamanoEmpresa()
                    == ClienteCorporativo.GRANDE)
            {
                return DESCUENTO_GRANDES;
            }
            else if (clienteCorporativo.getTamanoEmpresa()
                    == ClienteCorporativo.MEDIANA)
            {
                return DESCUENTO_MEDIANAS;
            }
            else if (clienteCorporativo.getTamanoEmpresa()
                    == ClienteCorporativo.PEQUENA)
            {
                return DESCUENTO_PEQ;
            }
        }

        return 0;
    }
}
