import Maude.Module;
import RabbitHop.RabbitHop;
import java.util.List;
import java.util.Random;

public class Main {
    private final static String TARGET_STATE = "ooo_xxx";
    public static void main(String[] args) {
        Module rabbitHop = new RabbitHop(3);
        System.out.printf("Rewrite:\n\n");
        rewrite(rabbitHop.clone());
        System.out.printf("\nFair Rewrite:\n\n");
        fairRewrite(rabbitHop.clone());
        System.out.printf("\nTest Next:\n\n");
        testNext("xx_o_ox_");
        System.out.printf("\nSearch:\n\n");
        search(rabbitHop.clone(), TARGET_STATE);
        System.out.printf("\nSearch Block States:\n\n");
        searchBlockStates(new RabbitHop(2)); // With RabbitHop(2) because RabbitHop(3) gives too many states
    }

    public static void rewrite(Module module){
        String initialState = module.toString();
        module.rewrite();
        System.out.printf("%s ---> %s\n\n", initialState, module);
        module.printTrace();
    }

    public static void fairRewrite(Module module){
        String initialState = module.toString();
        module.fairRewrite(new Random(System.currentTimeMillis()));
        System.out.printf("%s ---> %s\n\n", initialState, module);
        module.printTrace();
    }

    public static void search(Module initialState, String pattern) {
        List<Module> states = initialState.search(pattern);

        System.out.printf("Paths from %s to %s: %d\n\n",initialState, pattern, states.size());
        System.out.println(states);

        for (Module state : states){
            System.out.println();
            System.out.printf("Result: %s ---> %s\n", initialState, state);
            state.printTrace();
        }
    }

    public static void searchBlockStates(Module initialState) {
        List<Module> finalStates = initialState.searchBlockStates();

        System.out.printf("Block states from %s: %d\n\n", initialState, finalStates.size());
        System.out.println(finalStates);

        for (Module finalState : finalStates){
            System.out.println();
            System.out.printf("Result: %s ---> %s\n", initialState, finalState);
            finalState.printTrace();
        }
    }

    public static void testNext(String rabbitHopRepresentation){
        Module initialState = new RabbitHop(rabbitHopRepresentation);
        List<Module> nextStates = initialState.next();

        System.out.printf("Initial state: %s\n\n", initialState);
        System.out.printf("Next states:\n%s\n", nextStates);

        for (Module nextState : nextStates){
            System.out.println();
            System.out.printf("Result: %s ---> %s\n", initialState, nextState);
            nextState.printTrace();
        }
    }
}