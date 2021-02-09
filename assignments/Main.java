import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {

    public static void main(String[] args){
        int tidTotal;
        float landingGjennomsnitt;
        float taAvGjennomsnitt;

        System.out.println("Velkommen til Ola International");
        System.out.print("Antall tidsteg for simulering: ");
        tidTotal = Integer.parseInt(System.console().readLine());
        System.out.println("Forventet gjennomsnitt for landing: ");
        landingGjennomsnitt = Float.parseFloat(System.console().readLine());
        System.out.println("Forventet gjennomsnitt for avtagninger: ");
        taAvGjennomsnitt = Float.parseFloat(System.console().readLine());


        simulering(tidTotal, landingGjennomsnitt, taAvGjennomsnitt);



    }


    public static void simulering(int tidTotal, float landingGjennomsnitt, float taAvGjennomsnitt){
        int avvisteLanding = 0;
        int avvisteAvtagning = 0;
        int flyTotal = 0;
        int antallLanding = 0;
        int antallAdganger = 0;
        int ledigRullebane = 0;
        Random R = new Random();

        Queue<Fly> landingsQueue = new LinkedList<>();
        Queue<Fly> avtagningQueue = new LinkedList<>();

        for (int tid = 1; tid <= tidTotal;tid++) {
            int landing = getPoissonRandom(landingGjennomsnitt);
            System.out.println(tid + ": ");
            if (landing == 0) {
                System.out.println("    Ingen nye fly som kommer inn for landing.");
            }
            for (int i = 0; i < landing;i++ ) {
                if (landingsQueue.size() < 10) {
                    flyTotal++;
                    Fly fly =  new Fly(tid, flyTotal);
                    landingsQueue.add(fly);
                    System.out.println("    Fly " + fly.getFlyNummer()+ " klar for å lande.");
                    antallLanding++;
                }
                else {
                    avvisteLanding++;
                }
            }

            int taAv = getPoissonRandom(taAvGjennomsnitt);
            if (taAv == 0) {
                System.out.println("    Ingen nye fly som skal ta av.");
            }
            for (int x = 0; x < taAv;x++) {
                if (avtagningQueue.size() < 10) {
                    flyTotal++;
                    Fly fly1 = new Fly(tid, flyTotal);
                    avtagningQueue.add(fly1);
                    System.out.println("    Fly " + fly1.getFlyNummer() + " klar for å ta av.");
                    antallAdganger++;
                }
                else{
                    avvisteAvtagning++;
                }
            }


            if (!landingsQueue.isEmpty()) {
                System.out.println("    Fly " + landingsQueue.peek().getFlyNummer() + " landet, ventet i " + landingsQueue.peek().ventetid(tid) + " enheter");
                landingsQueue.remove();
                continue;
            }
            else if (!avtagningQueue.isEmpty()){
                System.out.println("    Fly " + avtagningQueue.peek().getFlyNummer() + " tok av, ventet i " + avtagningQueue.peek().ventetid(tid) + " enheter");
                avtagningQueue.remove();
                continue;
            }
            else{
                ledigRullebane++;
            }
        }


        System.out.println("-----------------------------");
        System.out.println("Antall tidssteg i simuleringen: " + tidTotal);
        System.out.println("Antall fly behandlet: " + flyTotal);
        System.out.println("Antall fly landet: " + antallLanding);
        System.out.println("Antall fly tatt av: " + antallAdganger);
        System.out.println("Antall aviste avtagning: " + avvisteAvtagning);
        System.out.println("Antall aviste landinger: " + avvisteLanding);
        System.out.println("Antall fly i landingskøen: " + landingsQueue.size());
        System.out.println("Antall fly i avtagningskøen: " + avtagningQueue.size());
        System.out.println("Antall tidsenheter rullebane var ledig: " + ledigRullebane);
        System.out.println("\n");
        System.out.println("Velkommen tilbake til Ola International! ");
        System.out.println("-----------------------------");    
    }



    private static int getPoissonRandom(double x) {
        Random r = new Random();
        double L = Math.exp(-x);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public static class Fly {
        private int ankomst;
        private int flyNummer;

        public Fly(int ankomst, int flyNummer){
            this.ankomst = ankomst;
            this.flyNummer = flyNummer;
        }

        public int ventetid(int tid){
            return tid - ankomst;
        }

        public int getFlyNummer() {
            return flyNummer;
        }
    }
}