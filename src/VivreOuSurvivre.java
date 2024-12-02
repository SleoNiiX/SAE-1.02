class VivreOuSurvivre extends Program {
    final char cBombe = '*';
    final char cVide = ' ';
    final char cDepart = 'D';
    final char cFin = 'F';

    void _algorithm() {
        // Création d'une map juste pour les tests 
        Map mapParDefaut = newMap(9, new int[]{0,0}, new int[]{8,8});
        mapParDefaut.bombes = new boolean[][]{{false,true,true,true,true,true,true,true,true},
                                              {false,false,false,false,true,true,true,true,true},
                                              {true,true,true,false,false,true,true,true,true},
                                              {true,true,true,true,false,false,true,true,true},
                                              {true,true,true,true,true,false,false,true,true},
                                              {true,true,true,true,true,true,false,false,true},
                                              {true,true,true,true,true,true,true,false,true},
                                              {true,true,true,true,true,true,true,false,true},
                                              {true,true,true,true,true,true,true,false,false}};

        // Map pour le bloc avancer de n case
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1});
        viderMap(map1);

        afficheMap(map1);
        // CECI EST UN TEST
        afficheMap(mapParDefaut);
    }

    /* ========================= */
    /* Fonction de Base de blocs */
    /* ========================= */

    void testAvancerNord () {
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1});
        viderMap(map1);
        Joueur joueur1 = newJoueur(map1);

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
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1});
        viderMap(map1);
        Joueur joueur1 = newJoueur(map1);

        assertFalse(estSurBombe(joueur1, map1));
        map1.bombes[1][1] = true;
        joueur1.positionActuel = new int[]{1,1};
        assertTrue(estSurBombe(joueur1, map1));
    }

    boolean estSurBombe (Joueur j, Map m) {
        return m.bombes[j.positionActuel[0]][j.positionActuel[1]];
    }

    /* ============================== */
    /* Fonction de Base sur le joueur */
    /* ============================== */

    Joueur newJoueur (Map m) {
        Joueur j = new Joueur();
        j.positionActuel = m.indiceDepart;
        return j;
    }

    /* =========================== */
    /* Fonction de Base sur la Map */
    /* =========================== */

    Map newMap (int taille, int[] indiceD, int[] indiceF) {
        Map m = new Map();
        m.bombes = new boolean[taille][taille];
        viderMap(m);
        m.indiceDepart = indiceD;
        m.indiceFin = indiceF;
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
                    chaine = chaine + cBombe;
                } else if ((m.indiceDepart[0] == idxL) && (m.indiceDepart[1] == idxC)) { // vérifie s'il sagit de la case de Départ
                    chaine = chaine + cDepart;
                } else if ((m.indiceFin[0] == idxL) && (m.indiceFin[1] == idxC)) { // vérifie s'il sagit de la case de Fin
                    chaine = chaine + cFin;
                } else {
                    chaine = chaine + cVide;
                }
            }
        }

        return chaine;
    }

    void afficheMap (Map m) {
        println(toStringMap(m));
    }
}