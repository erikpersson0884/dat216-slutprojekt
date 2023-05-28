package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class HistoryItem extends AnchorPane {
    private MainViewController mainViewController;
    private final Model model = Model.getInstance();
    private Order order;
    @FXML
    private AnchorPane openAnchorPane;
    @FXML
    private ImageView openArrowImageView;
    @FXML
    private ImageView closeArrowImageView;
    @FXML
    private Label dateLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    public HistoryItem(MainViewController mainViewController, Order order) {
        this.mainViewController = mainViewController;
        this.order = order;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
       closeArrowImageView.setVisible(false);
        setLabels();
        onOpenButtonClick();
        populateList();
    }
    @FXML
    public void onOpenButtonClick(){
        if (openAnchorPane.isVisible()){
            openArrowImageView.setVisible(true);
            closeArrowImageView.setVisible(false);
            openAnchorPane.setVisible(false);
        } else {
            openArrowImageView.setVisible(false);
            closeArrowImageView.setVisible(true);
            openAnchorPane.setVisible(true);
        }

        System.out.println("Open");
    }


    private void setLabels(){
        var splitDate = order.getDate().toString().split(" ");
        dateLabel.setText(splitDate[1] + " " + splitDate[2] + " " + splitDate[5]);
        int totalPrice = 0;
        for(ShoppingItem item : order.getItems()){
            totalPrice += item.getTotal();
        }
        priceLabel.setText(totalPrice + " kr");
        quantityLabel.setText(order.getItems().size() + " st");
    }

    private void populateList(){
        for(ShoppingItem item : order.getItems()){
            openAnchorPane.getChildren().add(new HistoryListItem(mainViewController, item));
        }
    }
}
