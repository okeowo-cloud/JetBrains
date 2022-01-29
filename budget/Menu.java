package budget;

import java.util.List;

public class Menu implements Strategy {

    @Override
    public String addPurchase(String item, double price) {
        return String.format("%s $%.2f", item, price);
    }

    @Override
    public void displayPurchaseList(List<String> purchaseList) {
        for (String s: purchaseList) {
            System.out.println(s);
        }
    }

}