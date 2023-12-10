import org.w3c.dom.ranges.Range;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        patrickTest();
    }

    public static void patrickTest(){
        ArrayList<Product> products = new ArrayList<>();

        //DBConnector dbConnector = new DBConnector();
        //products = dbConnector.getProducts();
        products = generateDataset(5000);
        //for (Product product: products) {
        //System.out.println(product.toString());
        //}

        Recipe recipe = new Recipe("Bolo", new ArrayList<String>(Arrays.asList("hakket oksekød","smør","letmælk", "revet mozzarella", "peberfrugt", "ris")));

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
            System.out.println("Name: "+p.name +", Price per 100g: "+ (Math.round(p.pricePerGram() * 100.0) / 100.0) +", Price: "+ p.price+", Store: "+ p.storeType);
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

                System.out.println("Store name: "+store.storeName+", total price per 100g: "+ (Math.round(store.totalStorePrice * 100.0) / 100.0) + ", number of wares in store: "+ store.getProducts().size());
        }
        end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));


    }
    public static ArrayList<Product> generateDataset(int numEntries) {
        ArrayList<Product> dataset = new ArrayList<>();
        Random random = new Random();

        String[] mad = new String[]{"hakket oksekød", "letmælk", "smør", "revet mozzarella", "peberfrugt", "ris",
                "kyllingebryst", "æg", "tomater", "løg", "gulerødder", "kartofler", "hvedemel", "brød", "bananer",
                "æbler", "appelsiner", "spinat", "broccoli", "laks", "tunge fløde", "parmesan", "spaghetti",
                "olivenolie", "hvidløg", "agurk", "ærter", "kiks", "yoghurt", "honning", "avocado", "svinekød", "majs",
                "grønne bønner", "pølser", "jordnøddesmør", "risengrød", "rosiner", "havregryn", "kaffe", "te",
                "chokolade", "rugbrød", "solsikkefrø", "is", "rugbrødschips", "koriander", "tomatsauce", "mel", "bønner"
                , "mayonnaise", "citron", "ost", "æggepasta", "hakket kylling", "taco-skaller", "vaniljeis", "friske bær"
                , "hakket svinekød", "fløde", "mælk", "kyllingelever", "rugbrødstoast", "frossen pizza", "frossen grøntsagsblanding",
                "citronsaft", "appelsinjuice", "fiskesauce", "sojasauce", "pasta", "gær", "balsamicoeddike", "røde bønner",
                "mørk chokolade", "sukker", "kanel", "jordbærmarmelade", "ærtepuré", "kyllingefond", "tun på dåse", "frisk ingefær",
                "muskatnød", "flødeost", "rucola", "paprika", "rødvin", "hvidvin"};

        for (int i = 0; i < numEntries; i++) {
            String name = mad[random.nextInt(0, mad.length)];
            double weight = random.nextInt(50,1000);
            double price = random.nextInt(5,50);
            Enums.ProductType productType = Enums.ProductType.MEAT;
            Enums.StoreType storeType = Enums.StoreType.values()[random.nextInt(Enums.StoreType.values().length)]; // Vælg en tilfældig butikstype

            Product product = new Product(name, (int) weight, price, null, productType, storeType);
            dataset.add(product);
        }

        return dataset;
    }

}
