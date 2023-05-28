package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class header extends AnchorPane {
    MainViewController mainViewController;

    public header(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
    }
    @FXML
    public void onFavoriteClicked() {
        mainViewController.favoritePane.toFront();
    }
    @FXML
    public void onHistoryButtonClick() {
        mainViewController.showHistoryView();
    }

    @FXML
    public void onHomeButtonClick() {
        mainViewController.changeToMainView();
        System.out.println("Home button clicked");
    }
}


