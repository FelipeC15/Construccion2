package Principal.ControladoresImp;

import Principal.Controladores.ControladorAutorizadoBusiness;
import Principal.Controladores.ControladorFacturaBusiness;
import Principal.Controladores.ControladorPersonaBusiness;
import Principal.Controladores.ControladorSocioBusiness;
import Principal.ModeloImp.Club;
import Principal.ModeloImp.Factura;
import Principal.ModeloImp.Persona;
import Principal.ModeloImp.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControladorBusinessImp implements ControladorAutorizadoBusiness, ControladorPersonaBusiness, ControladorSocioBusiness, ControladorFacturaBusiness {

    @Autowired
    private Club club;

    public ControladorBusinessImp() {
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club inClub) {
        this.club = inClub;
    }

    // Business methods
    @Override
    public void validadorCedula(int cedula) throws Exception {
        for (Socio socio : getClub().getSocioList()) {
            if (socio.getCedula() == cedula) {
                throw new Exception("Ya existe un socio con esa cedula registrada.");
            }
        }
    }

    @Override
    public Persona buscarPersona(int cedula) throws Exception {
        for (Socio socio : getClub().getSocioList()) {
            if (socio.getCedula() == cedula) {
                return socio;
            }
        }
        throw new Exception("No existe una persona con esa cedula registrada.");
    }

    @Override
    public Socio buscarSocio(int cedula) throws Exception {
        for (Socio socio : getClub().getSocioList()) {
            if (socio.getCedula() == cedula) {
                return socio;
            }
        }
        throw new Exception("No existe un socio con esa cedula registrada.");
    }

    @Override
    public boolean agregarFondos(Socio socio, double cantidad) throws Exception {
        if (socio.getFondos() + cantidad <= socio.getFondosMaximos()) {
            socio.setFondos(socio.getFondos() + cantidad);
            return true;
        }
        throw new Exception("La cantidad excede el límite máximo de fondos para este tipo de socio.");
    }

    @Override
    public boolean procesarConsumo(Socio socio, double valor) throws Exception {
        return socio.getFondos() >= valor;
    }

    @Override
    public Factura pagarFacturaSocio(Socio socio) throws Exception {

        Factura factura = socio.pagarProxFactura();

        if (factura != null) {
            club.agregarFacturaPagada(factura);
        } else {
            System.out.println("No hay facturas pendientes.");
        }

        return factura;
    }

    @Override
    public double totalFacturasDeSocio(int cedula) {
        Socio socio = club.buscarSocio(cedula);

        double total;
        if (socio != null) {
            total = socio.montoGlobalFacturas();
        } else {
            total = 0;
        }

        return total;
    }

    @Override
    public boolean validarIdentificador(int inIdentificador) throws Exception {
        return inIdentificador > 0;
    }

    @Override
    public Factura buscarFactura(int cedula) throws Exception {
        return club.buscarFactura(cedula);
    }
}
