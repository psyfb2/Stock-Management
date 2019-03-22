package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Demo extends Application{

    public void start(Stage theStage) throws IOException{
    	 new StagePage(theStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
