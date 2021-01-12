import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MapReducerTest {
    @Test
    public void test_convert_to_real_pos_1x1_terrain() {
        given_terrain(heightmap_0x0,1 ,1);
        when_terrain_reduced_by(1);
        Assert.assertEquals(m_reducer.convertReducedPosToRealPos(new Coordinates2D(0, 0)), new Coordinates2D(0, 0));
    }

    @Test
    public void test_convert_to_real_pos_6x6_terrain() {
        given_terrain(heightmap_0x0,6,6);
        when_terrain_reduced_by(9);

        Assert.assertEquals(new Coordinates2D(1, 1), m_reducer.convertReducedPosToRealPos(new Coordinates2D(0, 0)));
        Assert.assertEquals(new Coordinates2D(4, 1), m_reducer.convertReducedPosToRealPos(new Coordinates2D(1, 0)));
        Assert.assertEquals(new Coordinates2D(1, 4), m_reducer.convertReducedPosToRealPos(new Coordinates2D(0, 1)));
        Assert.assertEquals(new Coordinates2D(4, 4), m_reducer.convertReducedPosToRealPos(new Coordinates2D(1, 1)));
    }

    @Test
    public void test_convert_to_real_pos_4x4_terrain() {
        given_terrain(heightmap_0x0,4,4);
        when_terrain_reduced_by(4);

        Assert.assertEquals(new Coordinates2D(1, 1), m_reducer.convertReducedPosToRealPos(new Coordinates2D(0, 0)));
        Assert.assertEquals(new Coordinates2D(3, 1), m_reducer.convertReducedPosToRealPos(new Coordinates2D(1, 0)));
        Assert.assertEquals(new Coordinates2D(1, 3), m_reducer.convertReducedPosToRealPos(new Coordinates2D(0, 1)));
        Assert.assertEquals(new Coordinates2D(3, 3), m_reducer.convertReducedPosToRealPos(new Coordinates2D(1, 1)));
    }

    @Test
    public void test_convert_to_reduced_pos_6x6_terrain() {
        given_terrain(heightmap_0x0,6,6);
        when_terrain_reduced_by(9);

        Assert.assertEquals(new Coordinates2D(0, 0), m_reducer.convertRealPosToReducedPos(new Coordinates2D(1, 0)));
        Assert.assertEquals(new Coordinates2D(1, 0), m_reducer.convertRealPosToReducedPos(new Coordinates2D(4, 1)));
        Assert.assertEquals(new Coordinates2D(0, 1), m_reducer.convertRealPosToReducedPos(new Coordinates2D(0, 3)));
        Assert.assertEquals(new Coordinates2D(1, 1), m_reducer.convertRealPosToReducedPos(new Coordinates2D(3, 5)));
    }

    @Test
    public void test_with_2x2_terrain_reduce_by_4() {
        given_terrain(heightmap_2x2,2,2);
        when_terrain_reduced_by(4);
        var reducedTerrain = m_reducer.getReducedMap();

        Assert.assertEquals(1, reducedTerrain.getMapSize()[0]);
        Assert.assertEquals(1, reducedTerrain.getMapSize()[1]);
        Assert.assertTrue(reducedTerrain.getHeightOfArea(new Coordinates2D(0, 0)) == 2.5);
    }

    @Test
    public void test_with_4x4_terrain_reduce_by_4() {
        given_terrain(heightmap_4x4,4,4);
        when_terrain_reduced_by(4);
        var reducedTerrain = m_reducer.getReducedMap();

        Assert.assertEquals(2, reducedTerrain.getMapSize()[0]);
        Assert.assertEquals(2, reducedTerrain.getMapSize()[1]);
        Assert.assertTrue(reducedTerrain.getHeightOfArea(new Coordinates2D(0, 0)) == 2.75);
        Assert.assertTrue(reducedTerrain.getHeightOfArea(new Coordinates2D(1, 0)) == 2.5);
        Assert.assertTrue(reducedTerrain.getHeightOfArea(new Coordinates2D(0, 1)) == 4);
        Assert.assertTrue(reducedTerrain.getHeightOfArea(new Coordinates2D(1, 1)) == 2);
    }

    private void then_size_should_be()
    {

    }

    private void then_should_be_equal(double[][] reducedTerrainExpected)
    {

    }

    @Test
    public void test_with_heightmap0_reduce_by_4() {
        given__terrain_from_image("heightmap0.png");
        when_terrain_reduced_by(4);

        double average = m_terrain.getHeightOfArea(new Coordinates2D(510, 510));
        average += m_terrain.getHeightOfArea(new Coordinates2D(511, 510));
        average += m_terrain.getHeightOfArea(new Coordinates2D(510, 511));
        average += m_terrain.getHeightOfArea(new Coordinates2D(511, 511));
        average /= 4;

        var reducedTerrain = m_reducer.getReducedMap();
        Assert.assertEquals(reducedTerrain.getMapSize()[0], 256);
        Assert.assertEquals(reducedTerrain.getMapSize()[1], 256);
        Assert.assertEquals(average, reducedTerrain.getHeightOfArea(new Coordinates2D(255, 255)), 0.1);
        average = 0.0;
        average += m_terrain.getHeightOfArea(new Coordinates2D(120, 110));
        average += m_terrain.getHeightOfArea(new Coordinates2D(121, 110));
        average += m_terrain.getHeightOfArea(new Coordinates2D(120, 111));
        average += m_terrain.getHeightOfArea(new Coordinates2D(121, 111));
        average /= 4;
        Assert.assertEquals(average, reducedTerrain.getHeightOfArea(new Coordinates2D(60, 55)), 0.1);

    }

    private void then_check_size_and_actual_average()
    {
        var reduced = m_reducer.getReducedMap();

    }

    private void check_size()
    {

    }
/* WARNING Ne gère pas encore les les image qui ne sont pas multiples de reduction factor
    @Test
    public void test_reduce_4x4_terrain_by_9()
    {
        var heightmap = new float[][] {{1, 2, 2, 4},
                                       {4, 4, 2, 2},
                                       {4, 4, 2, 2},
                                       {4, 4, 2, 2}};

        var terrain = new TerrainForTest(heightmap, new int[]{4,4});
        var reducer = new MapReducer(terrain, 9);
        var reducedTerrain = reducer.getReduceMap();
        Assert.assertEquals(2.7, reducedTerrain.getHeightOfArea(new Coordinates2D(0,0)), 0.1);
        Assert.assertEquals(2.6, reducedTerrain.getHeightOfArea(new Coordinates2D(1,0)),0.1);
        Assert.assertEquals(3.3, reducedTerrain.getHeightOfArea(new Coordinates2D(0,3)),0.1);
        Assert.assertEquals(2.0, reducedTerrain.getHeightOfArea(new Coordinates2D(3,3)),0);
    }*/

    private void given_terrain(float[][] heightmap, int width, int height)
    {
        m_terrain = new TerrainForTest(heightmap, new int[]{width, height});
    }

    private void given__terrain_from_image(String path)
    {
        try {
            m_terrain = new TerrainFromImage(path, 1);
        }
        catch(IOException exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private void when_terrain_reduced_by(int reduceFactor)
    {
        m_reducer = new MapReducer(m_terrain, reduceFactor);
    }


    private Terrain m_terrain;
    private MapReducer m_reducer;
    private GridTerrain m_reduced;

    private final float[][] heightmap_0x0 = new float[][]{};
    private final float[][] heightmap_1x1 = new float[][]{{1}};
    private final float[][] heightmap_2x2 = new float[][]{{1, 2}, {3, 4}};
    private final float[][] heightmap_4x4 = new float[][]{{1, 2, 2, 4},
                                                          {4, 4, 2, 2},
                                                          {4, 4, 2, 2},
                                                          {4, 4, 2, 2}};

}