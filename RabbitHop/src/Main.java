import RabbitHop.RabbitHop;
import Maude.Module;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Module rabbitHop = new RabbitHop(3);
        System.out.println(rabbitHop);
        rabbitHop.fairRewrite(new Random(System.currentTimeMillis()));
        System.out.println(rabbitHop);
        rabbitHop.printTrace();
    }
}