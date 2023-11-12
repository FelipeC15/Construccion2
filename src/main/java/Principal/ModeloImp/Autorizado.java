package Principal.ModeloImp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Autorizado extends Persona {

    private Socio socioPrincipal;

    public Autorizado() {
        super();
    }

    public Socio getSocioPrincipal() {
        return socioPrincipal;
    }

    @Override
    public String toString() {
        return "\n[nombre: " + this.getNombre() + ", cedula: " + this.getCedula() + ", socio: " + this.getSocioPrincipal() + "]";
    }

    public void setSocioPrincipal(Socio socioPrincipal) {
        this.socioPrincipal = socioPrincipal;
    }

}
