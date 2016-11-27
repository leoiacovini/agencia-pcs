package pcs.labsoft.agencia.models.graph;

import pcs.labsoft.agencia.models.Cidade;
import pcs.labsoft.agencia.models.Transporte;

import java.util.List;
import java.util.stream.Collectors;

public class Edge {

    private final int fromNodeId;
    private final int toNodeId;
    private final Double distance;
    private final int edgeId;

    public Edge(int fromNodeId, int toNodeId, Double distance, int edgeId) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.distance = distance;
        this.edgeId = edgeId;
    }

    Double getDistance() {
        return distance;
    }

    public int getEdgeId() {
        return edgeId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Edge && this.edgeId == ((Edge) obj).getEdgeId();
    }

    public int getFromNodeId() {
        return fromNodeId;
    }

    int getToNodeId() {
        return toNodeId;
    }

    static List<Edge> fromCidade(Cidade cidade) {
        List<Transporte> transportes = cidade.getTransportesDePartida();
        return transportes.stream().map(Edge::fromTransporte).collect(Collectors.toList());
    }

    private static Edge fromTransporte(Transporte transporte) {
        return new Edge(transporte.getCidadeDePartida().getId(), transporte.getCidadeDeChegada().getId(), transporte.getPreco(), transporte.getId());
    }

}
