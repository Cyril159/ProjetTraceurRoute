import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TerrainFromImage extends Terrain
{
    public TerrainFromImage(String imagePath, double heighestHeight) throws IOException {
            m_image = ImageIO.read(new File(imagePath));
            m_heighestHeight = heighestHeight;
    }

    @Override
    public AreaType getTypeOfTheArea(Coordinates2D coordinates) {
        return AreaType.Ground;
    }

    @Override
    public double getHeightOfArea(@NotNull Coordinates2D position) {
        return Math.abs(m_image.getRGB(position.m_x, position.m_y)) / m_heighestHeight;
    }

    @Override
    public float getDensityOfVegetation(@NotNull Coordinates2D position) {
        return 0;
    }

    @Override
    public double distanceBetweenTwoPos(@NotNull Coordinates2D start_pos, @NotNull Coordinates2D end_pos) {
        return Math.sqrt(Math.pow(start_pos.m_x - end_pos.m_x, 2) +  Math.pow(start_pos.m_y - end_pos.m_y, 2));
        // return Math.pow(start_pos.m_x - end_pos.m_x, 2) + Math.pow(start_pos.m_y - end_pos.m_y, 2);
    }

    @Override
    public int[] getMapSize() {
        return new int[]{m_image.getWidth(), m_image.getHeight()};
    }

    private BufferedImage m_image;
    private double m_heighestHeight;
}
