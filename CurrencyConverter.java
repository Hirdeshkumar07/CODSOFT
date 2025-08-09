import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter extends JFrame {

    private JComboBox<String> baseCurrency;
    private JComboBox<String> targetCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    private final String[] currencies = {"USD", "EUR", "INR", "GBP", "JPY", "AUD", "CAD"};

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        baseCurrency = new JComboBox<>(currencies);
        targetCurrency = new JComboBox<>(currencies);

        amountField = new JTextField();
        JButton convertButton = new JButton("Convert");
        resultLabel = new JLabel("Converted Amount: ", JLabel.CENTER);

        panel.add(new JLabel("Base Currency:"));
        panel.add(baseCurrency);
        panel.add(new JLabel("Target Currency:"));
        panel.add(targetCurrency);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel(""));
        panel.add(convertButton);
        panel.add(new JLabel(""));
        panel.add(resultLabel);

        add(panel);

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        setVisible(true);
    }

    private void convertCurrency() {
        try {
            String base = baseCurrency.getSelectedItem().toString();
            String target = targetCurrency.getSelectedItem().toString();
            double amount = Double.parseDouble(amountField.getText());

            String urlStr = "https://api.frankfurter.app/latest?amount=" + amount + "&from=" + base + "&to=" + target;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                json.append(line);
            }
            in.close();

            // Parse JSON manually
            String jsonString = json.toString();
            String key = "\"" + target + "\":";
            int index = jsonString.indexOf(key);
            if (index != -1) {
                int start = index + key.length();
                int end = jsonString.indexOf("}", start);
                String valueStr = jsonString.substring(start, end).trim();
                resultLabel.setText("Converted Amount: " + valueStr + " " + target);
            } else {
                resultLabel.setText("Error: Conversion failed.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}