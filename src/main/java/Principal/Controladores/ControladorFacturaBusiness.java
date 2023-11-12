package Principal.Controladores;

import Principal.ModeloImp.Factura;
import Principal.ModeloImp.Socio;

public interface ControladorFacturaBusiness {

    public boolean validarIdentificador(int identificador) throws Exception;

    public Factura buscarFactura(int identificador) throws Exception;

    public Factura pagarFacturaSocio(Socio socio) throws Exception;

    public double totalFacturasDeSocio(int cedula) throws Exception;
}
