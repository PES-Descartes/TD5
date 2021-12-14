package com.valentin.archives;
import com.valentin.Graph;
import com.valentin.Node;
import com.valentin.Vector;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Graphs {


  public static Graph fromFile(String filename) {

    try (Scanner scanner = new Scanner(new File(filename)).useLocale(Locale.US)) {
      return GraphReader.readGraph(scanner);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Graph graph6() {
    return Graphs.fromFile("resources/graph6.txt");
  }
  
  public static Graph graph0(){
  	Graph graph = new Graph();
  	Node a = new Node(new Vector(-1,0));
  	a.setRigidity(true);
  	Node b = new Node(new Vector(1,0));
  	graph.add(a,b);
  	return graph;
  }
  
  public static Graph graph1(){
  	Graph graph = new Graph();
  	Node a = new Node(new Vector(-1,0));
  	a.setRigidity(true);
  	Node b = new Node(new Vector(1,0));
  	b.setRigidity(true);
  	Node c = new Node(new Vector(0,1));
  	c.setRigidity(true);
  	graph.add(a,b);
  	graph.add(a,c);
  	graph.add(b,c);
  	return graph;
  }

  public static Graph graph5() {
    Graph graph = new Graph();

    Node a = new Node(new Vector(0,2));
    a.setRigidity(true);
    Node b = new Node(new Vector(-1.732,-1));
    b.setRigidity(true);
    Node c = new Node(new Vector(1.732, -1));
    c.setRigidity(true);

    Node d = new Node(new Vector(-3,2));
    Node e = new Node(new Vector(4,-1));

    graph.add(a,b);
    graph.add(a,c);
    graph.add(b,c);
    graph.add(a,d);
    graph.add(b,d);
    graph.add(b,e);
    graph.add(c,e);
    graph.add(d,e);

    return graph;
  }
}
