package imat;

import imat.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class Profile extends AnchorPane {
    MainViewController mainViewController;

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
    }
}

