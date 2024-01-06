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
    private int[] pixelBuffer;

    public CPURenderer()
    {
        // nothing here(for now)
    }

    public int[] renderCPU(Camera camera)
    {
        //set up pixelBuffer
        int width = camera.getPixelWidth();
        int height = camera.getPixelHeight();
        pixelBuffer = new int[width * height];

        //fill it with nothing
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = raytrace1D(x, y, width, height, camera);
                pixelBuffer[y * width + x] = pixelColor.getRGB(); // Store the color in the buffer
            }
        }

        return pixelBuffer;
    }

    private Color raytrace1D(int x, int y,int width, int height, Camera camera)
    {
        Color pixelColor = new Color(255, 0, 255);

        //use the index of the x and y to get a 3d vector
        //kind of hte ray rotation on each axis?
        if(y == 0)
        {
            Transform cameraTrans = camera.gameObject().transform();
            Vector3 cameraPos = cameraTrans.position();
            Vector3 cameraRot = cameraTrans.rotation();
            Scene cameraScene = cameraTrans.getScene();

            //actually shoot out rays
            //calculate the horizontal angle of the ray by adjusting the range, then addd player rotation

            float angle = (x * camera.getFOV() / width) - 45 + cameraRot.x;

            //calculate the unit vector of the ray direction
            Vector3 rayDir = new Vector3(
                    (float)Math.cos(Math.toRadians(angle)), 0f, (float)Math.sin(Math.toRadians(angle)));

            //fetch all the mesh faces in the scene
            Face[] faces = cameraScene.getGeometry();

            for(int i = 0; i < faces.length; i++)
            {
                //compute the determinant
                // det = e1 . (rayDir x e2)
                float det = faces[i].getEdge1().dotProduct(rayDir.crossProduct(faces[i].getEdge2()));

                //check for parralel rays(not super neccesary)
                //if the determinant is close to zero
                if(det == 0.0f)
                {
                    break;
                }
                
                //compute Barycentric Cooridinates
                //u = (rayDir . (rayOrig - vert0))/det
                //v = ((rayOrig - vert0) . (e1 x rayDir))/det
                float u = (rayDir.dotProduct(cameraPos.subtract(faces[i].getPoint0()))) / det;
                float v = ((cameraPos.subtract(faces[i].getPoint0())).dotProduct(faces[i].getEdge1().crossProduct(rayDir))) / det;
                
                //check intersection conditions
                //u and v are between (0, 1) and u+v <= 1
                System.out.println(faces.length);
                if(u > 0 && u < 1 && v > 0 && v < 1 && u + v <= 1)
                {
                    //the intersection point is inside the triangle
                    pixelColor = new Color(255, 0, 0);
                }
            }
        }
        else
        {
            //just copy first row
            pixelColor = new Color(pixelBuffer[x]);
        }

        return pixelColor;
    }

    private Color randomPixel(int x, int y,int width, int height)
    {
        Color pixelColor = new Color(
                (int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)
            ); // random color
        if(y % 8 == 0 || (y+1) % 8 == 0|| (y-1) % 8 == 0)
        {
            pixelColor = new Color(
                (int)(Math.random() * 100), (int)(Math.random() * 100), (int)(Math.random() * 100)
            ); // random color
        }
        return pixelColor;
    }
}
