package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;

public class History extends AnchorPane {
    private MainViewController mainViewController;
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    private FlowPane historyFlowPane;
    public History(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        updateOrders();

        //historyFlowPane.getChildren().add(new HistoryItem(mainViewController,null));

    }
    public void updateOrders() {
        historyFlowPane.getChildren().clear();
        for (Order order : iMatDataHandler.getOrders()) {
            historyFlowPane.getChildren().add(new HistoryItem(mainViewController, order));
        }
    }
}
