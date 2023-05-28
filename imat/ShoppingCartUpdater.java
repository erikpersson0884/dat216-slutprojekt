package imat;

import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

public class ShoppingCartUpdater implements ShoppingCartListener {
    MainViewController mainViewController;
    VarukorgUtcheckning varukorgUtcheckning;

    Payment payment;

    double totPrice = 0;
    private final ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
    public ShoppingCartUpdater(MainViewController mainViewController, VarukorgUtcheckning varukorgUtcheckning, Payment payment) {
        this.mainViewController = mainViewController;
        this.varukorgUtcheckning = varukorgUtcheckning;
        this.payment = payment;
        //shoppingCart.addShoppingCartListener(this);
        shoppingCart.addShoppingCartListener(e -> shoppingCartChanged(e));

    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        mainViewController.updateShoppingCart();
        varukorgUtcheckning.updateShoppingCart();
        mainViewController.updateTotalValues();

        mainViewController.updateTotalLabels();
        varukorgUtcheckning.updateTotalLabel();
        payment.updateTotalLabel();

    }
}
