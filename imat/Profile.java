package imat;

import imat.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Test;

import java.io.IOException;

public class Profile extends AnchorPane {
    MainViewController mainViewController;
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private CreditCard creditCard;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private TextField cvvTextField;
    @FXML
    private TextField monthTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postCodeTextField;

    public Profile(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        System.out.println("Profile");

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        this.creditCard = iMatDataHandler.getCreditCard();
        updatePayment();
        updateAddress();
    }
    public void updatePayment() {
        cardNumberTextField.setText(creditCard.getCardNumber());
        cvvTextField.setText(String.valueOf(creditCard.getVerificationCode()));
        monthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        yearTextField.setText(String.valueOf(creditCard.getValidYear()));
        nameTextField.setText(creditCard.getHoldersName());
    }

    public void updateAddress() {
        addressTextField.setText(iMatDataHandler.getCustomer().getAddress());
        postCodeTextField.setText(iMatDataHandler.getCustomer().getPostCode());
    }
    @FXML
    private void saveCardInformation() {
        creditCard.setCardNumber(cardNumberTextField.getText());
        creditCard.setHoldersName(nameTextField.getText());
        try {
            creditCard.setVerificationCode(Integer.parseInt(cvvTextField.getText()));
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid CVV");
            System.out.println("Invalid CVV");
        }
        try {
            creditCard.setValidMonth(Integer.parseInt(monthTextField.getText()));
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid month");
            System.out.println("Invalid month");
        }
        try {
            creditCard.setValidYear(Integer.parseInt(yearTextField.getText()));
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid year");
            System.out.println("Invalid year");
        }
        try {
            mainViewController.updatePayment();
        } catch (Exception e) {
            System.out.println("Error updating payment");
        }
    }
    @FXML
    private void saveAddress() {
        iMatDataHandler.getCustomer().setAddress(addressTextField.getText());
        iMatDataHandler.getCustomer().setPostCode(postCodeTextField.getText());
        try {
            mainViewController.updateAddress();
        } catch (Exception e) {
            System.out.println("Error updating payment");
        }
    }
}

