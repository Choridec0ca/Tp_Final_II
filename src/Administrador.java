public class Administrador extends Usuarios{
    private String codigo_admin;

    public Administrador(String dni, String email, String nombre, String password, String codigo_admin) throws Valores_Invalidos {
        super(dni,  email, nombre, password);
        if(codigo_admin == null){throw new Valores_Invalidos("El codigo de administrador no puede estar vacio...");}
        this.codigo_admin = codigo_admin;
    }

    @Override
    public String toString() {
        return "Administrador{" + super.toString() +
                ", codigo_admin='" + codigo_admin + '\'' +
                '}';
    }

    @Override
    public String Obtener_Tipo() {
        return "ADMINISTRADOR";
    }

    public String getCodigo_admin() {
        return codigo_admin;
    }

    public void setCodigo_admin(String codigo_admin) {
        this.codigo_admin = codigo_admin;
    }
}
