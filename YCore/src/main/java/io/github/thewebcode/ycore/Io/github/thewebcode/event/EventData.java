package ycore.io.github.thewebcode.ycore.Io.github.thewebcode.event;

import java.lang.reflect.Method;

public class EventData {
    public final Object source;
    public final Method target;
    public final byte priority;

    public EventData(Object source, Method target, byte priority){
        this.source = source;
        this.target = target;
        this.priority = priority;
    }

}
