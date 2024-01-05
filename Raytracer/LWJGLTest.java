import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import java.io.File;

//import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import java.io.File;

//import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LWJGLTest {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        //-Djava.library.path=C:\Users\reidj\Git Projects\RayEngine\Raytracer\libs
        //-Djava.library.path=C:/Users/reidj/Git Projects/RayEngine/Raytracer/libs
        //System.setProperty("org.lwjgl.librarypath", new File("libs\\lwjgl-glfw.jar\\").getAbsolutePath());
        // String libPath = new File("lwjgl3").getAbsolutePath();
        // File libsDirectory = new File(libPath);
        //System.out.println("Setting org.lwjgl.librarypath to: " + libPath);
        //System.setProperty("org.lwjgl.librarypath", libPath);
        
         // Check if the directory exists
        // if (libsDirectory.exists() && libsDirectory.isDirectory()) {
            // // List all files in the directory
            // File[] files = libsDirectory.listFiles();

            // // Print the names of the files
            // if (files != null) {
                // for (File file : files) {
                    // //System.out.println("File: " + file.getName());
                // }
            // } else {
                // System.out.println("No files found in the 'libs' directory.");
            // }
        // } else {
            // System.out.println("The 'libs' directory does not exist.");
        // }

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        long window = glfwCreateWindow(WIDTH, HEIGHT, "LWJGL Test", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window on the screen
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable v-sync

        // Make the window visible
        glfwShowWindow(window);

        // Set up OpenGL
        GL.createCapabilities();

        // Set the clear color (background color)
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Main loop
        while (!glfwWindowShouldClose(window)) {
            // Render
            glClear(GL_COLOR_BUFFER_BIT);
            
            // Draw a colored triangle (example)
            glBegin(GL_TRIANGLES);
            glColor3f(1.0f, 0.0f, 0.0f); // Red
            glVertex2f(-0.6f, -0.5f);
            
            glColor3f(0.0f, 1.0f, 0.0f); // Green
            glVertex2f(0.6f, -0.5f);
            
            glColor3f(0.0f, 0.0f, 1.0f); // Blue
            glVertex2f(0.0f, 0.5f);
            glEnd();
            
            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        // Terminate GLFW
        glfwTerminate();
    }
}