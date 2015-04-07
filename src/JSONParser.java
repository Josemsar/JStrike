import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by JoseMiguel on 06/04/2015.
 */
public class JSONParser {

    public static ArrayList<Torrent> parse(HttpsURLConnection connection){
        ArrayList<Torrent> torrents = new ArrayList<>();
        String inputLine;

        BufferedReader inputData;
        try {
            inputData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            inputLine = inputData.readLine();
            JSONArray jsonArray = new JSONArray(inputLine.substring(inputLine.indexOf("[")));

            for (int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Torrent torrent = new Torrent(
                        jsonObject.getString("torrent_title"),
                        jsonObject.getString("torrent_category"),
                        jsonObject.getString("torrent_hash"),
                        jsonObject.getString("magnet_uri"),
                        jsonObject.getInt("seeds"),
                        jsonObject.getInt("leeches"),
                        jsonObject.getDouble("size")
                );
                torrents.add(torrent);
            }
            inputData.close();
            return torrents;

        } catch (IOException e) {
            e.printStackTrace();
        }

    return null;
    }
}
