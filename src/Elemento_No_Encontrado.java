
public class Elemento_No_Encontrado extends RuntimeException {
    public Elemento_No_Encontrado(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "No encontrado: " + super.getMessage();
    }
}
