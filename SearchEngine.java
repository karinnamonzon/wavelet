import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> searchList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.join(", ", searchList);
        } else if (url.getPath().equals("/search")) {
            ArrayList<String> forReturn = new ArrayList<>();
            String[] parameters = url.getQuery().split("=");
                    for(String s : searchList)
                    {
                        if(s.contains(parameters[1]))
                        {
                            forReturn.add(s);
                        }
                    }
                
                return String.join(", ", forReturn);
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    searchList.add(parameters[1]);
                    return parameters[1] + " has been added!";
                }
            }
            return "";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
