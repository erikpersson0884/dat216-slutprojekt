package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

public class ProgressBar extends AnchorPane {
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private Button button4;
    @FXML private Rectangle rect1;
    @FXML private Rectangle rect2;
    @FXML private Rectangle rect3;

    ArrayList<Button> buttons = new ArrayList<Button>();;
    iMatApp iMatApp;
    MainViewController mainViewController;


    public ProgressBar(MainViewController mainViewController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("progress_bar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        button1.getStyleClass().removeAll("button_inactive");
        button1.getStyleClass().add("button_active");

    }

    public void onButtonClick(ActionEvent event){
        try {
            Button clicked = ((Button) event.getSource());
            var index = buttons.indexOf(clicked);
            buttonPressedAction(index,buttons);
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    private void buttonPressedAction(int index,ArrayList<Button>buttons){
        if(index >= 0){
            for (int i = 0; i < buttons.size(); i++){
                if(i<=index){
                    buttons.get(i).getStyleClass().clear();
                    buttons.get(i).getStyleClass().add("button");
                    buttons.get(i).getStyleClass().add("button_active");
                } else if (i>index){
                    buttons.get(i).getStyleClass().clear();
                    buttons.get(i).getStyleClass().add("button");
                    buttons.get(i).getStyleClass().add("button_inactive");

                }
            }
        }
        rect1.getStyleClass().clear();
        rect1.getStyleClass().add("bar");
        rect1.getStyleClass().add("bar_inactive");
        rect2.getStyleClass().clear();
        rect2.getStyleClass().add("bar");
        rect2.getStyleClass().add("bar_inactive");
        rect3.getStyleClass().clear();
        rect3.getStyleClass().add("bar");
        rect3.getStyleClass().add("bar_inactive");
        if (index>=1) {
            rect1.getStyleClass().clear();
            rect1.getStyleClass().add("bar");
            rect1.getStyleClass().add("bar_active");
        } if (index >= 2) {
            rect2.getStyleClass().clear();
            rect2.getStyleClass().add("bar");
            rect2.getStyleClass().add("bar_active");
        } if (index >= 3) {
            rect3.getStyleClass().clear();
            rect3.getStyleClass().add("bar");
            rect3.getStyleClass().add("bar_active");
        }
    }
}
