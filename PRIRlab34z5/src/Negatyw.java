import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class Gray extends Thread
{
    BufferedImage image;
    int width;
    int height;

    public Gray(int index)
    {
        int begin = 0, end = 0;

        switch(index)
        {
            case 0:
                begin = 0;
                end = (width / 4) * 1;
                break;
            case 1:
                begin = (height / 4) * 1;
                end = (width / 4) * 2;
                break;
            case 2:
                begin = (height / 4) * 2;
                end = (width / 4) * 3;
                break;
            case 3:
                begin = (height / 4) * 3;
                end = width;
                break;
        }

        try
        {
            File input = new File("KampusUwB.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            for(int i=1; i<height-1; i++)
            {
                for(int j=1; j<width-1; j++)
                {
                    Color c = new Color(image.getRGB(j, i));
                    int red = (c.getRed());
                    int green = (c.getGreen());
                    int blue = (c.getBlue());

                    int final_red, final_green, final_blue;


                    final_red = 255-red;
                    final_green = 255-green;
                    final_blue = 255-blue;
                    Color newColor = new Color(final_red, final_green, final_blue);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }

            File output = new File("KampusUwBNegatyw.jpg");
            ImageIO.write(image, "jpg", output);
            System.out.println("Wątek "+index+" zakonczył obliczenia");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

public class Negatyw
{
    public static void main(String[] args) throws Exception
    {
        Gray g;
        long startTime = System.currentTimeMillis();

        for(int i=0; i<4; i++)
        {
            g = new Gray(i);
            g.start();
            g.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Obliczenie trwało " + (endTime - startTime) + "ms");
    }
}