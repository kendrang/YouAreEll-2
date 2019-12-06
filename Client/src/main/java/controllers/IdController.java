package controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Id;

public class IdController {

    private  TransactionController transactionController = new TransactionController();
    Id myId;

    public ArrayList<Id> getIds(String payload) {
        Type listType = new TypeToken<List<Id>>(){}.getType();
        return new Gson().fromJson(payload, listType);
    }

    public Id postId(Id id) {
        return null;
    }

    public Id putId(Id id) {
        return null;
    }
 
}