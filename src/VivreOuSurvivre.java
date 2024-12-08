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

        Joueur joueur1 = newJoueur(map1, 'N');

        afficheMap(map1);
        NouveauBlocAlgo(joueur1, map1, SaisieAlgo(map1));

        int choixJoueur = -1;

        while (choixJoueur != 3) {
            afficheMap(map1);
            println("AFFICHER ALGO ICI");
            println();
            println("Que faire ?");
            println();
            println("1 : Ajouter un bloc");
            println("2 : Recommencer à zero");
            println("3 : Valider l'algorithme");
            println();

            do {
                print("Entre ton choix : ");
                choixJoueur = readInt();
            } while (choixJoueur < 1 || choixJoueur > 3);

            if (choixJoueur == 2) {
                println("TACOS");
            }
        }
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


    void NouveauBlocAlgo (Joueur joueur, Map m, int choix) {
        Bloc bloc = m.blocPossible[choix-1];

        if (choix == 1) {
            int nbCases;
            char direction = joueur.direction;

            if (direction == 'N') {
                do {
                    print("De combien de cases souhaites tu avancer : ");
                    nbCases = readInt();
                } while (nbCases-joueur.positionActuel[0] > 0 || nbCases < 1);

                bloc.valeur = nbCases;
            }
        }
        joueur.algo[joueur.idxLastBloc] = bloc;
        joueur.idxLastBloc++;
    }

    /* =================== */
    /* Fonction de Kaomoji */
    /* =================== */

    int nbChancesPrecedent = 10;

    int min(int premierNb, int deuxiemeNb){
        int nbMin;

        if(premierNb>deuxiemeNb){
            nbMin = deuxiemeNb;
        }else{
            nbMin = premierNb;
        }

        return nbMin;
    }

    void testMin(){
        assertEquals(1, min(2, 1));
        assertEquals(3, min(3, 5));
    }

    String maitreKaomiji(int nbChances){
        String[] kaomiji = new String[]{"(◍•ᴗ•◍)", "(˶˃ ᵕ ˂˶)", "O_o", "(⌐■-■)", "(ಠ_ಠ)>⌐■-■", "ಠ_ʖಠ", "ರ_ರ", "(ꐦ¬_¬)", "(⪖ ⩋⪕)", "୧(๑•̀ᗝ•́)૭", "(⌐■_■)︻デ═一"};
        int idx = min(length(kaomiji)-1, length(kaomiji) - (nbChances+1));

        if(nbChances<nbChancesPrecedent){
            idx = min(length(kaomiji)-1, length(kaomiji) - (nbChances+1));
            nbChancesPrecedent = nbChances;
        }

        return kaomiji[idx];
    }

    void testMaitreKaomiji(){
        int nbChances;

        nbChances = 10;
        assertEquals("(◍•ᴗ•◍)", maitreKaomiji(nbChances));

        nbChances = 9;
        assertEquals("(˶˃ ᵕ ˂˶)", maitreKaomiji(nbChances));

        nbChances = 8;
        assertEquals("O_o", maitreKaomiji(nbChances));
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
        j.algo = new Bloc[10];
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
        println();
    }
}