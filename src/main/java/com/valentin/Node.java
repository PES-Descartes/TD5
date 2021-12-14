package com.valentin;

import java.util.HashSet;

public class Node {
    private static final double FRICTION = -0.8;

    private Vector position, velocity = new Vector(0,0);
    private final HashSet<Node> neighbors = new HashSet<>();
    private boolean isRigid;

    public Node(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public HashSet<Node> getNeighbors() {
        return neighbors;
    }

    public boolean isRigid() {
        return isRigid;
    }

    public void setRigidity(boolean rigid) {
        isRigid = rigid;
    }

    public void add(Node neighbor) {
        getNeighbors().add(neighbor);
    }

    private Vector getForce() {
        Vector force = velocity.scale(FRICTION);
        for(Node neighbor : neighbors)
            force = force.add(neighbor.getPosition().subtract(this.getPosition()));

        return force;
    }

    public void updateVelocity(double dt) {
        velocity = velocity.add(getForce().scale(dt));
    }

    public void updatePosition(double dt) {
        if(!isRigid())
            position = position.add(velocity.scale(dt));
    }
}
