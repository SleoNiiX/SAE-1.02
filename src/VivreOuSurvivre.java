class VivreOuSurvivre extends Program {
    final char cBombe = '*';
    final char cVide = ' ';
    final char cDepart = 'D';
    final char cFin = 'F';

    void algorithm() {
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
        afficheMap(mapParDefaut);
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