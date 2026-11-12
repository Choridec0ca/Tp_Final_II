public class Valores_Invalidos extends RuntimeException {
    public Valores_Invalidos(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Valor ingresado invalido: " + super.getMessage();
    }
}
