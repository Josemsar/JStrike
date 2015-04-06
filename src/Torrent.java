/**
 * Created by JoseMiguel on 06/04/2015.
 */
public class Torrent {
    private String name;
    private String category;
    private String hash;
    private int seeds;
    private int leeches;
    private double size;

    public Torrent(String name, String category, String hash, int seeds, int leeches, double size) {
        this.name = name;
        this.category = category;
        this.hash = hash;
        this.seeds = seeds;
        this.leeches = leeches;
        this.size = size;
    }

    public String getName() {

        return name;
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

    public double getSize() {
        return size;
    }

    public String getParsedSize (){
        if (size / Math.pow(1024, 2) < 1024)
            return String.format("%.4f MB", (size / Math.pow(1024, 2)));
        return String.format("%.4f GB", (size / Math.pow(1024, 3)));

    }
}
