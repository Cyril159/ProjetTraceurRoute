import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public abstract class Terrain
{
    public enum AreaType
    {
        Ground,
        Water
    }
    public abstract AreaType getTypeOfTheArea(Coordinates2D coordinates);
    /**
     * @pre : coordinates must be contained in the Terrain
     * @post : The return value must be positive .
     */
    public abstract double getHeightOfArea(@NotNull Coordinates2D coordinates);

    /**
     * @pre : coordinates must be contained in the Terrain
     * @post : The return value must always be  between 0 and 1 .
     */
    public abstract float getDensityOfVegetation(@NotNull Coordinates2D coordinates);

    /**
     * @pre : currentCoordinates2D and previousCoordinates2D must be contained in the Terrain.
     * @post : The return valuel must be positive.
     */
    public double getSlope(@NotNull Coordinates2D currentCoordinates, @NotNull Coordinates2D previousCoordinates)
    {
        return (getHeightOfArea(currentCoordinates) - getHeightOfArea(previousCoordinates)) / distanceBetweenTwoPos(currentCoordinates, previousCoordinates);
    }

    /**
     * @pre : start_pos and end_pos must be contained in the Terrain
     * @post : The return value must always be positive
     */
    public abstract double distanceBetweenTwoPos(@NotNull Coordinates2D start_pos, @NotNull Coordinates2D end_pos);


    /**
     * @post : getMapSize()[0] > 0 and getMapSize()[1] > 0
     */
    public abstract int[] getMapSize();

    public boolean IsInTerrainLimits(Coordinates2D pos)
    {
        return  pos.m_x >= 0 && pos.m_y >= 0 && pos.m_x < getMapSize()[0] && pos.m_y < getMapSize()[1];
    }

}

