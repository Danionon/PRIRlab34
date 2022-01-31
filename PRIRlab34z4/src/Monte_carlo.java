import java.util.Random;
        import java.util.Scanner;

public class Monte_carlo extends Thread
{
    static int iteracje;
    static double promien;
    double x_start, y_start, x_koniec, y_koniec, wynik;
    int wsrodku;
    Random r;

    public Monte_carlo(int iter, double xx_start, double yy_start, double xx_koniec, double yy_koniec)
    {
        this.iteracje = iter;
        this.x_start = xx_start;
        this.y_start = yy_start;
        this.x_koniec = xx_koniec;
        this.y_koniec = yy_koniec;
        r = new Random();
        wynik = 0;
        wsrodku = 0;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < this.iteracje /4; i++)
        {
            double x = r.nextDouble();
            double y = r.nextDouble();

            if(Math.sqrt((Math.pow(x,2) + Math.pow(y,2))) <= 1)
            {
                wsrodku++;
            }
        }

        System.out.println("liczba w środku: "+ wsrodku +"  Wszystkie: "+(iteracje /4)+System.lineSeparator());
        wynik = wsrodku;
    }

    public static void main(String[] args)
    {
        Monte_carlo t1, t2, t3, t4;
        Scanner s = new Scanner(System.in);

        System.out.print("Podaj promien kola ");
        promien = s.nextDouble();

        System.out.print(System.lineSeparator()+"Podaj liczbe prób ");
        int i = s.nextInt();


        t1 = new Monte_carlo(i, promien, promien, 0, 0);
        t2 = new Monte_carlo(i, promien, promien, promien, 0);
        t3 = new Monte_carlo(i, promien, promien, 0, promien);
        t4 = new Monte_carlo(i, promien, promien, promien, promien);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try
        {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        double surface = (t1.wynik + t2.wynik + t3.wynik + t4.wynik)/ iteracje *Math.pow(2* promien, 2);
        double pi = 4*(t1.wynik + t2.wynik + t3.wynik + t4.wynik)/ iteracje;
        System.out.println("Pole kola = "+surface+System.lineSeparator()+"Pi = "+pi);
    }
}