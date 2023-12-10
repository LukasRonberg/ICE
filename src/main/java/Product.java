

public class Product {
    public Product(String name, int weight, double price, String image, Enums.ProductType productType, Enums.StoreType storeType) {
        this.name = name;
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
        return "Name: " + name + ", weight: " + weight + ", price: " + price + ", product type: " + productType + ", store: " + storeType;
    }
}
