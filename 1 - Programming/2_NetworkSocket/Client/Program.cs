using System.Net;
using System.Net.Sockets;

class Client
{
    static void Main(string[] args)
    {
        try
        {
            // Create a TcpClient
            Int32 port = 13356;
            String host = "localhost";
            using TcpClient client = new TcpClient(host, port);

            // Get a client stream for reading and writing
            NetworkStream stream = client.GetStream();

            // Send the message to the connected TcpServer
            for (int packetNo = 1; packetNo < 11; packetNo++)
            {
                Byte[] data = System.Text.Encoding.ASCII.GetBytes($"Hello! This is packet {packetNo}");
                stream.Write(data, 0, data.Length);

                Console.WriteLine($"Sent: Hello! This is packet {packetNo}");

                // Buffer to store the response bytes
                data = new Byte[256];

                // String to store the response ASCII representation
                String responseData = String.Empty;

                // Read the first batch of the TcpServer response bytes
                Int32 bytes = stream.Read(data, 0, data.Length);
                responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                Console.WriteLine($"Received: {responseData}");
                Thread.Sleep(5000);

            }
        }
        catch (ArgumentNullException e)
        {
            Console.WriteLine($"SocketException: {e}");
        }
        catch (SocketException e)
        {
            Console.WriteLine($"SocketException: {e}");
        }

        Console.WriteLine("\n Press any key to exit program...");
        Console.ReadKey();
    }

}