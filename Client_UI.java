import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client_UI {
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    

    private static JFrame frame;
    private static JTextArea menuTextArea;
    private static JTextArea consoleTextArea;
    private static JTextField idField;
    private static JButton addButton;
    private static JButton checkoutButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
            connectToServer();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Restaurant Ordering System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout(10, 10));

        menuTextArea = new JTextArea();
        menuTextArea.setEditable(false);
        menuTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane menuScrollPane = new JScrollPane(menuTextArea);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu List"));

        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        consoleTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        consoleTextArea.setBackground(Color.BLACK);
        consoleTextArea.setForeground(Color.GREEN);
        JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);
        consoleScrollPane.setBorder(BorderFactory.createTitledBorder("Order Status & Receipt"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuScrollPane, consoleScrollPane);
        splitPane.setDividerLocation(450);
        frame.add(splitPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JLabel idLabel = new JLabel("Enter Product ID:");
        idField = new JTextField(10);
        addButton = new JButton("Add to Order");
        checkoutButton = new JButton("Checkout");

        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        checkoutButton.setBackground(new Color(46, 139, 87));
        checkoutButton.setForeground(Color.WHITE);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(addButton);
        inputPanel.add(checkoutButton);

        frame.add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> sendMessage());
        idField.addActionListener(e -> sendMessage());
        checkoutButton.addActionListener(e -> handleCheckout());

        frame.setVisible(true);
    }

    private static void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 5000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                appendConsole("Connected to server successfully!\n");

                StringBuilder menuBuilder = new StringBuilder();
                String serverLine;
                while ((serverLine = in.readLine()) != null) {
                    if (serverLine.equals("--- END_OF_MENU ---")) {
                        break;
                    }
                    menuBuilder.append(serverLine).append("\n");
                }
                menuTextArea.setText(menuBuilder.toString());

                while ((serverLine = in.readLine()) != null) {
                    appendConsole(serverLine + "\n");
                }

            } catch (Exception e) {
                appendConsole("Connection error: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private static void sendMessage() {
        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            out.println(idText);
            idField.setText("");
        }
    }

    private static void handleCheckout() {
        out.println("checkout");
        addButton.setEnabled(false);
        idField.setEnabled(false);
        checkoutButton.setEnabled(false);
    }

    private static void appendConsole(String text) {
        SwingUtilities.invokeLater(() -> {
            consoleTextArea.append(text);
            consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
        });
    }
}