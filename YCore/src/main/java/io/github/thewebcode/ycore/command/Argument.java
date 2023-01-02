package io.github.thewebcode.ycore.command;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Argument {
    private ArrayList<Object> values;

    public Argument(ArrayList<Object> values) {
        this.values = values;
    }

    public Argument(Object... values){
        this.values = new ArrayList<>();

        this.values.addAll(Arrays.asList(values));
    }

    public Argument(String... values){
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
    }

    public Argument(int... values){
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
    }

    public Argument(Object o){
        this.values = new ArrayList<>();
        this.values.add(o);
    }

    public ArrayList<Object> getValues() {
        return values;
    }

    public static HashMap<Integer, Argument> asMap(Argument... arguments){
        HashMap<Integer, Argument> map = new HashMap<>();
        for(int i = 0; i < arguments.length; i++){
            map.put(i, arguments[i]);
        }
        return map;
    }
}
