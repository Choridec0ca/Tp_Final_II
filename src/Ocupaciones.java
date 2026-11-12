import java.time.LocalDate;
public class Ocupaciones {
    public static class Ocupacion {
        private Integer idOcupacion;
        private Boolean estaActiva;
        private Integer reservaId;

        public Ocupacion( Boolean estaActiva, Integer reservaId) throws Valores_Invalidos {
            if (reservaId == null || reservaId <= 0) {
                throw new Valores_Invalidos("El ID de la reserva es obligatorio y debe ser mayor a 0.");
            }
            this.estaActiva = estaActiva;
            this.reservaId = reservaId;
        }

        public Ocupacion(Integer idOcupacion, Boolean estaActiva, Integer reservaId) {
            this.idOcupacion = idOcupacion;
            this.estaActiva = estaActiva;
            this.reservaId = reservaId;
        }

        @Override
        public String toString() {
            return "Ocupacion{" +
                    "idOcupacion=" + idOcupacion +
                    ", estaActiva=" + estaActiva +
                    ", reservaId=" + reservaId +
                    '}';
        }

        public void setIdOcupacion(Integer idOcupacion) {
            this.idOcupacion = idOcupacion;
        }

        public Integer getIdOcupacion() {
            return idOcupacion;
        }

        public Boolean getEstaActiva() {
            return estaActiva;
        }

        public Integer getReservaId() {
            return reservaId;
        }
    }
}
