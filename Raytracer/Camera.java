import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
/**
 * The Camera will 
 */
public class Camera extends Component
{
    private float FOV; //feild of view
    private int pixelWidth; //width of the camera resultion
    private int pixelHeight; //height of the camera resoltuion

    //aspect ratio is essentially the value used to calculate the
    //vertical FOV relative to the horizontal FOV 
    private static final float APSECT_RATIO = 0.5625f;// 9(height):16(width)

    public Camera(GameObject gameObject, float FOV, int pixelWidth, int pixelHeight)
    {
        super(gameObject);
        this.FOV = FOV;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    public Camera(GameObject gameObject, float FOV) //will make a camera of monitor resulution
    {
        this(gameObject, FOV, -1, -1); //90 FOV default
        
        //LWJGL stuff
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor()); //get primary moniter info
        this.setPixelWidth(vidMode.width()); //make camera width monitor width
        this.setPixelHeight(vidMode.height()); //make camera height monitor height
    }

    public Camera(GameObject gameObject) //will make a 90 deg FOV camera of monitor resulution
    {
        this(gameObject, 90f); //90 FOV default
    }

    //getter setter
    public float getFOV(){return FOV;}

    public void setFOV(float FOV){this.FOV = FOV;}

    public int getPixelWidth(){return pixelWidth;}

    public void setPixelWidth(int pixelWidth){this.pixelWidth = pixelWidth;}

    public int getPixelHeight(){return pixelHeight;}

    public void setPixelHeight(int pixelHeight){this.pixelHeight = pixelHeight;}
}
