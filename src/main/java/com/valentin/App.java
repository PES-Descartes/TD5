package com.valentin;

import com.valentin.archives.Graphs;
import com.valentin.archives.SVGAnimation;

public class App {
    public static void main(String[] args) {
        dynamicGraph();
    }

    private static void exampleGraph() {
        Node node = new Node(new Vector(1,0));
        Node node1 = new Node(new Vector(-1,0));
        Node node2 = new Node(new Vector(0,1));

        Graph graph = new Graph();
        graph.add(node, node1);
        graph.add(node1, node2);
        graph.add(node2, node);

        SVGAnimation svgAnimation = new SVGAnimation(graph);
        svgAnimation.record(0);
        svgAnimation.printToFile("fix_graph.svg");

    }

    private static void dynamicGraph() {
        Graph myGraph = Graphs.fromFile("resources/graph6.txt");
        SVGAnimation animation = new SVGAnimation(myGraph);

        double t, dt = 0.1;
        for(t = 0; t < 20; t+=dt) {
            animation.record(t);
            myGraph.update(dt);
        }
        animation.record(t);

        animation.printToFile("dynamic_graph.svg");
    }
}
