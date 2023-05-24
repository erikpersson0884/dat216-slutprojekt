package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;


public class ProductListItem extends AnchorPane {
    private MainViewController parentController;
    private Product product;
    private ShoppingItem shoppingItem;
    private final Model model = Model.getInstance();

    @FXML Label ProductNameLabel;
    @FXML ImageView ProductImageView;
    @FXML Label PriceLabel;
    @FXML Label AmountLabel;
    @FXML Label AmountUnitLabel, AmountUnitLabel2;
    @FXML ImageView LikeImageView;
    @FXML Label ComparisonPriceLabel;
    @FXML ImageView AddImageView;
    @FXML Label MinusImageView;



    public ProductListItem(Product product, MainViewController MainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        this.product = product;
        this.parentController = MainViewController;



        initializeCard();

    }
    public void initializeCard(){
        ProductNameLabel.setText(product.getName());
//        ProductImageView.setImage();
        PriceLabel.setText(String.valueOf(product.getPrice()));
        AmountUnitLabel.setText(product.getUnit());
        AmountUnitLabel2.setText(product.getUnit());
        ComparisonPriceLabel.setText(calculateComparisonPrice());

    }


    private String calculateComparisonPrice(){
//        This method does not have enough data to calculate the actual Comparision price, so for now it simply randomises a price
        double min = 20.0;
        double max = 120.0;

        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random double between min and max
        double newRandomValue = min + (max - min) * random.nextDouble();

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String newRandomValueString = decimalFormat.format(newRandomValue);

        return(newRandomValueString);

    }
    public void updateQuantity(){
        //var quantity = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        var quantity = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        AmountLabel.setText((int)quantity + " st");
        System.out.println((int)quantity + " st");
    }

    @FXML
    public void onPlusButtonClick(){
        System.out.println("Plus");
        try {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() + 1);
            updateQuantity();
        } catch (Exception e) {
            System.out.println("Can't increse amount");
            model.getShoppingCart().addItem(new ShoppingItem(product, 1));

        }
        /*if (model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() == 0){
            model.getShoppingCart().addItem(new ShoppingItem(product, 1));
        } else {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() + 1);
        }*/
        //model.getShoppingCart().addItem(shoppingItem);
        updateQuantity();
    }
    @FXML
    public void onMinusButtonClick(){
        System.out.println("Minus");
        try {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() - 1);
            updateQuantity();
        } catch (Exception e) {
            System.out.println("Can't remove item");
        }
    }
    @FXML
    public void onLikeButtonClick(){
        System.out.println("Like button clicked");
    }


}