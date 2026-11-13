import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Empresa_Alquiler empresa = new Empresa_Alquiler();
        System.out.println(empresa.Resumen_Carga());

//     empresa.Agregar_Usuario(new Administrador("10000000", "admin@empresa.com", "Admin Jefe", "admin123", "ADM-001"));

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== EMPRESA DE ALQUILER DE VEHICULOS =====");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarme como cliente");
            System.out.println("0. Salir");
            opcion = Leer_Entero("Opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        Iniciar_Sesion(empresa);
                        break;
                    case 2:
                        Cliente nuevo = Pedir_Cliente();
                        empresa.Agregar_Usuario(nuevo);
                        System.out.println("Registro exitoso, ya podes iniciar sesion.");
                        break;
                    case 0:
                        System.out.println("Hasta luego!");
                        break;
                    default:
                        System.out.println("Opcion invalida...");
                }
            } catch (Valores_Invalidos | Elemento_No_Encontrado | Vehiculo_No_Disponible e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void Iniciar_Sesion(Empresa_Alquiler empresa) {
       String email = Leer_Texto("Email: ");
        String password = Leer_Texto("Password: ");
        Usuarios usuario = empresa.Login(email, password);
        System.out.println("\nBienvenido " + usuario.getNombre() + " (" + usuario.Obtener_Tipo() + ")");

        if (usuario instanceof Administrador) {
            Menu_Administrador(empresa, usuario);
        } else if (usuario instanceof Empleado) {
            Menu_Empleado(empresa, usuario);
        } else {
            Menu_Cliente(empresa, usuario);
        }
    }

    public static void Menu_Administrador(Empresa_Alquiler empresa, Usuarios usuario) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Agregar vehiculo");
            System.out.println("5. Eliminar vehiculo");
            System.out.println("6. Cambiar estado de un vehiculo");
            System.out.println("7. Listar todos los vehiculos");
            System.out.println("0. Cerrar sesion");
            opcion = Leer_Entero("Opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        Crear_Usuario(empresa);
                        break;
                    case 2:
                        System.out.println(empresa.Listar_Usuarios());
                        break;
                    case 3:
                        String dni = Leer_Texto("DNI del usuario a eliminar: ");
                        empresa.Eliminar_Usuario(dni);
                        System.out.println("Usuario eliminado.");
                        break;
                    case 4:
                        Agregar_Vehiculo(empresa);
                        break;
                    case 5:
                        String patente = Leer_Texto("Patente del vehiculo a eliminar: ");
                        empresa.Eliminar_Vehiculo(patente);
                        System.out.println("Vehiculo eliminado.");
                        break;
                    case 6:
                        Cambiar_Estado(empresa);
                        break;
                    case 7:
                        System.out.println(empresa.Listar_Todos_Los_Vehiculos());
                        break;
                    case 0:
                        System.out.println("Sesion cerrada.");
                        break;
                    default:
                        System.out.println("Opcion invalida...");
                }
            } catch (Valores_Invalidos | Elemento_No_Encontrado | Vehiculo_No_Disponible e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void Menu_Empleado(Empresa_Alquiler empresa, Usuarios usuario) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU EMPLEADO ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Ver vehiculos disponibles");
            System.out.println("4. Ver vehiculos no disponibles (con motivo)");
            System.out.println("5. Ver vehiculos alquilados (con datos del cliente)");
            System.out.println("6. Crear reserva para un cliente");
            System.out.println("7. Ver reservas activas");
            System.out.println("8. Registrar pago de una reserva");
            System.out.println("9. Cancelar reserva");
            System.out.println("10. Retirar vehiculo (entrega)");
            System.out.println("11. Devolver vehiculo");
            System.out.println("12. Cambiar estado de un vehiculo");
            System.out.println("0. Cerrar sesion");
            opcion = Leer_Entero("Opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        Cliente nuevo = Pedir_Cliente();
                        empresa.Agregar_Usuario(nuevo);
                        System.out.println("Cliente registrado.");
                        break;
                    case 2:
                        System.out.println(empresa.Listar_Clientes());
                        break;
                    case 3:
                        System.out.println(empresa.Listar_Vehiculos_Disponibles());
                        break;
                    case 4:
                        System.out.println(empresa.Listar_Vehiculos_No_Disponibles());
                        break;
                    case 5:
                        System.out.println(empresa.Listar_Vehiculos_Alquilados());
                        break;
                    case 6:
                        String dniCliente = Leer_Dni_Cliente_Existente(empresa);
                        Hacer_Reserva(empresa, dniCliente);
                        break;
                    case 7:
                        System.out.println(empresa.Listar_Reservas_Activas());
                        break;
                    case 8:
                        int idPago = Leer_Reserva_Para_Pago(empresa);
                        if(idPago == 0)
                        {
                            System.out.println("Pago rechazado.");
                        }
                        System.out.println("Pago registrado.");
                        empresa.Registrar_Pago(idPago);
                        System.out.println("Pago realizado.");
                        break;
                    case 9:
                        empresa.Cancelar_Reserva(Leer_Entero("Numero de reserva: "));
                        System.out.println("Reserva cancelada.");
                        break;
                    case 10:
                        Retirar_Vehiculo(empresa, usuario);
                        break;
                    case 11:
                        Devolver_Vehiculo(empresa, usuario);
                        break;
                    case 12:
                        Cambiar_Estado(empresa);
                        break;
                    case 0:
                        System.out.println("Sesion cerrada.");
                        break;
                    default:
                        System.out.println("Opcion invalida...");
                }
            } catch (Valores_Invalidos | Elemento_No_Encontrado | Vehiculo_No_Disponible e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void Menu_Cliente(Empresa_Alquiler empresa, Usuarios usuario) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Ver vehiculos disponibles");
            System.out.println("2. Reservar un vehiculo");
            System.out.println("3. Ver mis reservas activas");
            System.out.println("4. Cancelar una reserva");
            System.out.println("5. Ver mi historial de reservas");
            System.out.println("0. Cerrar sesion");
            opcion = Leer_Entero("Opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        System.out.println(empresa.Listar_Vehiculos_Disponibles());
                        break;
                    case 2:
                        Hacer_Reserva(empresa, usuario.getDni());
                        break;
                    case 3:
                        System.out.println(empresa.Listar_Reservas_De_Cliente(usuario.getDni()));
                        break;
                    case 4:
                        int id = Leer_Entero("Numero de reserva a cancelar: ");
                        empresa.Cancelar_Reserva_De_Cliente(id, usuario.getDni());
                        System.out.println("Reserva cancelada.");
                        break;
                    case 5:
                        System.out.println(empresa.Historial_De_Cliente(usuario.getDni()));
                        break;
                    case 0:
                        System.out.println("Sesion cerrada.");
                        break;
                    default:
                        System.out.println("Opcion invalida...");
                }
            } catch (Valores_Invalidos | Elemento_No_Encontrado | Vehiculo_No_Disponible e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    public static void Hacer_Reserva(Empresa_Alquiler empresa, String dniCliente) {
        System.out.println(empresa.Listar_Vehiculos_Disponibles());
        String patente = Leer_Texto("Patente del vehiculo: ");
        LocalDate inicio = Leer_Fecha("Fecha de inicio (aaaa-mm-dd): ");
        LocalDate fin = Leer_Fecha("Fecha de fin (aaaa-mm-dd): ");
        Reservas reserva = empresa.Crear_Reserva(dniCliente, patente, inicio, fin);
        System.out.println("Reserva creada: " + reserva);
        System.out.println("Recorda que para retirar el vehiculo la reserva debe estar paga , el pago se realiza con un empleado en la sucursal...");
    }

    public static void Retirar_Vehiculo(Empresa_Alquiler empresa, Usuarios empleado) {
        int idReserva = Leer_Entero("Numero de reserva: ");
        double km = Leer_Decimal("Kilometraje de salida: ");
        double combustible = Leer_Decimal("Nivel de combustible (0 a 100): ");
        System.out.println(empresa.Retirar_Vehiculo(idReserva, empleado.getDni(), km, combustible));
    }

    public static void Devolver_Vehiculo(Empresa_Alquiler empresa, Usuarios empleado) {
        int idReserva = Leer_Reserva_Para_Devolucion(empresa);
        if (idReserva == 0) {
            System.out.println("Devolucion cancelada...");
            return;
        }
        double km = Leer_Decimal("Kilometraje de llegada: ");
        String detalles = Leer_Texto("Detalles de la condicion del vehiculo: ");
        double cargos = Leer_Decimal("Cargos extra (0 si no hay): ");
        System.out.println("Como queda el vehiculo? 1. Disponible  2. En limpieza  3. En reparacion");
        int estado = Leer_Entero("Opcion: ");
        Estado_Vehiculo estadoFinal;
        if (estado == 2) {
            estadoFinal = Estado_Vehiculo.EN_LIMPIEZA;
        } else if (estado == 3) {
            estadoFinal = Estado_Vehiculo.EN_REPARACION;
        } else {
            estadoFinal = Estado_Vehiculo.DISPONIBLE;
        }
        System.out.println(empresa.Devolver_Vehiculo(idReserva, empleado.getDni(), km, detalles, cargos, estadoFinal));
    }

    public static void Cambiar_Estado(Empresa_Alquiler empresa) {
        String patente = Leer_Texto("Patente del vehiculo: ");
        System.out.println("Nuevo estado: 1. Disponible  2. En limpieza  3. En reparacion");
        int estado = Leer_Entero("Opcion: ");
        Estado_Vehiculo nuevo;
        if (estado == 1) {
            nuevo = Estado_Vehiculo.DISPONIBLE;
        } else if (estado == 2) {
            nuevo = Estado_Vehiculo.EN_LIMPIEZA;
        } else if (estado == 3) {
            nuevo = Estado_Vehiculo.EN_REPARACION;
        } else {
            System.out.println("Opcion invalida...");
            return;
        }
        empresa.Cambiar_Estado_Vehiculo(patente, nuevo);
        System.out.println("Estado actualizado.");
    }

    public static void Crear_Usuario(Empresa_Alquiler empresa) {
        System.out.println("Tipo de usuario: 1. Cliente  2. Empleado  3. Administrador");
        int tipo = Leer_Entero("Opcion: ");
        if (tipo == 1) {
            empresa.Agregar_Usuario(Pedir_Cliente());
        } else if (tipo == 2) {
            String dni = Leer_Texto("DNI: ");
            String nombre = Leer_Texto("Nombre: ");
            String email = Leer_Texto("Email: ");
            String password = Leer_Texto("Password: ");
            String legajo = Leer_Texto("Legajo: ");
            String sucursal = Leer_Texto("Sucursal: ");
            empresa.Agregar_Usuario(new Empleado(dni, email, nombre, password, legajo, sucursal));
        } else if (tipo == 3) {
            String dni = Leer_Texto("DNI: ");
            String nombre = Leer_Texto("Nombre: ");
            String email = Leer_Texto("Email: ");
            String password = Leer_Texto("Password: ");
            String codigo = Leer_Texto("Codigo de administrador: ");
            empresa.Agregar_Usuario(new Administrador(dni, email, nombre, password, codigo));
        } else {
            System.out.println("Opcion invalida...");
            return;
        }
        System.out.println("Usuario creado.");
    }

    public static void Agregar_Vehiculo(Empresa_Alquiler empresa) {
        System.out.println("Tipo de vehiculo: 1. Auto  2. Camioneta");
        int tipo = Leer_Entero("Opcion: ");
        if (tipo != 1 && tipo != 2) {
            System.out.println("Opcion invalida...");
            return;
        }
        String patente = Leer_Texto("Patente: ");
        String marca = Leer_Texto("Marca: ");
        String modelo = Leer_Texto("Modelo: ");
        double tarifa = Leer_Decimal("Tarifa diaria: ");
        if (tipo == 1) {
            int puertas = Leer_Entero("Cantidad de puertas (3 o 5): ");
            boolean baul = Leer_Si_No("Tiene baul grande? (s/n): ");
            empresa.Agregar_Vehiculo(new Auto(patente, marca, modelo, tarifa, Estado_Vehiculo.DISPONIBLE, puertas, baul));
        } else {
            double capacidad = Leer_Decimal("Capacidad de carga en kg: ");
            boolean traccion = Leer_Si_No("Tiene traccion 4x4? (s/n): ");
            empresa.Agregar_Vehiculo(new Camioneta(patente, marca, modelo, tarifa, Estado_Vehiculo.DISPONIBLE, capacidad, traccion));
        }
        System.out.println("Vehiculo agregado.");
    }


    public static Cliente Pedir_Cliente() {
        String dni = Leer_Dni("DNI (7 u 8 digitos): ");
        String nombre = Leer_Texto("Nombre: ");
        String email = Leer_Email("Email: ");
        String password = Leer_Texto("Password: ");
        String licencia = Leer_Texto("Licencia de conducir: ");
        String tarjeta = Leer_Tarjeta("Tarjeta de credito: ");
        return new Cliente(dni, email, nombre, password, tarjeta, licencia);
    }

    public static int Leer_Reserva_Para_Pago(Empresa_Alquiler empresa) {
        while (true) {
            try {
                int idReserva = Leer_Entero("Numero de reserva (0 para cancelar): ");
                if (idReserva == 0) {
                    return 0;
                }
                Reservas reserva = empresa.Buscar_Reserva_Para_Pago(idReserva);
                System.out.println("Monto a pagar: " + reserva.getCostoTotal());
                return idReserva;
            } catch (Elemento_No_Encontrado | Valores_Invalidos e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Vuelva a ingresar el numero de reserva.");
            }
        }
    }

    public static int Leer_Reserva_Para_Devolucion(Empresa_Alquiler empresa) {
        while (true) {
            try {
                int idReserva = Leer_Entero("Numero de reserva (0 para cancelar): ");
                if (idReserva == 0) {
                    return 0;
                }
                empresa.Buscar_Reserva_Para_Devolucion(idReserva);
                return idReserva;
            } catch (Elemento_No_Encontrado | Valores_Invalidos e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Vuelva a ingresar el numero de reserva.");
            }
        }
    }

    public static String Leer_Texto(String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este dato no puede quedar vacio...");
            }
        }
        return texto;
    }

    public static boolean Es_Tarjeta_Valida(String tarjeta) {
        return tarjeta.matches("\\d{16}");
    }

    public static String Leer_Tarjeta(String mensaje) throws Valores_Invalidos {
            while (true) {
                try {
                    String tarjeta = Leer_Texto(mensaje).replace(" ", "").replace("-", "");

                    if (!Es_Tarjeta_Valida(tarjeta)) {
                        throw new Valores_Invalidos("Tarjeta invalida. Debe tener 16 digitos numericos.");
                    }

                    return tarjeta;

                } catch (Valores_Invalidos e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public static boolean Es_Dni_Valido(String dni) {
        return dni.matches("\\d{7,8}");
    }


    public static String Leer_Dni_Cliente_Existente(Empresa_Alquiler empresa) {
        while (true) {
            String dni = Leer_Dni("DNI del cliente: ");
            try {
                empresa.Buscar_Cliente(dni);
                return dni;
            } catch (Elemento_No_Encontrado e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Vuelva a ingresar el DNI.");
            }
        }
    }

    public static String Leer_Dni(String mensaje) {
        while (true) {
            try {
                String dni = Leer_Texto(mensaje);

                if (!Es_Dni_Valido(dni)) {
                    throw new Valores_Invalidos("DNI invalido. Debe tener 7 u 8 digitos numericos, sin puntos ni espacios.");
                }

                return dni;

            } catch (Valores_Invalidos e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean Es_Email_Valido(String email) {
        return email.matches("[\\w.+-]+@[\\w-]+\\.[\\w.-]+");
    }

    public static String Leer_Email(String mensaje) throws Valores_Invalidos{
        while (true) {
            try {
                String email = Leer_Texto(mensaje);

                if (!Es_Email_Valido(email)) {
                    throw new Valores_Invalidos("Formato de email inválido. Ejemplo: nombre@dominio.com");
                }

                return email;

            } catch (Valores_Invalidos e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int Leer_Entero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String texto = teclado.nextLine().trim();
                try {
                    return Integer.parseInt(texto);
                } catch (NumberFormatException e) {
                    throw new Valores_Invalidos("Tenes que ingresar un numero entero...");
                }
            } catch (Valores_Invalidos e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double Leer_Decimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String texto = teclado.nextLine().trim();
                try {
                    return Double.parseDouble(texto);
                } catch (NumberFormatException e) {
                    throw new Valores_Invalidos("Tenes que ingresar un numero (ejemplo 1500.5)...");
                }
            } catch (Valores_Invalidos e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static LocalDate Leer_Fecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String texto = teclado.nextLine().trim();
                try {
                    return LocalDate.parse(texto);
                } catch (DateTimeParseException e) {
                    throw new Valores_Invalidos("Fecha invalida, el formato es aaaa-mm-dd (ejemplo 2026-12-24)...");
                }
            } catch (Valores_Invalidos e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean Leer_Si_No(String mensaje) {
        String respuesta = Leer_Texto(mensaje);
        return respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si");
    }

}
