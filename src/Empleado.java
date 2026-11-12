public class Empleado extends Usuarios {
    private String legajo;
    private String sucursal;

    public Empleado(String dni, String email, String nombre, String password, String legajo, String sucursal) throws Valores_Invalidos {
        super(dni, email, nombre, password);
        if(legajo == null){throw new Valores_Invalidos("El legajo no puede estar vacio...");}
        if(sucursal == null){throw new Valores_Invalidos("La sucursal no puede estar vacia...");}
        this.legajo = legajo;
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "Empleado{" + super.toString() +
                ", legajo='" + legajo + '\'' +
                ", sucursal='" + sucursal + '\'' +
                '}';
    }

    @Override
    public String Obtener_Tipo() {
        return "EMPLEADO";
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
}
