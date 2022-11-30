package Maude;

import java.util.List;
import java.util.Random;

public interface Module {
    void rewrite();
    void fairRewrite(Random random);

    void printTrace();

    List<Node> search(String pattern);

    List<Node> searchBlockStates();
}
