package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class HistoryListItem extends AnchorPane {
    private MainViewController mainViewController;
    private ShoppingItem shoppingItem;
    private final Model model = Model.getInstance();
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private ImageView productImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;

    public HistoryListItem(MainViewController mainViewController, ShoppingItem shoppingItem) {
        this.mainViewController = mainViewController;
        this.shoppingItem = shoppingItem;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history_list_item.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception e) {
            System.out.println("Error loading history_list_item.fxml");
            e.printStackTrace();
        }
        setLabels();
    }
    private void setLabels(){
        productImageView.setImage(iMatDataHandler.getFXImage(shoppingItem.getProduct()));
        nameLabel.setText(shoppingItem.getProduct().getName());
        priceLabel.setText(String.format("%.2f", shoppingItem.getProduct().getPrice()) + " kr");
        quantityLabel.setText(String.format("%.2f", shoppingItem.getAmount()) + " st");
    }

}
