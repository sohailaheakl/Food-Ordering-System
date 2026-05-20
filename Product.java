import java.util.ArrayList;

public abstract class Product { //Interfaces&amp; Abstract classes
    protected int id;//Encapsulation
    protected String name;
    protected double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    abstract public void display();
    abstract public double calculate_price();
}

class Food extends Product { //Inheritance
    boolean spicy;

    public Food(int id, String name, double price) {
        super(id, name, price);
        spicy = false;
    }

    @Override //Polymorphism
    public void display() {
        System.out.printf("%-10d %-25s %.0f%n", id, name, price);
    }

    @Override
    public double calculate_price() {
        return price;
    }
}
class Drink extends Product {
    boolean with_sugar;

    public Drink(int id, String name, double price) {
        super(id, name, price);
        with_sugar = false;
    }

    @Override
    public void display() {
        System.out.printf("%-10d %-25s %.0f%n", id, name, price);
    }

    @Override
    public double calculate_price() {
        return price;
    }
}
class Dessert extends Product {
    boolean size_is_big;

    public Dessert(int id, String name, double price) {
        super(id, name, price);
        size_is_big = false;
    }

    @Override
    public void display() {
        System.out.printf("%-10d %-25s %.0f%n", id, name, price);
    }

    @Override
    public double calculate_price() {
        return price;
    }
}
class Menu {
    ArrayList<Food> food = new ArrayList<>();
    ArrayList<Drink> drink = new ArrayList<>();
    ArrayList<Dessert> dessert = new ArrayList<>();

    public Menu() {
        food.add(new Food(1,  "Cheese Beef Burger",   100));
        food.add(new Food(2,  "Luizana Burger",        150));
        food.add(new Food(3,  "Burger Tower",          200));
        food.add(new Food(4,  "Cizler Filet",           75));
        food.add(new Food(5,  "Chicken Burger",         95));
        food.add(new Food(6,  "Chicken Ranch",         115));
        food.add(new Food(7,  "Chicken Doritos",       115));
        food.add(new Food(8,  "Chicken Mozz",          125));
        food.add(new Food(9,  "Pizza Fries Strips",     80));
        food.add(new Food(10, "Twist Roll",             85));

        drink.add(new Drink(11, "VCola",                   25));
        drink.add(new Drink(12, "Strawberry Smoothie",     50));
        drink.add(new Drink(13, "Blueberry Smoothie",      55));
        drink.add(new Drink(14, "Lemon Mint Smoothie",     50));
        drink.add(new Drink(15, "Banana Smoothie",         50));
        drink.add(new Drink(16, "Mango Smoothie",          55));
        drink.add(new Drink(17, "Tea",                     25));
        drink.add(new Drink(18, "Coffee",                  30));
        drink.add(new Drink(19, "Caramel Frappuccino",     70));
        drink.add(new Drink(20, "Chocolate Frappuccino",   65));

        dessert.add(new Dessert(21, "Chocolate Ice Cream",  25));
        dessert.add(new Dessert(22, "Vanilla Ice Cream",    25));
        dessert.add(new Dessert(23, "Chocolate Waffle",     50));
        dessert.add(new Dessert(24, "Kinder Waffle",        60));
        dessert.add(new Dessert(25, "Pistachio Waffle",     60));
    }

    public void display_menu() {
        System.out.println("______________________________________________________________________________________________________________");
        System.out.println("                                                   MENU");
        System.out.println("______________________________________________________________________________________________________________");
        System.out.printf("%-40s %-40s %-40s%n", "FOOD", "DRINKS", "DESSERTS");
        System.out.println("______________________________________________________________________________________________________________");
        int max = Math.max(food.size(), Math.max(drink.size(), dessert.size()));
        for (int i = 0; i < max; i++) {
            String foods    = "";
            String drinks   = "";
            String desserts = "";
            if (i < food.size())
                foods    = food.get(i).id    + ". " + food.get(i).name    + " - " + String.format("%.0f", food.get(i).price);
            if (i < drink.size())
                drinks   = drink.get(i).id   + ". " + drink.get(i).name   + " - " + String.format("%.0f", drink.get(i).price);
            if (i < dessert.size())
                desserts = dessert.get(i).id + ". " + dessert.get(i).name + " - " + String.format("%.0f", dessert.get(i).price);
            System.out.printf("%-40s %-40s %-40s%n", foods, drinks, desserts);
        }
    }
}

class Order {
    ArrayList<Product> orders = new ArrayList<>();//File Handling and Data Structures

    public void addProduct(Product p) {
        orders.add(p);
    }

    public double calculate_total() {
        double total = 0;
        for (Product p : orders) {
            total += p.calculate_price();
        }
        return total;
    }
    public void displayOrder() {//ريسيت
        System.out.println("\n*********** YOUR ORDER ***********");
        for (Product p : orders) {
            p.display();
        }
        System.out.println("*****************************************");
        System.out.printf("Total Price = %.0f%n", calculate_total());
    }
}
