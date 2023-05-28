package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.concurrent.Flow;

public class Favorites extends AnchorPane {
    MainViewController mainViewController;
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    private FlowPane favoritesFlowPane;

    public Favorites(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("favoriter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        System.out.println("Favoriter");

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        loadFavorites();
    }

    public void loadFavorites() {
        favoritesFlowPane.getChildren().clear();
        iMatDataHandler.favorites().forEach(favorite -> {
            favoritesFlowPane.getChildren().add(new ProductListItem(favorite,mainViewController));
        });
    }
}
