package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DeliveryTime extends AnchorPane {
    MainViewController mainViewController;
    @FXML
    AnchorPane progressBarAnchorPane;
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
