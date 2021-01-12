import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Stack;

public class CharacterizedPosition implements Comparable<CharacterizedPosition>
{

    public CharacterizedPosition(Terrain terrain, Coordinates2D pos, Coordinates2D goalPos) {
        m_terrain = terrain;
        m_position = pos;
        m_goalPos = goalPos;
        m_parent = null;
        initalizeCost();
    }
    public CharacterizedPosition(Terrain terrain, Coordinates2D pos,Coordinates2D goalPos, CharacterizedPosition previousPos) {
        this(terrain, pos, goalPos);
        m_parent = previousPos;
        initalizeCost();
    }


    @Override
    public int compareTo(CharacterizedPosition other) {
        if(m_total_cost > other.m_total_cost)
            return 1;
        else if(m_total_cost == other.m_total_cost) {
            return compareIfSameCost(other);
        }
        else
            return -1;
    }

    private int compareIfSameCost(CharacterizedPosition other)
    {
        if(m_prodCost == other.m_prodCost)
            return 0;
        else if(m_prodCost > other.m_prodCost)
            return 1;
        else
            return -1;
    }

    public String toString()
    {
        return String.valueOf(m_position.m_x) + "," + String.valueOf(m_position.m_y);
    }

    @Override
    public boolean equals(Object obj)
    {
        var otherPos = (CharacterizedPosition)obj;
        return   m_position.equals(otherPos.m_position) && m_terrain == otherPos.m_terrain ;

    }


    public boolean isSamePosition(Coordinates2D coordinates)
    {
        return m_position.equals(coordinates);
    }
    public double getProductionCost()
    {
        return m_prodCost;
    }

    public Stack<Coordinates2D> getPathToHereFromOrigin()
    {
        var currentPos = this;
        var path = new Stack<Coordinates2D>();
        while(currentPos != null)
        {
            path.push(currentPos.m_position);
            currentPos = currentPos.m_parent;
        }
        return path;
    }

    public ArrayList<CharacterizedPosition> getNeighbours(int distance)
    {
        var neighbours = new ArrayList<CharacterizedPosition>();
        var neighbourFinder = new NeighboursFinder(m_terrain.getMapSize()[0], m_terrain.getMapSize()[1], m_position, distance);
        for(var neighbour : neighbourFinder.getNeighours())
            neighbours.add(new CharacterizedPosition(m_terrain, neighbour, m_goalPos,this));
        return neighbours;
    }

    public Coordinates2D getPos()
    {
        return m_position;
    }

    public void initalizeCost()
    {
        m_prodCost = 0.0;
        if(m_parent != null) {
            m_prodCost = m_parent.m_total_cost;
            m_prodCost += ((Math.abs(m_terrain.getSlope(m_position, m_parent.m_position))) * 1000);
        }
        var goalVector = new Coordinates2D(m_goalPos.m_x-146, m_goalPos.m_y-188);
        var currentVector = new Coordinates2D(m_goalPos.m_x-m_position.m_x, m_goalPos.m_y-m_position.m_y);
       // double pr = (currentVector.m_x*goalVector.m_x + currentVector.m_y*goalVector.m_y ) / (m_terrain.distanceBetweenTwoPos(currentVector, new Coordinates2D(0,0)) * m_terrain.distanceBetweenTwoPos(goalVector, new Coordinates2D(0,0)));
        double heuristique = m_terrain.distanceBetweenTwoPos(m_position, m_goalPos) * 10;
        m_total_cost = m_prodCost;
        m_total_cost += heuristique;
    }

    private Terrain m_terrain;
    private Coordinates2D m_position;
    private Coordinates2D m_goalPos;
    private CharacterizedPosition m_parent;
    private double m_prodCost;
    private double m_total_cost;
}