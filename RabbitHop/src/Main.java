import Maude.Node;
import RabbitHop.RabbitHop;
import Maude.Module;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Module rabbitHop = new RabbitHop(3);
        //fairRewrite(rabbitHop);
        search(rabbitHop, "ooo_xxx");
        //searchBlockStates(rabbitHop);
        //testNext("xx_o_ox_");
    }

    private static void fairRewrite(Module module){
        System.out.println(module);
        module.fairRewrite(new Random(System.currentTimeMillis()));
        System.out.println(module);
        module.printTrace();
    }

    private static void search(Module initialState, String pattern) {
        List<Node> states = initialState.search(pattern);
        System.out.printf("Paths from %s to %s: %d\n\n",initialState, pattern, states.size());
        System.out.println(states);
        for (Node node : states){
            System.out.println();
            RabbitHop state = (RabbitHop) node;
            System.out.printf("Result: %s ---> %s\n", initialState, state);
            state.printTrace();
        }
    }

    private static void searchBlockStates(Module initialState) {
        List<Node> finalStates = initialState.searchBlockStates();
        System.out.printf("Final states paths: %d\n\n", finalStates.size());
        System.out.println(finalStates);
        for (Node node : finalStates){
            System.out.println();
            RabbitHop finalState = (RabbitHop) node;
            System.out.printf("Result: %s ---> %s\n", initialState, finalState);
            finalState.printTrace();
        }
    }

    private static void testNext(String rabbitHopRepresentation){
        RabbitHop initialState = new RabbitHop(rabbitHopRepresentation);
        List<Node> nextStates = initialState.next();
        System.out.printf("Initial state: %s\n\n", initialState);
        System.out.printf("Next states:\n%s\n", nextStates);
        for (Node node : nextStates){
            System.out.println();
            RabbitHop nextState = (RabbitHop) node;
            System.out.printf("Result: %s ---> %s\n", initialState, nextState);
            nextState.printTrace();
        }
    }
}