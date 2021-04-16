package olawp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

// Hashing av tekststrenger med kjeding i lenket liste
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell/lange lister
// - Tilbyr bare innsetting og sÃ¸king
//
public class hashChained {
    // Indre klasse:
    // Node med data, kjedes sammen i lenkede lister
    //
    private class hashNode {
        // Data, en tekststreng
        String data;
        // Neste node i listen
        hashNode neste;

        // KonstruktÃ¸r for listenoder
        public hashNode(String S, hashNode hN) {
            data = S;
            neste = hN;
        }

        @Override
        public String toString() {
            return "{" +
                    "data='" + data + '\'' +
                    ", neste=" + neste +
                    '}';
        }
    }

    // Hashlengde
    private final int hashLengde;

    // Hashtabell, pekere til lister
    private final hashNode[] hashTabell;

    // Antall elementer lagret i tabellen
    private int n;

    // Antall kollisjoner ved innsetting
    private int antKollisjoner;

    // KonstruktÃ¸r
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashChained(int lengde) {
        hashLengde = lengde;
        hashTabell = new hashNode[lengde];
        n = 0;
        antKollisjoner = 0;
    }


    // Debugging
    public hashNode[] getHashTabell() {
        return hashTabell;
    }

    // Returnerer load factor
    public float loadFactor() {
        return ((float) n) / hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData() {
        return n;
    }

    // Returnerer antall kollisjoner ved innsetting
    public int antKollisjoner() {
        return antKollisjoner;
    }

    // Hashfunksjon
    int hash(String S) {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med kjeding
    //
    void insert(String S) {
        // Beregner hashverdien
        int h = hash(S);
        System.out.println("Hash: " + h);

        // Ã˜ker antall elementer som er lagret
        n++;

        // Sjekker om kollisjon
        if (hashTabell[h] != null)
            antKollisjoner++;

        // Setter inn ny node fÃ¸rst i listen
        hashTabell[h] = new hashNode(S, hashTabell[h]);
    }

    // SÃ¸king etter tekststreng i hashtabell med kjeding
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S) {
        // Finner listen som S skal ligge i
        hashNode hN = hashTabell[hash(S)];

        // Leter gjennom listen
        while (hN != null) {
            // Har vi funnet tekststrengen?
            if (hN.data.compareTo(S) == 0)
                return true;
            // PrÃ¸ver neste
            hN = hN.neste;
        }
        // Finner ikke strengen, har kommet til slutten av listen
        return false;
    }


    // ------- IMPLEMENTERT AV MEG -------
    // Har fungert i alle testene jeg har gjort. Kunne laget en mer fleksibel måte å slette ting(med tanke på hvordan jeg kaller den i main),
    // men var enklere å teste med den samme verdien hver gang. Og hadde rett og slett ikke tid til mer.
    // Kun tested ved sletting av et element eksekevering
    public void remove(String S) {
        // Kilde: https://www.youtube.com/watch?v=gfFn-OXxcgU

        if (!search(S)) {
            System.out.println("Strengen finnes ikke i tabellen og kan derfor ikke slettes");
            return;
        }

        System.out.println("Sletter '" + S + "'");

        hashNode head = hashTabell[hash(S)];


        while (head != null && head.data.equals(S)) {
            head = head.neste;
        }

        hashNode current = head;

        while (current != null && current.neste != null) {
            if (current.neste.data.equals(S)) {
                current.neste = current.neste.neste;
                break;
            } else {
                current = current.neste;
            }
        }
    }


    // Enkelt testprogram:
    //
    // * Hashlengde gis som input pÃ¥ kommandolinjen
    //
    // * Leser tekststrenger linje for linje fra standard input
    //   og lagrer dem i hashtabellen
    //
    // * Skriver ut litt statistikk etter innsetting
    //
    // * Tester om sÃ¸k fungerer for et par konstante verdier
    //

    // ------- IMPLEMENTER AV MEG -------
    // Er noen endringer her gjort av meg.
    // Valgte å ikke bruke biler eksempelet da det er enklere å bekrefte at noe er slettet når det er få verdier i tabellen
    // Ctrl+d kan brukes for å slutte å ta input og la programmet fortsette
    public static void main(String[] argv) {
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 0;
        Scanner input = new Scanner(System.in);
        try {
            if (argv.length != 1)
                throw new IOException("Feil: Hashlengde mÃ¥ angis");
            hashLengde = Integer.parseInt(argv[0]);
            if (hashLengde < 1)
                throw new IOException("Feil: Hashlengde mÃ¥ vÃ¦re stÃ¸rre enn 0");
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }

        // Lager ny hashTabell
        hashChained hC = new hashChained(hashLengde);

        // Leser input og hasher alle linjer
        while (input.hasNext()) {
            hC.insert(input.nextLine());
            // Debugging
            System.out.println("Hashtabell:" + Arrays.toString(hC.getHashTabell()));
        }

        // Har testet her med hashlengde 3000 og verdiene 'Z', 'sad' og 'asd' da de har samme hashverdi
        hC.remove("Z");
        hC.remove("Z");
        System.out.println("-----------------");
        System.out.println("Tabell etter sletting: " + Arrays.toString(hC.getHashTabell()));

        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hC.antData());
        System.out.printf("Load factor : %5.3f\n", hC.loadFactor());
        System.out.println("Kollisjoner : " + hC.antKollisjoner());

        // Et par enkle sÃ¸k
        /*
        String S = "Volkswagen Karmann Ghia";
        if (hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

        */

    }
}
