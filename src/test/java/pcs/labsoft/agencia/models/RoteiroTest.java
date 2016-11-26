package pcs.labsoft.agencia.models;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class RoteiroTest {

    static Trecho trechoInicial;
    static Trecho trecho2;
    static Trecho trecho3;
    static Cliente cliente;
    static Funcionario funcionario;

    @BeforeClass
    public static void setUp() {
        Cidade cidade = new Cidade("Teste", "Brasil", "estado", 1);
        Cidade cidade2 = new Cidade("Teste_2", "Brasil_2", "estado_2", 2);
        Transporte transporte = new Transporte(cidade, cidade2, "onibus", 200.0, 1);
        Transporte transporte2 = new Transporte(cidade2, cidade, "onibus", 150.0, 2);
        Hotel hotel = new Hotel("Hotel", 50.0, cidade2, 1);

        cliente = new Cliente("Cliete", "1234567", "1234567", "cliente@mail.com", "1234567", "1234567", 1);
        funcionario = new Funcionario("fulano", "12345", "Fulano", "agente", 1);

        trechoInicial = new Trecho(cidade, transporte, null, 0, true, 1);
        trecho2 = new Trecho(cidade, transporte2, hotel, 4, false, 2);
        trecho3 = new Trecho(cidade, transporte2, hotel, 5, false, 2);
    }

    @Test
    public void getValor() {
        Roteiro roteiro = new Roteiro(cliente, funcionario);
        roteiro.addTrecho(trechoInicial);
        roteiro.addTrecho(trecho2);
        assertEquals(550.0, roteiro.getValor(), 0.0001);
    }

    @Test
    public void getDuracao() {
        Roteiro roteiro = new Roteiro(cliente, funcionario);
        roteiro.addTrecho(trechoInicial);
        roteiro.addTrecho(trecho2);
        roteiro.addTrecho(trecho3);
        assertEquals(9, roteiro.getDuracao());
    }


}
