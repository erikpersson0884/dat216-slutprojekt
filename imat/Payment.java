package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class Payment extends AnchorPane {
    MainViewController mainViewController;
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    AnchorPane progressBarAnchorPane;
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

    private void placeOrder(){
        iMatDataHandler.placeOrder();
    }
}
