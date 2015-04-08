import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by JoseMiguel on 06/04/2015.
 */
public class Connection {
    public static HttpsURLConnection connect(String searchString) {
        String encodedSearchString;

        try {
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8").replace("+", "%20");
            URL data = new URL("https://getstrike.net/api/v2/torrents/search/?phrase=" + encodedSearchString);
            HttpsURLConnection con = (HttpsURLConnection) data.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            return con;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
