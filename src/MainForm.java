import javax.swing.*;

/**
 * Created by JoseMiguel on 06/04/2015.
 */
public class MainForm extends JFrame{
    private JList list1;
    private JPanel panel1;

    public MainForm(String[] listModel){
        super("JStrike");
        list1.setListData(listModel);


        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);



    }
}
