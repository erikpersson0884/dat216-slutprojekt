package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Payment extends AnchorPane {
    MainViewController mainViewController;
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    AnchorPane progressBarAnchorPane;

    @FXML
    TextField cardNumberTextField;
    @FXML TextField ValidMonthTextField;
    @FXML TextField ValidYearTextField;
    @FXML TextField VerificationCodeTextField;
    @FXML TextField NameTextField;

    @FXML
    Label savedInfoLabel;

    @FXML Label totalPriceLabel;

    public Payment(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("betalning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        System.out.println("Payment");

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        progressBarAnchorPane.getChildren().add(new ProgressBar(mainViewController,3));
        savedInfoLabel.setVisible(false); // Hide the label initially
        setSavedInformation();

    }
    @FXML
    public void onNextButtonClick() {
        System.out.println("Next");
        placeOrder();
        mainViewController.changeCheckoutView(4);

    }
    @FXML
    public void onBackButtonClick(){
        System.out.println("Back");
        mainViewController.changeCheckoutView(2);
    }

    public void saveInformationOnClick(){
        iMatDataHandler.getCreditCard().setHoldersName(NameTextField.getText());
        iMatDataHandler.getCreditCard().setCardNumber(cardNumberTextField.getText());
        iMatDataHandler.getCreditCard().setValidMonth(Integer.parseInt(ValidMonthTextField.getText()));
        iMatDataHandler.getCreditCard().setValidYear(Integer.parseInt(ValidYearTextField.getText()));
        iMatDataHandler.getCreditCard().setVerificationCode(Integer.parseInt(VerificationCodeTextField.getText()));
        displaySavedInfoLabel(5);
    }

    public void setSavedInformation(){
        NameTextField.setText(iMatDataHandler.getCreditCard().getHoldersName());
        cardNumberTextField.setText(iMatDataHandler.getCreditCard().getCardNumber());
        ValidMonthTextField.setText(String.valueOf(iMatDataHandler.getCreditCard().getValidMonth()));
        ValidYearTextField.setText(String.valueOf(iMatDataHandler.getCreditCard().getValidYear()));
        VerificationCodeTextField.setText(String.valueOf(iMatDataHandler.getCreditCard().getVerificationCode()));
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

    public void updateTotalLabel(){
        totalPriceLabel.setText("Totalt: " + mainViewController.getTotalPriceOfProducts() + " Kr");
    }

    private void placeOrder(){
        iMatDataHandler.placeOrder();
        mainViewController.updateHistory();
    }
}
