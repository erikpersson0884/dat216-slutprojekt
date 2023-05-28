package imat;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    Label totalAmountLabel;
    @FXML
    Label totalPriceLabel;
    @FXML
    AnchorPane headerPane;

    @FXML
    AnchorPane progressBarAnchorPane;
    // Imageviews
    @FXML
    ImageView profileImageView;
    @FXML
    ImageView favoriteImageView;

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
    AnchorPane favoritePane;
    @FXML
    AnchorPane historyPane;
    @FXML
    AnchorPane profilePane;

    private String[] checkoutViews = {"betalning.fxml", "varukorg-utcheckning.fxml","delivery_time.fxml","receipt_page.fxml","leveransadress.fxml", "header.fxml"};
    private ArrayList<AnchorPane> checkoutViewPanes = new ArrayList<AnchorPane>();
    private ProgressBar progressBar = new ProgressBar(this,1);
    private final VarukorgUtcheckning varukorgUtcheckning = new VarukorgUtcheckning(this);
    private final Payment payment = new Payment(this);
    private final DeliveryTime deliveryTime = new DeliveryTime(this);
    private final Leveransadress leveransadress = new Leveransadress(this);
    private final History history = new History(this);
    private final header Header = new header(this);
    private final RecieptPage receiptPage = new RecieptPage(this);
    private final Favorites favorite = new Favorites(this);
    private final Profile profile = new Profile(this);

    private final Model model = Model.getInstance();
    private ShoppingCartUpdater shoppingCartUpdater = new ShoppingCartUpdater(this, varukorgUtcheckning, payment);

    private String decidedDeliveryTime;
    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private String totalNumberOfProducts;
    private String totalPriceOfProducts;

    public Button clickedButton;

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        initializeHashMap();
        //iMatDataHandler
        basketCheckoutPane.getChildren().add(varukorgUtcheckning);
        paymentPane.getChildren().add(payment);
        deliveryTimePane.getChildren().add(deliveryTime);
        addressPane.getChildren().add(leveransadress);
        headerPane.getChildren().add(Header);
        receiptPane.getChildren().add(receiptPage);
        favoritePane.getChildren().add(favorite);
        profilePane.getChildren().add(profile);
        //productListFlowPane.getChildren().add(progressBar);
        checkoutViewPanes.add(varukorgUtcheckning);
        checkoutViewPanes.add(deliveryTime);
        checkoutViewPanes.add(leveransadress);
        checkoutViewPanes.add(payment);
        historyPane.getChildren().add(history);
        searchbar.setOnKeyTyped(event -> handleKeyPress());
        updateTotalValues();
        model.getShoppingCart().fireShoppingCartChanged(null, true);
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

    public void updateTotalLabels(){
        totalPriceLabel.setText(getTotalPriceOfProducts());
        totalAmountLabel.setText(totalNumberOfProducts);

    }

    public void updateTotalValues(){
        int totalNumberOfProducts = getNumberOfAddedItems();
        this.totalNumberOfProducts = String.valueOf(totalNumberOfProducts);

        double totalPrice = iMatDataHandler.getShoppingCart().getTotal();
        DecimalFormat df = new DecimalFormat("#.##");
        String roundedValue = df.format(totalPrice);
        this.totalPriceOfProducts = roundedValue;
    }

        private int getNumberOfAddedItems(){
        List<ShoppingItem> addedItems = iMatDataHandler.getShoppingCart().getItems();
        int totalNumberOfItems = 0;
        for (ShoppingItem item: addedItems){
            totalNumberOfItems += item.getAmount();
        }
        return totalNumberOfItems;
    }

    public String getTotalNumberOfProducts(){
        return this.totalNumberOfProducts;
    }
    public String getTotalPriceOfProducts(){
        return this.totalPriceOfProducts;
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
        }
    }

    public void onCategoryButtonClicked(MouseEvent event){
        try {
            clickedButton.setStyle("");
        } catch (Exception e){
            System.out.println("No chosen time yet");
        }


        clickedButton = (Button) event.getSource();
        clickedButton.setStyle("-fx-background-color: #67E083;");
    }
    public void showPod(MouseEvent event) {
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.POD));
    }
    public void showBread(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.BREAD));
    }
    public void showBerry(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.BERRY));
    }
    public void showFruit(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.FRUIT));
    }
    public void showCitrusFruit(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.CITRUS_FRUIT));
    }
    public void showExoticFruit(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.EXOTIC_FRUIT));
    }
    public void showHotDrinks(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.HOT_DRINKS));
    }
    public void showColdDrinks(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.COLD_DRINKS));
    }
    public void showFish(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.FISH));
    }
    public void showMEAT(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.MEAT));
    }
    public void showVegetables(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT));
    }
    public void showCabbage(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.CABBAGE));
    }
    public void showDaires(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.DAIRIES));
    }
    @FXML public void showMelons(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.MELONS));
    }
    @FXML public void showFlourSugarSalt(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
    }
    @FXML public void showNutsAndSeeds(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.NUTS_AND_SEEDS));
    }
    @FXML public void showPasta(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.PASTA));
    }
    public void showPotatoAndRice(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.POTATO_RICE));
    }
    public void showRootVegetable(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.ROOT_VEGETABLE));
    }
    public void showSweets(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.SWEET));
    }
    public void showHerbs(MouseEvent event){
        onCategoryButtonClicked(event);
        updateProductList(iMatDataHandler.getProducts(ProductCategory.HERB));
    }
    public void showAll(javafx.scene.input.MouseEvent event){
        onCategoryButtonClicked(event);
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
    public void LoadHistory(){

    }
    public void updateFavorites(){
        favorite.loadFavorites();
    }
    public void updateHistory(){
        history.updateOrders();
    }
    public void updatePayment(){
        payment.updatePayment();
        profile.updatePayment();
    }
    public void updateAddress(){
        leveransadress.updateAddress();
        profile.updateAddress();
    }

}
