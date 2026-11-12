import java.time.LocalDateTime;
public class Devoluciones {
    public static class Devolucion {
        private int idDevolucion;
        private LocalDateTime fechaHoraReal;
        private Float kilometrajeLlegada;
        private String detallesCondicion;
        private Float cargosExtra;
        private int ocupacionId;
        private String empleadoDni;

        public Devolucion(LocalDateTime fechaHoraReal, Float kilometrajeLlegada, String detallesCondicion, Float cargosExtra, Integer ocupacionId, String empleadoDni) throws Valores_Invalidos {

            if (fechaHoraReal == null) {
                throw new Valores_Invalidos("La fecha y hora real de devolución no pueden ser nulas...");
            }

            if (kilometrajeLlegada == null || kilometrajeLlegada < 0) {
                throw new Valores_Invalidos("El kilometraje de llegada no puede ser nulo ni negativo...");
            }

            if (cargosExtra != null && cargosExtra < 0) {
                throw new Valores_Invalidos("Los cargos extra no pueden ser negativos...");
            }

            if (ocupacionId == null || ocupacionId <= 0) {
                throw new Valores_Invalidos("El ID de ocupación es obligatorio y debe ser mayor a 0...");
            }

            if (empleadoDni == null) {
                throw new Valores_Invalidos("El DNI del empleado no puede estar vacío...");
            }

            this.fechaHoraReal = fechaHoraReal;
            this.kilometrajeLlegada = kilometrajeLlegada;
            this.detallesCondicion = detallesCondicion;
            this.cargosExtra = cargosExtra;
            this.ocupacionId = ocupacionId;
            this.empleadoDni = empleadoDni;
        }

        public Devolucion(int idDevolucion, LocalDateTime fechaHoraReal, Float kilometrajeLlegada, String detallesCondicion, Float cargosExtra, int ocupacionId, String empleadoDni) {
            this.idDevolucion = idDevolucion;
            this.fechaHoraReal = fechaHoraReal;
            this.kilometrajeLlegada = kilometrajeLlegada;
            this.detallesCondicion = detallesCondicion;
            this.cargosExtra = cargosExtra;
            this.ocupacionId = ocupacionId;
            this.empleadoDni = empleadoDni;
        }

        @Override
        public String toString() {
            return "Devolucion{" +
                    "idDevolucion=" + idDevolucion +
                    ", fechaHoraReal=" + fechaHoraReal +
                    ", kilometrajeLlegada=" + kilometrajeLlegada +
                    ", detallesCondicion='" + detallesCondicion + '\'' +
                    ", cargosExtra=" + cargosExtra +
                    ", ocupacionId=" + ocupacionId +
                    ", empleadoDni='" + empleadoDni + '\'' +
                    '}';
        }

        public void setIdDevolucion(int idDevolucion) {
            this.idDevolucion = idDevolucion;
        }

        public int getIdDevolucion() {
            return idDevolucion;
        }

        public LocalDateTime getFechaHoraReal() {
            return fechaHoraReal;
        }

        public Float getKilometrajeLlegada() {
            return kilometrajeLlegada;
        }

        public String getDetallesCondicion() {
            return detallesCondicion;
        }

        public Float getCargosExtra() {
            return cargosExtra;
        }

        public int getOcupacionId() {
            return ocupacionId;
        }

        public String getEmpleadoDni() {
            return empleadoDni;
        }
    }
}
