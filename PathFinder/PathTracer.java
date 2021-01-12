import javafx.geometry.Pos;

import java.io.IOException;
import java.util.*;

public abstract class PathTracer {
    PathTracer(Coordinates2D startPixel, Coordinates2D endPixel, Terrain terrain, int reductionFactor) throws IOException {
        System.out.println("Mere");
        m_reducer = new MapReducer(terrain, reductionFactor);
        m_algorithm = new AStar(m_reducer.getReducedMap(), m_reducer.convertRealPosToReducedPos(startPixel),
                m_reducer.convertRealPosToReducedPos(endPixel));
        m_realPath = new Stack<>();
    }

    public void tracePath() throws IOException, RuntimeException
    {
        var path = m_algorithm.getBestPath();
        createRealPathFromReduced(path);
        trace();
    }

    private void createRealPathFromReduced(Optional<Stack<Coordinates2D>> reducedPath)
    {
        if(reducedPath.isEmpty())
            throw  new RuntimeException("Error no path found !");
        for(var pos : reducedPath.get()) {
            System.out.println(m_reducer.convertReducedPosToRealPos(pos));
            m_realPath.add(m_reducer.convertReducedPosToRealPos(pos));
        }
    }
    protected abstract void trace() throws IOException;

    private Algorithm m_algorithm;
    private MapReducer m_reducer;
    protected Stack<Coordinates2D> m_realPath;
}
