import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Julia extends Thread
{
    final static int N = 4096;
    final static int CUTOFF = 64;
    static int[][] set = new int[N][N];

    int index;

    public Julia(int index)
    {
        this.index = index;
    }

    @Override
    public void run()
    {
        int begin = 0, end = 0;

        switch(index)
        {
            case 0:
                begin = 0;
                end = (N / 4) * 1;
                break;
            case 1:
                begin = (N / 4) * 1;
                end = (N / 4) * 2;
                break;
            case 2:
                begin = (N / 4) * 2;
                end = (N / 4) * 3;
                break;
            case 3:
                begin = (N / 4) * 3;
                end = N;
                break;
        }

        for (int i = begin; i < end; i++)
        {
            for (int j = 0; j < N; j++)
            {

                double cr = -0.25;//-0.2;
                double ci = 0.76;//0.65;
                double zr = i * (1.25 - -1.25) / N + -1.25;
                double zi = j * (1.25 - -1.25) / N + -1.25;
                int k = 0;

                while (k < CUTOFF && zr * zr + zi * zi < 4.0)
                {
                    double newr = cr + zr * zr - zi * zi;
                    double newi = ci + 2 * zr * zi;
                    zr = newr;
                    zi = newi;
                    k++;
                }

                set[i][j] = k;
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        Julia thread;

        long startTime = System.currentTimeMillis();

        for(int i=0; i<4; i++)
        {
            thread = new Julia(i);
            thread.start();
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Obliczenia trwało " + (endTime - startTime) + "ms");

        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int k = set[i][j];
                float levelr, levelg, levelb;
                if (k < CUTOFF)
                {
                    levelr = (float) k / CUTOFF;
                    levelg = (float) k / CUTOFF;
                    levelb = (float) k / CUTOFF;
                }
                else
                {
                    levelr = 0;
                    levelg = 0;
                    levelb = 0;
                }

                Color c = new Color(levelr, levelg, levelb);
                img.setRGB(i, j, c.getRGB());
            }
        }

        ImageIO.write(img, "PNG", new File("Julia.png"));
    }
}