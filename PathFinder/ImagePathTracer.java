import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePathTracer  extends PathTracer
{
    public ImagePathTracer(Terrain terrain, Coordinates2D startPixel, Coordinates2D endPixel, int reductionFactor, String imagePath) throws IOException {
        super(startPixel, endPixel, terrain, reductionFactor);
        try {
            m_image = ImageIO.read(new File(imagePath));
            m_graphics = m_image.createGraphics();
        }
        catch(IOException e) {
            throw new IOException("Impossible de charger l'image !");
        }
    }

    @Override
    protected void trace() throws IOException {
        int size = m_realPath.size();
        var x_pos = new int[size];
        var y_pos = new int[size];
        int i = 0;
        while(!m_realPath.empty()){
         var pos = m_realPath.pop();
         x_pos[i] = pos.m_x;
         y_pos[i] = pos.m_y;
         i++;
        }

        m_graphics.setColor(Color.RED);
        m_graphics.drawPolyline(x_pos, y_pos, size);
        try {
            ImageIO.write(m_image, "PNG", new File("result.png"));
        }
        catch(IOException e) {
            throw new IOException("Cannot write image !");
        }
    }

    private BufferedImage m_image;
    private Graphics2D m_graphics;
}
