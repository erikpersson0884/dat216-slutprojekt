package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ItemInCart extends AnchorPane {
    private MainViewController parentController;
    private Product product;
    private ShoppingItem shoppingItem;
    private final Model model = Model.getInstance();
    @FXML
    Label ProductNameLabel;
    @FXML
    Label PriceLabel;
    @FXML
    ImageView ProductImageView;
    @FXML
    Label QuantityLabel;
    public ItemInCart(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vara-i-varukorg.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.shoppingItem = shoppingItem;
        this.product = shoppingItem.getProduct();
        initializeCard();
    }
    public void initializeCard(){
        ProductNameLabel.setText(product.getName());
        ProductImageView.setImage(model.getImage(product, 100, 100));
        PriceLabel.setText(String.valueOf(product.getPrice()));
        updateQuantity();
        //QuantityLabel.setText(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() + " " + product.getUnit());
    }

    public void updateQuantity(){
        //var quantity = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        var quantity = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        QuantityLabel.setText((int)quantity + " st");
        System.out.println((int)quantity + " st");
    }

    @FXML
    public void onRemoveButtonClick(){
        model.getShoppingCart().removeItem(shoppingItem);
        updateQuantity();
    }
    @FXML
    public void onPlusButtonClick(){
        if (model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() == 0){
            model.getShoppingCart().addItem(shoppingItem);
        } else {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() + 1);
        }
        //model.getShoppingCart().addItem(shoppingItem);
        updateQuantity();
    }
    @FXML
    public void onMinusButtonClick(){
        try {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() - 1);
            updateQuantity();
        } catch (Exception e) {
            System.out.println("Can't remove item");
        }
    }
}
