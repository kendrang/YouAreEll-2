package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import controllers.IdController;
import controllers.MessageController;
import controllers.TransactionController;
import models.Id;
import models.Message;
import youareell.YouAreEll;

// Simple Shell is a Console view for youareell.YouAreEll.
public class SimpleShell {


    public static void prettyPrint(String output) {
        // yep, make an effort to format things nicely, eh?
        System.out.println(output);
    }
    public static void main(String[] args) throws IOException {

        YouAreEll webber = new YouAreEll(new MessageController(), new IdController());
        TransactionController transCtrl = new TransactionController();
        IdController idCtrl = new IdController();
        MessageController msgCtrl = new MessageController();
        
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<String>();

            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("bye!");
                break;
            }

            //loop through to see if parsing worked
            for (int i = 0; i < commands.length; i++) {
                //System.out.println(commands[i]); //***check to see if parsing/split worked***
                list.add(commands[i]);

            }
            System.out.print(list); //***check to see if list was added correctly***
            history.addAll(list);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
//                if (list.contains("ids")) {
//                    String results = webber.get_ids();
//                    SimpleShell.prettyPrint(results);
//                    continue;
//                }
                if (list.contains("ids") && list.size() == 3) {
                    Id foundId = idCtrl.getIdByGit(list.get(2)) ;
                    if (foundId == null) {
                        idCtrl.postId(new Id(list.get(1), list.get(2)));
                    } else {
                        String newName = list.get(2);
                        foundId.setName(newName);
                        idCtrl.putId(foundId);
                    }
                    continue;
                }
                if (list.contains("ids")) {
                    ArrayList<Id> results = idCtrl.parseIds(webber.get_ids());
                    for(Id idFin : results){
                    SimpleShell.prettyPrint("USER ID : "+ idFin.getUserid()+ "\n" +"GITHUB : "+idFin.getGithub() + " NAME : " + idFin.getName() + "\n==========\n");
                    }
                    continue;
                }

                // messages
//                if (list.contains("messages")) {
//                    String results = webber.get_messages();
//                    SimpleShell.prettyPrint(results);
//                    continue;
//                }

                if (list.contains("send") && list.contains("to")) {
                    String[] messageArr = commandLine.split("'");
                    String messageText = messageArr[1];
                    String fromGitId = list.get(1);
                    String toGitId = list.get(list.size()-1);

                    Id fromid = idCtrl.getIdByGit(fromGitId);
                    Id toid = idCtrl.getIdByGit(toGitId);

                    Message message = new Message(messageText, fromGitId, toGitId);
                    msgCtrl.postMessage(fromid, toid, message);
                    continue;
                }
                if (list.contains("send")) {
                    String[] messageArr = commandLine.split("'");
                    String messageText = messageArr[1];
                    String fromGitId = list.get(1);

                    Id fromid = idCtrl.getIdByGit(fromGitId);

                    Message message = new Message(messageText, fromGitId , "");
                    msgCtrl.postMessage(fromid, null , message);
                    continue;
                }
                if (list.contains("messages") && list.size() == 3) {
                    Id myId = idCtrl.getIdByGit(list.get(1));
                    Id friendId = idCtrl.getIdByGit(list.get(2));
                    ArrayList<Message> messages = msgCtrl.getMessagesFromFriend(myId, friendId);
                    SimpleShell.prettyPrint(messages.toString());
                    continue;
                }
                if (list.contains("messages") && list.size() == 2) {
                    Id foundId = idCtrl.getIdByGit(list.get(1));
                    ArrayList<Message> messages = msgCtrl.getMessagesForId(foundId);
                    SimpleShell.prettyPrint(messages.toString());
                    continue;
                }
                if (list.contains("messages")) {
                    ArrayList<Message> messages = msgCtrl.getMessages();
                    msgCtrl.printMessages(messages);
                    continue;
                }
                // you need to add a bunch more.

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                // wait, wait, what curiousness is this?
                Process process = pb.start();

                //obtain the input stream
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //read output of the process
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
                br.close();


            }

            //catch ioexception, output appropriate message, resume waiting for input
            catch (IOException e) {
                System.out.println("Input Error, Please try again!");
            }
            // So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }


    }

}