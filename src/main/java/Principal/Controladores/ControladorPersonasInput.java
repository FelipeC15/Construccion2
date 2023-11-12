package Principal.Controladores;

public interface ControladorPersonasInput {

    public String validarNombre(String nombre) throws Exception;

    public int validarCedula(String cedula) throws Exception;

    public double validarFondos(double fondos) throws Exception;
}
