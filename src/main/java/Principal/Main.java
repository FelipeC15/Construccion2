package Principal;

import Principal.ControladoresImp.ControladorBusinessImp;
import Principal.ControladoresImp.ControladorPersonaInput;
import Principal.ModeloImp.Autorizado;
import Principal.ModeloImp.Club;
import Principal.ModeloImp.Factura;
import Principal.ModeloImp.Socio;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    @Autowired
    public ApplicationContext ac;

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args); // Iniciar la aplicación Spring

        // Acceder a la instancia de Main administrada por Spring
        Main main = ctx.getBean(Main.class);

        // TODO Auto-generated method stub
        Scanner lector = new Scanner(System.in);

        boolean ejecucion = true;
        while (ejecucion) {
            try {
                System.out.println("\n<<< Menu >>>");
                System.out.println("ingrese 0 para salir");
                System.out.println("ingrese 1 para afiliar un socio al club");
                System.out.println("ingrese 2 Registrar una persona autorizada por un socio");
                System.out.println("ingrese 3 Pagar factura");
                System.out.println("ingrese 4 Registrar un consumo en la cuenta del socio");
                System.out.println("ingrese 5 Aumentar fondos de la cuenta de un socio");
                System.out.println("ingrese 6 Eliminar a un socio");
                System.out.println("ingrese 7 para consultar el total de consumos del socio");
                System.out.println("ingrese 8 para consultar los autorizados del socio");
                System.out.println("esperando opcion= ? ");

                String auxStr = lector.nextLine();
                int opcion;
                try {
                    opcion = Integer.parseInt(auxStr);
                } catch (NumberFormatException numberFormatException) {
                    opcion = -1;
                }

                switch (opcion) {
                    case 0: {
                        ejecucion = false;
                        lector.close();
                        break;
                    }
                    case 1: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean(ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean(ControladorBusinessImp.class);

                        System.out.println("ingrese el nombre del socio");
                        String nombre = controladorPersonaInput.validarNombre(lector.nextLine());

                        System.out.println("ingrese la cedula del socio");
                        int cedula = controladorPersonaInput.validarCedula(lector.nextLine());
                        controladorBusinessImp.validadorCedula(cedula);

                        System.out.println("ingrese los fondos ");
                        double fondosInput = 0;
                        try {
                            fondosInput = Double.parseDouble(lector.nextLine());
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Error formato invalido");
                            break;
                        }

                        double fondos = controladorPersonaInput.validarFondos(fondosInput);

                        System.out.println("ingrese el tipo de suscripcion del socio");
                        String tipSuscripcion = controladorPersonaInput.validarTipSuscripcion(lector.nextLine().toUpperCase());
                        controladorBusinessImp.setClub(main.ac.getBean("club", Club.class));

                        Socio socio = main.ac.getBean("socio", Socio.class);
                        socio.setCedula(cedula);
                        socio.setNombre(nombre);
                        socio.setFondos(fondos);
                        socio.setTipSuscripcion(tipSuscripcion);

                        if (controladorBusinessImp.getClub().agregarSocio(socio)) {
                            System.out.println("exito al agregar socio con datos: " + socio.toString());
                        } else {
                            System.out.println("NO se pudo agregar el socio con datos: " + socio.toString());
                        }

                        break;
                    }
                    case 2: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean("controladorPersonaInput",
                                ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);
                        controladorBusinessImp.setClub(main.ac.getBean("club", Club.class));

                        System.out.println("Ingrese nombre del autorizado:");
                        String nombreAutorizado = controladorPersonaInput.validarNombre(lector.nextLine());

                        System.out.println("Ingrese cédula del autorizado:");
                        int cedulaAutorizado = controladorPersonaInput.validarCedula(lector.nextLine());

                        System.out.println("Ingrese cédula del socio al que pertenece:");
                        int cedulaDelSocioPrincipal = controladorPersonaInput.validarCedula(lector.nextLine());

                        Socio socioPrincipal = null;

                        socioPrincipal = controladorBusinessImp.getClub().buscarSocio(cedulaDelSocioPrincipal);

                        if (socioPrincipal != null) {
                            Autorizado autorizado = main.ac.getBean(Autorizado.class);
                            autorizado.setCedula(cedulaAutorizado);
                            autorizado.setNombre(nombreAutorizado);
                            autorizado.setSocioPrincipal(socioPrincipal);
                            socioPrincipal.agregarAutorizado(autorizado);
                            System.out.println("Autorizado agregado exitosamente.\n");
                        } else {
                            System.out.println("Socio no encontrado.\n");
                        }
                    }
                    break;

                    case 3: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean("controladorPersonaInput",
                                ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);

                        System.out.print("Ingrese la cédula del socio que desea pagar: ");
                        int cedula = controladorPersonaInput.validarCedula(lector.nextLine());

                        Socio socio = controladorBusinessImp.getClub().buscarSocio(cedula);
                        if (socio != null) {
                            Factura factura = controladorBusinessImp.pagarFacturaSocio(socio);

                            if (factura != null) {
                                System.out.println("Factura pagada: " + factura.toString());
                            } else {
                                System.out.println("No se encontraron facturas con cédula " + cedula);
                            }

                        } else {
                            System.out.println("No se encontraron facturas con cédula " + cedula);
                        }
                    }
                    break;

                    case 4: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean("controladorPersonaInput",
                                ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);

                        System.out.println("Ingrese cédula del socio:");
                        int cedula = controladorPersonaInput.validarCedula(lector.nextLine());

                        Socio socio = controladorBusinessImp.buscarSocio(cedula);

                        System.out.println("Ingrese el concepto de la factura:");
                        String concepto = lector.nextLine();

                        System.out.println("Ingrese el monto de la factura:");
                        double monto = Double.parseDouble(lector.nextLine());

                        boolean tieneFondos = controladorBusinessImp.procesarConsumo(socio, monto);

                        if (tieneFondos) {
                            Factura factura = main.ac.getBean(Factura.class);

                            factura.setCedula(socio.getCedula());
                            factura.setMonto(monto);
                            factura.setConcepto(concepto);

                            controladorBusinessImp.getClub().agregarFacturaSocio(factura);
                            System.out.println("Consumo registrado exitosamente.");
                        } else {
                            System.out.println("El socio no tiene fondos suficientes para realizar este consumo.");
                        }
                    }
                    break;

                    case 5: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean("controladorPersonaInput",
                                ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);

                        System.out.println("Ingrese cédula del socio:");
                        int cedula = controladorPersonaInput.validarCedula(lector.nextLine());

                        try {
                            Socio socio = controladorBusinessImp.buscarSocio(cedula);

                            if (socio == null) {
                                System.out.println("Socio no encontrado.");
                                break;
                            }

                            System.out.println("Ingrese la cantidad de fondos a agregar:");

                            double cantidadInput = 0;
                            try {
                                cantidadInput = Double.parseDouble(lector.nextLine());
                            } catch (NumberFormatException numberFormatException) {
                            }

                            double cantidad = controladorPersonaInput.validarFondos(cantidadInput);

                            if (controladorBusinessImp.agregarFondos(socio, cantidad)) {
                                System.out.println("Fondos agregados exitosamente.");
                            } else {
                                System.out.println("El socio supera el monto maximo.");
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }

                    case 6: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean("controladorPersonaInput",
                                ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);

                        System.out.println("Ingrese la cedula del socio a eliminar ");
                        int cedula = controladorPersonaInput.validarCedula(lector.nextLine());
                        Socio socio = controladorBusinessImp.getClub().EliminarSocio(cedula);
                        if (socio != null) {
                            System.out.println("se elimina el socio: " + socio);
                        } else {
                            System.out.println("Socio no encontrado");
                        }
                        break;
                    }

                    case 7: {
                        ControladorPersonaInput controladorPersonaInput = main.ac.getBean("controladorPersonaInput",
                                ControladorPersonaInput.class);
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);

                        System.out.print("Ingrese la cédula del socio: ");
                        int cedula = controladorPersonaInput.validarCedula(lector.nextLine());
                        try {
                            double total = controladorBusinessImp.totalFacturasDeSocio(cedula);
                            if (total > 0) {
                                System.out.println("Total Global de facturas para el socio con cédula " + cedula + ": $" + total);
                            } else {
                                System.out.println("No hay facturas para el socio con cédula " + cedula);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }

                    case 8: {
                        ControladorBusinessImp controladorBusinessImp = main.ac.getBean("controladorBusinessImp",
                                ControladorBusinessImp.class);

                        System.out.println("INICIO lista global de autorizados");
                        System.out.println(controladorBusinessImp.getClub().toString());
                        controladorBusinessImp.getClub().mostrarTodosAutorizadosgetSocios();
                        System.out.println("FIN lista global de autorizados");
                        break;
                    }

                    default: {
                        System.out.println("opcion no valida");
                    }
                }
            } catch (Exception e) {
                System.out.println("\n*** Error: " + e.getMessage());
            }
        }

        System.out.println("\n\n*** Fin del Programa ***\n\n");

    }

}
