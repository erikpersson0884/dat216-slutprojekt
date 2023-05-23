package imat;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;




public class MainViewController implements Initializable {

    @FXML
    FlowPane productListFlowPane;
    @FXML
    AnchorPane headerAnchorPane;
    @FXML
    AnchorPane HEADER;


    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        initializeHashMap();
//        HEADER.getChildren().add(headerAnchorPane);

    }

    private void initializeHashMap() {
        for (Product product : iMatDataHandler.getProducts()) {
            ProductListItem productListItem = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), productListItem);
        }
        updateProductList();
    }

    private void updateProductList() {
        productListFlowPane.getChildren().clear();
        for (Product product : iMatDataHandler.getProducts())
            productListFlowPane.getChildren().add(productListItemMap.get(product.getName()));
    }


    @FXML
    private Button nextButtonMain;

    @FXML
    public void openFile2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("betalning.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nextButtonMain.getScene().getWindow(); // Get the current window
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
