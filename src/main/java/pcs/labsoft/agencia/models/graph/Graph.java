package pcs.labsoft.agencia.models.graph;

import pcs.labsoft.agencia.models.Cidade;
import java.util.*;

import static java.util.Collections.reverse;

/**
 * Created by leoiacovini on 27/11/16.
 */

class DistanceMap extends HashMap<Integer, Double>{

    Integer getNearestFrom(Set<Integer> nodesIds) {
        Double minDistance = Double.MAX_VALUE;
        Integer minDistanceNodeId = (Integer) nodesIds.toArray()[0]; // Any node can be the default one
        for (Integer nodeId: nodesIds) {
            Double distance = this.get(nodeId);
            if (distance < minDistance) {
                minDistance = distance;
                minDistanceNodeId = nodeId;
            }
        }
        return minDistanceNodeId;
    }

}

class PreviousMap extends HashMap<Integer, Edge>{}

public class Graph {

    private final HashMap<Integer, List<Edge>> adjacencyMap;
    private final HashMap<Integer, DistanceMap> distanceMap;
    private final HashMap<Integer, PreviousMap> previousMap;

    public Graph(HashMap<Integer, List<Edge>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
        this.distanceMap = new HashMap<>();
        this.previousMap = new HashMap<>();

        for (Integer index1: adjacencyMap.keySet()) {
            DistanceMap dm = new DistanceMap();
            PreviousMap pm = new PreviousMap();
            for (Integer index2: adjacencyMap.keySet()) {
                Double value = index1.equals(index2) ? 0.0 : Double.MAX_VALUE;
                dm.put(index2, value);
                pm.put(index2, null);
            }
            distanceMap.put(index1, dm);
            previousMap.put(index1, pm);
        }
    }


    public Path getShortestPath(Integer fromNodeId, Integer toNodeId) {
        DistanceMap distanceMapFromSource = distanceMap.get(fromNodeId);
        PreviousMap previousMapFromSource = previousMap.get(fromNodeId);
        Set<Integer> unvisitedNodesIds = ((HashMap<Integer, List<Edge>>)adjacencyMap.clone()).keySet();

        if (distanceMapFromSource == null || previousMapFromSource == null) { // Xunxo for when the Graph is empty
            return new Path(new ArrayList<>());
        }

        List<Edge> shortestPath = new ArrayList<>();

        while (!unvisitedNodesIds.isEmpty()) {
            Integer nearestNodeId = distanceMapFromSource.getNearestFrom(unvisitedNodesIds);
            unvisitedNodesIds.remove(nearestNodeId);
            for (Edge edge: adjacencyMap.get(nearestNodeId)) {
                Double alt = distanceMapFromSource.get(nearestNodeId) + edge.getDistance();
                if (alt < distanceMapFromSource.get(edge.getToNodeId())) {
                    distanceMapFromSource.put(edge.getToNodeId(), alt);
                    previousMapFromSource.put(edge.getToNodeId(), edge);
                }
            }
        }

        Integer lastNode = toNodeId;
        while (previousMapFromSource.get(lastNode) != null) {
            shortestPath.add(previousMapFromSource.get(lastNode));
            lastNode = previousMapFromSource.get(lastNode).getFromNodeId();
        }
        reverse(shortestPath);

        return new Path(shortestPath);
    }

    public static Graph buildFromCidades(List<Cidade> cidades) {
        HashMap<Integer, List<Edge>> adjMap = new HashMap<>();
        for (Cidade cidade:cidades) {
            adjMap.put(cidade.getId(), Edge.fromCidade(cidade));
        }
        return new Graph(adjMap);
    }
}

