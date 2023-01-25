// See https://aka.ms/new-console-template for more information
public class InstanceVar
{
    int variable = 0;
    public static InstanceVar Instance = new InstanceVar();

    public void Write(int input)
    {
        lock (_lock)
        {
            variable = input;
        }
    }
    public int Read()
    {
        lock (_lock)
        {
            return variable;
        }
    }

    private object _lock = new object();
}

public class ClassVar
{
    static int variable = 0;
    public static ClassVar Class = new ClassVar();

    public void Write(int input)
    {
        lock (_lock)
        {
            variable = input;
        }
    }
    public int Read()
    {
        lock (_lock)
        {
            return variable;
        }
    }

    private object _lock = new object();
}