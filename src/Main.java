import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        String toSearch = "The Flash";


        HttpsURLConnection connection = Connection.connect(toSearch);
        ArrayList<Torrent> torrents = JSONParser.parse(connection);
        connection.disconnect();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainForm mainForm = new MainForm();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
