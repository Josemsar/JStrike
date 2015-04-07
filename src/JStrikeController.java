import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;


/**
 * Created by JoseMiguel on 07/04/2015.
 */
public class JStrikeController {
    @FXML
    private Button searchButton;

    @FXML
    private TextArea searchTextArea;

    @FXML
    private TableView torrentTable;

    @FXML
    private ProgressBar searchProgressBar;

    private ObservableList<Torrent> torrentData;


    public JStrikeController(){
        torrentData = FXCollections.observableArrayList();
    }


    @FXML
    private void initialize(){

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        titleColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn categoryColumn = new TableColumn("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory("category"));
        categoryColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn sizeColumn = new TableColumn("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory("size"));
        sizeColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn seedsColumn = new TableColumn("Seeds");
        seedsColumn.setCellValueFactory(new PropertyValueFactory("seeds"));
        seedsColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn leechesColumn = new TableColumn("Leeches");
        leechesColumn.setCellValueFactory(new PropertyValueFactory("leeches"));
        leechesColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn hashColumn = new TableColumn("Hash");
        hashColumn.setCellValueFactory(new PropertyValueFactory("hash"));
        hashColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        torrentTable.getColumns().addAll(titleColumn,
                categoryColumn,
                sizeColumn,
                seedsColumn,
                leechesColumn,
                hashColumn);


        torrentTable.setItems(torrentData);


    }

    @FXML
    public void searchButtonListener () {
        // Button was clicked, do something...
        //System.out.println("TEST");
        new Thread(new TorrentHandler()).start();
        System.out.println("TEST");
    }

    @FXML
    public void rowSelectListener () {
       ((Torrent) torrentTable.getSelectionModel().getSelectedItem()).callMagnet();
    }

    class TorrentHandler implements Runnable{
        private ArrayList<Torrent> torrentList;

        public TorrentHandler() {
            torrentList = new ArrayList<>();
        }

        void searchTorrents() {
            HttpsURLConnection connection = Connection.connect(searchTextArea.getText());
            torrentList = JSONParser.parse(connection);
        }

        void updateTableResults() {

            searchProgressBar.setProgress(.0);
            torrentList.clear();
            searchProgressBar.setProgress(.25);

            searchTorrents();

            searchProgressBar.setProgress(.50);
            torrentData.clear();

            searchProgressBar.setProgress(.75);
            if (torrentList != null) {
                torrentData.addAll(torrentList);
                   // model.addRow(new Object[]{torrent.getTitle(), torrent.getCategory(), torrent.getSize(), torrent.getSeeds(), torrent.getLeeches(), torrent.getHash()});



               // mainPanel.repaint();
            }
            searchProgressBar.setProgress(1);
        }

        @Override
        public void run() {
            updateTableResults();
        }
    }


}
