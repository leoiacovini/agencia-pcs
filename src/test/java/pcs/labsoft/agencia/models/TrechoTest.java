package pcs.labsoft.agencia.models;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class TrechoTest {

    static Cidade cidade;
    static Cidade cidade2;
    static Transporte transporte;
    static Transporte transporte2;
    static Hotel hotel;

    @BeforeClass
    public static void createStubs() {
        cidade = new Cidade("Teste", "Brasil", "estado", 1);
        cidade2 = new Cidade("Teste_2", "Brasil_2", "estado_2", 2);
        transporte = new Transporte(cidade, cidade2, "onibus", 200.0, 1);
        transporte2 = new Transporte(cidade2, cidade, "onibus", 150.0, 2);
        hotel = new Hotel("Hotel", 50.0, cidade2, 1);
    }

    @Test
    public void getValor() {
        Trecho trechoInicial = new Trecho(cidade, transporte, null, 0, true, 1);
        Trecho trecho2 = new Trecho(cidade, transporte2, hotel, 4, false, 2);

        assertEquals(200.0, trechoInicial.getValor(), 0.0001);
        assertEquals(350.0, trecho2.getValor(), 0.0001);
    }

}
