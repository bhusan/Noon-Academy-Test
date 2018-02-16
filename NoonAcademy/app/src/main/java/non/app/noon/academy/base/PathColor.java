package non.app.noon.academy.base;

import android.graphics.Path;

/**
 * Created by bharat on 15/2/18.
 */

public class PathColor {
    private Path path;
    private int color;

    public PathColor(Path path, int color) {
        this.path = path;
        this.color = color;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
