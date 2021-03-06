package com.loanlassalle.smartcity.carte;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Représente un fournisseur de tuiles contenant des points de couleurs selon
 * l'emplacement d'événements
 *
 * @author Wojciech Myskorowski
 * @author Jérémie Zanone
 */
public final class FournisseurTuilePointCouleur implements FournisseurTuile {

    public final static int TAILLE_TUILE = 256;
    private final static Color[] COULEURS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
            Color.MAGENTA};
    private final ArrayList<Event> evenements;


    /**
     * Crée un fournisseur de tuile isochrone avec l'arbre des trajets, la table
     * des couleurs et la vitesse de marche donnés
     *
     * @param evenements liste des événements à afficher sur la tuile
     */
    public FournisseurTuilePointCouleur(ArrayList<Event> evenements) {
        this.evenements = evenements;
    }

    @Override
    public Tuile getTuile(int zoom, int x, int y) {

        // création d'une tuile
        BufferedImage image = new BufferedImage(TAILLE_TUILE, TAILLE_TUILE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        String nomAffichage;
        int rayon = 5;
        for (Event e : evenements) {
            g.setColor(COULEURS[e.getCategorie()]);
            Ellipse2D ellipse = new Ellipse2D.Double(e.getPosition().toOSM(zoom).x() - TAILLE_TUILE * x - rayon,
                    e.getPosition().toOSM(zoom).y() - TAILLE_TUILE * y - rayon, rayon * 2, rayon * 2);
            g.fill(ellipse);

            // contour en noir
            g.setColor(Color.black);
            g.draw(ellipse);

            g.setColor(Color.white);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 17));

            nomAffichage = e.getId() + ": " + e.getNom();
            // permet d'avoir un bord blanc autour de l'écriture
            g.drawString(nomAffichage, e.getPosition().toOSM(zoom).arrondiX() - TAILLE_TUILE * x - 6,
                    e.getPosition().toOSM(zoom).arrondiY() - TAILLE_TUILE * y - 9);
            g.drawString(nomAffichage, e.getPosition().toOSM(zoom).arrondiX() - TAILLE_TUILE * x - 6,
                    e.getPosition().toOSM(zoom).arrondiY() - TAILLE_TUILE * y - 7);
            g.drawString(nomAffichage, e.getPosition().toOSM(zoom).arrondiX() - TAILLE_TUILE * x - 4,
                    e.getPosition().toOSM(zoom).arrondiY() - TAILLE_TUILE * y - 9);
            g.drawString(nomAffichage, e.getPosition().toOSM(zoom).arrondiX() - TAILLE_TUILE * x - 4,
                    e.getPosition().toOSM(zoom).arrondiY() - TAILLE_TUILE * y - 7);
            //puis écrit le fond en noir
            g.setColor(Color.black);
            g.drawString(nomAffichage, e.getPosition().toOSM(zoom).arrondiX() - TAILLE_TUILE * x - 5,
                    e.getPosition().toOSM(zoom).arrondiY() - TAILLE_TUILE * y - 8);
        }

        return new Tuile(zoom, x, y, image);
    }
}
