package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
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
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    Label ProductNameLabel;
    @FXML
    ImageView ProductImageView;
    @FXML
    Label PriceLabel;
    @FXML
    Label AmountLabel;
    @FXML
    Label AmountUnitLabel;
    @FXML
    ImageView FavouriteImageView;
    @FXML
    Label ComparisonPriceLabel;
    @FXML
    ImageView AddImageView;
    @FXML
    Label MinusImageView;


    Image favoriteImage = new Image(getClass().getClassLoader().getResourceAsStream("iMat/resources/favorite.png"));
    Image notFavoriteImage = new Image(getClass().getClassLoader().getResourceAsStream("iMat/resources/add-favorite.png"));


    public ProductListItem(Product product, MainViewController MainViewController) {
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

    public void initializeCard() {
        ProductNameLabel.setText(product.getName());
        PriceLabel.setText(String.valueOf(product.getPrice()) + product.getUnit());
        ComparisonPriceLabel.setText("Jmf-pris " + calculateComparisonPrice() + "kr/kg");
        ProductImageView.setImage(iMatDataHandler.getFXImage(product, 225, 225));


        if (iMatDataHandler.isFavorite(product)) {
            FavouriteImageView.setImage(favoriteImage);
        } else {
            FavouriteImageView.setImage(notFavoriteImage);
        }
    }

    public void changeFavouriteOnClick() {
        if (iMatDataHandler.isFavorite(product)) {
            System.out.println(iMatDataHandler.favorites());
            iMatDataHandler.removeFavorite(product);
            FavouriteImageView.setImage(notFavoriteImage);

        } else {
            iMatDataHandler.addFavorite(product);
            System.out.println(product.getName() + " is not favo");

            FavouriteImageView.setImage(favoriteImage);

        }
    }

    private String calculateComparisonPrice() {
        if (product.getUnit() == "kr/kg") {
            return String.valueOf(product.getPrice());
        } else {

            double min = 20.0;
            double max = 120.0;

            // Create an instance of the Random class
            Random random = new Random();

            // Generate a random double between min and max
            double newRandomValue = min + (max - min) * random.nextDouble();

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String newRandomValueString = decimalFormat.format(newRandomValue);

            return (newRandomValueString);
        }

    }

    public void updateQuantity() {
        //var quantity = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        var quantity = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        AmountLabel.setText((int) quantity + " st");
        System.out.println((int) quantity + " st");
    }
    public void updateQuantity(int quantity) {
        AmountLabel.setText((int) quantity + " st");
        System.out.println((int) quantity + " st");
    }

    @FXML
    public void onPlusButtonClick() {
        System.out.println("Plus");
        try {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount() + 1);
            updateQuantity();
            model.getShoppingCart().fireShoppingCartChanged(null,true);
        } catch (Exception e) {
            System.out.println("Can't increse amount");
            model.getShoppingCart().addItem(new ShoppingItem(product, 1));
        }
        updateQuantity();
        //parentController.updateShoppingCart();
        //model.getShoppingCart().fireShoppingCartChanged(null,true);
    }

    @FXML
    public void onMinusButtonClick() {
        System.out.println("Minus");
        double currentNumberOfItems = model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().getAmount();
        if (currentNumberOfItems > 0) {
            model.getShoppingCart().getItems().stream().filter(item -> item.getProduct().equals(product)).findFirst().get().setAmount(currentNumberOfItems - 1);
            model.getShoppingCart().fireShoppingCartChanged(null,true);
            if ( currentNumberOfItems == 1) {
                model.getShoppingCart().removeProduct(product);
            }
            try {
                updateQuantity();
            } catch (Exception e){
                updateQuantity(0);
            }
        }
        //parentController.updateShoppingCart();
       // model.getShoppingCart().fireShoppingCartChanged(null,true);
    }

    @FXML
    public void onLikeButtonClick(){
        if (iMatDataHandler.isFavorite(product)) {
            iMatDataHandler.removeFavorite(product);
            FavouriteImageView.setImage(notFavoriteImage);

        } else {
            iMatDataHandler.addFavorite(product);
            FavouriteImageView.setImage(favoriteImage);

        }
        parentController.updateFavorites();
    }
}