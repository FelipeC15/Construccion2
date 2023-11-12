package Principal.ModeloImp;

import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Persona {

    private int cedula;
    private String nombre;
    private Stack<Factura> facturasImpagasStack;

    Persona() {
        super();
        this.facturasImpagasStack = new Stack<>();
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean tieneFactura() {
        return !facturasImpagasStack.isEmpty();
    }

    public int cantFacturas() {
        return facturasImpagasStack.size();
    }

    public double montoProxFactura() {

        double monto;
        if (tieneFactura()) {
            monto = facturasImpagasStack.peek().getMonto();
        } else {
            monto = 0.00;
        }

        return monto;
    }

    public boolean agregarFactura(Factura inFactura) {

        if (inFactura == null) {
            return false;
        }

        facturasImpagasStack.push(inFactura);

        return true;
    }

    public Factura pagarFactura() {

        Factura factura;
        if (tieneFactura()) {
            factura = facturasImpagasStack.pop();
            factura.setMontoPagado(factura.getMonto());
        } else {
            factura = null;
        }

        return factura;
    }

    public double montoTotalFacturas() {

        double total = 0;
        Iterator<Factura> iterator = facturasImpagasStack.iterator();

        while (iterator.hasNext()) {
            total += iterator.next().getMonto();
        }

        return total;
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        Persona persona = (Persona) inO;
        return cedula == persona.getCedula();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

}
