package ycore.io.github.thewebcode.ycore.Io.github.thewebcode.event;

public class EventCancelable extends Event {
    private boolean cancelled  = false;

    public boolean isCancelled(){
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

}
