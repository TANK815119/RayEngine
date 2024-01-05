
/**
 * Every Renderer will need to be able to:
 * (necessary?)store info in a CPU-readable 2d array if necessary
 * (necessary?) store into in a GPU-readable compute shader(later)
 * (not really take any information in its constructor)
 * have some method that spits out a CPU-readable 2d array
 * have some method that spits out a GPU-readable compute shader
 */
public interface Renderer
{
    public int[] renderCPU(Camera camera); //returns pixelBuffer
}
