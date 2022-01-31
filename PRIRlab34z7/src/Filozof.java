import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Filozof
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        System.out.println("Liczba filozofów w symulacji (od 2 do 100)");
        int filozof = s.nextInt();
        System.out.println(System.lineSeparator()+"wybór jednego z 3 wariantów do uruchomienia, (1,2,3) ");
        int method = s.nextInt();

        switch(method)
        {
            case 1:
                Filozof1.licz(filozof);
                break;
            case 2:
                Filozof2.licz(filozof);
                break;
            case 3:
                Filozof3.licz(filozof);
                break;
            default:
                System.out.println(System.lineSeparator()+"Nieprawidłowa wersja wybierz (1,2,3) ");
                System.exit(1);
        }
    }
}


class Filozof1 extends Thread
{
    static int MAX = 100;
    static Semaphore[] widelec = new Semaphore[MAX];
    int mojNum;

    public Filozof1(int nr)
    {
        mojNum = nr;
    }

    @Override
    public void run()
    {
        while(true)
        {
            // myslenie
            System.out.println("Myślę " + mojNum);

            try
            {
                Thread.sleep((long)(7000*Math.random()));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            widelec[mojNum].acquireUninterruptibly(); //L
            widelec[(mojNum+1)%MAX].acquireUninterruptibly(); //P

            // jedzenie
            System.out.println("Zaczyna jeść "+mojNum);
            try
            {
                Thread.sleep((long)(5000*Math.random()));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Konczy jeść "+mojNum);
            widelec[mojNum].release(); //L
            widelec[(mojNum+1)%MAX].release(); //P
        }
    }

    public static void licz(int max)
    {
        MAX = max;

        for (int i=0; i<MAX; i++)
        {
            widelec[i] = new Semaphore(1);
        }

        for (int i=0; i<MAX; i++)
        {
            new Filozof1(i).start();
        }
    }
}


class Filozof2 extends Thread
{
    static int MAX = 100;
    static Semaphore[] widelec = new Semaphore[MAX];
    int mojNum;

    public Filozof2(int nr)
    {
        mojNum=nr;
    }

    @Override
    public void run()
    {
        while(true)
        {
            //myslenie
            System.out.println("Myślę ¦ "+mojNum);
            try
            {
                Thread.sleep((long)(5000*Math.random()));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            if(mojNum == 0)
            {
                widelec[(mojNum+1)%MAX].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            }
            else
            {
                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum+1)%MAX].acquireUninterruptibly();
            }

            //jedzenie
            System.out.println("Zaczyna jeść "+mojNum);

            try
            {
                Thread.sleep((long)(3000*Math.random()));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Konczy jeść "+mojNum);

            widelec[mojNum].release();
            widelec[(mojNum+1)%MAX].release();

        }
    }

    public static void licz(int max)
    {
        MAX = max;

        for (int i=0; i<MAX; i++)
        {
            widelec[i] = new Semaphore(1);
        }

        for (int i=0; i<MAX; i++)
        {
            new Filozof1(i).start();
        }
    }
}


class Filozof3 extends Thread
{
    static int MAX = 100;
    static Semaphore[] widelec = new Semaphore [MAX];
    int mojNum;
    Random losuj;

    public Filozof3(int nr)
    {
        mojNum=nr ;
        losuj = new Random(mojNum);
    }

    @Override
    public void run()
    {
        while (true)
        {
            // myslenie
            System.out.println("Myślę ¦ " + mojNum);

            try
            {
                Thread.sleep((long)(5000*Math.random()));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            int strona = losuj.nextInt(2);
            boolean podnioslDwaWidelce = false;

            do
            {
                if(strona == 0)
                {
                    widelec[mojNum].acquireUninterruptibly();
                    if(!(widelec[(mojNum+1)%MAX].tryAcquire()))
                    {
                        widelec[mojNum].release();
                    }
                    else
                    {
                        podnioslDwaWidelce = true;
                    }
                }
                else
                {
                    widelec[(mojNum+1)%MAX].acquireUninterruptibly();
                    if(!(widelec[mojNum].tryAcquire()))
                    {
                        widelec[(mojNum+1)%MAX].release();
                    }
                    else
                    {
                        podnioslDwaWidelce = true;
                    }
                }
            } while (podnioslDwaWidelce == false);

            System.out.println("Zaczyna jeść "+mojNum);
            try
            {
                Thread.sleep((long)(3000*Math.random()));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Konczy jeść "+mojNum);
            widelec[mojNum].release();
            widelec[(mojNum+1)%MAX].release();
        }
    }

    public static void licz(int max)
    {
        MAX = max;

        for (int i=0; i<MAX; i++)
        {
            widelec[i] = new Semaphore(1);
        }

        for (int i=0; i<MAX; i++)
        {
            new Filozof1(i).start();
        }
    }
}