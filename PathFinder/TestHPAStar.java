import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class TestHPAStar
{

    @Test
    public void testWithOneAbstractLevel()
    {
        m_testTerrain = new TerrainForTest(m_heightmap5x5, new int[]{5, 5});
        m_algo = new HPAStar(m_testTerrain, new Coordinates2D(0,0), new Coordinates2D(0,4), 1);
        var res = m_algo.getBestPath().get();
        var expected = new Stack<Coordinates2D>();
        expected.push(new Coordinates2D(0,4));
        expected.push(new Coordinates2D(0,3));
        expected.push(new Coordinates2D(1,2));
        expected.push(new Coordinates2D(1,1));
        expected.push(new Coordinates2D(0,0));
        Assert.assertEquals(expected, res);
    }

    private final float[][] m_heightmap5x5 = new float[][]{{ 1, 99, 99, 99, 1},
                                                           {99,  1, 99, 99, 1},
                                                           {99,  1, 99, 99, 1},
                                                           { 1, 99, 99, 99, 1},
                                                           { 1,  1,  1,  1, 1}};
    private TerrainForTest m_testTerrain;
    private HPAStar m_algo;
}

