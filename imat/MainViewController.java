
package imat;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class MainViewController implements Initializable {

    @FXML FlowPane productListFlowPane;


    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        updateProductList();
    }



    private void updateProductList(){
        productListFlowPane.getChildren().clear();
        for (Product product : iMatDataHandler.getProducts())
            productListFlowPane.getChildren().add(product);
    }

}