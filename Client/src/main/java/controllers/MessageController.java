package controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Id;
import models.Message;
import youareell.YouAreEll;

public class MessageController {


    private  TransactionController transactionController = new TransactionController();
    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public ArrayList<Message> getMessages() {
        Type listType = new TypeToken<List<Message>>(){}.getType();
        return new Gson().fromJson(YouAreEll.get_messages(), listType);
    }
    public ArrayList<Message> getMessagesForId(Id Id) {

        String targetId = Id.getGithub();
        String messages = transactionController.makeURLCall("/ids/" + targetId + "/messages", "GET", "");
        Type listType = new TypeToken<List<Message>>(){}.getType();
        return new Gson().fromJson(messages, listType);
    }
    public Message getMessageForSequence(String seq) {
        return null;
    }

    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        String targetId = myId.getGithub();
        String friendIdGit = friendId.getGithub();
        String messages = transactionController.makeURLCall("/ids/" + targetId + "/from/" + friendIdGit, "GET", "");
        Type listType = new TypeToken<List<Message>>(){}.getType();
        return new Gson().fromJson(messages, listType);
    }

    public Message postMessage(Id myId, Id toId, Message msg) {
        String payload = new Gson().toJson(msg);
        String myGitHub = myId.getGithub();
        transactionController.makeURLCall("/ids/" + myGitHub + "/messages", "POST", payload);
        return msg;
    }

    public void printMessages(List<Message> messages) {
        for (Message message : messages) {
            System.out.println(message.getSequence() + message.getTimestamp() + " " + message.getFromid() + ":" + message.getMessage());
        }
    }
}