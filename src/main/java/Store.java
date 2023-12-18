import java.util.ArrayList;

public class Store{

    public String storeName;
    public double totalStorePrice = 0;
    ArrayList<Product> products = new ArrayList<>();

    public Store(String name) {
        //this.totalStorePrice = totalStorePrice;
        this.storeName = name;
        this.products = new ArrayList<>();
    }

    public void calcTotalStorePrice(){
        for (Product p: products) {
            totalStorePrice += p.getPricePerHundredGrams();
        }
    }

    public void calcTotalPricePerHundred(){

    }

    public void addToProducts(Product product){
        products.add(product);
    }

    public double getTotalStorePrice(){
        return totalStorePrice;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    @Override
    public String toString() {
        String prettyName = storeName;

        String prettyPricePerHundred = (Math.round(totalStorePrice * 100.0) / 100.0) + " DKK";
        String prettyProductsInStore = getProducts().size()+"";

        for (int i = prettyName.length(); i < 20; i++) {
            prettyName += " ";
        }
        for (int i = prettyPricePerHundred.length(); i < 20; i++) {
            prettyPricePerHundred += " ";
        }
        for (int i = prettyProductsInStore.length(); i < 20; i++) {
            prettyProductsInStore += " ";
        }


        return "\u001B[35mName: \u001B[0m"+prettyName + "\u001B[32mPrice per 100g: \u001B[0m" + prettyPricePerHundred +
                "\u001b[34mWares Available: \u001B[0m" + prettyProductsInStore;

    }
}