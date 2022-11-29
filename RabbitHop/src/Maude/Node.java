package Maude;

import java.util.List;

public interface Node {
    boolean hasNext();
    List<Node> next();
}
