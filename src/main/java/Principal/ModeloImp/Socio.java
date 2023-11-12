package Principal.ModeloImp;

import java.util.ArrayList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Socio extends Persona {

    // Static constants
    public static final int MAX_AUTORIZADO = 10;
    public static final double FONDOS_MAX_VIP = 5000000.0;
    public static final double FONDOS_MAX_REGULAR = 1000000.0;

    // Instance variables
    private String tipSuscripcion;
    private double fondos;

    // Lists
    private final ArrayList<Autorizado> autoList = new ArrayList<>();

    // Constructors
    public Socio() {
        super();

        tipSuscripcion = "REGULAR";
        fondos = 0.00;
    }

    // Business methods
    public ArrayList<Autorizado> getAutorizados() {
        return autoList;
    }

    public boolean tieneAutorizado() {
        return !autoList.isEmpty();
    }

    public int cantAutorizado() {
        return autoList.size();
    }

    public boolean agregarAutorizado(Autorizado autorizado) {

        if (autoList.size() >= MAX_AUTORIZADO) {
            System.out.println("No se puede agregar autorizado, se ha alcanzado el limite máximo de: " + String.valueOf(MAX_AUTORIZADO));
            return false;
        }

        if (fondos <= 0) {
            System.out.println("No se puede agregar autorizado, fondos insuficientes");
            return false;
        }

        if (existeAutorizado(autorizado)) {
            System.out.println("Ya esta persona ha sido agregada");
            return false;
        }

        autoList.add(autorizado);

        return true;
    }

    public boolean existeAutorizado(Autorizado autorizado) {

        for (Autorizado a : autoList) {
            if (a.getCedula() == autorizado.getCedula()) {
                return true;
            }
        }

        return false;
    }

    public double getFondos() {
        return fondos;
    }

    public boolean setFondos(double inFondos) {

        if (inFondos < 0) {
            System.out.println("No se puede agregar Fondos negativos");
            return false;
        }

        if (getTipSuscripcion().equalsIgnoreCase("VIP") && (inFondos > FONDOS_MAX_VIP)
                || getTipSuscripcion().equalsIgnoreCase("Regular") && (inFondos > FONDOS_MAX_REGULAR)) {
            System.out.println("No se puede agregar Fondos, supera el maximo para la suscripcion");
            return false;
        }

        this.fondos = inFondos;

        return true;
    }

    public boolean agregarFondos(double inFondos) throws Exception {

        if (inFondos < 0) {
            throw new Exception("No se puede agregar Fondos negativos");
        }

        return setFondos(inFondos + fondos);
    }

    public String getTipSuscripcion() {
        return tipSuscripcion;
    }

    public void setTipSuscripcion(String inTipSuscripcion) {

        if (inTipSuscripcion != null) {
            this.tipSuscripcion = inTipSuscripcion.toUpperCase();
        }
    }

    public double getFondosMaximos() {
        switch (tipSuscripcion) {
            case "VIP":
                return FONDOS_MAX_VIP;
            case "REGULAR":
                return FONDOS_MAX_REGULAR;
            default:
                return FONDOS_MAX_REGULAR;
        }
    }

    public int cantAutorizados() {
        return autoList.size();
    }

    // Incluye autorizados
    public int cantGlobalFacturas() {

        int contador = cantFacturas();

        for (Autorizado a : autoList) {
            contador += a.cantFacturas();
        }

        return contador;
    }

    // Incluye autorizados
    public double montoGlobalFacturas() {

        double total = montoTotalFacturas();

        for (Autorizado a : autoList) {
            total += a.montoTotalFacturas();
        }

        return total;
    }

    public boolean registrarFactura(Factura inFactura) {

        if ((inFactura == null) || (inFactura.getMonto() <= 0)) {
            System.out.println("Datos de factura invalidos");
            return false;
        }

        double aux = montoGlobalFacturas() + inFactura.getMonto();

        if (aux > fondos) {
            System.out.println("Fondos insuficientes");
            return false;
        }

        // Agregar factura al socio
        if (inFactura.getCedula() == this.getCedula()) {
            return agregarFactura(inFactura);
        }

        // Buscar autorizado
        Autorizado autorizado = null;
        for (Autorizado a : autoList) {
            if (a.getCedula() == inFactura.getCedula()) {
                autorizado = a;
                break;
            }
        }

        if (autorizado == null) {
            System.out.println("La factura no corresponde al socio/autorizados");
            return false;
        }

        // Agregar factura al autorizado
        autorizado.agregarFactura(inFactura);
        return true;
    }

    // Pagar la proxima factura del socio/autorizado
    public Factura pagarProxFactura() {

        if (montoGlobalFacturas() <= 0) {
            System.out.println("No hay factura por pagar");
            return null;
        }

        // Pagar factura del socio
        if (tieneFactura()) {
            return pagarFactura();
        }

        // Buscar autorizado
        Autorizado autorizado = null;
        for (Autorizado a : autoList) {
            if (a.tieneFactura()) {
                autorizado = a;
                break;
            }
        }

        if (autorizado == null) {
            System.out.println("No hay factura por pagar");
            return null;
        }

        return autorizado.pagarFactura();

    }

    @Override
    public String toString() {
        return "\n[nombre: " + this.getNombre() + ", cedula: " + this.getCedula() + ", tipo Suscripcion: "
                + this.getTipSuscripcion() + "]";
    }
}
