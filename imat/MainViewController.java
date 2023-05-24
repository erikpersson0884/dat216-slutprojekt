package imat;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;


public class MainViewController implements Initializable {

    @FXML
    FlowPane productListFlowPane;
    @FXML
    AnchorPane headerAnchorPane;
    @FXML
    AnchorPane HEADER;

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
    SplitPane iMatAppPane;
    @FXML
    FlowPane shoppingCartFlowPane;


    private String[] checkoutViews = {"betalning.fxml", "varukorg-utcheckning.fxml","delivery_time.fxml", "receipt_page.fxml"};
    private ArrayList<AnchorPane> checkoutViewPanes = new ArrayList<AnchorPane>();
    private ProgressBar progressBar = new ProgressBar(this,1);
    private final VarukorgUtcheckning varukorgUtcheckning = new VarukorgUtcheckning(this);
    private final Payment betalning = new Payment(this);
    private final DeliveryTime deliveryTime = new DeliveryTime(this);
    //private final ReceiptPage receiptPage = new ReceiptPage(this);

    private final Model model = Model.getInstance();
    private ShoppingCartUpdater shoppingCartUpdater = new ShoppingCartUpdater(this, varukorgUtcheckning);


    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        initializeHashMap();
//        HEADER.getChildren().add(headerAnchorPane);
        basketCheckoutPane.getChildren().add(varukorgUtcheckning);
        paymentPane.getChildren().add(betalning);
        deliveryTimePane.getChildren().add(deliveryTime);
        //receiptPane.getChildren().add(receiptPage);
        //productListFlowPane.getChildren().add(progressBar);
        checkoutViewPanes.add(varukorgUtcheckning);
        checkoutViewPanes.add(deliveryTime);
        checkoutViewPanes.add(betalning);



        //checkoutViewPanes.add(receiptPage);
    }

    private void initializeHashMap() {
        for (Product product : model.getProducts()) {
            ProductListItem productListItem = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), productListItem);
        }
        updateProductList();
    }

    private void updateProductList() {
        productListFlowPane.getChildren().clear();
        for (Product product : model.getProducts())
            productListFlowPane.getChildren().add(productListItemMap.get(product.getName()));
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
                paymentPane.toFront();
                break;
            case(3):
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
}
