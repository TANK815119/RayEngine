
/**
 * The Vector3 as written here will act as a
 * simple data type that will be used throught
 * the project in multiple different ways
 */
public class Vector3
{
    public float x;
    public float y;
    public float z;
    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3()
    {
        this(0, 0, 0);
    }

    // getter, setter that make the Vecotr3 act like an array
    public float get(int i)
    {
        try
        {
            switch(i)
            {
                case 0: return x;
                case 1: return y;
                case 2: return z;
                default: throw new Exception("Vector3 out of bounds");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    public void set(int i, float value)
    {
        try
        {
            switch(i)
            {
                case 0: x = value; break;
                case 1: y = value; break;
                case 2: z = value; break;
                default: throw new Exception("Vector3 out of bounds");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
