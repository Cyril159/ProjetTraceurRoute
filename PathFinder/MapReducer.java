
//TODO Faire une classe 2DMatrix
//TODO Simplifier les tests de la classe MapReducer.

import java.util.ArrayList;

public class MapReducer
{

    public MapReducer(Terrain terrain, int reductionFactor)
    {
        assert Math.sqrt(reductionFactor) - (int)Math.sqrt(reductionFactor) == 0 : "sqrt(reductionFactor) must be an integer";
        assert terrain.getMapSize()[0] % Math.sqrt(reductionFactor) == 0 && terrain.getMapSize()[1] % Math.sqrt(reductionFactor) == 0 : "terrain size must be a multiple of reductionFactor";
        m_orginalTerrain = terrain;
        m_reductionFactor = reductionFactor;
        m_reductionLength = (int)Math.sqrt(reductionFactor);
        m_reducedWidth = terrain.getMapSize()[0] / (m_reductionLength);
        m_reducedHeight = terrain.getMapSize()[1] / (m_reductionLength);
        m_reducedTerrain = new double[m_reducedWidth * m_reducedHeight];
        m_waterIndice = new ArrayList<>();
    }

    public GridTerrain getReducedMap()
    {
        initializeReducedTerrain();
        return new GridTerrain(m_reducedTerrain, m_waterIndice, m_reducedWidth, m_reducedHeight);
    }

    private void initializeReducedTerrain() {
        for(int i = 0; i < m_orginalTerrain.getMapSize()[1]; i+= (m_reductionLength))
        {
            for(int j = 0; j < m_orginalTerrain.getMapSize()[0]; j+= (m_reductionLength))
                insertHeightAverage(i, j);
        }
        m_orginalTerrain = null;
    }

    private void insertHeightAverage(int rowPos, int columnPos) {
        double heightAverage = 0.0;
        int nbWaterArea = 0;
        for(int i = rowPos; i < m_reductionLength + rowPos; i++)
        {
            for(int j = columnPos; j < m_reductionLength + columnPos; j++) {
                if(m_orginalTerrain.IsInTerrainLimits(new Coordinates2D(j, i))) {
                    heightAverage += m_orginalTerrain.getHeightOfArea(new Coordinates2D(j, i));
                    if(m_orginalTerrain.getTypeOfTheArea(new Coordinates2D(j,i)) == Terrain.AreaType.Water)
                        nbWaterArea++;
                }
            }
        }
        heightAverage /= m_reductionFactor;
        int insertIndice = (rowPos * m_reducedWidth + columnPos) / (m_reductionLength);
        m_reducedTerrain[insertIndice] = heightAverage;
        if(nbWaterArea > Math.round(m_reductionFactor/2.0))
            m_waterIndice.add(insertIndice);
    }

    public Coordinates2D convertReducedPosToRealPos(Coordinates2D pos)
    {
        int realX = pos.m_x * m_reductionLength + (m_reductionLength / 2);
        int realY = pos.m_y * m_reductionLength + (m_reductionLength / 2);
        return new Coordinates2D(realX,realY);
    }

    public Coordinates2D convertRealPosToReducedPos(Coordinates2D pos)
    {
        int reducedX = pos.m_x / m_reductionLength;
        int reducedY = pos.m_y / m_reductionLength;
        return new Coordinates2D(reducedX,reducedY);
    }

    private Terrain m_orginalTerrain;
    private int m_reductionFactor;
    private int m_reductionLength;
    private double[] m_reducedTerrain;
    private int m_reducedWidth;
    private int m_reducedHeight;
    private ArrayList<Integer> m_waterIndice;
}
