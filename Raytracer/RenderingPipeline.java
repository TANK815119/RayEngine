import java.lang.reflect.Constructor;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryStack;
import java.awt.Color;
/**
 * The RenderingPipeline here won't do much
 * It'll essentially be the interface for the
 * use of different rendering techniques
 * itll be able to create the classes that represent
 * different techniques and itll be able to turn that information
 * either into an array of values(slow) or a compute shader(fast)
 * 
 * I probably don't need getter,setter for the renderer(probably)(for now)
 * having this pipeline structure like this may be overly compex
 * but I think it may be useful in the future
 */
public class RenderingPipeline <T extends Renderer>
{
    private T renderer;
    RenderingPipeline(Class<T> renderingTechnique)
    {
        try {
            // Get the constructor of the class
            //no parameters for the .getConstructor because I don't think the Renderers
            //will require any parameters in thei constructors
            //in fact, I may make them pretty much static classes
            Constructor<T> constructor = renderingTechnique.getConstructor();

            // Create a new instance using the constructor
            T rendererInstance = constructor.newInstance();

            renderer = rendererInstance;
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public int[] render(Camera camera)
    {
        return renderer.renderCPU(camera);
    }
    
    public static Vector3 colorToVector(int pixelRGB)
    {
        //decode the pixelColor into a Vector3 with rgb being from 0 to 1
        Vector3 pixelVec = new Vector3(1, 0, 1);
        
        //convert rgb into java.awt.color Color object
        Color pixelColor = new Color(pixelRGB);
        //getRed and assign to vec3(float)
        pixelVec.x = pixelColor.getRed() * 1f/255f;
        //getBlue and assign to vec3(float)
        pixelVec.y = pixelColor.getGreen() * 1f/255f;
        //getGreen and assign to vec3(float)
        pixelVec.z = pixelColor.getBlue() * 1f/255f;
        //for each, clamp to 0-1
        
        return pixelVec;
    }

    public static int createTexture(int[] pixelBuffer, int width, int height) {
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA8, // Assuming you want an 8-bit per channel texture
            width,
            height,
            0,
            0x80E1, // GL_BGRA constant value
            GL12.GL_UNSIGNED_INT_8_8_8_8_REV, // Use GL_UNSIGNED_INT_8_8_8_8_REV for ARGB
            pixelBuffer
        );

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        return textureId;
    }
}