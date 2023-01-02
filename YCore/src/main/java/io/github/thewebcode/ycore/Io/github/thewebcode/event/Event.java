package io.github.thewebcode.ycore.Io.github.thewebcode.event;

import java.util.ArrayList;

public class Event {
    public Event call(){
        final ArrayList<io.github.thewebcode.ycore.Io.github.thewebcode.event.EventData> dataList = io.github.thewebcode.ycore.Io.github.thewebcode.event.EventManager.get(this.getClass());

        if(dataList != null){
            for(io.github.thewebcode.ycore.Io.github.thewebcode.event.EventData data : dataList){
                try{
                    data.target.invoke(data.source, this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return this;
    }
}
