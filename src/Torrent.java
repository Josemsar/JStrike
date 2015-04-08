import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by JoseMiguel on 06/04/2015.
 */
public class Torrent {
    private String title;
    private String category;
    private String hash;
    private String magnet;
    private int seeds;
    private int leeches;
    private double size;

    public Torrent(String title, String category, String hash, String magnet, int seeds, int leeches, double size) {
        this.title = title;
        this.category = category;
        this.hash = hash;
        this.magnet = magnet;
        this.seeds = seeds;
        this.leeches = leeches;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getHash() {
        return hash;
    }

    public int getSeeds() {
        return seeds;
    }

    public int getLeeches() {
        return leeches;
    }

    public String getSize() {
        if (size / Math.pow(1024, 2) < 1024)
            return String.format("%.2f MB", (size / Math.pow(1024, 2)));
        return String.format("%.2f GB", (size / Math.pow(1024, 3)));
    }

    public void callMagnet() {
        try {
            Desktop.getDesktop().browse(new URI(magnet));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
