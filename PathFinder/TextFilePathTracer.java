import java.io.*;

public class TextFilePathTracer extends PathTracer {
    TextFilePathTracer(Terrain terrain, Coordinates2D startPixel, Coordinates2D endPixel, int reductionFactor) throws IOException {
        super(startPixel, endPixel, terrain, reductionFactor);
        System.out.println("Fille");
    }

    @Override
    protected void trace() throws IOException {
        var streamPath = new OutputStreamWriter(new FileOutputStream("result.txt"));
        streamPath.append(String.valueOf(m_realPath.size()));
        while(!m_realPath.isEmpty()){
            streamPath.append("\n");
            streamPath.append(m_realPath.pop().toString());
        }
        streamPath.close();
    }
}
