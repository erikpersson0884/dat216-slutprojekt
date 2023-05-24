package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
public class VarukorgUtcheckning extends AnchorPane {
    @FXML
    AnchorPane progressBarAnchorPane;
    @FXML
    FlowPane shoppingCartFlowPane;
    private final Model model = Model.getInstance();

    private MainViewController mainViewController;

    public VarukorgUtcheckning(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("varukorg-utcheckning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        progressBarAnchorPane.getChildren().add(new ProgressBar(mainViewController,0));
    }
    @FXML
    public void onBackButtonClick(){
        System.out.println("Back");
        mainViewController.changeToMainView();
    }
    @FXML
    public void stuff(){
        progressBarAnchorPane.getStyleClass().setAll("red_border");
    }

    public void updateShoppingCart(){
        shoppingCartFlowPane.getChildren().clear();
        for (ShoppingItem shoppingItem : model.getShoppingCart().getItems()) {
            shoppingCartFlowPane.getChildren().add(new ItemInCart(shoppingItem));
            System.out.println(shoppingItem.getProduct().getName() + " added to shopping cart");
        }
    }
}
