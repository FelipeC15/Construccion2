package Principal.Controladores;

import Principal.ModeloImp.Socio;

public interface ControladorSocioBusiness {

    public void validadorCedula(int cedula) throws Exception;

    public Socio buscarSocio(int cedula) throws Exception;

    public boolean agregarFondos(Socio socio, double cantidad) throws Exception;

    public boolean procesarConsumo(Socio socio, double valor) throws Exception;

}
