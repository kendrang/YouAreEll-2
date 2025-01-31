package youareell;

import controllers.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class YouAreEll {

    private MessageController msgCtrl;
    private IdController idCtrl;
    private String mainUrl = "http://zipcode.rocks:8085"; //initiallized  and assigned to this URL
    //variable declarations

    public YouAreEll (MessageController m, IdController j) {
        // used j because i seems awkward
        this.msgCtrl = m;
        this.idCtrl = j;
    }
    // setting the class variables equals to the variables being passed in
    //injecting dependencies


    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
//        YouAreEll urlhandler = new YouAreEll(new MessageController(), new IdController());
        TransactionController tra = new TransactionController();
        System.out.println(tra.makeURLCall("/ids", "GET", ""));
        System.out.println(tra.makeURLCall("/messages", "GET", ""));
    }

    public static String get_ids() {
        return new TransactionController().makeURLCall("/ids", "GET", "");
    }

    public static String get_messages() {
        return new TransactionController().makeURLCall("/messages", "GET", "");
    }


}
