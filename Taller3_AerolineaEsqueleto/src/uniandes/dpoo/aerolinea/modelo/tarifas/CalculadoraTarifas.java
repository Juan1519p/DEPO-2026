package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public abstract class CalculadoraTarifas
{
    public static final double IMPUESTO = 0.28;

    public CalculadoraTarifas()
    {
    }

    public int calcularTarifa(Vuelo vuelo, Cliente cliente)
    {
        int costoBase = calcularCostoBase(vuelo, cliente);

        double porcentajeDescuento = calcularPorcentajeDescuento(cliente);

        int descuento = (int)(costoBase * porcentajeDescuento);

        int costoConDescuento = costoBase - descuento;

        int impuestos = calcularValorImpuestos(costoConDescuento);

        return costoConDescuento + impuestos;
    }

    protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);

    protected abstract double calcularPorcentajeDescuento(Cliente cliente);

    protected int calcularDistanciaVuelo(Ruta ruta)
    {
        return Aeropuerto.calcularDistancia(
                ruta.getOrigen(),
                ruta.getDestino());
    }

    protected int calcularValorImpuestos(int costoBase)
    {
        return (int)(costoBase * IMPUESTO);
    }
}