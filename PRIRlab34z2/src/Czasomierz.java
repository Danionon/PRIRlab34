public class Czasomierz
{
    private int h = 0;
    private int m = 0;
    private int s = 0;

    public void start()
    {
        System.out.println("h:m:s");
        while(true)
        {
            try
            {
                System.out.println(h+":"+m+":"+s);
                Thread.sleep(1000);
                s++;
                licz();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void licz()
    {
        if(s==60)
        {
            m++;
            s = 0;
        }

        if(m==60)
        {
            h++;
            m = 0;
        }
    }

    public static void main(String[] args)
    {
        Czasomierz czas = new Czasomierz();
        czas.start();

    }
}