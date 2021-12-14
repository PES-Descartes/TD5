package com.valentin.archives;
import com.valentin.Graph;
import com.valentin.Node;
import com.valentin.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import javax.swing.InternalFrameFocusTraversalPolicy;

public class SVGAnimation {

  private final Graph graph;
  private final Map<Node,List<Vector>> trajectories;
  private final List<Double> timesteps;
  private double maxTime = 0;

  public SVGAnimation(Graph graph) {
    this.graph = graph;
    this.timesteps = new ArrayList<>();
    this.trajectories = new HashMap<>();
    for (Node node : this.graph.getNodes()) {
      trajectories.put(node, new ArrayList<>());
    }
  }

  public void record(double time) {
    timesteps.add(time);
    maxTime = Math.max(maxTime,time);
    for (Node node : this.graph.getNodes()) {
      trajectories.get(node).add(node.getPosition());
    }
  }


  public void print(PrintWriter writer) {
    new SVGPrinter(this,writer).printSVG();
  }

  public void printToFile(String filename) {
    try (PrintWriter writer = new PrintWriter(new File(filename))) {
      print(writer);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("Je n'ai pas pu ouvrir le fichier requis.");
    }
  }


  private String projections(Node node, ToDoubleFunction<Vector> proj) {
    return
      trajectories.get(node).stream()
      .mapToDouble(proj::applyAsDouble)
      .map(x -> SVGPrinter.ZOOM * x)
      .mapToObj(Double::toString)
      .collect(Collectors.joining(";"));
  }

  private String xs(Node node) {
    return projections(node, Vector::getX);
  }

  private String ys(Node node) {
    return projections(node, Vector::getY);
  }

  private static class SVGPrinter {
    private final Graph graph;
    private final PrintWriter writer;

    private final String timeString;
    private final Map<Node,String> xStrings = new HashMap<>();
    private final Map<Node,String> yStrings = new HashMap<>();
    private final double maxTime;

    private SVGPrinter(SVGAnimation animation, PrintWriter writer) {
      this.graph = animation.graph;
      this.writer = writer;
      this.maxTime = 
        animation.timesteps.stream()
        .mapToDouble(x -> x)
        .max().orElse(0);
      timeString = 
        animation.timesteps.stream()
        .map(d -> Double.toString(d / maxTime))
        .collect(Collectors.joining(";"));
      for (Node node : animation.graph.getNodes()) {
        xStrings.put(node,animation.xs(node));
        yStrings.put(node,animation.ys(node));
      }
    }

    private static final String XMLNS = "http://www.w3.org/2000/svg";
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 800;
    private static final double NODE_RADIUS = 3;
    private static final double ZOOM = 100;
    private static final String VIEW_BOX = "-300 -300 600 600";

    public void printSVG() {
      writer.println(
        "<svg"
          + "\n  " + attribute("xmlns", XMLNS)
          + "\n  " + attribute("width", IMAGE_WIDTH)
          + "\n  " + attribute("height", IMAGE_HEIGHT)
          + "\n  " + attribute("viewBox", VIEW_BOX)
          + "\n  " + attribute ("style", "background: white")
          + ">\n"
      );
      writer.println("  <g" 
        + " " + attribute("fill","black")
        + " " + attribute("stroke", "black") 
        + ">\n"
      );
      for (Node node : graph.getNodes()) {
        print(node);
      }
      for (Node node : graph.getNodes()) {
        for (Node neighbor : node.getNeighbors()) {
          print(node, neighbor);

        }
      }
      writer.println("  </g>\n");
      writer.println("</svg>");
    }

    private void print(Node u, Node v) {
      if (u.getPosition().getX() > v.getPosition().getX()) {
        return;
      }
      writer.println(
        "    <line"
          + " " + attribute("x1", ZOOM * u.getPosition().getX())
          + " " + attribute("y1", ZOOM * u.getPosition().getY())
          + " " + attribute("x2", ZOOM * v.getPosition().getX())
          + " " + attribute("y2", ZOOM * v.getPosition().getY())
          + ">\n"
      );
      if (!u.isRigid()) {
        animate(xStrings.get(u), "x1");
        animate(yStrings.get(u), "y1");
      }
      if (!v.isRigid()) {
        animate(xStrings.get(v), "x2");
        animate(yStrings.get(v), "y2");
      }
      writer.println("    </line>");
    }

    private void print(Node node) {
      writer.print("    <circle"
        + " " + attribute("cx", ZOOM * node.getPosition().getX())
        + " " + attribute("cy", ZOOM * node.getPosition().getY())
        + " " + attribute("r", NODE_RADIUS)
      );
      if (node.isRigid()) {
        writer.println("/>\n");
        return;
      }
      writer.println(">");
      animate(xStrings.get(node), "cx");
      animate(yStrings.get(node), "cy");
      writer.println("    </circle>\n");
    }

    private void animate(String values, String attribute) {
      writer.println(
          "      <animate"
        + "\n        " + attribute("attributeName", attribute)
        + "\n        " + attribute("values", values)
        + "\n        " + attribute("keyTimes", timeString)
        + "\n        " + attribute("dur", maxTime)
        + "\n        " + attribute("repeatCount", "indefinite")
        + "\n      />"
      );
    }

    private String attribute(String name, String value) {
      return name + "='" + value + "'";
    }

    private String attribute(String name, double value) {
      return name + "='" + value + "'";
    }

    private String attribute(String name, int value) {
      return name + "='" + value + "'";
    }  
  }




}
