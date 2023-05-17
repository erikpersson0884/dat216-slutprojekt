package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;


public class ProductListItem extends AnchorPane {
    private MainViewController parentController;
    private Product product;

    @FXML Label ProductNameLabel;
    @FXML Label PriceLabel;
    @FXML Label AmountLabel;
    @FXML Label AmountUnitLabel;
    @FXML ImageView ProductImageView;
    @FXML ImageView LikeImageView;
    @FXML ImageView AddImageView;
    @FXML Label MinusImageView;





    public ProductListItem(Product product, MainViewController MainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = MainViewController;



//        ProductImageView.setImage(product.getImageName());
    }
}