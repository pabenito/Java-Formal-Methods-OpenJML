import Maude.Node;
import RabbitHop.RabbitHop;
import Maude.Module;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Module rabbitHop = new RabbitHop(3);
        //fairRewrite(rabbitHop);
        searchFinalStates(rabbitHop);
        //testNext();
    }

    private static void fairRewrite(Module module){
        System.out.println(module);
        module.fairRewrite(new Random(System.currentTimeMillis()));
        System.out.println(module);
        module.printTrace();
    }

    private static void searchFinalStates(Module initialState) {
        List<Node> finalStates = initialState.searchFinalStates();
        System.out.printf("Final states paths: %d\n", finalStates.size());
        for (Node node : finalStates){
            System.out.println();
            RabbitHop finalState = (RabbitHop) node;
            System.out.printf("Result: %s ---> %s\n", initialState, finalState);
            finalState.printTrace();
        }
    }

    private static void testNext(){
        RabbitHop rabbitHop = new RabbitHop(3);
        System.out.println(rabbitHop);
        System.out.println(rabbitHop.next());
    }

}