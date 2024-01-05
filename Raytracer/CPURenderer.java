import java.awt.Color;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 * Write a description of class CPURenderer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CPURenderer implements Renderer
{
    public CPURenderer()
    {
        // nothing here(for now)
    }

    public int[] renderCPU(Camera camera)
    {
        //set up pixelBuffer
        int width = camera.getPixelWidth();
        int height = camera.getPixelHeight();
        int[] pixelBuffer = new int[width * height];

        //fill it with nothing
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = new Color(
                        (int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)
                    ); // random color
                pixelBuffer[y * width + x] = pixelColor.getRGB(); // Store the color in the buffer
            }
        }

        //System.out.printf("%08X ", pixelBuffer[0]);

        return pixelBuffer;
    }

    public static void renderCPUShit(Camera camera)
    {
        float height = camera.getPixelHeight();
        float width = camera.getPixelWidth();
        GL11.glBegin(GL11.GL_POINTS);

        for (float y = -1; y < 1; y+=(2/height)) {
            for (float x = -1; x < 1; x+=(2/width)) {
                GL11.glColor3f((float)Math.random(), (float)Math.random(), (float)Math.random());
                GL11.glVertex2f(x, y);
            }
        }
        GL11.glEnd();
    }
}
