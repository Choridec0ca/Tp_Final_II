import java.util.ArrayList;

public class Arreglo_Generico <T> {
    private ArrayList<T> Regitro;

    public Arreglo_Generico() {
        Regitro = new ArrayList<>();
    }

    public String Mostrar_Arreglo()
    {
        if(Regitro.isEmpty()){return "No hay registros cargados...\n";}
        StringBuilder info = new StringBuilder();
        for(int i = 0 ; i < Regitro.size() ; i++)
        {
            info.append(Regitro.get(i).toString()).append("\n");
        }
        return info.toString();
    }

    public String Cargar(ArrayList<T> Registro_Aux)
    {
        Regitro.clear();
        Regitro.addAll(Registro_Aux);
        return "Arreglo Cargado";
    }

    public boolean Cargar_Registro(T elemento)
    {
        if(elemento == null){return false;}
        return Regitro.add(elemento);
    }

    public boolean Eliminar_Registro(T elemento)
    {
        return Regitro.remove(elemento);
    }

    public <K> T Buscar_Por_Id(K id)
    {
        for(int i = 0 ; i < Regitro.size() ; i++)
        {
            T elemento = Regitro.get(i);
            if(elemento instanceof Identificable)
            {
                Identificable<?> identificable = (Identificable<?>) elemento;
                if(identificable.Obtener_Id().equals(id)){return elemento;}
            }
        }
        return null;
    }

    public T Obtener(int posicion)
    {
        return Regitro.get(posicion);
    }

    public int Tamanio()
    {
        return Regitro.size();
    }

    public ArrayList<T> getRegitro() {
        return Regitro;
    }
}
