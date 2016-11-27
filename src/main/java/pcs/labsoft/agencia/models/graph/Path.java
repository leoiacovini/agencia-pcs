package pcs.labsoft.agencia.models.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Path {

    private final List<Edge> edges;

    public Path(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Integer> getNodesId() {

        List<Integer> nodesIds = new ArrayList<>();

        for(int i = 0; i < edges.size(); i++) {
            if (i == 0) {
                nodesIds.add(edges.get(i).getFromNodeId());
            }
            nodesIds.add(edges.get(i).getToNodeId());
        }
        return nodesIds;
    }

    public List<Integer> getEdgesIds() {
        return edges.stream().map(Edge::getEdgeId).collect(Collectors.toList());
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public Double getDistance() {
        return edges.stream().map(Edge::getDistance).reduce(0.0, (acc, d) -> acc + d);
    }

    public boolean isEmpty() {
        return edges.isEmpty();
    }

}
