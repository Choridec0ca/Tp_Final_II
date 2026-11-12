import java.sql.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ConexionDB {

    final static String archivo = "alquiler_vehiculos.sqlite";
    final static String url = "jdbc:sqlite:" + archivo + "?foreign_keys=on";
    final static DateTimeFormatter formato_fecha_hora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static int InsertarUsuario(String dni, String nombre, String email, String password, String tipo_Usuario, String codigo_admin, String legajo, String sucursal, String licencia_conducir, String tarjeta_credito) {
        int filas_afectadas = 0;
        String insert = "insert into usuarios (dni , nombre , email, password , tipo_usuario , codigo_admin , legajo , sucursal , licencia_conducir , tarjeta_credito ) values (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, dni);
            statement.setString(2, nombre);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, tipo_Usuario);
            statement.setString(6, codigo_admin);
            statement.setString(7, legajo);
            statement.setString(8, sucursal);
            statement.setString(9, licencia_conducir);
            statement.setString(10, tarjeta_credito);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static void MostrarUsuarios() {
        String select = "select dni , nombre , email , password , tipo_usuario , codigo_admin , legajo , sucursal , licencia_conducir , tarjeta_credito from usuarios";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                String dni = resultado.getString("dni");
                String nombre = resultado.getString("nombre");
                String email = resultado.getString("email");
                String password = resultado.getString("password");
                String tipo_usuario = resultado.getString("tipo_usuario");
                String codigo_admin = resultado.getString("codigo_admin");
                String legajo = resultado.getString("legajo");
                String sucursal = resultado.getString("sucursal");
                String licencia_conducir = resultado.getString("licencia_conducir");
                String tarjeta_credito = resultado.getString("tarjeta_credito");
                System.out.println(dni + " | " + nombre + " | " + email + " | " + password + " | " + tipo_usuario + " | " + codigo_admin + " | " + legajo + " | " + sucursal + " | " + licencia_conducir + " | " + tarjeta_credito);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int InsertarVehiculo(String patente, String marca, String modelo, double tarifa_diaria, String estado, String tipo_vehiculo, Integer cantidad_puertas, Boolean tiene_baul_grande, Double capacidad_carga_kg, Boolean traccion_4x4) {
        int filas_afectadas = 0;
        String insert = "insert into vehiculos (patente , marca , modelo , tarifa_diaria , estado , tipo_vehiculo , cantidad_puertas , tiene_baul_grande , capacidad_carga_kg , traccion_4x4) values (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, patente);
            statement.setString(2, marca);
            statement.setString(3, modelo);
            statement.setDouble(4, tarifa_diaria);
            statement.setString(5, estado);
            statement.setString(6, tipo_vehiculo);
            statement.setObject(7, cantidad_puertas);
            statement.setObject(8, tiene_baul_grande);
            statement.setObject(9, capacidad_carga_kg);
            statement.setObject(10, traccion_4x4);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }


    public static void MostrarVehiculos() {
        String select = "select patente , marca , modelo , tarifa_diaria , estado , tipo_vehiculo , cantidad_puertas , tiene_baul_grande , capacidad_carga_kg , traccion_4x4 from vehiculos";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                String patente = resultado.getString("patente");
                String marca = resultado.getString("marca");
                String modelo = resultado.getString("modelo");
                double tarifa_diaria = resultado.getDouble("tarifa_diaria");
                String estado = resultado.getString("estado");
                String tipo_vehiculo = resultado.getString("tipo_vehiculo");
                Object cantidad_puertas = resultado.getObject("cantidad_puertas");
                Object tiene_baul_grande = resultado.getObject("tiene_baul_grande");
                Object capacidad_carga_kg = resultado.getObject("capacidad_carga_kg");
                Object traccion_4x4 = resultado.getObject("traccion_4x4");
                System.out.println(patente + " | " + marca + " | " + modelo + " | " + tarifa_diaria + " | " + estado + " | " + tipo_vehiculo + " | " + cantidad_puertas + " | " + tiene_baul_grande + " | " + capacidad_carga_kg + " | " + traccion_4x4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int InsertarReserva(String fecha_inicio, String fecha_fin, double costo_total, boolean esta_pagada, String vehiculo_patente, String cliente_dni) {
        int id_generado = -1;
        String insert = "insert into reservas (fecha_inicio , fecha_fin , costo_total , esta_pagada , vehiculo_patente , cliente_dni) values (? , ? , ? , ? , ? , ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fecha_inicio);   // formato yyyy-MM-dd
            statement.setString(2, fecha_fin);      // formato yyyy-MM-dd
            statement.setDouble(3, costo_total);
            statement.setBoolean(4, esta_pagada);
            statement.setString(5, vehiculo_patente);
            statement.setString(6, cliente_dni);
            int filas_afectadas = statement.executeUpdate();
            if (filas_afectadas > 0) {
                try (ResultSet claves = statement.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_generado = claves.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_generado;
    }


    public static void MostrarReservas() {
        String select = "select id_reserva , fecha_inicio , fecha_fin , costo_total , esta_pagada , vehiculo_patente , cliente_dni from reservas";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                int id_reserva = resultado.getInt("id_reserva");
                String fecha_inicio = resultado.getString("fecha_inicio");
                String fecha_fin = resultado.getString("fecha_fin");
                double costo_total = resultado.getDouble("costo_total");
                boolean esta_pagada = resultado.getBoolean("esta_pagada");
                String vehiculo_patente = resultado.getString("vehiculo_patente");
                String cliente_dni = resultado.getString("cliente_dni");
                System.out.println(id_reserva + " | " + fecha_inicio + " | " + fecha_fin + " | " + costo_total + " | " + esta_pagada + " | " + vehiculo_patente + " | " + cliente_dni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int InsertarOcupacion(boolean esta_activa, int reserva_id) {
        int id_generado = -1;
        String insert = "insert into ocupaciones (esta_activa , reserva_id) values (? , ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, esta_activa);
            statement.setInt(2, reserva_id);
            int filas_afectadas = statement.executeUpdate();
            if (filas_afectadas > 0) {
                try (ResultSet claves = statement.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_generado = claves.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_generado;
    }


    public static void MostrarOcupaciones() {
        String select = "select id_ocupacion , esta_activa , reserva_id from ocupaciones";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                int id_ocupacion = resultado.getInt("id_ocupacion");
                boolean esta_activa = resultado.getBoolean("esta_activa");
                int reserva_id = resultado.getInt("reserva_id");
                System.out.println(id_ocupacion + " | " + esta_activa + " | " + reserva_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int InsertarEntrega(String fecha_hora_real, double kilometraje_salida, double nivel_combustible, int ocupacion_id, String empleado_dni) {
        int id_generado = -1;
        String insert = "insert into entregas (fecha_hora_real , kilometraje_salida , nivel_combustible , ocupacion_id , empleado_dni) values (? , ? , ? , ? , ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fecha_hora_real);
            statement.setDouble(2, kilometraje_salida);
            statement.setDouble(3, nivel_combustible);
            statement.setInt(4, ocupacion_id);
            statement.setString(5, empleado_dni);
            int filas_afectadas = statement.executeUpdate();
            if (filas_afectadas > 0) {
                try (ResultSet claves = statement.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_generado = claves.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_generado;
    }


    public static void MostrarEntregas() {
        String select = "select id_entrega , fecha_hora_real , kilometraje_salida , nivel_combustible , ocupacion_id , empleado_dni from entregas";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                int id_entrega = resultado.getInt("id_entrega");
                String fecha_hora_real = resultado.getString("fecha_hora_real");
                double kilometraje_salida = resultado.getDouble("kilometraje_salida");
                double nivel_combustible = resultado.getDouble("nivel_combustible");
                int ocupacion_id = resultado.getInt("ocupacion_id");
                String empleado_dni = resultado.getString("empleado_dni");
                System.out.println(id_entrega + " | " + fecha_hora_real + " | " + kilometraje_salida + " | " + nivel_combustible + " | " + ocupacion_id + " | " + empleado_dni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int InsertarDevolucion(String fecha_hora_real, double kilometraje_llegada, String detalles_condicion, double cargos_extra, int ocupacion_id, String empleado_dni) {
        int id_generado = -1;
        String insert = "insert into devoluciones (fecha_hora_real , kilometraje_llegada , detalles_condicion , cargos_extra , ocupacion_id , empleado_dni) values (? , ? , ? , ? , ? , ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fecha_hora_real);
            statement.setDouble(2, kilometraje_llegada);
            statement.setString(3, detalles_condicion);
            statement.setDouble(4, cargos_extra);
            statement.setInt(5, ocupacion_id);
            statement.setString(6, empleado_dni);
            int filas_afectadas = statement.executeUpdate();
            if (filas_afectadas > 0) {
                try (ResultSet claves = statement.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_generado = claves.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_generado;
    }


    public static void MostrarDevoluciones() {
        String select = "select id_devolucion , fecha_hora_real , kilometraje_llegada , detalles_condicion , cargos_extra , ocupacion_id , empleado_dni from devoluciones";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                int id_devolucion = resultado.getInt("id_devolucion");
                String fecha_hora_real = resultado.getString("fecha_hora_real");
                double kilometraje_llegada = resultado.getDouble("kilometraje_llegada");
                String detalles_condicion = resultado.getString("detalles_condicion");
                double cargos_extra = resultado.getDouble("cargos_extra");
                int ocupacion_id = resultado.getInt("ocupacion_id");
                String empleado_dni = resultado.getString("empleado_dni");
                System.out.println(id_devolucion + " | " + fecha_hora_real + " | " + kilometraje_llegada + " | " + detalles_condicion + " | " + cargos_extra + " | " + ocupacion_id + " | " + empleado_dni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int EditarNombreUsuario(String dni, String nombre) {
        int filas_afectadas = 0;
        String update = "update usuarios set nombre = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, nombre);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarEmailUsuario(String dni, String email) {
        int filas_afectadas = 0;
        String update = "update usuarios set email = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, email);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarPasswordUsuario(String dni, String password) {
        int filas_afectadas = 0;
        String update = "update usuarios set password = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, password);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarTipoUsuarioUsuario(String dni, String tipo_usuario) {
        int filas_afectadas = 0;
        String update = "update usuarios set tipo_usuario = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, tipo_usuario);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarCodigoAdminUsuario(String dni, String codigo_admin) {
        int filas_afectadas = 0;
        String update = "update usuarios set codigo_admin = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, codigo_admin);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarLegajoUsuario(String dni, String legajo) {
        int filas_afectadas = 0;
        String update = "update usuarios set legajo = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, legajo);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarSucursalUsuario(String dni, String sucursal) {
        int filas_afectadas = 0;
        String update = "update usuarios set sucursal = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, sucursal);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarLicenciaConducirUsuario(String dni, String licencia_conducir) {
        int filas_afectadas = 0;
        String update = "update usuarios set licencia_conducir = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, licencia_conducir);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarTarjetaCreditoUsuario(String dni, String tarjeta_credito) {
        int filas_afectadas = 0;
        String update = "update usuarios set tarjeta_credito = ? where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, tarjeta_credito);
            statement.setString(2, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }


    public static int EditarMarcaVehiculo(String patente, String marca) {
        int filas_afectadas = 0;
        String update = "update vehiculos set marca = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, marca);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarModeloVehiculo(String patente, String modelo) {
        int filas_afectadas = 0;
        String update = "update vehiculos set modelo = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, modelo);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarTarifaDiariaVehiculo(String patente, double tarifa_diaria) {
        int filas_afectadas = 0;
        String update = "update vehiculos set tarifa_diaria = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, tarifa_diaria);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarEstadoVehiculo(String patente, String estado) {
        int filas_afectadas = 0;
        String update = "update vehiculos set estado = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, estado);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarTipoVehiculoVehiculo(String patente, String tipo_vehiculo) {
        int filas_afectadas = 0;
        String update = "update vehiculos set tipo_vehiculo = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, tipo_vehiculo);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarCantidadPuertasVehiculo(String patente, Integer cantidad_puertas) {
        int filas_afectadas = 0;
        String update = "update vehiculos set cantidad_puertas = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setObject(1, cantidad_puertas);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarTieneBaulGrandeVehiculo(String patente, Boolean tiene_baul_grande) {
        int filas_afectadas = 0;
        String update = "update vehiculos set tiene_baul_grande = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setObject(1, tiene_baul_grande);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarCapacidadCargaKgVehiculo(String patente, Double capacidad_carga_kg) {
        int filas_afectadas = 0;
        String update = "update vehiculos set capacidad_carga_kg = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setObject(1, capacidad_carga_kg);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarTraccion4x4Vehiculo(String patente, Boolean traccion_4x4) {
        int filas_afectadas = 0;
        String update = "update vehiculos set traccion_4x4 = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setObject(1, traccion_4x4);
            statement.setString(2, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }


    public static int EditarFechaInicioReserva(int id_reserva, String fecha_inicio) {
        int filas_afectadas = 0;
        String update = "update reservas set fecha_inicio = ? where id_reserva = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, fecha_inicio);
            statement.setInt(2, id_reserva);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarFechaFinReserva(int id_reserva, String fecha_fin) {
        int filas_afectadas = 0;
        String update = "update reservas set fecha_fin = ? where id_reserva = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, fecha_fin);
            statement.setInt(2, id_reserva);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarCostoTotalReserva(int id_reserva, double costo_total) {
        int filas_afectadas = 0;
        String update = "update reservas set costo_total = ? where id_reserva = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, costo_total);
            statement.setInt(2, id_reserva);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarEstaPagadaReserva(int id_reserva, boolean esta_pagada) {
        int filas_afectadas = 0;
        String update = "update reservas set esta_pagada = ? where id_reserva = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setBoolean(1, esta_pagada);
            statement.setInt(2, id_reserva);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarVehiculoPatenteReserva(int id_reserva, String vehiculo_patente) {
        int filas_afectadas = 0;
        String update = "update reservas set vehiculo_patente = ? where id_reserva = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, vehiculo_patente);
            statement.setInt(2, id_reserva);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }


    public static int EditarEstaActivaOcupacion(int id_ocupacion, boolean esta_activa) {
        int filas_afectadas = 0;
        String update = "update ocupaciones set esta_activa = ? where id_ocupacion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setBoolean(1, esta_activa);
            statement.setInt(2, id_ocupacion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }


    public static int EditarFechaHoraRealEntrega(int id_entrega, String fecha_hora_real) {
        int filas_afectadas = 0;
        String update = "update entregas set fecha_hora_real = ? where id_entrega = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, fecha_hora_real);
            statement.setInt(2, id_entrega);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarKilometrajeSalidaEntrega(int id_entrega, double kilometraje_salida) {
        int filas_afectadas = 0;
        String update = "update entregas set kilometraje_salida = ? where id_entrega = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, kilometraje_salida);
            statement.setInt(2, id_entrega);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarNivelCombustibleEntrega(int id_entrega, double nivel_combustible) {
        int filas_afectadas = 0;
        String update = "update entregas set nivel_combustible = ? where id_entrega = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, nivel_combustible);
            statement.setInt(2, id_entrega);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarEmpleadoDniEntrega(int id_entrega, String empleado_dni) {
        int filas_afectadas = 0;
        String update = "update entregas set empleado_dni = ? where id_entrega = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, empleado_dni);
            statement.setInt(2, id_entrega);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }


    public static int EditarFechaHoraRealDevolucion(int id_devolucion, String fecha_hora_real) {
        int filas_afectadas = 0;
        String update = "update devoluciones set fecha_hora_real = ? where id_devolucion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, fecha_hora_real);
            statement.setInt(2, id_devolucion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarKilometrajeLlegadaDevolucion(int id_devolucion, double kilometraje_llegada) {
        int filas_afectadas = 0;
        String update = "update devoluciones set kilometraje_llegada = ? where id_devolucion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, kilometraje_llegada);
            statement.setInt(2, id_devolucion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarDetallesCondicionDevolucion(int id_devolucion, String detalles_condicion) {
        int filas_afectadas = 0;
        String update = "update devoluciones set detalles_condicion = ? where id_devolucion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, detalles_condicion);
            statement.setInt(2, id_devolucion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarCargosExtraDevolucion(int id_devolucion, double cargos_extra) {
        int filas_afectadas = 0;
        String update = "update devoluciones set cargos_extra = ? where id_devolucion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, cargos_extra);
            statement.setInt(2, id_devolucion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarOcupacionIdDevolucion(int id_devolucion, int ocupacion_id) {
        int filas_afectadas = 0;
        String update = "update devoluciones set ocupacion_id = ? where id_devolucion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setInt(1, ocupacion_id);
            statement.setInt(2, id_devolucion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static int EditarEmpleadoDniDevolucion(int id_devolucion, String empleado_dni) {
        int filas_afectadas = 0;
        String update = "update devoluciones set empleado_dni = ? where id_devolucion = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, empleado_dni);
            statement.setInt(2, id_devolucion);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas_afectadas;
    }

    public static HashSet<Usuarios> ObtenerUsuarios() {
        HashSet<Usuarios> usuarios = new HashSet<>();
        String select = "select dni , nombre , email , password , tipo_usuario , codigo_admin , legajo , sucursal , licencia_conducir , tarjeta_credito from usuarios";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                String dni = resultado.getString("dni");
                String nombre = resultado.getString("nombre");
                String email = resultado.getString("email");
                String password = resultado.getString("password");
                String tipo_usuario = resultado.getString("tipo_usuario");
                String codigo_admin = resultado.getString("codigo_admin");
                String legajo = resultado.getString("legajo");
                String sucursal = resultado.getString("sucursal");
                String licencia_conducir = resultado.getString("licencia_conducir");
                String tarjeta_credito = resultado.getString("tarjeta_credito");
                try {
                    if (tipo_usuario.equals("ADMINISTRADOR")) {
                        usuarios.add(new Administrador(dni, email, nombre, password, codigo_admin));
                    } else if (tipo_usuario.equals("EMPLEADO")) {
                        usuarios.add(new Empleado(dni, email, nombre, password, legajo, sucursal));
                    } else if (tipo_usuario.equals("CLIENTE")) {
                        usuarios.add(new Cliente(dni, email, nombre, password, tarjeta_credito, licencia_conducir));
                    }
                } catch (Valores_Invalidos e) {
                    System.out.println("Se ignoro el usuario " + dni + " de la BD: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public static ArrayList<Vehiculo> ObtenerVehiculos() {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        String select = "select patente , marca , modelo , tarifa_diaria , estado , tipo_vehiculo , cantidad_puertas , tiene_baul_grande , capacidad_carga_kg , traccion_4x4 from vehiculos";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                String patente = resultado.getString("patente");
                String marca = resultado.getString("marca");
                String modelo = resultado.getString("modelo");
                double tarifa_diaria = resultado.getDouble("tarifa_diaria");
                String estado = resultado.getString("estado");
                String tipo_vehiculo = resultado.getString("tipo_vehiculo");
                int cantidad_puertas = resultado.getInt("cantidad_puertas");
                boolean tiene_baul_grande = resultado.getBoolean("tiene_baul_grande");
                double capacidad_carga_kg = resultado.getDouble("capacidad_carga_kg");
                boolean traccion_4x4 = resultado.getBoolean("traccion_4x4");
                try {
                    Estado_Vehiculo estado_vehiculo = Estado_Vehiculo.valueOf(estado);
                    if (tipo_vehiculo.equals("AUTO")) {
                        vehiculos.add(new Auto(patente, marca, modelo, tarifa_diaria, estado_vehiculo, cantidad_puertas, tiene_baul_grande));
                    } else if (tipo_vehiculo.equals("CAMIONETA")) {
                        vehiculos.add(new Camioneta(patente, marca, modelo, tarifa_diaria, estado_vehiculo, capacidad_carga_kg, traccion_4x4));
                    }
                } catch (Valores_Invalidos | IllegalArgumentException e) {
                    System.out.println("Se ignoro el vehiculo " + patente + " de la BD: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    private static Reservas ReservaDesdeFila(ResultSet resultado) throws SQLException {
        int id_reserva = resultado.getInt("id_reserva");
        LocalDate fecha_inicio = LocalDate.parse(resultado.getString("fecha_inicio"));
        LocalDate fecha_fin = LocalDate.parse(resultado.getString("fecha_fin"));
        float costo_total = resultado.getFloat("costo_total");
        boolean esta_pagada = resultado.getBoolean("esta_pagada");
        String vehiculo_patente = resultado.getString("vehiculo_patente");
        String cliente_dni = resultado.getString("cliente_dni");
        return new Reservas(id_reserva, fecha_inicio, fecha_fin, costo_total, esta_pagada, vehiculo_patente, cliente_dni);
    }

    public static ArrayList<Reservas> ObtenerReservasActivas() {
        ArrayList<Reservas> reservas = new ArrayList<>();
        String select = "select id_reserva , fecha_inicio , fecha_fin , costo_total , esta_pagada , vehiculo_patente , cliente_dni from reservas " +
                "where id_reserva not in (select o.reserva_id from ocupaciones o join devoluciones d on d.ocupacion_id = o.id_ocupacion)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                reservas.add(ReservaDesdeFila(resultado));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public static ArrayList<Reservas> ObtenerReservasDeCliente(String cliente_dni) {
        ArrayList<Reservas> reservas = new ArrayList<>();
        String select = "select id_reserva , fecha_inicio , fecha_fin , costo_total , esta_pagada , vehiculo_patente , cliente_dni from reservas where cliente_dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setString(1, cliente_dni);
            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    reservas.add(ReservaDesdeFila(resultado));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }


    public static int ObtenerOcupacionIdPorReserva(int reserva_id) {
        int id_ocupacion = -1;
        String select = "select id_ocupacion from ocupaciones where reserva_id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, reserva_id);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    id_ocupacion = resultado.getInt("id_ocupacion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_ocupacion;
    }

    public static double ObtenerKilometrajeSalida(int ocupacion_id) {
        double kilometraje = -1;
        String select = "select kilometraje_salida from entregas where ocupacion_id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, ocupacion_id);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    kilometraje = resultado.getDouble("kilometraje_salida");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kilometraje;
    }

    public static int EliminarUsuario(String dni) {
        int filas_afectadas = 0;
        String delete = "delete from usuarios where dni = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setString(1, dni);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el usuario: " + e.getMessage());
        }
        return filas_afectadas;
    }

    public static int EliminarVehiculo(String patente) {
        int filas_afectadas = 0;
        String delete = "delete from vehiculos where patente = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setString(1, patente);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el vehiculo: " + e.getMessage());
        }
        return filas_afectadas;
    }

    public static int EliminarReserva(int id_reserva) {
        int filas_afectadas = 0;
        String delete = "delete from reservas where id_reserva = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, id_reserva);
            filas_afectadas = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la reserva: " + e.getMessage());
        }
        return filas_afectadas;
    }

    public static int RegistrarRetiro(int reserva_id, String fecha_hora_real, double kilometraje_salida, double nivel_combustible, String empleado_dni, String patente) {
        int id_entrega = -1;
        String insert_ocupacion = "insert into ocupaciones (esta_activa , reserva_id) values (1 , ?)";
        String insert_entrega = "insert into entregas (fecha_hora_real , kilometraje_salida , nivel_combustible , ocupacion_id , empleado_dni) values (? , ? , ? , ? , ?)";
        String update_vehiculo = "update vehiculos set estado = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);
            try (PreparedStatement st_ocupacion = connection.prepareStatement(insert_ocupacion, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement st_entrega = connection.prepareStatement(insert_entrega, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement st_vehiculo = connection.prepareStatement(update_vehiculo)) {

                st_ocupacion.setInt(1, reserva_id);
                st_ocupacion.executeUpdate();
                int id_ocupacion = -1;
                try (ResultSet claves = st_ocupacion.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_ocupacion = claves.getInt(1);
                    }
                }

                st_entrega.setString(1, fecha_hora_real);
                st_entrega.setDouble(2, kilometraje_salida);
                st_entrega.setDouble(3, nivel_combustible);
                st_entrega.setInt(4, id_ocupacion);
                st_entrega.setString(5, empleado_dni);
                st_entrega.executeUpdate();
                try (ResultSet claves = st_entrega.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_entrega = claves.getInt(1);
                    }
                }

                st_vehiculo.setString(1, Estado_Vehiculo.ALQUILADO.name());
                st_vehiculo.setString(2, patente);
                st_vehiculo.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                id_entrega = -1;
                System.out.println("No se pudo registrar el retiro (se deshicieron los cambios): " + e.getMessage());
            }
        } catch (SQLException e) {
            id_entrega = -1;
            e.printStackTrace();
        }
        return id_entrega;
    }

    public static int RegistrarDevolucion(Devoluciones.Devolucion devolucion, String patente, String estado_final) {
        int id_devolucion = -1;
        String insert_devolucion = "insert into devoluciones (fecha_hora_real , kilometraje_llegada , detalles_condicion , cargos_extra , ocupacion_id , empleado_dni) values (? , ? , ? , ? , ? , ?)";
        String update_ocupacion = "update ocupaciones set esta_activa = 0 where id_ocupacion = ?";
        String update_vehiculo = "update vehiculos set estado = ? where patente = ?";
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);
            try (PreparedStatement st_devolucion = connection.prepareStatement(insert_devolucion, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement st_ocupacion = connection.prepareStatement(update_ocupacion);
                 PreparedStatement st_vehiculo = connection.prepareStatement(update_vehiculo)) {

                st_devolucion.setString(1, devolucion.getFechaHoraReal().format(formato_fecha_hora));
                st_devolucion.setDouble(2, devolucion.getKilometrajeLlegada());
                st_devolucion.setString(3, devolucion.getDetallesCondicion());
                st_devolucion.setDouble(4, devolucion.getCargosExtra());
                st_devolucion.setInt(5, devolucion.getOcupacionId());
                st_devolucion.setString(6, devolucion.getEmpleadoDni());
                st_devolucion.executeUpdate();
                try (ResultSet claves = st_devolucion.getGeneratedKeys()) {
                    if (claves.next()) {
                        id_devolucion = claves.getInt(1);
                    }
                }

                st_ocupacion.setInt(1, devolucion.getOcupacionId());
                st_ocupacion.executeUpdate();

                st_vehiculo.setString(1, estado_final);
                st_vehiculo.setString(2, patente);
                st_vehiculo.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                id_devolucion = -1;
                System.out.println("No se pudo registrar la devolucion (se deshicieron los cambios): " + e.getMessage());
            }
        } catch (SQLException e) {
            id_devolucion = -1;
            e.printStackTrace();
        }
        return id_devolucion;
    }


}
