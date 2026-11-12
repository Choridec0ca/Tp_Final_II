import java.time.LocalDateTime;
public class Entragas {
    public static class Entrega {
        private int idEntrega;
        private LocalDateTime fechaHoraReal;
        private Float kilometrajeSalida;
        private Float nivelCombustible;
        private int ocupacionId;
        private String empleadoDni;

        public Entrega(LocalDateTime fechaHoraReal, Float kilometrajeSalida, Float nivelCombustible, Integer ocupacionId, String empleadoDni) throws Valores_Invalidos {

            if (fechaHoraReal == null) {
                throw new Valores_Invalidos("La fecha y hora real de entrega no pueden ser nulas...");
            }

            if (kilometrajeSalida == null || kilometrajeSalida < 0) {
                throw new Valores_Invalidos("El kilometraje de salida no puede ser nulo ni negativo...");
            }

            if (nivelCombustible == null || nivelCombustible < 0) {
                throw new Valores_Invalidos("El nivel de combustible no puede ser nulo ni negativo.");
            }

            if (ocupacionId == null || ocupacionId <= 0) {
                throw new Valores_Invalidos("El ID de ocupación es obligatorio y debe ser mayor a 0.");
            }

            if (empleadoDni == null || empleadoDni.trim().isEmpty()) {
                throw new Valores_Invalidos("El DNI del empleado no puede estar vacío.");
            }

            this.fechaHoraReal = fechaHoraReal;
            this.kilometrajeSalida = kilometrajeSalida;
            this.nivelCombustible = nivelCombustible;
            this.ocupacionId = ocupacionId;
            this.empleadoDni = empleadoDni;
        }

        public Entrega(int idEntrega , LocalDateTime fechaHoraReal,Float kilometrajeSalida, Float nivelCombustible, int ocupacionId, String empleadoDni) {
            this.fechaHoraReal = fechaHoraReal;
            this.idEntrega = idEntrega;
            this.kilometrajeSalida = kilometrajeSalida;
            this.nivelCombustible = nivelCombustible;
            this.ocupacionId = ocupacionId;
            this.empleadoDni = empleadoDni;
        }

        @Override
        public String toString() {
            return "Entrega{" +
                    "idEntrega=" + idEntrega +
                    ", fechaHoraReal=" + fechaHoraReal +
                    ", kilometrajeSalida=" + kilometrajeSalida +
                    ", nivelCombustible=" + nivelCombustible +
                    ", ocupacionId=" + ocupacionId +
                    ", empleadoDni='" + empleadoDni + '\'' +
                    '}';
        }

        public void setIdEntrega(int idEntrega) {
            this.idEntrega = idEntrega;
        }

        public int getIdEntrega() {
            return idEntrega;
        }

        public LocalDateTime getFechaHoraReal() {
            return fechaHoraReal;
        }

        public Float getKilometrajeSalida() {
            return kilometrajeSalida;
        }

        public Float getNivelCombustible() {
            return nivelCombustible;
        }

        public int getOcupacionId() {
            return ocupacionId;
        }

        public String getEmpleadoDni() {
            return empleadoDni;
        }
    }
}
