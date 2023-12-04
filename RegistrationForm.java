import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JFrame {
    private final JTextField idField, nameField, addressField, contactField;
    private final JComboBox<String> genderComboBox;
    private final JButton submitButton;

    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Gender:"));
        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);
        add(genderComboBox);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Contact:"));
        contactField = new JTextField();
        add(contactField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        add(submitButton);

        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitForm() {
        String id = idField.getText();
        String name = nameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String address = addressField.getText();
        String contact = contactField.getText();

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/reg";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Insert data into the database
            String query = "INSERT INTO reg1 (id, name, gender, address, contact) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, contact);

            preparedStatement.executeUpdate();

           
            preparedStatement.close();
            connection.close();

            JOptionPane.showMessageDialog(this, "Registration successful");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegistrationForm();
            }
        });
    }
}
