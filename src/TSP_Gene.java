import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class TSP_Gene {
    private final int x;
    private final int y;

    TSP_Gene(final int x,
            final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    double distance(final TSP_Gene other) {
        return sqrt(pow(getX() - other.getX(), 2) + pow(getY() - other.getY(), 2));
    }

    @Override
    public boolean equals(Object o) { //created using "Generate" hash/equals on TSP_Gene
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TSP_Gene gene = (TSP_Gene) o;
        return this.x == gene.x &&
                this.y == gene.y;
    }

    @Override
    public int hashCode() { //also generated with the same command
        return Objects.hash(x, y);
    }
}
