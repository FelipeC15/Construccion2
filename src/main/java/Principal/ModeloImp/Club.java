package Principal.ModeloImp;

import java.util.ArrayList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public final class Club {

    public static final int MAX_VIP = 3;
    public static final int MAX_SOCIOS = 35;

    // Factura pagadas
    private final ArrayList<Factura> facturaPagadasList = new ArrayList<>();
    private final ArrayList<Socio> socioList = new ArrayList<>();

    private int sociosVIP = 0;

    public boolean agregarSocio(Socio inSocio) throws Exception {
        if (getSocioList().size() < MAX_SOCIOS) {

            boolean encontrado = false;
            for (Socio s : getSocioList()) {
                if (s.getCedula() == inSocio.getCedula()) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                throw new Exception("No se pueden agregar, ya que la cedula ya esta ingresada");
            } else {

                if (inSocio.getTipSuscripcion().equalsIgnoreCase("VIP") && (inSocio.getFondos() >= 100000)
                        || inSocio.getTipSuscripcion().equalsIgnoreCase("Regular") && (inSocio.getFondos() >= 50000)) {

                    if (inSocio.getTipSuscripcion().equalsIgnoreCase("VIP")) {
                        if (sociosVIP >= MAX_VIP) {
                            throw new Exception("Se ha alcanzado el maximo de socios VIP");
                        } else {
                            getSocioList().add(inSocio);
                            sociosVIP++;
                        }

                    } else {
                        getSocioList().add(inSocio);
                    }

                } else {
                    throw new Exception("Fondos no acorde con el tipo de Suscripcion");
                }
            }
        } else {
            throw new Exception("No se pueden admitir mas socios, se ha alcanzado el máximo");
        }

        return true;
    }

    public ArrayList<Factura> getFacturasList() {
        return facturaPagadasList;
    }

    public boolean agregarFacturaSocio(Factura inFactura) throws Exception {

        Socio socio = buscarSocio(inFactura.getCedula());

        if (socio == null) {
            throw new Exception("No se puede agregar factura, socio no encontrado");
        }

        int contador = socio.cantGlobalFacturas();

        if (contador >= 20) {
            throw new Exception("No se puede agregar factura, se ha alcanzado el limite máximo de factura");
        } else {
            socio.agregarFactura(inFactura);
        }

        return true;
    }

    public boolean agregarFacturaPagada(Factura inFactura) throws Exception {

        Socio socio = buscarSocio(inFactura.getCedula());

        if (inFactura.getMontoPagado() < inFactura.getMonto()) {
            throw new Exception("No se puede agregar factura pagada, pago incompleto");
        }

        facturaPagadasList.add(inFactura);

        return true;
    }

    public Socio buscarSocio(int inCedula) {
        Socio socio = null;
        for (Socio s : getSocioList()) {
            if (s.getCedula() == inCedula) {
                socio = s;
                break;
            }
        }
        return socio;
    }

    public Factura buscarFactura(int inCedula) {
        Factura factura = null;
        for (Factura f : getFacturasList()) {
            if (f.getCedula() == inCedula) {
                factura = f;
                break;
            }
        }
        return factura;
    }

    public Socio EliminarSocio(int inCedula) throws Exception {
        Socio socio = null;
        for (Socio s : getSocioList()) {
            if (s.getCedula() == inCedula) {
                socio = s;
                break;
            }
        }

        if (socio != null) {

            if (socio.getTipSuscripcion().equalsIgnoreCase("VIP")) {
                throw new Exception("No se puede eliminar un socio VIP");
            }

            double monto = socio.montoGlobalFacturas();
            if (monto > 0) {
                throw new Exception("No se puede eliminar un socio con deudas, monto deuda es: " + String.valueOf(monto));
            }

            if (socio.cantAutorizado() > 1) {
                throw new Exception("No se puede eliminar un socio con mas de un autorizado, cantidad: " + String.valueOf(socio.cantAutorizado()));
            }

            getSocioList().remove(socio);
        }

        return socio;
    }

    public void mostrarTodosAutorizadosgetSocios() {
        for (Socio s : getSocioList()) {
            for (Autorizado a : s.getAutorizados()) {
                System.out.println("El autorizado " + a.getNombre() + " está asociado al socio "
                        + a.getSocioPrincipal().toString());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n***   Este es el CLub   ***\n");
        for (Socio socio : this.getSocioList()) {
            sb.append(socio.toString());
        }
        return sb.toString();
    }

    /**
     * @return the socioList
     */
    public ArrayList<Socio> getSocioList() {
        return socioList;
    }

}
