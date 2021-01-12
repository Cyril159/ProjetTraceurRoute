import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TerrainForTest extends Terrain
{
    public TerrainForTest(@NotNull float[][] heightmap,@NotNull int[] size) {
        assert size[0] > 0 && size[1] > 0 : "Terrain cannot have a 0 size !";
        m_map = heightmap;
        m_size = size;

    }

    @Override
    public AreaType getTypeOfTheArea(Coordinates2D coordinates) {
        return AreaType.Ground;
    }

    @Override
    public double getHeightOfArea(Coordinates2D position) {
        assert IsInTerrainLimits(position) : "Coordinates2D is out of limit";
        return m_map[position.m_y][position.m_x];
    }

    @Override
    public float getDensityOfVegetation(Coordinates2D position) {
        assert IsInTerrainLimits(position) : "Coordinates2D is out of limits";
        return 0.0f;
    }

    @Override
    public double distanceBetweenTwoPos(Coordinates2D start_pos, Coordinates2D end_pos) {
        assert IsInTerrainLimits(start_pos) && IsInTerrainLimits(end_pos) : "Coordinates2Ds are out of limits";
        return Math.sqrt(Math.pow(start_pos.m_x - end_pos.m_x, 2) + Math.pow(start_pos.m_y - end_pos.m_y, 2));
    }


    @Override
    public int[] getMapSize() {
        return m_size;
    }

    private float[][] m_map;
    private  int[] m_size;
}

