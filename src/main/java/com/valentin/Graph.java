package com.valentin;

import java.util.HashSet;

public class Graph {
    private final HashSet<Node> nodes = new HashSet<>();

    public HashSet<Node> getNodes() {
        return nodes;
    }

    public void add(Node node) {
        nodes.add(node);
    }

    public void add(Node node1, Node node2) {
        node1.add(node2);
        node2.add(node1);
        if(!nodes.contains(node1))
            nodes.add(node1);
        if(!nodes.contains(node2))
            nodes.add(node2);
    }

    private void updateVelocities(double dt) {
        for(Node node : nodes)
            node.updateVelocity(dt);
    }

    private void updatePosition(double dt) {
        for(Node node : nodes)
            node.updatePosition(dt);
    }

    public void update(double dt) {
        updateVelocities(dt);
        updatePosition(dt);
    }

}
