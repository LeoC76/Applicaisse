package stage;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private List<Produit> produits;

    public Panier() {
        produits = new ArrayList<>();
    }
 
    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public List<Produit> getProduits() {
        return produits;
    }
    public double calculerTotal() {
        double total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix() * produit.getQuantite();
        }
        return total;
    }
    public void viderPanier() {
        produits.clear();
    }
}
