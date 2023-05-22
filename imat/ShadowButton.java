package imat;

import javafx.scene.control.Button;

public class ShadowButton {
    public Button button;
    public Button shadow;
    public boolean active = false;
    public ShadowButton(Button button, Button buttonShadow){
        this.button = button;
        this.shadow = buttonShadow;
    }

}
