package imat;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class Leveransadress extends AnchorPane{
    MainViewController mainViewController;
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    AnchorPane progressBarAnchorPane;

    @FXML
    Button saveInformationButton;
    @FXML
    TextField streetAdressTextField;
    @FXML TextField postalNumberTextField;
    @FXML Label savedInfoLabel;
    @FXML Label noAdressChosenLabel;

    public Leveransadress(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leveransadress.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        progressBarAnchorPane.getChildren().add(new ProgressBar(mainViewController,2));
        setSavedInformation();
        noAdressChosenLabel.setVisible(false); // Hide the label initially
        savedInfoLabel.setVisible(false); // Hide the label initially
        updateAddress();

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
        System.out.println(streetAdressTextField.getText());
        if (!streetAdressTextField.getText().isEmpty() & !postalNumberTextField.getText().isEmpty()) {
            mainViewController.changeCheckoutView(3);
        } else {
            displayLabel(4, noAdressChosenLabel);
            System.out.println("No adress chosen");

        }
    }
    @FXML
    public void onBackButtonClick(){
        System.out.println("Back");
        mainViewController.changeCheckoutView(1);
    }

    public void saveInformationOnClick(){
        mainViewController.iMatDataHandler.getCustomer().setAddress(streetAdressTextField.getText());
        mainViewController.iMatDataHandler.getCustomer().setPostCode(postalNumberTextField.getText());
        displaySavedInfoLabel(4);
        mainViewController.updateAddress();
    }

    public void setSavedInformation(){
        streetAdressTextField.setText(iMatDataHandler.getCustomer().getAddress());
        postalNumberTextField.setText(iMatDataHandler.getCustomer().getPostCode());
    }

    private void displaySavedInfoLabel(int seconds) {
        savedInfoLabel.setVisible(true); // Make the label visible

        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000); // Sleep for the specified duration in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Run on the JavaFX Application Thread to modify the UI
            javafx.application.Platform.runLater(() -> savedInfoLabel.setVisible(false));
        }).start();
    }

    public void updateAddress(){
        streetAdressTextField.setText(iMatDataHandler.getCustomer().getAddress());
        postalNumberTextField.setText(iMatDataHandler.getCustomer().getPostCode());
    }

}
