package com.valentin.archives;
import com.valentin.Graph;
import com.valentin.Node;
import com.valentin.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class GraphReader {


  public static Graph readGraph(Scanner scanner) throws IOException {
    List<Node> nodes = new ArrayList<>();
    Graph graph = new Graph();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      Node node = readNode(nodes, new Scanner(line).useLocale(Locale.US));
      graph.add(node);
      nodes.add(node);
    }

    while (scanner.hasNext()) {
      int end1 = scanner.nextInt();
      int end2 = scanner.nextInt();
      graph.add(nodes.get(end1), nodes.get(end2));
    }

    return graph;
  }

  private static Node readNode(List<Node> nodes, Scanner scanner) {
    double x = scanner.nextDouble();
    double y = scanner.nextDouble();
    Node node = new Node(new Vector(x,y));
    if (scanner.hasNext()) {
      String token = scanner.next();
      if (token.toUpperCase().equals("FIX")) {
        node.setRigidity(true);
      }
    }
    return node;
  }
}
