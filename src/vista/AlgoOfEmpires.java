package vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlgoOfEmpires extends Application{

    public static void main(String[] args) {
    	launch(args);
    }

    @Override
    public void start(Stage primaryStage){
    	Scene escenaInicial = new Scene(new MenuVista(primaryStage));
    	primaryStage.setTitle("Algo of Empires");
    	primaryStage.getIcons().add(new Image("resources/images/ElementosMenu/icon.png"));
    	primaryStage.initStyle(StageStyle.UNDECORATED);
    	primaryStage.setFullScreen(true);
    	primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    	primaryStage.setMaximized(true);

        primaryStage.setScene(escenaInicial);
        primaryStage.show();
    }

}