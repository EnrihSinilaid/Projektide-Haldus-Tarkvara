package ee.erp.haldur.ui;

import java.io.IOException;
import java.net.URL;

import ee.erp.haldur.DAO.projektideHaldusDAO;
import ee.erp.haldur.ui.controllers.lisaSisuController;
import ee.erp.haldur.ui.controllers.uusProjectController;
import ee.erp.haldur.ui.controllers.vaataSisuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

public class HaldurUI extends Application{

    private projektideHaldusDAO dao = new projektideHaldusDAO();


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException, ParseException {
        this.dao.loe();

        Tab uusProjekt = new Tab();
        uusProjekt.setText("Uus projekt");
        uusProjekt.setClosable(false);
        uusProjekt.setContent(this.loadControls("/uusProjekt.fxml", new uusProjectController(this.dao)));

        Tab lisaSisu = new Tab();
        lisaSisu.setText("Lisa projektile sisu");
        lisaSisu.setClosable(false);
        lisaSisu.setContent(this.loadControls("/lisaSisu.fxml", new lisaSisuController(this.dao)));

        Tab vaataSisu = new Tab();
        vaataSisu.setText("Vaata projekti");
        vaataSisu.setClosable(false);
        vaataSisu.setContent(this.loadControls("/vaataSisu.fxml", new vaataSisuController(this.dao)));

        Group root = new Group();
        Scene scene = new Scene(root, 800.0D, 600.0D, Color.WHITE);
        scene.getStylesheets().add(this.getClass().getResource("/DefaultTheme.css").toExternalForm());
        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(new TabPane(new Tab[]{uusProjekt, lisaSisu, vaataSisu}));

        root.getChildren().add(borderPane);
        primaryStage.setTitle("Projekti haldus by Enrih Sinilaid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node loadControls(String fxml, Initializable controller) throws IOException, ParseException {
        URL resource = this.getClass().getResource(fxml);
        if (resource == null) {
            throw new IllegalArgumentException(fxml + " not found");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setController(controller);
            return (Node)fxmlLoader.load();
        }
    }
}
