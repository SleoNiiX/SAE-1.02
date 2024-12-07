class VivreOuSurvivre extends Program {
    final char BOMBE = '*';
    final char VIDE = ' ';
    final char DEPART = 'D';
    final char FIN = 'F';
    Bloc[] blocMap1 = new Bloc[]{newBloc("Avancer de n case(s)"), newBloc("Tourner à Gauche"), newBloc("Tourner à Droite")};

    void algorithm() {
        // Map pour le bloc avancer de n case
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1}, blocMap1);
        viderMap(map1);

        afficheMap(map1);
        println();
        SaisieAlgo(map1);
    }

    /* ==================== */
    /* Fonction de Gameplay */
    /* ==================== */
    
    int SaisieAlgo (Map m) {
        println("Construit ton algorithme :");
        println();

        for (int idx = 0; idx < length(m.blocPossible); idx++) {
            println(idx+1 + " : " + m.blocPossible[idx].nom);
        }
        println();

        int choix;
        do {
            print("Entre l'entier du bloc correspondant : ");
            choix = readInt();
        } while (choix > length(m.blocPossible) || choix < 1);

        return choix;
    }

    /* ================= */
    /* Fonction de blocs */
    /* ================= */

    void testAvancerNord () {
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1}, blocMap1);
        viderMap(map1);
        Joueur joueur1 = newJoueur(map1, 'N');

        assertTrue(avancerNord(joueur1, map1, 2));
        map1.bombes[1][1] = true;
        joueur1.positionActuel = map1.indiceDepart;
        assertFalse(avancerNord(joueur1, map1, 2));
    }

    boolean avancerNord (Joueur j, Map m, int nombreCases) {
        int idxL;
        boolean echec = false;
        int cases = 1; 
        
        while (cases <= nombreCases && !echec) {
            idxL = j.positionActuel[0]-1;
            if (idxL < 0) {
                return false;
            }
            j.positionActuel[0] = idxL;
            echec = estSurBombe(j, m);
            cases++;
        }

        return !echec;
    }


    void testEstSurBombe () {
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1}, blocMap1);
        viderMap(map1);
        Joueur joueur1 = newJoueur(map1, 'N');

        assertFalse(estSurBombe(joueur1, map1));
        map1.bombes[1][1] = true;
        joueur1.positionActuel = new int[]{1,1};
        assertTrue(estSurBombe(joueur1, map1));
    }

    boolean estSurBombe (Joueur j, Map m) {
        return m.bombes[j.positionActuel[0]][j.positionActuel[1]];
    }

    /* ============================ */
    /* Fonction de Base sur le bloc */
    /* ============================ */

    Bloc newBloc (String nom) {
        Bloc b = new Bloc();
        b.nom = nom;
        return b;
    }

    /* ============================== */
    /* Fonction de Base sur le joueur */
    /* ============================== */

    Joueur newJoueur (Map m, char d) {
        Joueur j = new Joueur();
        j.positionActuel = m.indiceDepart;
        j.direction = d;
        return j;
    }

    /* =========================== */
    /* Fonction de Base sur la Map */
    /* =========================== */

    Map newMap (int taille, int[] indiceD, int[] indiceF, Bloc[] listeBloc) {
        Map m = new Map();
        m.bombes = new boolean[taille][taille];
        viderMap(m);
        m.indiceDepart = indiceD;
        m.indiceFin = indiceF;
        m.blocPossible = listeBloc;
        return m;
    }

    // Met toutes les cases à false pour avoir une carte vide
    void viderMap (Map m) {
        for (int idxL = 0; idxL < length(m.bombes, 1); idxL++) {
            for (int idxC = 0; idxC < length(m.bombes, 2); idxC++) {
                m.bombes[idxL][idxC] = false;
            }
        }
    }

    String toStringMap (Map m) {
        String chaine = "  ";

        // Première Ligne : ABCD...
        for (int idx = 0; idx < length(m.bombes, 1); idx++) {
            chaine = chaine + (char) ('A' + idx);
        }

        // Reste Map
        for (int idxL = 0; idxL < length(m.bombes, 1); idxL++) {
            chaine = chaine + "\n";
            chaine = chaine + (idxL+1) + ' ';
            for (int idxC = 0; idxC < length(m.bombes, 2); idxC++) {
                if (m.bombes[idxL][idxC]) {
                    chaine = chaine + BOMBE;
                } else if ((m.indiceDepart[0] == idxL) && (m.indiceDepart[1] == idxC)) { // vérifie s'il sagit de la case de Départ
                    chaine = chaine + DEPART;
                } else if ((m.indiceFin[0] == idxL) && (m.indiceFin[1] == idxC)) { // vérifie s'il sagit de la case de Fin
                    chaine = chaine + FIN;
                } else {
                    chaine = chaine + VIDE;
                }
            }
        }

        return chaine;
    }

    void afficheMap (Map m) {
        println(toStringMap(m));
    }
}