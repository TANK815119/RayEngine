import java.awt.Color;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;

/**
 * Write a description of class CPURenderer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CPURenderer implements Renderer
{
    private int[] pixelBuffer;
    private float EPSILON = (float)Math.pow(10, -6);

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
                Color pixelColor = raytrace2D(x, y, width, height, camera);
                pixelBuffer[y * width + x] = pixelColor.getRGB(); // Store the color in the buffer
            }
        }

        return pixelBuffer;
    }

    private Color raytrace2D(int x, int y,int width, int height, Camera camera)
    {
        Color pixelColor = new Color(0, 0, 0);

        //use the index of the x and y to get a 3d vector
        //kind of hte ray rotation on each axis?
        Transform cameraTrans = camera.gameObject().transform();
        Vector3 cameraPos = cameraTrans.position();
        Vector3 cameraRot = cameraTrans.rotation();
        Scene cameraScene = cameraTrans.getScene();

        Vector3 rayDir = this.computeRayOrg(x, y, width, height, camera);

        //return new Vector3(cameraPos.x, cameraPos.y, cameraPos.z).add(rayDirectionCameraSpace);
        ArrayList<GameObject> sphereList = cameraScene.getGameObjectList(Sphere.class);
        //new Vector3(cameraPos.x, cameraPos.y, cameraPos.z).add(rayDir)
        pixelColor = intersectSpheres(rayDir, cameraPos, sphereList);

        if(pixelColor.getRed() != 255)
        {
            //fetch all the mesh faces in the scene
            Face[] faces = cameraScene.getGeometry();

            pixelColor = intersectTriangles(rayDir, cameraPos, faces);
        }

        return pixelColor;
    }

    private Vector3 computeRayGPT(int x, int y, int width, int height, Camera camera)
    {
        // Calculate normalized device coordinates (NDC)
        float ndcX = (2.0f * x) / width - 1.0f;
        float ndcY = 1.0f - (2.0f * y) / height;

        // Calculate the aspect ratio
        float aspectRatio = (float) width / height;

        // Calculate the ray direction in camera space
        float tanFOV = (float) Math.tan(Math.toRadians(camera.getFOV() / 2.0));
        float cameraSpaceX = ndcX * aspectRatio * tanFOV;
        float cameraSpaceY = ndcY * tanFOV;

        // Create a direction vector in camera space
        Vector3 rayDirectionCameraSpace = new Vector3(cameraSpaceX, cameraSpaceY, -1.0f);
        rayDirectionCameraSpace.normalize(); // Make sure the direction is normalized

        return rayDirectionCameraSpace;
    }

    private Vector3 computeRayOrg(int x, int y, int width, int height, Camera camera)
    {
        Transform cameraTrans = camera.gameObject().transform();
        Vector3 cameraPos = cameraTrans.position();
        Vector3 cameraRot = cameraTrans.rotation();

        //actually shoot out rays
        //calculate the horizontal angle of the ray by adjusting the range, then addd player rotation
        //-45f to make sure left side of monitor shoots rays out left of the display towards the negative z direction
        float angleHori = (x * camera.getFOV() / width) - 45f + cameraRot.y;

        //startrs at pos z rotation top and then paints down to the neg rotation at higher y  values
        float angleVert = 45f - (y * camera.getFOV() / height) + cameraRot.z;

        //calculate the unit vector of the ray direction
        // 0 degrees = the positive x direction
        // -90 degrees = the negative z direction
        Vector3 rayDir = new Vector3(
                (float)Math.cos(Math.toRadians(angleHori)),
                (float)Math.sin(Math.toRadians(angleVert)),
                (float)Math.sin(Math.toRadians(angleHori)));
        rayDir.normalize(); //make the vector a length of one

        // //check the intersection of array in middle of screen
        // Vector3 midVec = new Vector3(1f, 0f, 0f);
        // Vector3 difVec = midVec.subtract(rayDir);
        // if(Math.abs(difVec.dotProduct(difVec)) < EPSILON)
        // {
        // if(pixelColor.equals(new Color(0, 0, 0)))
        // {
        // System.out.println("intersection failed");
        // }
        // else
        // {
        // System.out.println("something else failed");
        // }
        // }
        return rayDir;
    }

    private Color intersectSpheres(Vector3 rayDir, Vector3 cameraPos,ArrayList<GameObject> sphereList)
    {
        Color pixelColor = new Color(0, 0, 0);

        //loop though the shpere objects for intersection testing
        for(int i = 0; i < sphereList.size(); i++)
        {
            Vector3 spherePos = sphereList.get(i).transform().position();
            float sphereRadius = sphereList.get(i).getComponent(Sphere.class).getRadius();

            if(isIntersection(rayDir, cameraPos, spherePos, sphereRadius))
            {
                pixelColor = new Color(255, 0, 0);
            }

            // //formula to deetermine if a ray intersects with a sphere
            // //at*t + bt + x = 0
            // //t is the prameter along the ray
            // //a = rayDir . rayDir
            // //b = 2 * ((cameraPos - spherePos) . rayDir)
            // //c = (cameraPos - spherePos) . (cameraPos - spherePos)  -  sphereRadius * sphereRadius
            // Vector3 oc = cameraPos.subtract(spherePos); // vector from ray origin to sphere center
            // float a = rayDir.dotProduct(rayDir);
            // float b = oc.dotProduct(rayDir) * 2f;
            // float c = oc.dotProduct(oc) - (float)Math.pow(sphereRadius, 2);

            // //if the discriminate of the equation is < 0, there are no solutions
            // float discriminant = b*b - 4 * a * c;
            // if(discriminant < 0)
            // {
            // //let it loop
            // continue;
            // }

            // //t represents the value along the rayDir vector that the intersections occur
            // //i wont do that for now as all I needed to know was is there is an intesrsection
            // // Calculate solutions for t
            // float t1 = (-b - (float) Math.sqrt(discriminant)) / (2 * a);
            // float t2 = (-b + (float) Math.sqrt(discriminant)) / (2 * a);

            // // Return the minimum positive solution
            // if (t1 > 0 && (t2 <= 0 || t1 < t2)) {
            // //return t1;
            // pixelColor = new Color(255, 0, 0);
            // break; //early exit
            // } else if (t2 > 0) {
            // //return t2;
            // pixelColor = new Color(255, 0, 0);
            // break; //early exit
            // } else {
            // //return -1;
            // //let it loop
            // continue;
            // }
        }

        return pixelColor;
    }

    private static boolean isIntersection(Vector3 rayDir, Vector3 cameraPos, Vector3 spherePos, float sphereRadius) {
        // Vector from the camera position to the sphere position
        Vector3 cameraToSphere = spherePos.subtract(cameraPos);

        // Calculate the distance along the ray direction to the point closest to the sphere
        float t = cameraToSphere.dotProduct(rayDir);

        // Calculate the closest point on the ray to the sphere
        Vector3 closestPoint = cameraPos.add(rayDir.multiply(t));

        // Calculate the distance between the closest point and the sphere center
        float distanceToSphereCenter = closestPoint.subtract(spherePos).magnitude();

        // Check if the distance to the sphere center is less than or equal to the sphere radius
        return distanceToSphereCenter <= sphereRadius;
    }

    private Color intersectTriangles(Vector3 rayDir, Vector3 cameraPos, Face[] faces)
    {
        Color pixelColor = new Color(0, 0, 0);
        for(int i = 0; i < faces.length; i++)
        {
            //compute the determinant
            // det = e1 . (rayDir x e2)
            float det = faces[i].getEdge1().dotProduct(rayDir.crossProduct(faces[i].getEdge2()));
            faces[i].getNormal();
            //check for parralel rays(not super neccesary)
            //if the determinant is close to zero

            if(Math.abs(det) < EPSILON)
            {
                break;
            }

            //compute Barycentric Cooridinates
            //u = (rayDir . (rayOrig - vert0))/det
            //v = ((rayOrig - vert0) . (e1 x rayDir))/det
            float u = (rayDir.dotProduct(cameraPos.subtract(faces[i].getPoint0()))) / det;
            float v = 
                ((cameraPos.subtract(faces[i].getPoint0()))
                    .dotProduct
                    (faces[i].getEdge1().crossProduct(rayDir)))
                / det;

            //check intersection conditions
            //u and v are between (0, 1) and u+v <= 1
            //System.out.println(u + " " + v);
            if(u > 0 && u < 1 && v > 0 && v <  1 && u + v <= 1)
            {
                //the intersection point is inside the triangle
                //System.out.println(u + " hit " + v);
                pixelColor = new Color(255, 0, 0);
                break; //early exit
            }
            else
            {
                //System.out.println(u + " not hit " + v);
            }
        }
        return pixelColor;
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
            //-45f to make sure left side of monitor shoots rays out left of the display towards the negative z direction
            float angle = (x * camera.getFOV() / width) - 45f + cameraRot.y;

            //calculate the unit vector of the ray direction
            // 0 degrees = the positive x direction
            // -90 degrees = the negative z direction
            Vector3 rayDir = new Vector3(
                    (float)Math.cos(Math.toRadians(angle)), 0f, (float)Math.sin(Math.toRadians(angle)));

            //fetch all the mesh faces in the scene
            Face[] faces = cameraScene.getGeometry();

            for(int i = 0; i < faces.length; i++)
            {
                //compute the determinant
                // det = e1 . (rayDir x e2)
                float det = faces[i].getEdge1().dotProduct(rayDir.crossProduct(faces[i].getEdge2()));
                faces[i].getNormal();
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
                float v = 
                    ((cameraPos.subtract(faces[i].getPoint0()))
                        .dotProduct
                        (faces[i].getEdge1().crossProduct(rayDir)))
                    / det;

                //check intersection conditions
                //u and v are between (0, 1) and u+v <= 1
                //System.out.println(u + " " + v);
                if(u >= 0 && u <= 1 && v >= 0 && v <= 1 && u + v <= 1)
                {
                    //the intersection point is inside the triangle
                    //System.out.println(u + " hit " + v);
                    pixelColor = new Color(255, 0, 0);
                }
                else
                {
                    //System.out.println(u + " not hit " + v);
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
