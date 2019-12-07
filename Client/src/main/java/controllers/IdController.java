package controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Id;
import youareell.YouAreEll;

public class IdController {


    private  TransactionController transactionController = new TransactionController();
    Id myId;



    public ArrayList<Id> parseIds(String payload) {
        Type listType = new TypeToken<List<Id>>(){}.getType();
        return new Gson().fromJson(payload, listType);
    }

    public Id postId(Id id) {
        String payload = new Gson().toJson(id);
        transactionController.makeURLCall("/ids", "POST", payload);
        return id;
    }

    public Id putId(Id id) {
        String payload = new Gson().toJson(id);
        transactionController.makeURLCall("/ids","PUT", payload);
        return id;
    }

public Id getIdByGit(String gitId) {
    ArrayList<Id> idList = parseIds(YouAreEll.get_ids());
    Id foundId = null;

    for (Id idInList : idList) {
        if (gitId.equals(idInList.getGithub())) {
            foundId = idInList;
            break;
        }
    } return foundId;
}
}