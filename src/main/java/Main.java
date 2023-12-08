import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Oksekød", 500, 15, null, Enums.ProductType.MEAT, Enums.StoreType.Kvickly));
        products.add(new Product("Oksekød", 500, 15, null, Enums.ProductType.MEAT, Enums.StoreType.Bilka));
        products.add(new Product("Oksekød", 500, 20, null, Enums.ProductType.MEAT, Enums.StoreType.LetKoeb));
        products.add(new Product("Oksekød", 500, 15, null, Enums.ProductType.MEAT, Enums.StoreType.Kvickly));
        products.add(new Product("Oksekød", 424, 44, null, Enums.ProductType.MEAT, Enums.StoreType.Bilka));
        products.add(new Product("Oksekød", 1000, 19, null, Enums.ProductType.MEAT, Enums.StoreType.ALDI));
        products.add(new Product("Oksekød", 550, 16, null, Enums.ProductType.MEAT, Enums.StoreType.Kvickly));

        products.add(new Product("Ost", 500, 15, null, Enums.ProductType.MEAT, Enums.StoreType.Kvickly));
        products.add(new Product("Ost", 500, 10, null, Enums.ProductType.MEAT, Enums.StoreType.Bilka));
        products.add(new Product("Ost", 500, 20, null, Enums.ProductType.MEAT, Enums.StoreType.LetKoeb));
        products.add(new Product("Ost", 500, 15, null, Enums.ProductType.MEAT, Enums.StoreType.Kvickly));
        products.add(new Product("Ost", 424, 44, null, Enums.ProductType.MEAT, Enums.StoreType.Bilka));
        products.add(new Product("Ost", 1000, 19, null, Enums.ProductType.MEAT, Enums.StoreType.ALDI));
        products.add(new Product("Ost", 550, 16, null, Enums.ProductType.MEAT, Enums.StoreType.Kvickly));

        Recipe recipe = new Recipe("Bolo", new ArrayList<String>(Arrays.asList("Oksekød","Ost","Mælk")));

        //Sorts products in the products arraylist by pricePerGram
        products.sort((Comparator.comparingDouble(Product::pricePerGram)));


        System.out.println();
        System.out.println("Test 1: Using Bolo");
        long start1 = System.nanoTime();
        var test1 = recipe.findCheapestProducts(products);
        System.out.println("Name: "+test1.get(0).name +", Price per 100g: "+test1.get(0).pricePerGram() +", Price: "+ test1.get(0).price+", Store: "+ test1.get(0).storeType);
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));


        System.out.println();
        System.out.println("Test 2: Using Bolo");
        start1 = System.nanoTime();
        var test2 = recipe.findCheapestProductsNewAndBetter(products,null);
        for (Product p: test2) {
            System.out.println("Name: "+p.name +", Price per 100g: "+p.pricePerGram() +", Price: "+ p.price+", Store: "+ p.storeType);
        }
        System.out.println("Total price for this recipe is: "+ recipe.calculateTotalPrice());
        end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));


        System.out.println();
        System.out.println("Test 3: Using Bolo");
        start1 = System.nanoTime();
        var test3 = recipe.findCheapestStore(products);
        test3.sort((Comparator.comparingDouble(Recipe.Store::getTotalStorePrice)));
        for (Recipe.Store store: test3) {
            if(store.getTotalStorePrice() > 0)
                System.out.println("Store name: "+store.storeName+", total price per 100g: "+store.totalStorePrice);
        }
        end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));
    }
}
