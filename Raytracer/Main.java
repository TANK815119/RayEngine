import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    public static void main(String args[])
    {
        Scene scene = new Scene();

        //--make a cube object--
        GameObject cubeObject = new GameObject("My Object");
        scene.addRootGameObject(cubeObject);
        cubeObject.addComponent(PlaneMesh.class);

        //--make a camera object--
        GameObject cameraObject = new GameObject("My Object");
        scene.addRootGameObject(cameraObject);
        //manipulate the transformto look at the cube
        //ill have to think about what the fuck that actually means
        //like what direction is 0 rotation even looking?
        cameraObject.transform().position(-2f, 0f, 0f);
        cameraObject.transform().rotation(0f, 0f, 0f);
        //the more bare-bones addCompnent method may be efficient here
        //cameraObject.addComponent(Camera.class);
        //I fucking hate the way the getComponent format is looking
        //mentions the camera class twice for safety ig
        //Camera camera = (Camera)cameraObject.getComponent(Camera.class);
        Camera camera = new Camera(cameraObject, 90f, 800, 600);
        cameraObject.addComponent(camera);

        //--make a light object--(later)

        RenderingPipeline renderPipe = new RenderingPipeline(CPURenderer.class);
        // put something here like
        // <graphicsAPI>.set(renderPipe.GPUCPURender(camera));

        initWindow(camera, renderPipe);
    }

    private static void drawQuad(float x, float y, float width, float height, float r, float g, float b) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(r, g, b);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);

        GL11.glEnd();
    }
    
    private static void render(Camera camera, int[] pixelBuffer)
    {
        //this must all be re-written
        float height = camera.getPixelHeight();
        float width = camera.getPixelWidth();
        int bufferIndex = 0;
        GL11.glBegin(GL11.GL_POINTS);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //assign color from buffer
                Vector3 vec = RenderingPipeline.colorToVector(pixelBuffer[bufferIndex]);
                GL11.glColor3f(vec.x, vec.y, vec.z);
                
                //assign texture position with the index and some math
                //first get to range of two, then subtract 1 to get range [-1, 1]
                //i could maybe throw an error here, but I wont cause my math is perfect
                float textureY = ((float)y / (height/2)) - 1;
                float textureX = ((float)x / (width/2)) - 1;
                GL11.glVertex2f(textureX, textureY);
                
                bufferIndex++;
            }
        }
        
        GL11.glEnd();
        //Renderer.renderCPU(camera);
    }
    
    private static void initWindow(Camera camera, RenderingPipeline renderPipe)
    {
        // Initialize GLFW
        GLFWErrorCallback errorCallback;
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window
        //System.out.println("Width: " + camera.getPixelWidth());
        //System.out.println("Height: " + camera.getPixelHeight());
        long window = glfwCreateWindow(camera.getPixelWidth(), camera.getPixelHeight(), "Raytracer", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window on the screen
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - camera.getPixelWidth()) / 2, (vidMode.height() - camera.getPixelHeight()) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable v-sync

        // Make the window visible
        glfwShowWindow(window);

        // Set up OpenGL
        GL.createCapabilities();

        // Set the clear color (background color)
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        //fun stugg
        boolean flip = false;
        
        // Main loop
        while (!glfwWindowShouldClose(window)) {
            // Render
            glClear(GL_COLOR_BUFFER_BIT);
            
            //rotate the camera a little
            //camera.gameObject().transform().rotate(0.01f, 0.01f, 0.01f);
            // System.out.println(camera.gameObject().transform().rotation());
            
            //move the camera a little
            if(!flip)
            {
                //camera.gameObject().transform().translate(-0.1f * 1f, 0f, 0f);
            }
            else
            {
                //camera.gameObject().transform().translate(0.1f * 1f, 0, 0f);
            }
            if(camera.gameObject().transform().position().x <= -6)
            {
                flip = true;
            }
            if(camera.gameObject().transform().position().x >= 6)
            {
                flip = false;
            }
            //System.out.println(camera.gameObject().transform().position());
            
            
            render(camera, renderPipe.render(camera));

            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        // Terminate GLFW
        glfwTerminate();
    }
}
