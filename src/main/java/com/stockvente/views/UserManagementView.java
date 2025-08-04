package com.stockvente.views;

import com.stockvente.controller.UserManagementController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout(15, 15));

        // -------- TITRE --------
        JLabel title = new JLabel("Gestion des Utilisateurs", SwingConstants.CENTER);
        title.setFont(new Font("Bell MT", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        add(title, BorderLayout.NORTH);

        // -------- FORMULAIRE --------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Formulaire Utilisateur",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Bell MT", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        Font font = new Font("Bell MT", Font.PLAIN, 16);

        JLabel lblId = new JLabel("ID :");
        lblId.setFont(font);
        idField = new JTextField();
        idField.setFont(font);
        idField.setEditable(false);

        JLabel lblUsername = new JLabel("Nom d'utilisateur :");
        lblUsername.setFont(font);
        usernameField = new JTextField();
        usernameField.setFont(font);

        JLabel lblPassword = new JLabel("Mot de passe :");
        lblPassword.setFont(font);
        passwordField = new JPasswordField();
        passwordField.setFont(font);

        showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
        showPasswordCheckBox.setFont(font);
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '•');
        });

        JLabel lblEmail = new JLabel("Email :");
        lblEmail.setFont(font);
        emailField = new JTextField();
        emailField.setFont(font);

        JLabel lblRole = new JLabel("Rôle :");
        lblRole.setFont(font);
        roleField = new JTextField();
        roleField.setFont(font);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblId, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel(""), gbc);  // Empty label for alignment
        gbc.gridx = 1;
        formPanel.add(showPasswordCheckBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(lblRole, gbc);
        gbc.gridx = 1;
        formPanel.add(roleField, gbc);

        // -------- BOUTONS --------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("Ajouter");
        JButton updateButton = new JButton("Modifier");
        JButton displayButton = new JButton("Afficher tous");

        addButton.setFont(font);
        updateButton.setFont(font);
        displayButton.setFont(font);

        addButton.setBackground(new Color(33, 150, 243));
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(33, 150, 243));
        updateButton.setForeground(Color.WHITE);
        displayButton.setBackground(new Color(33, 150, 243));
        displayButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> handleAddUser());
        updateButton.addActionListener(e -> handleUpdateUser());
        displayButton.addActionListener(e -> handleDisplayUsers());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // -------- TABLEAU --------
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom d'utilisateur", "Email", "Rôle"}, 0);
        usersTable = new JTable(tableModel);
        usersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usersTable.setRowHeight(25);

        JScrollPane tableScrollPane = new JScrollPane(usersTable);

        // -------- SPLITPANE --------
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, tableScrollPane);
        splitPane.setResizeWeight(0.35); // 35% formulaire, 65% tableau
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(320);
        splitPane.setBackground(Color.WHITE);

        add(splitPane, BorderLayout.CENTER);

        // -------- STATUS --------
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Bell MT", Font.PLAIN, 16));
        add(statusLabel, BorderLayout.SOUTH);
    }

    // --- Les méthodes handleAddUser, handleUpdateUser, handleDisplayUsers et clearFields restent inchangées ---
    // (Copie exactement celles que tu as fournies, je ne les réécris pas ici pour éviter la redondance)



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

  
    
}
