import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class TerrainFromFile extends Terrain{

    public TerrainFromFile(String pathName) throws IOException {
        m_filePath = pathName;
        m_heightmap = new ArrayList<>();
        parseFile();
    }


    private void parseFile() throws IOException {
        var dataFile = new File(m_filePath);
        m_stream = new InputStreamReader(new FileInputStream(dataFile));
        extractWidthAndWaterLevel();
        extractHeightMap();
        m_stream.close();
        checkDataExtracted();
    }

    private void extractWidthAndWaterLevel() throws IOException {
        extractNextField(' ').ifPresent((Double res)-> m_width = (int)res.doubleValue());
        skipNextField();
        extractNextField('\n').ifPresent((Double res)-> m_waterLevel = res);
        System.out.println(m_width + ";" + m_waterLevel);
    }


    private Optional<Double> extractNextField(char ...separators) throws IOException {
        var stringField = new String();
        m_currentChar = m_stream.read();
        while (isNotSeperator((char)m_currentChar, separators) && m_currentChar != -1){
            stringField += (char)m_currentChar;
            m_currentChar = m_stream.read();
        }
        stringField = stringField.replace(',','.');
        double field = 0.0;
        if(stringField.isEmpty())
            return Optional.empty();
        field = Double.parseDouble(stringField);
        return Optional.of(field);
    }

    private boolean isNotSeperator(char character, char ...separators)
    {
        for(char sep : separators)
        {
            if(character == sep)
                return false;
        }
        return true;
    }

    private void skipNextField() throws IOException {
        m_currentChar = m_stream.read();
        while(m_currentChar != ' ')
            m_currentChar = m_stream.read();
    }

    private void extractHeightMap() throws IOException {
        extractNextField(' ', '\n').ifPresent((Double res)-> m_heightmap.add(res));
        while(m_currentChar != -1)
            extractNextField(' ', '\n').ifPresent((Double res)-> m_heightmap.add(res));
    }


    private void checkDataExtracted() throws IOException
    {
        if((m_heightmap.size()/(double)m_width) - (m_heightmap.size()/m_width) != 0)
            throw new IOException("incoherent heightmap size !");
    }


    @Override
    public AreaType getTypeOfTheArea(Coordinates2D coordinates) {
        if(getHeightOfArea(coordinates) >= m_waterLevel)
            return AreaType.Ground;
        else
            return AreaType.Water;
    }

    @Override
    public double getHeightOfArea(@NotNull Coordinates2D coordinates) {
        return m_heightmap.get(coordinates.m_y * m_width + coordinates.m_x);
    }

    @Override
    public float getDensityOfVegetation(@NotNull Coordinates2D coordinates) {
        return 0;
    }

    @Override
    public double distanceBetweenTwoPos(@NotNull Coordinates2D start_pos, @NotNull Coordinates2D end_pos) {
        return Math.sqrt(Math.pow(end_pos.m_x - start_pos.m_x, 2) + Math.pow(end_pos.m_y - start_pos.m_y, 2));
    }

    @Override
    public int[] getMapSize() {
        return new int[]{m_width, m_heightmap.size()/m_width};
    }

    private String m_filePath;
    private InputStreamReader m_stream;
    private ArrayList<Double> m_heightmap;
    private int m_width;
    private int m_currentChar;
    private double m_waterLevel;

}
