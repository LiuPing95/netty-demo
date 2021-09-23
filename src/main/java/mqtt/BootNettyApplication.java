package mqtt;





public class BootNettyApplication
{
    public static void main( String[] args )
    {
        // 启动  1883
        new BootNettyServer().startup();
    }
}