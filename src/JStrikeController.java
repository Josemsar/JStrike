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
    private TableView<Torrent> torrentTable;

    @FXML
    private ProgressBar searchProgressBar;

    private ObservableList<Torrent> torrentData;


    public JStrikeController() {
        torrentData = FXCollections.observableArrayList();
    }


    @FXML
    private void initialize() {

        TableColumn<Torrent, Object> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn<Torrent, Object> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn<Torrent, Object> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        sizeColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn<Torrent, Object> seedsColumn = new TableColumn<>("Seeds");
        seedsColumn.setCellValueFactory(new PropertyValueFactory<>("seeds"));
        seedsColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn<Torrent, Object> leechesColumn = new TableColumn<>("Leeches");
        leechesColumn.setCellValueFactory(new PropertyValueFactory<>("leeches"));
        leechesColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        TableColumn<Torrent, Object> hashColumn = new TableColumn<>("Hash");
        hashColumn.setCellValueFactory(new PropertyValueFactory<>("hash"));
        hashColumn.prefWidthProperty().bind(torrentTable.widthProperty().divide(6));

        torrentTable.getColumns().addAll(titleColumn,
                categoryColumn,
                sizeColumn,
                seedsColumn,
                leechesColumn,
                hashColumn);


        torrentTable.setItems(torrentData);

        torrentTable.setPlaceholder(new Label(""));


    }

    @FXML
    public void searchButtonListener() {
        new Thread(new TorrentHandler()).start();
    }

    @FXML
    public void rowSelectListener() {

        if (torrentTable.getSelectionModel().getSelectedItem() != null)
            (torrentTable.getSelectionModel().getSelectedItem()).callMagnet();
    }

    class TorrentHandler implements Runnable {
        private ArrayList<Torrent> torrentList;

        public TorrentHandler() {
            torrentList = new ArrayList<>();
        }

        void searchTorrents() {
            if (!searchTextArea.getText().isEmpty()) {
                HttpsURLConnection connection = Connection.connect(searchTextArea.getText());
                torrentList = JSONParser.parse(connection);
            }
        }

        void updateTableResults() {

            searchProgressBar.setProgress(.0);
            torrentList.clear();
            searchProgressBar.setProgress(.25);

            searchTorrents();

            searchProgressBar.setProgress(.50);
            torrentData.clear();

            searchProgressBar.setProgress(.75);
            if (torrentList != null)
                torrentData.addAll(torrentList);

            searchProgressBar.setProgress(1);
        }

        @Override
        public void run() {
            updateTableResults();
        }
    }


}
