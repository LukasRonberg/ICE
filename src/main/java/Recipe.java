import java.util.*;

public class Recipe {

    String name;

    List<String> ingredients;

    Double TotalPrice;
    private ArrayList<Product> productList = null;


    public Recipe(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public double calculateTotalPrice(ArrayList<Product> allProducts){
        double priceCalc = 0;
        if(productList == null || productList.isEmpty()) findCheapestProductsNewAndBetter(allProducts, null);
        for (Product product: productList) {
            priceCalc += product.price;
        }
        TotalPrice = priceCalc;
        return priceCalc;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ingredients: " + ingredients;
    }

    //Gammel ikke brug - den er blot her for at vise forskel på hurtighed
    public ArrayList<Product> findCheapestProducts(ArrayList<Product> productList)
    {
        ArrayList<Product> cheapestProducts = new ArrayList<>();
        for (String ingredient : ingredients) {
            for (Product product: productList) {
                if(ingredient.equals(product.name) && !cheapestProducts.isEmpty()){
                    for (Product priceCompare:cheapestProducts) {
                        if(product.pricePerGram() < priceCompare.pricePerGram()){
                            cheapestProducts.add(product);
                            cheapestProducts.remove(priceCompare);
                        }
                    }
                } else if(ingredient.equals(product.name)){
                    cheapestProducts.add(product);
                }
            }
        }
        this.productList = cheapestProducts;
        return cheapestProducts;
    }

    // TODO: 11-12-2023 der skal tages hensyn til hvor meget af hver produkt der skal bruges
    public List<Product> findCheapestProductsNewAndBetter(List<Product> productList, List<String> ingredients) {
        Map<String, Product> cheapestProductMap = new HashMap<>();

        if(ingredients == null) ingredients = this.ingredients;
        for (Product product : productList) {
            if (ingredients.contains(product.name)) {
                String ingredient = product.name;
                if (!cheapestProductMap.containsKey(ingredient) ||
                        product.pricePerGram() < cheapestProductMap.get(ingredient).pricePerGram()) {
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
            if (ingredients.contains(product.name) && product.storeType == storeType) {
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
                totalStorePrice += p.pricePerGram();
            }
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
            return "Store name: "+ storeName+", total price per 100g: "+ (Math.round(totalStorePrice * 100.0) / 100.0)
                    + ", number of wares in store: "+ getProducts().size();
        }
    }
}
