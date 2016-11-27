package pcs.labsoft.agencia.models;

import org.junit.Test;
import static org.junit.Assert.*;
import pcs.labsoft.agencia.models.graph.Edge;
import pcs.labsoft.agencia.models.graph.Graph;
import pcs.labsoft.agencia.models.graph.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leoiacovini on 27/11/16.
 */
public class GraphTest {

    @Test
    public void getShortestPath() {
        HashMap<Integer, List<Edge>> adjMap = new HashMap<>();

        List<Edge> adjList1 = new ArrayList<>();
        adjList1.add(new Edge(1, 2, 1.0, 1));
        adjList1.add(new Edge(1, 3, 2.0, 2));
        adjList1.add(new Edge(1, 4, 3.0, 3));

        List<Edge> adjList2 = new ArrayList<>();
        adjList2.add(new Edge(2, 4, 2.0, 4));
        adjList2.add(new Edge(2, 4, 1.0, 5));
        adjList2.add(new Edge(2, 3, 3.0, 6));

        List<Edge> adjList3 = new ArrayList<>();
        adjList3.add(new Edge(3, 5, 1.0, 7));

        List<Edge> adjList4 = new ArrayList<>();
        adjList4.add(new Edge(4, 3, 4.0, 8));

        List<Edge> adjList5 = new ArrayList<>();
        adjList5.add(new Edge(5, 4, 2.0, 9));

        adjMap.put(1, adjList1);
        adjMap.put(2, adjList2);
        adjMap.put(3, adjList3);
        adjMap.put(4, adjList4);
        adjMap.put(5, adjList5);

        Graph graph = new Graph(adjMap);

        Path shortestPath = graph.getShortestPath(1, 5);
        Integer[] expectedEdgesIds = {2,7};
        Integer[] expectedNodesIds = {1,3,5};
        assertArrayEquals(expectedEdgesIds, shortestPath.getEdgesIds().toArray());
        assertArrayEquals(expectedNodesIds, shortestPath.getNodesId().toArray());
        assertEquals(3, shortestPath.getDistance(), 0.001);

        shortestPath = graph.getShortestPath(1, 4);
        Integer[] expectedEdgesIds2 = {1,5};
        Integer[] expectedNodesIds2 = {1,2,4};
        assertArrayEquals(expectedEdgesIds2, shortestPath.getEdgesIds().toArray());
        assertArrayEquals(expectedNodesIds2, shortestPath.getNodesId().toArray());
        assertEquals(2, shortestPath.getDistance(), 0.001);

        shortestPath = graph.getShortestPath(5, 1); // Impossible Path
        assertTrue(shortestPath.isEmpty());

    }

    @Test
    public void onEmptyGraph() {
        HashMap<Integer, List<Edge>> adjMap = new HashMap<>();
        Graph graph = new Graph(adjMap);
        Path shortestPath = graph.getShortestPath(1, 5);

        assertTrue(shortestPath.isEmpty());
    }

    @Test
    public void loadFromCidades() {

        Cidade cidade1 = new Cidade("cidade1", "pais1", "estadp1", 1);
        Cidade cidade2 = new Cidade("cidade2", "pais2", "estado2", 2);
        Cidade cidade3 = new Cidade("cidade3", "pais3", "estado3", 3);
        Cidade cidade4 = new Cidade("cidade4", "pais4", "estado4", 4);
        Cidade cidade5 = new Cidade("cidade5", "pais5", "estado5", 5);

        // Cidade 1 -> Cidade X
        Transporte transporte1 = new Transporte(cidade1, cidade2, "tipo1", 1.0, 1);
        Transporte transporte2 = new Transporte(cidade1, cidade3, "tipo1", 2.0, 2);
        Transporte transporte3 = new Transporte(cidade1, cidade4, "tipo1", 3.0, 3);
        cidade1.addTransportesDePartida(transporte1);
        cidade1.addTransportesDePartida(transporte2);
        cidade1.addTransportesDePartida(transporte3);

        cidade2.addTransportesDeChegada(transporte1);
        cidade3.addTransportesDeChegada(transporte2);
        cidade4.addTransportesDeChegada(transporte3);

        // Cidade 2 -> Cidade X
        Transporte transporte4 = new Transporte(cidade2, cidade4, "tipo1", 2.0, 4);
        Transporte transporte5 = new Transporte(cidade2, cidade4, "tipo1", 1.0, 5);
        Transporte transporte6 = new Transporte(cidade2, cidade3, "tipo1", 3.0, 6);
        cidade2.addTransportesDePartida(transporte4);
        cidade2.addTransportesDePartida(transporte5);
        cidade2.addTransportesDePartida(transporte6);

        cidade4.addTransportesDeChegada(transporte4);
        cidade4.addTransportesDeChegada(transporte5);
        cidade3.addTransportesDeChegada(transporte6);

        // Cidade 3 -> Cidade X
        Transporte transporte7 = new Transporte(cidade3, cidade5, "tipo1", 1.0, 7);
        cidade3.addTransportesDePartida(transporte7);

        cidade5.addTransportesDeChegada(transporte7);

        // Cidade 4 -> Cidade X
        Transporte transporte8 = new Transporte(cidade4, cidade3, "tipo1", 4.0, 8);
        cidade4.addTransportesDePartida(transporte8);

        cidade3.addTransportesDeChegada(transporte8);

        // Cidade 5 -> Cidade X
        Transporte transporte9 = new Transporte(cidade5, cidade4, "tipo1", 2.0, 9);
        cidade5.addTransportesDePartida(transporte9);

        cidade4.addTransportesDeChegada(transporte9);

        // List Cidades

        List<Cidade> cidades = new ArrayList<>();
        cidades.add(cidade1);
        cidades.add(cidade2);
        cidades.add(cidade3);
        cidades.add(cidade4);
        cidades.add(cidade5);


        // Build the same Graph as the last test
        Graph graph = Graph.buildFromCidades(cidades);
        assertNotNull(graph);

        // Only one check to be sure thr graph was the same
        Path shortestPath = graph.getShortestPath(1, 5);
        Integer[] expectedEdgesIds = {2,7};
        Integer[] expectedNodesIds = {1,3,5};
        assertArrayEquals(expectedEdgesIds, shortestPath.getEdgesIds().toArray());
        assertArrayEquals(expectedNodesIds, shortestPath.getNodesId().toArray());
        assertEquals(3, shortestPath.getDistance(), 0.001);

    }

}
