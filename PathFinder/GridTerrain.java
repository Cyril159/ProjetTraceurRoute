import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class GridTerrain extends Terrain {

    private class Box
    {
        public Box(AreaType tpe, double hgt){type = tpe; height = hgt;}
        public AreaType type;
        public double height;
    }

    public GridTerrain(double[] terrain, ArrayList<Integer> waterIndice, int width, int height){
       m_terrrain = new Box[width * height];
       for(int i = 0; i < width*height; i++)
       {
           if(waterIndice.contains(i))
               m_terrrain[i] = new Box(AreaType.Water, terrain[i]);
           else
               m_terrrain[i] = new Box(AreaType.Ground, terrain[i]);
       }
       m_width = width;
       m_height = height;
    }

    @Override
    public AreaType getTypeOfTheArea(Coordinates2D coordinates) {
        int indice = CoordinatesToIndice(coordinates);
        if(m_terrrain[indice].type == AreaType.Ground)
            return AreaType.Ground;
        else
            return AreaType.Water;
    }

    @Override
    public double getHeightOfArea(@NotNull Coordinates2D coordinates) {
        return m_terrrain[CoordinatesToIndice(coordinates)].height;
    }

    @Override
    public float getDensityOfVegetation(@NotNull Coordinates2D position) {
        return 0;
    }

    private int CoordinatesToIndice(Coordinates2D coordinates)
    {
        return coordinates.m_y * m_width + coordinates.m_x;
    }

    @Override
    public double distanceBetweenTwoPos(@NotNull Coordinates2D start_pos, @NotNull Coordinates2D end_pos) {
        return Math.sqrt(Math.pow(end_pos.m_x - start_pos.m_x, 2) + Math.pow(end_pos.m_y - start_pos.m_y, 2));
    }

    @Override
    public int[] getMapSize() {
        return new int[]{m_width, m_height};
    }

    private Box[] m_terrrain;
    private int m_width;
    private int m_height;
}
