package models;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    private List<Message> messages=new ArrayList<>();

    public Messages(){
    }

    public Messages(List<Message> messages){
        this.messages=messages;
    }
}
