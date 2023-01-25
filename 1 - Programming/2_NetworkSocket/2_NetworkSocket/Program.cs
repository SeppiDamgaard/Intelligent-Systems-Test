using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;

class Server
{
    public static void Main()
    {
        TcpListener server = null;
        try
        {
            // Create a TcpListener
            int port = 13356;
            String host = "localhost";
            IPAddress localAddr = IPAddress.Parse(host);
            server = new TcpListener(localAddr, port);

            // Start listening for client requests
            server.Start();

            // Buffer for reading data
            Byte[] bytes = new Byte[256];

            // Enter the listening loop
            while (true)
            {
                Console.Write("Waiting for a connection... ");

                // Perform a blocking call to accept requests
                using TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Connected!");

                String data = null;

                // Get a stream object for reading and writing
                NetworkStream stream = client.GetStream();

                int i;

                // Loop to receive all the data sent by the client
                while ((i = stream.Read(bytes, 0, bytes.Length)) != 0)
                {
                    // Translate data bytes to a ASCII string
                    data = System.Text.Encoding.ASCII.GetString(bytes, 0, i);
                    Console.WriteLine($"Received: {data}");

                    // Process the data sent by the client
                    data = data.ToUpper();

                    byte[] msg = System.Text.Encoding.ASCII.GetBytes(data);

                    // Send back a response
                    stream.Write(msg, 0, msg.Length);
                    Console.WriteLine($"Sent: {data}");
                }
            }
        }
        catch (SocketException e)
        {
            Console.WriteLine($"SocketException: {e}");
        }
        finally
        {
            server.Stop();
        }

        Console.WriteLine("\nPress any key to exit program...");
        Console.ReadKey();
    }
}