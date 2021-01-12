import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main
{

    public static void main(String[] args)
    {/*
        try {
           var terrain = new TerrainFromImage("heightmap0.png",1);
           var tracer = new ImagePathTracer(terrain, new Coordinates2D(449,479), new Coordinates2D(241,430),
                   256, "heightmap0.png");
           tracer.tracePath();

          //  var terr = new TerrainFromFile("height_map.txt");
           // System.out.println(terr.getHeightOfArea(new Coordinates2D(0,0)));
        }
        catch(Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }*/
     try{
        var terrainFromFile = new TerrainFromFile("carte.txt");
        System.out.println(terrainFromFile.getHeightOfArea(new Coordinates2D(0,0)));

       // System.out.println(terrainFromFile.getMapSize()[0] + ";" + terrainFromFile.getMapSize()[1]);
    }catch(Exception e){System.out.println(e.getMessage());}
}

}