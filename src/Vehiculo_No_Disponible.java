
public class Vehiculo_No_Disponible extends RuntimeException {
    public Vehiculo_No_Disponible(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Vehiculo no disponible: " + super.getMessage();
    }
}
