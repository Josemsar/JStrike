import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class Main {

    public static void main(String[] args) {

        String toSearch = "Rick and Morty";
        String inputLine;

        String title = "";
        String age;
        String size;
        int seeds;
        int leeches;


        try {
            toSearch = URLEncoder.encode(toSearch, "UTF-8").replace("+", "%20");
            URL data = new URL("https://getstrike.net/api/v2/torrents/search/?phrase=" + toSearch);
            HttpsURLConnection con = (HttpsURLConnection) data.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

           /* while ((inputLine = in.readLine()) != null){
                System.out.println(inputLine);
            }
*/
            inputLine = in.readLine();
            JSONArray jsonArray = new JSONArray(inputLine.substring(65));

            //jsonArray.

            System.out.println("Number of results: " + jsonArray.length());


            String arrays [] = new String[100];


            for (int i = 0; i < jsonArray.length(); ++i){
                title = jsonArray.getJSONObject(i).getString("torrent_title");
                seeds = jsonArray.getJSONObject(i).getInt("seeds");
                leeches = jsonArray.getJSONObject(i).getInt("leeches");

                String result = "Titulo: " + title + "- Seeders: " + seeds + "- Leechers: " + leeches;
                arrays[i] = result;

                System.out.println(result);

            }

            MainForm myForm = new MainForm(arrays);

            in.close();
            con.disconnect();




        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


    }
}
