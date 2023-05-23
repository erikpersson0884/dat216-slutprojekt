//package imat;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.Label;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import se.chalmers.cse.dat216.project.Product;
//
//import java.io.IOException;
//
//public class header extends AnchorPane {
//    private MainViewController parentController;
//    private Header header;
//
//    @FXML AnchorPane headerAnchorPane;
//    @FXML ImageView clockImageView;
//    @FXML ImageView heartImageView;
//    @FXML ImageView profileImageView;
//
//    public header(Header header, MainViewController MainViewController){
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//
//        try {
//            fxmlLoader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//
//        this.header = header;
//        this.parentController = MainViewController;
//
//
//    }
//
//
//
//}
