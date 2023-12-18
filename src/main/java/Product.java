public class Product {

    private String name = "";

    private int weight = 0;

    private double price = 0;

    private String image = "";

    Enums.ProductType productType = null;
    Enums.StoreType storeType = null;



    public Product(String name, int weight, double price, String image, Enums.ProductType productType, Enums.StoreType storeType)
    {
        this.name = name.toLowerCase();
        this.weight = weight;
        this.price = price;
        this.image = image;
        this.productType = productType;
        this.storeType = storeType;
    }

    public double getPricePerHundredGrams()
    {
        //return 0;
        return price/weight*100;
    }
    @Override
    public String toString()
    {
        char firstLetter = name.charAt(0);
        String prettyName = Character.toUpperCase(firstLetter) + name.substring(1);
        String prettyPrice = price + " DKK";
        String prettyPricePerHundred = (Math.round(getPricePerHundredGrams() * 100.0) / 100.0) + " DKK";
        String prettyWeight = weight + " g";
        String prettyStore = storeType.toString();

        for (int i = prettyName.length(); i < 35; i++) {
            prettyName += " ";
        }
        for (int i = prettyPrice.length(); i < 25; i++) {
            prettyPrice += " ";
        }
        for (int i = prettyPricePerHundred.length(); i < 25; i++) {
            prettyPricePerHundred += " ";
        }
        for (int i = prettyWeight.length(); i < 25; i++) {
            prettyWeight += " ";
        }
        for (int i = prettyStore.length(); i < 25; i++) {
            prettyStore += " ";
        }

        return "\u001B[35mName: \u001B[0m"+prettyName + "\u001B[32m Price: \u001B[0m" + prettyPrice + "\u001B[32mPrice per 100g: \u001B[0m" + prettyPricePerHundred +  /*", product type: " + productType +*/ "\u001b[34mWeight: \u001B[0m" + prettyWeight+ "\u001b[31mStore: \u001B[0m" + prettyStore +"\u001B[0m";
    }

    public int getWeight() {
        return weight;
    }

    public String getImage() {
        return image;
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
