package Graph;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    
    private Set<Node> allNodes;
    private Set<Edge> allEdges;
    private boolean hasNegativeWeight;

    public Graph() {
        allNodes = new HashSet<>();
        allEdges = new HashSet<>();
        hasNegativeWeight = false;
    }
    
    public Set<Node> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(Set<Node> allNodes) {
        this.allNodes = allNodes;
    }

    public Set<Edge> getAllEdges() {
        return allEdges;
    }

    public void setAllEdges(Set<Edge> allEdges) {
        this.allEdges = allEdges;
    }

    public boolean hasNegativeWeight() {
        return hasNegativeWeight;
    }

    public void setHasNegativeWeight(boolean hasNegativeWeight) {
        this.hasNegativeWeight = hasNegativeWeight;
    }
    
    public boolean addNode(Node node){
        return allNodes.add(node);
    }
    
    public boolean addEdge(Edge edge){
        return allEdges.add(edge);
    }
    
    public Set<Edge> getAdjacentEdges(Node node){
        Set<Edge> adjacentEdges = new HashSet<>();
        for (Edge edge : allEdges) {
            if (edge.getStartNode().getId() == node.getId()) {
                adjacentEdges.add(edge);
            }
        }
        return adjacentEdges;
    }
    
    public boolean removeNode(Node node){
        if (allNodes.remove(node)) {
            for (Edge edge : allEdges) {
                if(edge.getStartNode().getId() == node.getId() || edge.getEndNode().getId() == node.getId())
                    allEdges.remove(edge);
            }
            return true;
        }
        return false;
    }
    
    public boolean removeEdge(Edge edge){
        return allEdges.remove(edge);
    }
    
}