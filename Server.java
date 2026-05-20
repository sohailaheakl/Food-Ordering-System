import java.io.*;
import java.net.*;
import java.util.ArrayList;


abstract class Product {
    protected int id;
    protected String name;
    protected double price;

    public Product(int id, String name, double price) {
        this.id    = id;
        this.name  = name;
        this.price = price;
    }

    public abstract void   display();
    public abstract double calculate_price();
}

class Food extends Product {
    boolean spicy;
    public Food(int id, String name, double price) {
        super(id, name, price);
        spicy = false;
    }
    @Override public void   display()         { System.out.printf("%-10d %-25s %.0f%n", id, name, price); }
    @Override public double calculate_price() { return price; }
}

class Drink extends Product {
    boolean with_sugar;
    public Drink(int id, String name, double price) {
        super(id, name, price);
        with_sugar = false;
    }
    @Override public void   display()         { System.out.printf("%-10d %-25s %.0f%n", id, name, price); }
    @Override public double calculate_price() { return price; }
}

class Dessert extends Product {
    boolean size_is_big;
    public Dessert(int id, String name, double price) {
        super(id, name, price);
        size_is_big = false;
    }
    @Override public void   display()         { System.out.printf("%-10d %-25s %.0f%n", id, name, price); }
    @Override public double calculate_price() { return price; }
}

class Menu {
    ArrayList<Food>    food    = new ArrayList<>();
    ArrayList<Drink>   drink   = new ArrayList<>();
    ArrayList<Dessert> dessert = new ArrayList<>();

    public Menu() {
        food.add(new Food(1,  "Cheese Beef Burger",  100));
        food.add(new Food(2,  "Luizana Burger",       150));
        food.add(new Food(3,  "Burger Tower",         200));
        food.add(new Food(4,  "Cizler Filet",          75));
        food.add(new Food(5,  "Chicken Burger",        95));
        food.add(new Food(6,  "Chicken Ranch",        115));
        food.add(new Food(7,  "Chicken Doritos",      115));
        food.add(new Food(8,  "Chicken Mozz",         125));
        food.add(new Food(9,  "Pizza Fries Strips",    80));
        food.add(new Food(10, "Twist Roll",            85));

        drink.add(new Drink(11, "VCola",                  25));
        drink.add(new Drink(12, "Strawberry Smoothie",    50));
        drink.add(new Drink(13, "Blueberry Smoothie",     55));
        drink.add(new Drink(14, "Lemon Mint Smoothie",    50));
        drink.add(new Drink(15, "Banana Smoothie",        50));
        drink.add(new Drink(16, "Mango Smoothie",         55));
        drink.add(new Drink(17, "Tea",                    25));
        drink.add(new Drink(18, "Coffee",                 30));
        drink.add(new Drink(19, "Caramel Frappuccino",    70));
        drink.add(new Drink(20, "Chocolate Frappuccino",  65));

        dessert.add(new Dessert(21, "Chocolate Ice Cream", 25));
        dessert.add(new Dessert(22, "Vanilla Ice Cream",   25));
        dessert.add(new Dessert(23, "Chocolate Waffle",    50));
        dessert.add(new Dessert(24, "Kinder Waffle",       60));
        dessert.add(new Dessert(25, "Pistachio Waffle",    60));
    }
}

class Order {
    ArrayList<Product> orders = new ArrayList<>();

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
}



public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("--- Server is running on port 5000 ---");
            System.out.println("Waiting for clients...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("New client connected! IP: " + socket.getRemoteSocketAddress());

                ClientHandler clientThread = new ClientHandler(socket);
                clientThread.start();
            }
        } catch (IOException e) {//Exception
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {//Correct client–server communication using sockets
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Menu menu = new Menu();
            Order order = new Order();

            sendMenuToClient(out, menu);

            String message;
            while (true) {
                message = in.readLine();

                if (message == null || message.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("Client [" + socket.getRemoteSocketAddress() + "] sent: " + message);

                if (message.equalsIgnoreCase("checkout")) {
                    sendReceiptToClient(out, order);
                    break;
                }

                try {
                    int id = Integer.parseInt(message.trim());
                    Product selectedProduct = findProductById(menu, id);

                    if (selectedProduct != null) {
                        order.addProduct(selectedProduct);
                        out.println("Added to order: " + selectedProduct.name + " (" + String.format("%.0f", selectedProduct.price) + " EGP)");
                    } else {
                        out.println("Error: Invalid Product ID. Please try a number between 1 and 25.");
                    }
                } catch (NumberFormatException e) {
                    out.println("Error: Please enter a valid product ID number or type 'checkout'.");
                }
            }
            //Exception Handling
        } catch (IOException e) {//مثلاً الـ port محجوز 
            System.out.println("Client disconnected abruptly: " + socket.getRemoteSocketAddress());
        } finally {
            try {
                socket.close();
                System.out.println("Connection closed for client: " + socket.getRemoteSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Product findProductById(Menu menu, int id) {
        for (Food f : menu.food) {
            if (f.id == id) return f;
        }
        for (Drink d : menu.drink) {
            if (d.id == id) return d;
        }
        for (Dessert des : menu.dessert) {
            if (des.id == id) return des;
        }
        return null;
    }

    private void sendMenuToClient(PrintWriter out, Menu menu) {
        out.println("______________________________________________________________________________________________________________");
        out.println("                                                   MENU");
        out.println("______________________________________________________________________________________________________________");
        out.println(String.format("%-40s %-40s %-40s", "FOOD", "DRINKS", "DESSERTS"));
        out.println("______________________________________________________________________________________________________________");

        int max = Math.max(menu.food.size(), Math.max(menu.drink.size(), menu.dessert.size()));
        for (int i = 0; i < max; i++) {
            String foods = "";
            String drinks = "";
            String desserts = "";
            if (i < menu.food.size()) {
                foods = menu.food.get(i).id + ". " + menu.food.get(i).name + " - " + String.format("%.0f", menu.food.get(i).price);
            }
            if (i < menu.drink.size()) {
                drinks = menu.drink.get(i).id + ". " + menu.drink.get(i).name + " - " + String.format("%.0f", menu.drink.get(i).price);
            }
            if (i < menu.dessert.size()) {
                desserts = menu.dessert.get(i).id + ". " + menu.dessert.get(i).name + " - " + String.format("%.0f", menu.dessert.get(i).price);
            }
            out.println(String.format("%-40s %-40s %-40s", foods, drinks, desserts));
        }
        out.println("--- END_OF_MENU ---");
    }

    private void sendReceiptToClient(PrintWriter out, Order order) {
        if (order.orders.isEmpty()) {
            out.println("=== RECEIPT ===");
            out.println("No items were ordered.");
            out.println("Thank you for your visit!");
            out.println("--- END_OF_RECEIPT ---");
            return;
        }

        out.println("=== RECEIPT ===");
        out.println("*********** YOUR ORDER ***********");
        for (Product p : order.orders) {
            out.println(String.format("%-10d %-25s %.0f EGP", p.id, p.name, p.price));
        }
        out.println("*****************************************");
        out.println("Total Price = " + String.format("%.0f", order.calculate_total()) + " EGP");
        out.println("Thank you for your visit!");
        out.println("--- END_OF_RECEIPT ---");
    }
}