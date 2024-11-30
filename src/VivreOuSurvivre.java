class VivreOuSurvivre extends Program {
    final char cBombe = '*';
    final char cVide = ' ';

    void algorithm() {
        Map m = newMap(9, new int[]{0,0}, new int[]{9,9});
        viderMap(m);
        m.bombes[1][1] = true;
        println(toStringMap(m));
    }

    /* =========================== */
    /* Fonction de Base sur la Map */
    /* =========================== */

    Map newMap (int taille, int[] indiceD, int[] indiceF) {
        Map m = new Map();
        m.bombes = new boolean[taille][taille];
        m.indiceDepart = indiceD;
        m.indiceFin = indiceF;
        return m;
    }

    void viderMap (Map m) {
        for (int idxL = 0; idxL < length(m.bombes, 1); idxL++) {
            for (int idxC = 0; idxC < length(m.bombes, 2); idxC++) {
                m.bombes[idxL][idxC] = false;
            }
        }
    }

    String toStringMap (Map m) {
        String chaine = "  ";

        // Première Ligne
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
                } else {
                    chaine = chaine + cVide;
                }
            }
        }

        return chaine;
    }
}