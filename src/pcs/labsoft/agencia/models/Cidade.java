package pcs.labsoft.agencia.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoiacovini on 10/7/16.
 */
public class Cidade {

    private final int id;
    private final String nome;
    private final String pais;
    private final String estado;

    public Cidade(String nome, String pais, String estado, int id) {
        this.nome = nome;
        this.pais = pais;
        this.estado = estado;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return id + " - " + nome + ", " + estado + ", " + pais;
    }

    public static List<Cidade> mockData() {
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(new Cidade("São Paulo", "Brasil", "SP", 1));
        cidades.add(new Cidade("Rio de Janeiro", "Brasil", "RJ", 2));
        cidades.add(new Cidade("Brasilia", "Brasil", "DF", 3));
        cidades.add(new Cidade("Paris", "França", "Ilha de França", 4));
        return cidades;
    }

}
