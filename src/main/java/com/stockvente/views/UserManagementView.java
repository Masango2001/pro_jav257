package com.stockvente.views;

import com.stockvente.controller.UserManagementController;
import com.stockvente.dao.UtilisateurDao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagementView extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(UserManagementView.class.getName());
    private final UserManagementController userController;
    private final String roleConnecte;

    private JTextField idField, usernameField, emailField, roleField;
    private JPasswordField passwordField;
    private DefaultTableModel tableModel;
    private JTable usersTable;
    private JLabel statusLabel;
    private JCheckBox showPasswordCheckBox;

    public UserManagementView(UserManagementController userController, String roleConnecte) {
        this.userController = userController;
        this.roleConnecte = roleConnecte;

        // Sécurité : seul un admin peut accéder
        if (!"admin".equalsIgnoreCase(roleConnecte)) {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé. Seul un administrateur peut gérer les utilisateurs.",
                    "Accès interdit", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        initComponents();
    }

    public UserManagementView(UserManagementController userManagementController, String roleConnecte, String roleChoisi) {
        this.userController = userManagementController;
        this.roleConnecte = roleConnecte;

       if (roleConnecte == null || !"Admin".equalsIgnoreCase(roleConnecte)) {
            JOptionPane.showMessageDialog(this,
                "Accès refusé. Seul un administrateur peut gérer les utilisateurs.",
                "Accès interdit", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }


        initComponents();
    }

    private void clearFields() {
        idField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        roleField.setText("");
        showPasswordCheckBox.setSelected(false);
        passwordField.setEchoChar('•');
    }

    private void initComponents() {
        setTitle("Gestion des Utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        idField = new JTextField(); idField.setEditable(false);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        emailField = new JTextField();
        roleField = new JTextField();
        showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
        showPasswordCheckBox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '•');
        });

        formPanel.add(new JLabel("ID"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Nom d'utilisateur"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Mot de passe"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Afficher mot de passe"));
        formPanel.add(showPasswordCheckBox);
        formPanel.add(new JLabel("Email"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Rôle"));
        formPanel.add(roleField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        JButton updateButton = new JButton("Modifier");
        JButton displayButton = new JButton("Afficher tous");

        addButton.addActionListener(e -> handleAddUser());
        updateButton.addActionListener(e -> handleUpdateUser());
        displayButton.addActionListener(e -> handleDisplayUsers());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom d'utilisateur", "Email", "Rôle"}, 0);
        usersTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(usersTable);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);

        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(tableScrollPane);
        mainPanel.add(statusLabel);

        setContentPane(mainPanel);
    }

    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String email = emailField.getText().trim();
        String role = roleField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            statusLabel.setText("Erreur : nom d'utilisateur, mot de passe et rôle sont requis.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        if (!email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            statusLabel.setText("Erreur : l'email n'est pas valide.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        try {
            String result = userController.ajouterUtilisateur(username, password, email, role);
            statusLabel.setText(result);
            statusLabel.setForeground(result.startsWith("Erreur") ? Color.RED : Color.GREEN);
            if (result.startsWith("Utilisateur ajouté")) {
                clearFields();
                handleDisplayUsers();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ajout de l'utilisateur", e);
            statusLabel.setText("Erreur inattendue : " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    private void handleUpdateUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow < 0) {
            statusLabel.setText("Veuillez sélectionner un utilisateur dans le tableau.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        try {
            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String role = roleField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                statusLabel.setText("Erreur : nom d'utilisateur, mot de passe et rôle sont requis.");
                statusLabel.setForeground(Color.RED);
                return;
            }

            if (!email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                statusLabel.setText("Erreur : l'email n'est pas valide.");
                statusLabel.setForeground(Color.RED);
                return;
            }

            String result = userController.mettreAJourUtilisateur(id, username, password, email, role);
            statusLabel.setText(result);
            statusLabel.setForeground(result.startsWith("Erreur") ? Color.RED : Color.GREEN);
            if (result.startsWith("Utilisateur mis à jour")) {
                clearFields();
                handleDisplayUsers();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la mise à jour de l'utilisateur", e);
            statusLabel.setText("Erreur inattendue : " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    private void handleDisplayUsers() {
        tableModel.setRowCount(0);
        try {
            List<String> users = userController.afficherTousLesUtilisateurs("Admin");
            for (String user : users) {
                try {
                    String[] parts = user.split(", ");
                    if (parts.length != 4) continue;
                    String id = parts[0].split(": ")[1];
                    String username = parts[1].split(": ")[1];
                    String email = parts[2].split(": ")[1];
                    String role = parts[3].split(": ")[1];
                    tableModel.addRow(new Object[]{id, username, email, role});
                } catch (Exception e) {
                    LOGGER.warning("Erreur parsing ligne utilisateur : " + user);
                }
            }
            statusLabel.setText("Utilisateurs chargés.");
            statusLabel.setForeground(Color.GREEN);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur chargement utilisateurs", e);
            statusLabel.setText("Erreur chargement : " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UtilisateurDao dao = new UtilisateurDao();
            UserManagementController controller = new UserManagementController(dao);
            new UserManagementView(controller, "Admin").setVisible(true);
        });
    }
    
}
