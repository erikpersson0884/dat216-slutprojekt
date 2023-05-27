package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeliveryTime extends AnchorPane {
    MainViewController mainViewController;
    @FXML
    AnchorPane progressBarAnchorPane;

    @FXML
    Button todayMorning;
    @FXML
    Button todayEvening;
    @FXML
    Button tomorrowMorning;
    @FXML
    Button tomorrowEvening;
    @FXML
    Button nextDayMorning;
    @FXML
    Button nextDayEvening;

    Button clickedButton;

    public DeliveryTime(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("delivery_time.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        progressBarAnchorPane.getChildren().add(new ProgressBar(mainViewController,1));
    }


    public void pickDeliveryTime(javafx.scene.input.MouseEvent event){
        String chosenDay = "leveransdatum ej valt";

        try {
            clickedButton.setStyle("-fx-background-color: #B6F3A5;");
        } catch (Exception e){
            System.out.println("No chosen time yet");
        }


        clickedButton = (Button) event.getSource();
        clickedButton.setStyle("-fx-background-color: #00FF00;");

        if (clickedButton == todayMorning){
            chosenDay = "Idag 11-12";
        }
        else if (clickedButton == todayEvening){
            chosenDay = "Idag 13-14";
        }
        else if (clickedButton == tomorrowMorning){
            chosenDay = "Imorgon 13-14";
        }
        else if (clickedButton == tomorrowEvening){
            chosenDay = "Imorgon 13-14";
        }
        else if (clickedButton == nextDayMorning){
            chosenDay = "Övermorgon 13-14";
        }
        else if (clickedButton == nextDayEvening){
            chosenDay = "Övermorgon 13-14";
        }
        System.out.println(chosenDay);
    }

    @FXML
    public void onNextButtonClick() {
        System.out.println("Next");
        mainViewController.changeCheckoutView(2);
    }
    @FXML
    public void onBackButtonClick(){
        System.out.println("Back");
        mainViewController.changeCheckoutView(0);
    }
}
