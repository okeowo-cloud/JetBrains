package budget;

import java.util.List;

public interface Strategy {

    String addPurchase(String item, double price);

    void displayPurchaseList(List<String> purchaseList);
}