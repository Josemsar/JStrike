
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private ArrayList<Torrent> torrentList;

    public MainForm() {
        super("JStrike");
        torrentList = new ArrayList<>();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Category");
        model.addColumn("Size");
        model.addColumn("Seeds");
        model.addColumn("Leeches");
        model.addColumn("Hash");


        torrentTable.setModel(model);

        ListSelectionModel listSelectionModel = torrentTable.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting())
                    torrentList.get(torrentTable.getSelectedRow()).callMagnet();
                    //System.out.println(torrentList.get(torrentTable.getSelectedRow()).getName());

            }
        });

        searchButton.addActionListener(e -> {

            torrentList.clear();
            model.getDataVector().removeAllElements();
            HttpsURLConnection connection = Connection.connect(searchText.getText());

            torrentList = JSONParser.parse(connection);

            if (torrentList != null) {
                for (Torrent torrent : torrentList)
                    model.addRow(new Object[]{torrent.getName(), torrent.getCategory(), torrent.getParsedSize(), torrent.getSeeds(), torrent.getLeeches(), torrent.getHash()});
            }
            mainPanel.repaint();
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
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    torrentList.clear();
                    model.getDataVector().removeAllElements();
                    HttpsURLConnection connection = Connection.connect(searchText.getText());

                    torrentList = JSONParser.parse(connection);

                    if (torrentList != null) {
                        for (Torrent torrent : torrentList)
                            model.addRow(new Object[]{torrent.getName(), torrent.getCategory(), torrent.getParsedSize(), torrent.getSeeds(), torrent.getLeeches(), torrent.getHash()});
                    }
                    mainPanel.repaint();
                }

            }
        });


        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        class SearchTorrents{
            public void searchTorrents (){

            }
        }
    }

}
