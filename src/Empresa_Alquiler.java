import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Empresa_Alquiler {
    private Arreglo_Generico<Vehiculo> vehiculos;
    private Arreglo_Generico<Reservas> reservasActivas;
    private Hashset_Generico<Usuarios> usuarios;

    public Empresa_Alquiler() {
        vehiculos = new Arreglo_Generico<>();
        reservasActivas = new Arreglo_Generico<>();
        usuarios = new Hashset_Generico<>();
        Cargar_Datos();
    }

    public void Cargar_Datos() {
        usuarios.Cargar(ConexionDB.ObtenerUsuarios());
        vehiculos.Cargar(ConexionDB.ObtenerVehiculos());
        reservasActivas.Cargar(ConexionDB.ObtenerReservasActivas());
    }

    public String Resumen_Carga() {
        return "Datos cargados: " + usuarios.Tamanio() + " usuarios, " + vehiculos.Tamanio() + " vehiculos, " + reservasActivas.Tamanio() + " reservas activas.";
    }


    public Usuarios Login(String email, String password) {
        for (Usuarios usuario : usuarios.getRegistro()) {
            if (usuario.getEmail().equalsIgnoreCase(email) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        throw new Elemento_No_Encontrado("Email o contrasena incorrectos...");
    }

    public void Agregar_Usuario(Usuarios nuevo) {
        if (usuarios.Buscar_Por_Id(nuevo.getDni()) != null) {
            throw new Valores_Invalidos("Ya existe un usuario con el DNI " + nuevo.getDni());
        }

        int filas = 0;
        if (nuevo instanceof Administrador) {
            Administrador admin = (Administrador) nuevo;
            filas = ConexionDB.InsertarUsuario(admin.getDni(), admin.getNombre(), admin.getEmail(), admin.getPassword(), admin.Obtener_Tipo(), admin.getCodigo_admin(), null, null, null, null);
        } else if (nuevo instanceof Empleado) {
            Empleado empleado = (Empleado) nuevo;
            filas = ConexionDB.InsertarUsuario(empleado.getDni(), empleado.getNombre(), empleado.getEmail(), empleado.getPassword(), empleado.Obtener_Tipo(), null, empleado.getLegajo(), empleado.getSucursal(), null, null);
        } else if (nuevo instanceof Cliente) {
            Cliente cliente = (Cliente) nuevo;
            filas = ConexionDB.InsertarUsuario(cliente.getDni(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(), cliente.Obtener_Tipo(), null, null, null, cliente.getLicenciaConducir(), cliente.getTarjetaCredito());
        }

        if (filas == 0) {
            throw new Valores_Invalidos("No se pudo guardar el usuario (revisa que el email no este repetido)...");
        }
        usuarios.Cargar_Registro(nuevo);
    }

    public void Eliminar_Usuario(String dni) {
        Usuarios usuario = usuarios.Buscar_Por_Id(dni);
        if (usuario == null) {
            throw new Elemento_No_Encontrado("No existe un usuario con el DNI " + dni);
        }
        int filas = ConexionDB.EliminarUsuario(dni);
        if (filas == 0) {
            throw new Valores_Invalidos("No se pudo eliminar el usuario (puede tener reservas)...");
        }
        usuarios.Eliminar_Registro(usuario);
    }

    public String Listar_Usuarios() {
        return usuarios.Mostrar_Hashset();
    }

    public String Listar_Clientes() {
        StringBuilder info = new StringBuilder();
        for (Usuarios usuario : usuarios.getRegistro()) {
            if (usuario instanceof Cliente) {
                info.append(usuario.toString()).append("\n");
            }
        }
        if (info.length() == 0) {
            return "No hay clientes cargados...\n";
        }
        return info.toString();
    }


    public void Agregar_Vehiculo(Vehiculo nuevo) {
        if (vehiculos.Buscar_Por_Id(nuevo.getPatente()) != null) {
            throw new Valores_Invalidos("Ya existe un vehiculo con la patente " + nuevo.getPatente());
        }

        int filas = 0;
        if (nuevo instanceof Auto) {
            Auto auto = (Auto) nuevo;
            filas = ConexionDB.InsertarVehiculo(auto.getPatente(), auto.getMarca(), auto.getModelo(), auto.getTarifaDiaria(), auto.getEstadoVehiculo().name(), "AUTO", auto.getCantidad_Puertas(), auto.isBaul_grande(), null, null);
        } else if (nuevo instanceof Camioneta) {
            Camioneta camioneta = (Camioneta) nuevo;
            filas = ConexionDB.InsertarVehiculo(camioneta.getPatente(), camioneta.getMarca(), camioneta.getModelo(), camioneta.getTarifaDiaria(), camioneta.getEstadoVehiculo().name(), "CAMIONETA", null, null, camioneta.getCapacidadCargakg(), camioneta.isTraccion4x4());
        }

        if (filas == 0) {
            throw new Valores_Invalidos("No se pudo guardar el vehiculo en la base de datos...");
        }
        vehiculos.Cargar_Registro(nuevo);
    }

    public void Eliminar_Vehiculo(String patente) {
        Vehiculo vehiculo = vehiculos.Buscar_Por_Id(patente);
        if (vehiculo == null) {
            throw new Elemento_No_Encontrado("No existe un vehiculo con la patente " + patente);
        }
        int filas = ConexionDB.EliminarVehiculo(patente);
        if (filas == 0) {
            throw new Valores_Invalidos("No se pudo eliminar el vehiculo (puede tener reservas)...");
        }
        vehiculos.Eliminar_Registro(vehiculo);
    }

    public void Cambiar_Estado_Vehiculo(String patente, Estado_Vehiculo nuevoEstado) {
        Vehiculo vehiculo = vehiculos.Buscar_Por_Id(patente);
        if (vehiculo == null) {
            throw new Elemento_No_Encontrado("No existe un vehiculo con la patente " + patente);
        }
        if (vehiculo.getEstadoVehiculo() == Estado_Vehiculo.ALQUILADO) {
            throw new Vehiculo_No_Disponible("El vehiculo esta alquilado, primero hay que registrar la devolucion...");
        }
        ConexionDB.EditarEstadoVehiculo(patente, nuevoEstado.name());
        vehiculo.setEstadoVehiculo(nuevoEstado);
    }

    public String Listar_Todos_Los_Vehiculos() {
        return vehiculos.Mostrar_Arreglo();
    }

    public String Listar_Vehiculos_Disponibles() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < vehiculos.Tamanio(); i++) {
            Vehiculo vehiculo = vehiculos.Obtener(i);
            if (vehiculo.Verificar_Disponibilidad()) {
                info.append(vehiculo.toString()).append("\n");
            }
        }
        return info.toString();
    }

    public String Listar_Vehiculos_No_Disponibles() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < vehiculos.Tamanio(); i++) {
            Vehiculo vehiculo = vehiculos.Obtener(i);
            if (!vehiculo.Verificar_Disponibilidad()) {
                info.append(vehiculo.toString()).append("\n   Motivo: ").append(vehiculo.getEstadoVehiculo().getMotivo()).append("\n");
            }
        }

        return info.toString();
    }

    public String Listar_Vehiculos_Alquilados() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < reservasActivas.Tamanio(); i++) {
            Reservas reserva = reservasActivas.Obtener(i);
            if (ConexionDB.ObtenerOcupacionIdPorReserva(reserva.getIdReserva()) != -1) {
                Vehiculo vehiculo = vehiculos.Buscar_Por_Id(reserva.getVehiculoPatente());
                Usuarios conductor = usuarios.Buscar_Por_Id(reserva.getClienteDni());
                info.append(vehiculo).append("\n");
                info.append("   Conductor: ").append(conductor).append("\n");
                info.append("   Reserva Nro ").append(reserva.getIdReserva()).append(" hasta el ").append(reserva.getFechaFin()).append("\n");
            }
        }
        if (info.length() == 0) {
            return "No hay vehiculos alquilados en este momento...\n";
        }
        return info.toString();
    }


    public Reservas Crear_Reserva(String dniCliente, String patente, LocalDate fechaInicio, LocalDate fechaFin) {
        Usuarios cliente = usuarios.Buscar_Por_Id(dniCliente);
        if (cliente == null || !(cliente instanceof Cliente)) {
            throw new Elemento_No_Encontrado("No existe un cliente con el DNI " + dniCliente);
        }
        Vehiculo vehiculo = vehiculos.Buscar_Por_Id(patente);
        if (vehiculo == null) {
            throw new Elemento_No_Encontrado("No existe un vehiculo con la patente " + patente);
        }

        Reservas nueva = new Reservas(fechaInicio, fechaFin, null, false, patente, dniCliente);

        float costo = (float) vehiculo.Calcular_Costos(nueva.Calcular_Dias());
        nueva.setCostoTotal(costo);

        int id = ConexionDB.InsertarReserva(fechaInicio.toString(), fechaFin.toString(), costo, false, patente, dniCliente);
        if (id == -1) {
            throw new Valores_Invalidos("No se pudo guardar la reserva en la base de datos...");
        }
        nueva.setIdReserva(id);
        reservasActivas.Cargar_Registro(nueva);
        return nueva;
    }

    public Reservas Buscar_Reserva_Para_Pago(int idReserva) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null) {
            throw new Elemento_No_Encontrado("No existe una reserva activa con el numero " + idReserva);
        }
        if (reserva.isEstaPagada()) {
            throw new Valores_Invalidos("La reserva " + idReserva + " ya esta paga...");
        }
        return reserva;
    }

    public Reservas Buscar_Reserva_Para_Devolucion(int idReserva) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null) {
            throw new Elemento_No_Encontrado("No existe una reserva activa con el numero " + idReserva);
        }
        if (ConexionDB.ObtenerOcupacionIdPorReserva(idReserva) == -1) {
            throw new Valores_Invalidos("El vehiculo de la reserva " + idReserva + " todavia no fue retirado...");
        }
        return reserva;
    }

    public void Cancelar_Reserva(int idReserva) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null) {
            throw new Elemento_No_Encontrado("No existe una reserva activa con el numero " + idReserva);
        }
        int filas = ConexionDB.EliminarReserva(idReserva);
        if (filas == 0) {
            throw new Valores_Invalidos("No se pudo cancelar la reserva (puede que el vehiculo ya fue retirado)...");
        }
        reservasActivas.Eliminar_Registro(reserva);
    }

    public void Cancelar_Reserva_De_Cliente(int idReserva, String dniCliente) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null || !reserva.getClienteDni().equals(dniCliente)) {
            throw new Elemento_No_Encontrado("No tenes una reserva activa con el numero " + idReserva);
        }
        Cancelar_Reserva(idReserva);
    }

    public void Registrar_Pago(int idReserva) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null) {
            throw new Elemento_No_Encontrado("No existe una reserva activa con el numero " + idReserva);
        }
        ConexionDB.EditarEstaPagadaReserva(idReserva, true);
        reserva.setEstaPagada(true);
    }

    public Cliente Buscar_Cliente(String dni) {
        Usuarios usuario = usuarios.Buscar_Por_Id(dni);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new Elemento_No_Encontrado("No existe un cliente con el DNI " + dni);
        }
        return (Cliente) usuario;
    }

    public String Listar_Reservas_Activas() {
        if (reservasActivas.Tamanio() == 0) {
            return "No hay reservas activas...\n";
        }
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < reservasActivas.Tamanio(); i++) {
            Reservas reserva = reservasActivas.Obtener(i);
            info.append(reserva.toString());
            if (ConexionDB.ObtenerOcupacionIdPorReserva(reserva.getIdReserva()) != -1) {
                info.append(" -> VEHICULO RETIRADO");
            } else {
                info.append(" -> pendiente de retiro");
            }
            info.append("\n");
        }
        return info.toString();
    }

    public String Listar_Reservas_De_Cliente(String dniCliente) {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < reservasActivas.Tamanio(); i++) {
            Reservas reserva = reservasActivas.Obtener(i);
            if (reserva.getClienteDni().equals(dniCliente)) {
                info.append(reserva.toString()).append("\n");
            }
        }
        if (info.length() == 0) {
            return "No tenes reservas activas...\n";
        }
        return info.toString();
    }

    public String Historial_De_Cliente(String dniCliente) {
        ArrayList<Reservas> historial = ConexionDB.ObtenerReservasDeCliente(dniCliente);
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < historial.size(); i++) {
            info.append(historial.get(i).toString()).append("\n");
        }
        return info.toString();
    }


    public String Retirar_Vehiculo(int idReserva, String dniEmpleado, double kilometrajeSalida, double nivelCombustible) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null) {
            throw new Elemento_No_Encontrado("No existe una reserva activa con el numero " + idReserva);
        }
        if (!reserva.isEstaPagada()) {
            throw new Valores_Invalidos("La reserva " + idReserva + " no esta paga. Registre el pago antes de entregar el vehiculo.");
        }
        Vehiculo vehiculo = vehiculos.Buscar_Por_Id(reserva.getVehiculoPatente());
        if (!vehiculo.Verificar_Disponibilidad()) {
            throw new Vehiculo_No_Disponible("El vehiculo esta: " + vehiculo.getEstadoVehiculo().getMotivo());
        }

        String fechaHora = LocalDateTime.now().format(ConexionDB.formato_fecha_hora);
        int idEntrega = ConexionDB.RegistrarRetiro(idReserva, fechaHora, kilometrajeSalida, nivelCombustible, dniEmpleado, vehiculo.getPatente());
        if (idEntrega == -1) {
            throw new Valores_Invalidos("No se pudo registrar el retiro en la base de datos...");
        }
        vehiculo.setEstadoVehiculo(Estado_Vehiculo.ALQUILADO);
        return "Entrega Nro " + idEntrega + " registrada. El vehiculo " + vehiculo.getPatente() + " quedo ALQUILADO.";
    }


    public String Devolver_Vehiculo(int idReserva, String dniEmpleado, double kilometrajeLlegada, String detalles, double cargosExtra, Estado_Vehiculo estadoFinal) {
        Reservas reserva = reservasActivas.Buscar_Por_Id(idReserva);
        if (reserva == null) {
            throw new Elemento_No_Encontrado("No existe una reserva activa con el numero " + idReserva);
        }
        int idOcupacion = ConexionDB.ObtenerOcupacionIdPorReserva(idReserva);
        if (idOcupacion == -1) {
            throw new Valores_Invalidos("El vehiculo de esa reserva todavia no fue retirado...");
        }

        Devoluciones.Devolucion devolucion = new Devoluciones.Devolucion(LocalDateTime.now(), (float) kilometrajeLlegada, detalles, (float) cargosExtra, idOcupacion, dniEmpleado);

        int idDevolucion = ConexionDB.RegistrarDevolucion(devolucion, reserva.getVehiculoPatente(), estadoFinal.name());
        if (idDevolucion == -1) {
            throw new Valores_Invalidos("No se pudo registrar la devolucion en la base de datos...");
        }

        Vehiculo vehiculo = vehiculos.Buscar_Por_Id(reserva.getVehiculoPatente());
        vehiculo.setEstadoVehiculo(estadoFinal);
        reservasActivas.Eliminar_Registro(reserva);

        double total = reserva.getCostoTotal() + cargosExtra;
        return "Devolucion Nro " + idDevolucion + " registrada. El vehiculo " + vehiculo.getPatente() + " quedo: " + estadoFinal.getMotivo() +
                ".\nCosto del alquiler: " + reserva.getCostoTotal() + " + cargos extra: " + cargosExtra + " = Total: " + total;
    }

}
