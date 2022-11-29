package Maude;

import java.util.Random;

public interface Module {
    void rewrite();
    void fairRewrite(Random random);

    void printTrace();
}
