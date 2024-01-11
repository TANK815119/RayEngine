import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
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
        cubeObject.addComponent(TriMesh.class);

        //--make a sphere object--
        GameObject sphereObject = new GameObject("sphere");
        scene.addRootGameObject(sphereObject);
        sphereObject.addComponent(Sphere.class);
        sphereObject.getComponent(Sphere.class).setRadius(0.25f);
        //--make a sphere2 object--
        GameObject sphere2Object = new GameObject("sphere");
        scene.addRootGameObject(sphere2Object);
        sphere2Object.addComponent(Sphere.class);
        sphere2Object.getComponent(Sphere.class).setRadius(0.25f);
        sphere2Object.transform().translate(0f, 0f, -2f);

        //--make a camera object--
        GameObject cameraObject = new GameObject("My Object");
        scene.addRootGameObject(cameraObject);
        //manipulate the transformto look at the cube
        //ill have to think about what the fuck that actually means
        //like what direction is 0 rotation even looking?
        cameraObject.transform().position(0f, 0f, 3f);
        cameraObject.transform().rotation(0f, 90f, 0f);
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

        init(camera, renderPipe);
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

    private static void init(Camera camera, RenderingPipeline renderPipe)
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

        // Create key callback
        // GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
                // @Override
                // public void invoke(long window, int key, int scancode, int action, int mods) {
                    // // Handle key events here
                    // if (key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS) {
                        // // Close the window or perform other actions
                    // }
                // }
            // };

        // // Set the key callback
        // GLFW.glfwSetKeyCallback(window, keyCallback);

        // Main loop
        while (!glfwWindowShouldClose(window)) {
            // Render
            glClear(GL_COLOR_BUFFER_BIT);

            //rotate the camera a little
            //camera.gameObject().transform().rotate(0.01f, 0.01f, 0.01f);
            // System.out.println(camera.gameObject().transform().rotation());
            Transform sphereTrans = camera.gameObject().transform()
                .getScene().getRootGameObject("sphere").transform();
            Transform cameraTrans = camera.gameObject().transform();
            
            //some camera math for look-based movement
            float cosMult = (float)Math.cos(Math.toRadians(cameraTrans.rotation().y));
            float sinMult = (float)Math.sin(Math.toRadians(cameraTrans.rotation().y));
            //move the camera a little
            if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
            {
                camera.gameObject().transform().translate(-0.1f * cosMult, 0f, -0.1f * sinMult);
            }
            if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
            {
                camera.gameObject().transform().translate(0.1f * cosMult, 0f, 0.1f * sinMult);
            }
            if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
            {
                camera.gameObject().transform().translate(-0.1f * sinMult, 0f, 0.1f * cosMult);
            }
            if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
            {
                camera.gameObject().transform().translate(0.1f * sinMult, 0f, -0.1f * cosMult);
            }
            if(glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS)
            {
                camera.gameObject().transform().rotate(0f, -5f, 0f);
            }
            if(glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS)
            {
                camera.gameObject().transform().rotate(0f, 5f, 0f);
            }
            if(glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS)
            {
                camera.gameObject().transform().rotate(0f, 0f, -5f);
            }
            if(glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS)
            {
                camera.gameObject().transform().rotate(0f, 0f, 5f);
            }
            if(glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS)
            {
                camera.gameObject().transform().translate(0f, 0.1f, 0f);
            }
            if(glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
            {
                camera.gameObject().transform().translate(0f, -0.1f, 0f);
            }
            
            if(glfwGetKey(window, GLFW_KEY_T) == GLFW_PRESS)
            {
                sphereTrans.translate(0f, 0.1f, 0f);
            }
            if(glfwGetKey(window, GLFW_KEY_G) == GLFW_PRESS)
            {
                sphereTrans.translate(0f, -0.1f, 0f);
            }
            System.out.println("position: " + camera.gameObject().transform().position());
            System.out.println("roatation: " + camera.gameObject().transform().rotation());

            render(camera, renderPipe.render(camera));
            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        //keyCallback.free();
        //mouseButtonCallback.free();
        // Terminate GLFW
        glfwTerminate();
    }
}
