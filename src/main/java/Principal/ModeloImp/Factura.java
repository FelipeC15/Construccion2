package Principal.ModeloImp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Factura {

    private int cedula;
    private String concepto;
    private double monto;
    private double montoPagado;

    public Factura() {
        cedula = -1;
        concepto = null;
        monto = 0;
        montoPagado = 0;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getCedula() {
        return cedula;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "[Cedula: " + getCedula() + ", concepto: " + getConcepto() + ", monto: " + getMonto() + ", montoPag: " + getMontoPagado() + "]";
    }

    /**
     * @return the montoPagado
     */
    public double getMontoPagado() {
        return montoPagado;
    }

    /**
     * @param montoPagado the montoPagado to set
     */
    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

}
