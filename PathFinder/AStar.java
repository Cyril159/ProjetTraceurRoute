import org.jetbrains.annotations.NotNull;
import java.util.*;

public class AStar implements Algorithm
{
    public AStar(@NotNull Terrain terrain, @NotNull Coordinates2D start_pos, @NotNull Coordinates2D end_pos)
    {
        m_terrain = terrain;
        m_list = new PriorityQueue<>();
        m_processed_position = new ArrayList<>();
        m_start_pos = start_pos;
        m_end_pos = end_pos;
        m_currentPos = null;
    }

    @Override
    public Optional<Stack<Coordinates2D>> getBestPath() {
        m_currentPos = new CharacterizedPosition(m_terrain, m_start_pos, m_end_pos);
        m_list.add(m_currentPos);
        while(!m_list.isEmpty())
        {
            m_currentPos = m_list.poll();
            if(m_currentPos.isSamePosition(m_end_pos))
                return Optional.of(getPath());
            m_processed_position.add(m_currentPos);
            addNeighboursOfCurrentPos();
        }
        return Optional.empty();
    }


    protected Stack<Coordinates2D> getPath()
    {
        return m_currentPos.getPathToHereFromOrigin();
    }

    private void addNeighboursOfCurrentPos() {
        var neighboursPos = m_currentPos.getNeighbours(1);
        for(var neighbour: neighboursPos)
        {
            if(!m_processed_position.contains(neighbour))
            {
                var posInPriorityList = getSamePosFromPriorityList(neighbour);
                if(posInPriorityList != null && neighbour.getProductionCost() < posInPriorityList.getProductionCost())
                    m_list.add(neighbour);
                else if(posInPriorityList == null)
                    m_list.add(neighbour);
            }
        }
    }

    private CharacterizedPosition getSamePosFromPriorityList(CharacterizedPosition pos)
    {
        for(var otherPos: m_list)
        {
            if(otherPos.equals(pos))
                return otherPos;
        }
        return null;
    }

    private Terrain m_terrain;
    protected PriorityQueue<CharacterizedPosition> m_list;
    protected ArrayList<CharacterizedPosition> m_processed_position;
    protected Coordinates2D m_start_pos;
    protected Coordinates2D m_end_pos;
    protected CharacterizedPosition m_currentPos;
}
