import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                launch(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("JStrike.fxml"));
        GridPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("JStrike");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

