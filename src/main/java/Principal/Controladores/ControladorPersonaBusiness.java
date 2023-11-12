package Principal.Controladores;

import Principal.ModeloImp.Persona;

public interface ControladorPersonaBusiness {

    public void validadorCedula(int cedula) throws Exception;

    public Persona buscarPersona(int cedula) throws Exception;

}
