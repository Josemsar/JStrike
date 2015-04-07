
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


/**
 * Created by JoseMiguel on 07/04/2015.
 */
public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable torrentTable;
    private JTextArea searchText;
    private JButton searchButton;
    private JTabbedPane tabPanel;
    private JProgressBar searchProgressBar;
    private DefaultTableModel model;


    public MainForm() {
        super("JStrike");
        TorrentHandler torrentHandler = new TorrentHandler();
        model = new DefaultTableModel();

        searchProgressBar.setValue(50);
        setSize(1000, 1000);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        model.addColumn("Title");
        model.addColumn("Category");
        model.addColumn("Size");
        model.addColumn("Seeds");
        model.addColumn("Leeches");
        model.addColumn("Hash");


        torrentTable.setModel(model);

        torrentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                torrentHandler.callMagnet(torrentTable.getSelectedRow());
        });

        searchButton.addActionListener(e -> {
            new Thread(torrentHandler).start();
        });

        searchText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    new Thread(torrentHandler).start();
            }
        });

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    class TorrentHandler implements Runnable{
        private ArrayList<Torrent> torrentList;

        public TorrentHandler() {
            torrentList = new ArrayList<>();
        }

        void searchTorrents() {
            HttpsURLConnection connection = Connection.connect(searchText.getText());
            torrentList = JSONParser.parse(connection);
        }

        void updateTableResults() {
            torrentList.clear();
            model.getDataVector().removeAllElements();
            searchTorrents();

            if (torrentList != null) {
                for (Torrent torrent : torrentList)
                    model.addRow(new Object[]{torrent.getName(), torrent.getCategory(), torrent.getParsedSize(), torrent.getSeeds(), torrent.getLeeches(), torrent.getHash()});

                mainPanel.repaint();
            }

        }

        void callMagnet(int selectedTorrent) {
            torrentList.get(selectedTorrent).callMagnet();
        }

        @Override
        public void run() {
            updateTableResults();
        }
    }
}


