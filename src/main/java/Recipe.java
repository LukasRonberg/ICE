import java.util.*;

public class Recipe {

    private String name;

    private List<String> ingredients;

    private double totalPrice;
    private ArrayList<Product> productList = null;

    public Recipe(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public double calculateTotalPrice(ArrayList<Product> allProducts){
        double priceCalc = 0;
        if(productList == null || productList.isEmpty()) findCheapestProductsNewAndBetter(allProducts, null);
        for (Product product: productList) {
            priceCalc += product.getPrice();
        }
        totalPrice = priceCalc;
        return priceCalc;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ingredients: " + ingredients;
    }

    // TODO: 11-12-2023 der skal tages hensyn til hvor meget af hver produkt der skal bruges
    public List<Product> findCheapestProductsNewAndBetter(List<Product> productList, List<String> ingredients) {
        Map<String, Product> cheapestProductMap = new HashMap<>();

        if(ingredients == null) ingredients = this.ingredients;
        for (Product product : productList) {
            if (ingredients.contains(product.getName().toLowerCase())) {
                String ingredient = product.getName().toLowerCase();
                if (!cheapestProductMap.containsKey(ingredient) ||
                        product.getPricePerHundredGrams() < cheapestProductMap.get(ingredient).getPricePerHundredGrams()) {
                    cheapestProductMap.put(ingredient, product);
                }
            }
        }

        return this.productList = new ArrayList<>(cheapestProductMap.values());
    }


    public ArrayList<Product> findCheapestProductInStoreInOrder(List<Product> productList, List<String> ingredients, Enums.StoreType storeType) {
        ArrayList<Product> cheapestProducts = new ArrayList<>();

        if(ingredients == null) ingredients = this.ingredients;
        for (Product product : productList) {
            if (ingredients.contains(product.getName().toLowerCase()) && product.storeType == storeType) {
                cheapestProducts.add(product);
                }

        }

        return this.productList = cheapestProducts;
    }



    public ArrayList<Store> findCheapestStore(List<Product> productList)
    {
        //ArrayList<ArrayList<Product>> storeHolder = new ArrayList<ArrayList<Product>>();
        ArrayList<Store> storeHolder = new ArrayList<>();
        double totalStorePrice = 0;
        for (Enums.StoreType storeType: Enums.StoreType.values()){
            Store store = new Store(storeType.name());
            for (int i = 0; i < ingredients.size(); i++) {
                var cheapestProduct = findCheapestProductInStoreInOrder(productList, new ArrayList<>(Arrays.asList(ingredients.get(i))), storeType);
                //storeHolder.add(store);
                if(!cheapestProduct.isEmpty()){
                    store.addToProducts(cheapestProduct.get(0));
                }
                //cheapestProduct.get(0).pricePerGram();
            }
            store.calcTotalStorePrice();
            storeHolder.add(store);
        }
        //storeHolder.sort((Comparator.comparingDouble(Store::calcTotalStorePrice)));
        return storeHolder;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public List<String> getIngredients() { return ingredients; }

    public double getTotalPrice(){
        return totalPrice;
    }

    // TODO: 08-12-2023 ryk klasse?
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
}
