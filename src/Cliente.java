public class Cliente extends Usuarios{
    private String licenciaConducir;
    private String tarjetaCredito;

    public Cliente(String dni,  String email, String nombre, String password, String tarjetaCredito, String licenciaConducir) throws Valores_Invalidos {
        super(dni, email, nombre, password);
        this.tarjetaCredito = tarjetaCredito;
        this.licenciaConducir = licenciaConducir;
    }

    @Override
    public String toString() {
        return "Cliente{" + super.toString() +
                ", licenciaConducir='" + licenciaConducir + '\'' +
                ", tarjetaCredito='" + tarjetaCredito + '\'' +
                '}';
    }

    @Override
    public String Obtener_Tipo() {
        return "CLIENTE";
    }

    public String getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }

    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }
}
