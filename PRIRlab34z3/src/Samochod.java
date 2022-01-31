import java.util.Random;

public class Samochod extends Thread
{
    private String nrRej;
    private int pojZbiornika;
    private int paliwo;

    public Samochod (String nr, int _pojZbiornika)
    {
        nrRej = nr;
        pojZbiornika = _pojZbiornika;
        paliwo = _pojZbiornika;
    }

    public void tankowanie (int _paliwo)
    {
        System.out.println("Samochod o numerze "+nrRej+" zatankował "+_paliwo+"L paliwa");
        paliwo+=_paliwo;
        run();
    }

    @Override
    public void run()
    {
        Random r = new Random();

        while(true)
        {
            System.out.println("Ilosc paliwa w samochodzie o numerze "+nrRej+" to "+paliwo+"L");

            if(paliwo < 3 && paliwo > 0)
            {
                System.out.println("Samochod o numerze "+nrRej+" musi zatankowac");
                tankowanie(r.nextInt(10)+2);
                break;
            }
            else if(paliwo <=0)
            {
                System.out.println("Samochod o numerze "+nrRej+" nie ma paliwa");
                stop();
            }

            paliwo--;

            try
            {
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

class TestSamochod
{
    static final int liczba_samochodow = 3;

    public static void main(String[] args)
    {
        String nr = "";
        Random r = new Random();

        for(int i = 0; i < liczba_samochodow; i++)
        {
            nr = "0"+(i+1);
            Samochod s = new Samochod(nr,r.nextInt(60)+30);
            s.start();
        }
    }
}