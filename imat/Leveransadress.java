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
    TextField streesAdressTextField;
    @FXML TextField postalNumberTextField;
    @FXML Label savedInfoLabel;

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
        savedInfoLabel.setVisible(false); // Hide the label initially

    }
    @FXML
    public void onNextButtonClick() {
        System.out.println("Next");
        mainViewController.changeCheckoutView(3);
    }
    @FXML
    public void onBackButtonClick(){
        System.out.println("Back");
        mainViewController.changeCheckoutView(1);
    }

    public void saveInformationOnClick(){
        mainViewController.iMatDataHandler.getCustomer().setAddress(streesAdressTextField.getText());
        mainViewController.iMatDataHandler.getCustomer().setPostAddress(postalNumberTextField.getText());
        displaySavedInfoLabel(4);
    }

    public void setSavedInformation(){
        streesAdressTextField.setText(iMatDataHandler.getCustomer().getAddress());
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

}
