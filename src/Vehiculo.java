public abstract class Vehiculo implements Alquilable, Identificable<String> {
    private String patente;
    private String modelo;
    private String marca;
    private double tarifaDiaria;
    private Estado_Vehiculo estadoVehiculo;

    public Vehiculo(String patente, String marca, String modelo, double tarifaDiaria, Estado_Vehiculo estadoVehiculo) throws Valores_Invalidos {
        if(patente == null || modelo == null || marca == null) {throw new Valores_Invalidos("Los caracteres ingresados en alguno de estos elementos ( Patente | Modelo | Marca ) no estan permitidos en el sistema...");}
        if(tarifaDiaria < 0){throw new Valores_Invalidos("El valor de la tarifa ingresado no esta permitido en el sistema...");}
        if(estadoVehiculo == null){throw new Valores_Invalidos("El estado del vehiculo no puede estar vacio...");}
        this.modelo = modelo;
        this.patente = patente;
        this.marca = marca;
        this.tarifaDiaria = tarifaDiaria;
        this.estadoVehiculo = estadoVehiculo;
    }
    @Override
    public boolean Verificar_Disponibilidad() {
        if(estadoVehiculo != Estado_Vehiculo.DISPONIBLE){return false;}
        return true;
    }

    @Override
    public String Obtener_Id() {
        return patente;
    }

    @Override
    public String toString() {
        return
                "patente='" + patente + '\'' +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", tarifaDiaria=" + tarifaDiaria +
                ", estadoVehiculo=" + estadoVehiculo ;
    }

    public void setEstadoVehiculo(Estado_Vehiculo estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public void setTarifaDiaria(double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    public Estado_Vehiculo getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public String getPatente() {
        return patente;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

}
