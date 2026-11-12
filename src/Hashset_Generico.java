import java.util.HashSet;

public class Hashset_Generico <T> {
    private HashSet<T> Registro;

    public Hashset_Generico() {
        this.Registro = new HashSet<>();
    }

    public String Mostrar_Hashset()
    {
        if(Registro.isEmpty()){return "No hay registros cargados...\n";}
        StringBuilder info = new StringBuilder();
        for(T elemento : Registro)
        {
            info.append(elemento.toString()).append("\n");
        }
        return info.toString();
    }

    public String Cargar(HashSet<T> Registro_Aux)
    {
        Registro.clear();
        Registro.addAll(Registro_Aux);
        return "Hashset Cargado";
    }

    public boolean Cargar_Registro(T elemento)
    {
        if(elemento == null){return false;}
        return Registro.add(elemento);
    }

    public boolean Eliminar_Registro(T elemento)
    {
        return Registro.remove(elemento);
    }

    public <K> T Buscar_Por_Id(K id)
    {
        for(T elemento : Registro)
        {
            if(elemento instanceof Identificable)
            {
                Identificable<?> identificable = (Identificable<?>) elemento;
                if(identificable.Obtener_Id().equals(id)){return elemento;}
            }
        }
        return null;
    }

    public int Tamanio()
    {
        return Registro.size();
    }

    public HashSet<T> getRegistro() {
        return Registro;
    }
}
