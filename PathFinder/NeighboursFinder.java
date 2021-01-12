import java.util.ArrayList;


public class NeighboursFinder
{
    public NeighboursFinder(int widthLimit, int heightLimit, Coordinates2D origin, int distance)
    {
        assert widthLimit > 0 && heightLimit > 0 : "Les limites doivent être positives !";
        m_widthLimit = widthLimit;
        m_heightLimit = heightLimit;
        m_origin = origin;
        m_distance = distance;
    }

    public ArrayList<Coordinates2D> getNeighours()
    {
        assert m_origin.m_x >= 0 && m_origin.m_y >= 0 && m_origin.m_x < m_heightLimit && m_origin.m_y < m_widthLimit : "Erreur Coordinates2D n'est pas dans les limites !";
        var neighbours = new ArrayList<Coordinates2D>();
        for(m_currentRow = m_origin.m_y - m_distance; m_currentRow <= m_origin.m_y + m_distance; m_currentRow++)
        {
            for(m_currentColumn =m_origin.m_x - m_distance; m_currentColumn <= m_origin.m_x + m_distance; m_currentColumn++)
            {
                if(isInTerrainAndDifferentOfOrigin())
                    neighbours.add(new Coordinates2D(m_currentColumn, m_currentRow));
            }
        }
        return neighbours;
    }

    private boolean isInTerrainAndDifferentOfOrigin()
    {
       return  m_currentRow >= 0 && m_currentColumn >= 0 && m_currentRow < m_heightLimit && m_currentColumn < m_widthLimit
               &&  !(new Coordinates2D(m_currentColumn, m_currentRow)).equals(m_origin);
    }

    private int m_widthLimit;
    private int m_heightLimit;
    private Coordinates2D m_origin;
    private int m_distance;
    private int m_currentRow;
    private int m_currentColumn;

}

class Coordinates2D
{
    public Coordinates2D(int x, int y){
        m_x = x;
        m_y = y;
    }

    @Override
    public String toString()
    {
        return String.valueOf(m_x) + "," + String.valueOf(m_y);
    }

    public Coordinates2D clone() {
        return new Coordinates2D(m_x, m_y);
    }

    @Override
    public boolean equals(Object o) {
        return m_x == ((Coordinates2D)o).m_x && m_y == ((Coordinates2D)o).m_y;
    }

    public int m_x;
    public int m_y;
}


