class VivreOuSurvivre extends Program {
    final char BOMBE = '*';
    final char VIDE = ' ';
    final char DEPART = 'D';
    final char FIN = 'F';
    final int LIMITEBLOC = 10;

    final String AVANCERN = "Avancer de n case(s)";
    final String AVANCERS = "Avancer de n case(s) à Gauche";
    final String AVANCERO = "Avancer de n case(s) à Droite";
    final String AVANCERE = "Reculer de n case(s)";

    Bloc[] blocMap1 = new Bloc[]{newBloc(AVANCERN)};
    Bloc[] blocMap2 = new Bloc[]{newBloc(AVANCERN), newBloc(AVANCERS), newBloc(AVANCERO), newBloc(AVANCERE)};

    void algorithm() {
        // Map pour le bloc avancer de n case
        Map map1 = newMap(3, new int[]{2,1}, new int[]{0,1}, blocMap1);
        viderMap(map1);

        Map map2 = newMap(3, new int[]{2,1}, new int[]{0,1}, blocMap2);
        viderMap(map1);
        map2.bombes[1][1] = true;
        map2.bombes[1][2] = true;

        Joueur joueur1;

        int choixJoueur = -1;
        int choixMap;
        Map map;
        
        while (choixJoueur != 2) {
            println("==========================================================");
            println();
            println("Que Faire ?");
            println();
            println("1 : Nouvelle partie");
            println("2 : Fermer le jeu");
            println();

            do {
                print("Choix : ");
                choixJoueur = readInt();
            } while (choixJoueur < 1 || choixJoueur > 2);
            
            if (choixJoueur == 1) {
                // Plus tard ajouter des sauvegardes et une avancer progressive selon ce qu'à déjà fait le joueur ?
                println("==========================================================");
                println("Sur quel carte ? (1 ou 2)");
                println();

                do {
                    print("Choix : ");
                    choixMap = readInt();
                } while (choixMap < 1 || choixMap > 2);

                if (choixMap == 1) {
                    map = map1;
                } else {
                    map = map2;
                }

                joueur1 = newJoueur(map);
                lancementGame(map, joueur1);
            }
        }
    }

    /* ==================== */
    /* Fonction de Gameplay */
    /* ==================== */
    
    void lancementGame (Map map, Joueur joueur) {
        nouveauBlocAlgo(joueur, map, saisieAlgo(map, joueur));

        int choixJoueur = -1;

        while (choixJoueur != 3) {
            afficheMap(map);
            afficheAlgo(joueur);
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

            if (choixJoueur == 1) {
                nouveauBlocAlgo(joueur, map, saisieAlgo(map, joueur));
            } else if (choixJoueur == 2) {
                restartAlgo(joueur);
            } else {
                executionAlgo(map, joueur);
            }
        }
    }

    
    int saisieAlgo (Map m, Joueur joueur) {
        afficheMap(m);
        afficheAlgo(joueur);
        println();
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


    void nouveauBlocAlgo (Joueur joueur, Map m, int choix) {
        Bloc bloc = newBloc(m.blocPossible[choix-1].nom);

        if (choix > 0 && choix < 5) {
            // On ne verrifit pas le nombre de case entré volontairement pour que le joueur apprenne à pas sortir de la map
            print("De combien de cases souhaites tu avancer : ");
            int nbCases = readInt();

            bloc.valeur = nbCases;
        }

        joueur.algo[joueur.idxLastBloc] = bloc;
        joueur.idxLastBloc++;
    }


    void restartAlgo (Joueur joueur) {
        // TODO : demander de confirmer au joueur 
        joueur.algo = new Bloc[LIMITEBLOC];
        joueur.idxLastBloc = 0;
        println("Vous avez recommencé un algorithme de zéro"); 
    }


    void executionAlgo (Map map, Joueur joueur) {
        int idx = 0;
        boolean blocReussi = true;
        Bloc bloc;

        while (idx < joueur.idxLastBloc && blocReussi) {
            bloc = joueur.algo[idx];

            if (bloc.nom == AVANCERN) {
                blocReussi = avancerNord(joueur, map, bloc.valeur);
            } else {
                println("tacos");
            }
            idx++;
        }

        if (blocReussi == false || joueur.positionActuel[0] != map.indiceFin[0] || joueur.positionActuel[1] != map.indiceFin[1]) {
            println("Tu n'as pas réussi :(");
        } else {
            println("Bravo tu as reussi !!");
        }
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
                println("Sorti de la carte !! ");
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
        Joueur joueur1 = newJoueur(map1);

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

    String toStringBloc(Bloc b) {
        String str = b.nom;

        if (b.valeur != -1) {
            str = str + " n = " + b.valeur;
        }

        return str;
    }

    /* ============================== */
    /* Fonction de Base sur le joueur */
    /* ============================== */

    Joueur newJoueur (Map m) {
        Joueur j = new Joueur();
        j.positionActuel = m.indiceDepart;
        j.algo = new Bloc[LIMITEBLOC];
        return j;
    }


    void afficheAlgo (Joueur joueur) {
        for (int idx = 0; idx < joueur.idxLastBloc; idx++) {
            println(idx+1 + "." + toStringBloc(joueur.algo[idx]));
        }
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
        println("==========================================================");
        println();
        println(toStringMap(m));
        println();
    }
}