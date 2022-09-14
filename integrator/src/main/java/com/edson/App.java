package com.edson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.edson.util.ConfigurationPathUtil;


/**
 * JavaFX App
 */
public class App extends Application {
    private ConfigurableApplicationContext applicationContext;

    private static Scene scene;

    //@TODO: inject spring
    /* 
    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(SpringInjector.class)
                .run(args);
    }
    */

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(ConfigurationPathUtil.viewPath + "mainScreen"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}