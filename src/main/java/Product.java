

public class Product {
    public Product(String name, int weight, double price, String image, Enums.ProductType productType, Enums.StoreType storeType) {
        char firstLetter = Character.toUpperCase(name.charAt(0));
        this.name = firstLetter+name.substring(1);
        this.weight = weight;
        this.price = price;
        this.image = image;
        this.productType = productType;
        this.storeType = storeType;
    }

    String name;

    int weight;

    double price;

    String image;

    Enums.ProductType productType = null;
    Enums.StoreType storeType = null;

    public double pricePerGram()
    {
        //return 0;
        return price/weight*100;
    }


    @Override
    public String toString() {
        return name + ", Weight: " + weight + "g, Price: " + price + "DKK, Price per 100g: " + (Math.round(pricePerGram() * 100.0) / 100.0) + /*", product type: " + productType +*/ "DKK, Store: " + storeType;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Enums.ProductType getProductType() {
        return productType;
    }

    public Enums.StoreType getStoreType() {
        return storeType;
    }
}
