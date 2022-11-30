package Maude;

import RabbitHop.RabbitHop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public abstract class Module implements Cloneable{
    private List<String> trace;

    // Constructors
    public Module(){
        trace = new ArrayList<>();
    }

    public Module(List<String> trace){
        this.trace = trace;
    }

    // Public functionality

    public abstract void rewrite();

    public abstract void fairRewrite(Random random);

    public List<Module> search(String pattern) {
        List<Module> states = new ArrayList<>();
        for (Module child : next()) {
            states.addAll(child.search(pattern));
        }
        if (match(pattern))
            states.add(this);
        return states;
    }

    public List<Module> searchBlockStates(){
        List<Module> blockStates = new ArrayList<>();
        for(Module child : next()){
            blockStates.addAll(child.searchBlockStates());
        }
        if(blockStates.isEmpty())
            blockStates.add(this);
        return blockStates;
    }

    public void printTrace(){
        for(String action : this.trace)
            System.out.println(action);
    }

    public abstract List<Module> next();

    @Override
    public abstract RabbitHop clone();

    // Protected

    protected List<String> getTrace(){
        return new ArrayList<>(trace);
    }

    protected void addTrace(String message){
        trace.add(message);
    }

    // Private

    private boolean match(String pattern) {
        return Pattern.matches(pattern, this.toString());
    }
}
