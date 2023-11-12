package Principal.ControladoresImp;

import Principal.Controladores.ControladorAutorizadoInput;
import Principal.Controladores.ControladorFacturaInput;
import Principal.Controladores.ControladorPersonasInput;
import Principal.Controladores.ControladorSocioInput;
import org.springframework.stereotype.Component;

@Component()
public class ControladorPersonaInput extends ControladorInput implements ControladorAutorizadoInput, ControladorSocioInput, ControladorPersonasInput, ControladorFacturaInput {

    @Override
    public String validarNombre(String nombre) throws Exception {
        super.stringVacio(nombre, "nombre persona");
        return nombre;
    }

    @Override
    public int validarCedula(String cedula) throws Exception {
        return super.numeroValido(cedula, "cedula persona");
    }

    @Override
    public double validarFondos(double fondos) throws Exception {
        return super.numeroDoubleValido(fondos, "fondos persona");
    }

    @Override
    public int validarIdentificador(String Identificador) throws Exception {
        return super.numeroValido(Identificador, "cantidad material");
    }

    @Override
    public int validarCantidad(String cantidad) throws Exception {
        return super.numeroValido(cantidad, "cantidad material");
    }

    @Override
    public String validarTipSuscripcion(String tipSuscripcion) throws Exception {
        super.stringVacio(tipSuscripcion, "Tipo de suscripcion");
        if (!(tipSuscripcion.equalsIgnoreCase("VIP") || tipSuscripcion.equalsIgnoreCase("Regular"))) {
            throw new Exception("el tipo de suscripcion debe ser VIP O Regular");
        }

        return tipSuscripcion.toUpperCase();
    }

    public String validarTFondosTipSuscripcion(String inTipSuscripcion, String fondosTipSuscripcion) throws Exception {

        super.numeroValido(fondosTipSuscripcion, "fondos disponibles");
        if (!(inTipSuscripcion.equals("VIP") || inTipSuscripcion.equals("Regular"))) {
            throw new Exception("el tipo de suscripcion debe ser VIP O regular");
        }

        return fondosTipSuscripcion;

    }

}
