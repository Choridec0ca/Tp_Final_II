import java.util.Objects;

public abstract class Usuarios implements Identificable<String> {
    private String dni;
    private String nombre;
    private String email;
    private String password;

    public Usuarios(String dni, String email, String nombre, String password) throws Valores_Invalidos {
        if(!esDniValido(dni)){throw new Valores_Invalidos("El DNI ingresado no es valido, debe contener entre 7 y 8 digitos numericos...");}
        if(email == null){throw new Valores_Invalidos("El email no puede estar vacio...");}
        if(nombre == null){throw new Valores_Invalidos("El nombre no puede estar vacio...");}
        if(password == null){throw new Valores_Invalidos("La contraseña no puede estar vacio...");}
        this.dni = dni;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }

    @Override
    public String Obtener_Id() {
        return dni;
    }


    public abstract String Obtener_Tipo();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return Objects.equals(dni, usuarios.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }


    @Override
    public String toString() {
        return
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' ;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public  boolean esDniValido(String dni) {

        if (dni == null) {
            return false;
        }

        int longitud = dni.length();
        if (longitud < 7 || longitud > 8) {
            return false;
        }

        for (int i = 0; i < longitud; i++) {
            char c = dni.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}
