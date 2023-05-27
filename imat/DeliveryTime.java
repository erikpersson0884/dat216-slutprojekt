package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
            chosenDay = getDateWithOffset(0, clickedButton.getText());
        }
        else if (clickedButton == todayEvening){
            chosenDay = getDateWithOffset(0, clickedButton.getText());
        }
        else if (clickedButton == tomorrowMorning){
            chosenDay = getDateWithOffset(1, clickedButton.getText());
        }
        else if (clickedButton == tomorrowEvening){
            chosenDay = getDateWithOffset(1, clickedButton.getText());
        }
        else if (clickedButton == nextDayMorning){
            chosenDay = getDateWithOffset(2, clickedButton.getText());
        }
        else if (clickedButton == nextDayEvening){
            chosenDay = getDateWithOffset(2, clickedButton.getText());
        }
        mainViewController.setDeliveryTime(chosenDay);

    }

    public static String getDateWithOffset(int offset, String time) {
        LocalDate today = LocalDate.now();
        LocalDate desiredDate = today.plusDays(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM", new Locale("sv", "SE"));
        String date = desiredDate.format(formatter) + " " + time;
        return date;
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
