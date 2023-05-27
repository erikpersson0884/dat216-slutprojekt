package imat;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;


public class MainViewController implements Initializable {

    @FXML
    FlowPane productListFlowPane;
    @FXML
    TextField searchbar;
    @FXML
    Label totalAmount;
    @FXML
    Label totalPrice;
    @FXML
    AnchorPane headerPane;

    @FXML
    AnchorPane progressBarAnchorPane;


    // AnchorPanes to add views to
    @FXML
    AnchorPane checkoutPane;
    @FXML
    AnchorPane paymentPane;
    @FXML
    AnchorPane deliveryTimePane;
    @FXML
    AnchorPane receiptPane;
    @FXML
    AnchorPane basketCheckoutPane;
    @FXML
    AnchorPane iMatAppPane;
    @FXML
    FlowPane shoppingCartFlowPane;
    @FXML
    AnchorPane addressPane;
    @FXML
    FlowPane CategoryFlowPane;
    @FXML
    AnchorPane historyPane;

    private String[] checkoutViews = {"betalning.fxml", "varukorg-utcheckning.fxml","delivery_time.fxml","receipt_page.fxml","leveransadress.fxml", "header.fxml"};
    private ArrayList<AnchorPane> checkoutViewPanes = new ArrayList<AnchorPane>();
    private ProgressBar progressBar = new ProgressBar(this,1);
    private final VarukorgUtcheckning varukorgUtcheckning = new VarukorgUtcheckning(this);
    private final Payment betalning = new Payment(this);
    private final DeliveryTime deliveryTime = new DeliveryTime(this);
    private final Leveransadress leveransadress = new Leveransadress(this);
    private final History history = new History(this);
    private final header Header = new header(this);
    private final RecieptPage receiptPage = new RecieptPage(this);

    private final Model model = Model.getInstance();
    private ShoppingCartUpdater shoppingCartUpdater = new ShoppingCartUpdater(this, varukorgUtcheckning);

    private String decidedDeliveryTime;
    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        initializeHashMap();
        basketCheckoutPane.getChildren().add(varukorgUtcheckning);
        paymentPane.getChildren().add(betalning);
        deliveryTimePane.getChildren().add(deliveryTime);
        addressPane.getChildren().add(leveransadress);
        headerPane.getChildren().add(Header);
        receiptPane.getChildren().add(receiptPage);
        //productListFlowPane.getChildren().add(progressBar);
        checkoutViewPanes.add(varukorgUtcheckning);
        checkoutViewPanes.add(deliveryTime);
        checkoutViewPanes.add(leveransadress);
        checkoutViewPanes.add(betalning);
        historyPane.getChildren().add(history);
        searchbar.setOnKeyTyped(event -> handleKeyPress());
        updateRightSidebar();

        checkoutViewPanes.add(receiptPage);
    }

    private void initializeHashMap() {
        for (Product product : model.getProducts()) {
            ProductListItem productListItem = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), productListItem);
        }
        updateProductList();
    }

    public void updateProductList() {
        productListFlowPane.getChildren().clear();
        for (Product product : model.getProducts())
            productListFlowPane.getChildren().add(productListItemMap.get(product.getName()));
    }

    public void updateProductList(List<Product> productList) {
        productListFlowPane.getChildren().clear();
        for (Product product : productList)
            productListFlowPane.getChildren().add(productListItemMap.get(product.getName()));
    }

// Methods for searchbar
    public void handleKeyPress(){
        String text = searchbar.getText();
        updateProductList(getProducts(text));
    }
    public List<Product> getProducts(String s){
        return  model.findProducts(s);
    }

    // Methods for right sidebar

    public void updateRightSidebar(){
        totalAmount.setText(String.valueOf(iMatDataHandler.getProducts().size()));
    }



    @FXML
    private Button nextButtonMain;

    @FXML
    public void openFile2() {
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("varukorg-utcheckning.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nextButtonMain.getScene().getWindow(); // Get the current window
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //changeCheckoutView(0);
        basketCheckoutPane.toFront();
    }

    @FXML
    public void changeCheckoutView(int index) {
        checkoutViewPanes.get(index).toFront();
        switch (index){
            case(0):
                basketCheckoutPane.toFront();
                break;
            case(1):
                deliveryTimePane.toFront();
                break;
            case(2):
                addressPane.toFront();
                break;
            case(3):
                paymentPane.toFront();
                break;
            case(4):
                receiptPane.toFront();
                break;
        }
    }
    public void changeToMainView(){
        iMatAppPane.toFront();
    }

    public void updateShoppingCart(){
        shoppingCartFlowPane.getChildren().clear();
        for (ShoppingItem shoppingItem : model.getShoppingCart().getItems()) {
            shoppingCartFlowPane.getChildren().add(new ItemInCart(shoppingItem));
            System.out.println(shoppingItem.getProduct().getName() + " added to shopping cart");
        }
    }

    public void showPod(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.POD));
    }
    public void showBread(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.BREAD));
    }
    public void showBerry(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.BERRY));
    }
    public void showFruit(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.FRUIT));
    }
    public void showCitrusFruit(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.CITRUS_FRUIT));
    }
    public void showExoticFruit(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.EXOTIC_FRUIT));
    }
    public void showHotDrinks(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.HOT_DRINKS));
    }
    public void showColdDrinks(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.COLD_DRINKS));
    }
    public void showFish(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.FISH));
    }
    public void showMEAT(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.MEAT));
    }
    public void showVegetables(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT));
    }
    public void showCabbage(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.CABBAGE));
    }
    public void showDaires(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.DAIRIES));
    }
    public void showMelons(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.MELONS));
    }
    public void showFlourSugarSalt(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
    }
    public void showNutsAndSeeds(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.NUTS_AND_SEEDS));
    }
    public void showPasta(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.PASTA));
    }
    public void showPotatoAndRice(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.POTATO_RICE));
    }
    public void showRootVegetable(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.ROOT_VEGETABLE));
    }
    public void showSweets(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.SWEET));
    }
    public void showHerbs(){
        updateProductList(iMatDataHandler.getProducts(ProductCategory.HERB));
    }
    public void showAll(){
        updateProductList();
    }

    public String getDeliveryTime() {
        return decidedDeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.decidedDeliveryTime = deliveryTime;
        updateDeliveryTimeLabel();
    }

    public void updateDeliveryTimeLabel(){
        receiptPage.deliveryTimeLabel.setText("Ordern kommer att levereras: " + decidedDeliveryTime);
    }

    public void showHistoryView(){
        historyPane.toFront();
    }
}
