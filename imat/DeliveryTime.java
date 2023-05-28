package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML Button nextButton;
    @FXML
    Label noTimeChosenLabel;

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
        noTimeChosenLabel.setVisible(false); // Make the label visible
    }


    public void pickDeliveryTime(javafx.scene.input.MouseEvent event){
        String chosenDay = "leveransdatum ej valt";

        try {
            clickedButton.setStyle("-fx-background-color: #B6F3A5;");
        } catch (Exception e){
            System.out.println("No chosen time yet");
        }


        clickedButton = (Button) event.getSource();
        clickedButton.setStyle("-fx-background-color: #8fce00;");

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

    private void displayLabel(int seconds, Label label) {
        label.setVisible(true); // Make the label visible

        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000); // Sleep for the specified duration in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Run on the JavaFX Application Thread to modify the UI
            javafx.application.Platform.runLater(() -> label.setVisible(false));
        }).start();
    }

    @FXML
    public void onNextButtonClick() {
        if (clickedButton != null) {
            mainViewController.changeCheckoutView(2);
        } else {
            displayLabel(4, noTimeChosenLabel);
            System.out.println("No time chosen");

        }
    }



    @FXML
    public void onBackButtonClick(){
        mainViewController.changeCheckoutView(0);
    }
}
