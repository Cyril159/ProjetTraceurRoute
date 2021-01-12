import org.jetbrains.annotations.NotNull;

public class HPAStar extends AStar {
    public HPAStar(@NotNull Terrain terrain, @NotNull Coordinates2D start_pos, @NotNull Coordinates2D end_pos, int nbAbstractLevel) {
        super(terrain, start_pos, end_pos);
    }


}
