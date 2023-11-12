package Principal.Controladores;

public interface ControladorFacturaInput {

	public String validarNombre(String nombre) throws Exception;

	public int validarIdentificador(String Identificador) throws Exception;

	public int validarCantidad(String cantidad) throws Exception;
}
