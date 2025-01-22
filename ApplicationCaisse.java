package stage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationCaisse extends JFrame {

    private Panier panier;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;

    public ApplicationCaisse() {
        // Initialisation du panier
        panier = new Panier();

        // Configuration de la fenêtre
        setTitle("Caisse Enregistreuse");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création de la table pour afficher les produits
        tableModel = new DefaultTableModel(new String[]{"Nom", "Prix (€)", "Quantité"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panneau pour ajouter des produits
        JPanel panelAjout = new JPanel();
        panelAjout.setLayout(new GridLayout(5, 2));

        panelAjout.add(new JLabel("Nom du produit:"));
        JTextField txtNom = new JTextField();
        panelAjout.add(txtNom);

        panelAjout.add(new JLabel("Prix (€):"));
        JTextField txtPrix = new JTextField();
        panelAjout.add(txtPrix);

        panelAjout.add(new JLabel("Quantité:"));
        JTextField txtQuantite = new JTextField();
        panelAjout.add(txtQuantite);

        JButton btnAjouter = new JButton("Ajouter au panier");
        panelAjout.add(btnAjouter);

        // Label pour afficher le total
        lblTotal = new JLabel("Total: 0.0€");
        panelAjout.add(lblTotal);
        
        JButton btnValider = new JButton("Valider le panier");
        panelAjout.add(btnValider);

        add(panelAjout, BorderLayout.SOUTH);

        // Action du bouton "Ajouter au panier"
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNom.getText();
                double prix;
                int quantite;

                try {
                    prix = Double.parseDouble(txtPrix.getText());
                    quantite = Integer.parseInt(txtQuantite.getText());

                    Produit produit = new Produit(nom, prix, quantite);
                    panier.ajouterProduit(produit);

                    // Ajouter le produit à la table
                    tableModel.addRow(new Object[]{produit.getNom(), produit.getPrix(), produit.getQuantite()});

                    // Réinitialiser les champs
                    txtNom.setText("");
                    txtPrix.setText("");
                    txtQuantite.setText("");

                    // Mettre à jour le total
                    mettreAJourTotal();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ApplicationCaisse.this, "Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Première fenêtre de confirmation
                int confirmation = JOptionPane.showConfirmDialog(ApplicationCaisse.this, "Voulez-vous vraiment valider le panier ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    // Deuxième fenêtre pour choisir le moyen de paiement
                    String[] options = {"Chèque", "Espèces"};
                    String moyenPaiement = (String) JOptionPane.showInputDialog(ApplicationCaisse.this, "Choisissez un moyen de paiement:", "Moyen de Paiement", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (moyenPaiement != null) {
                        // Afficher les détails du panier
                        StringBuilder message = new StringBuilder("Produits achetés:\n\n");
                        for (Produit produit : panier.getProduits()) {
                            message.append(produit.toString()).append("\n");
                        }
                        message.append("\nTotal: ").append(panier.calculerTotal()).append("€");
                        message.append("\nMoyen de Paiement: ").append(moyenPaiement);

                        JOptionPane.showMessageDialog(ApplicationCaisse.this, message.toString(), "Validation du Panier", JOptionPane.INFORMATION_MESSAGE);

                        // Vider le panier et réinitialiser l'interface
                        panier.viderPanier();
                        tableModel.setRowCount(0);
                        mettreAJourTotal();
                    }
                }
            }
        });
    }

    private void mettreAJourTotal() {
        double total = panier.calculerTotal();
        lblTotal.setText("Total: " + total + "€");
    }
}
