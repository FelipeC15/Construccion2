package Principal.ControladoresImp;

import org.springframework.stereotype.Component;

@Component
public class ControladorInput {

    protected void stringVacio(String string, String nombre) throws Exception {
        if (string == null || string.equals("")) {
            throw new Exception(nombre + " no puede ser vacio");
        }
    }

    protected int numeroValido(String string, String nombre) throws Exception {
        if (string == null || string.equals("")) {
            throw new Exception(nombre + " no puede ser vacio");
        }
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            throw new Exception(nombre + " no es un numero valido");
        }
    }

    protected double numeroDoubleValido(double valor, String nombre) throws Exception {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new Exception(nombre + " no es un n�mero v�lido");
        }
        return valor;
    }

}
