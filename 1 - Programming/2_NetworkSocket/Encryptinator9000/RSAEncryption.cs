﻿using System;
using System.Numerics;
using System.Security.Cryptography;
using System.Net.Sockets;
using System.Net;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace Encryptinator9000
{
    public class RSAEncryption
    {
        public static Random rnd = new Random();

        private readonly byte[] _Key = new byte[256];

        private readonly TcpClient client;
        private readonly NetworkStream stream;
        private readonly int nBitsInKey = 2048;

        public event EventHandler<byte[]> RecievedData;

        public RSAEncryption(TcpClient _client)
        {
            client = _client;
            stream = client.GetStream();

            ExchangeServer();

            ListenForData();
        }

        public RSAEncryption(IPAddress ip, int port)
        {
            client = new TcpClient();
            client.Connect(ip, port);
            stream = client.GetStream();

            ExchangeClient();

            ListenForData();
        }

        public static byte[] EncryptByte(byte[] message, byte[] key)
        {
            int messageLength = message.Length;
            int keyLength = key.Length;
            
            byte shiftBy;

            for (int i = 0, j = 0; i < messageLength; i++, j++)
            {
                if (j == keyLength) j = 0;
                shiftBy = (byte)(Math.Pow(key[j], 2) % 8);
                message[i] = (byte)((message[i] << shiftBy) | (message[i] >> (8 - shiftBy)));
            }
            return message;
        }

        public static byte[] DecryptByte(byte[] message, byte[] key)
        {
            int messageLength = message.Length;
            int keyLength = key.Length;
            byte shiftBy;

            for (int i = 0, j = 0; i < messageLength; i++, j++)
            {
                if (j == keyLength) j = 0;
                shiftBy = (byte)(Math.Pow(key[j], 2) % 8);
                message[i] = (byte)((message[i] >> shiftBy) | (message[i] << (8 - shiftBy)));
            }
            return message;
        }


        private void ExchangeServer()
        {
            BigInteger g;
            BigInteger n;

            // Generate random prime numbers for g and n
            using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider(nBitsInKey))
            {
                RSAParameters parameters = rsa.ExportParameters(true);

                byte[] d = parameters.D;
                byte[] modulus = parameters.Modulus;

                if (BitConverter.IsLittleEndian)
                {
                    Array.Reverse(d);
                    Array.Reverse(modulus);
                }

                g = new BigInteger(d, true);
                n = new BigInteger(modulus, true);
            }
            BigInteger a = RandomBigInt(nBitsInKey);
            BigInteger ang = BigInteger.ModPow(g, a, n);

            InitKeyShare initKey = new InitKeyShare(n, g, ang);
            stream.Write(ObjectToByteArray(initKey));

            byte[] message = GetMessage();
            BigInteger gb = ByteArrayToObject<KeyShare>(message).abgn;
            byte[] key = BigInteger.ModPow(gb, a, n).ToByteArray();
            Array.Copy(key, _Key, _Key.Length);
        }

        private void ExchangeClient()
        {
            BigInteger b = RandomBigInt(nBitsInKey);

            byte[] message = GetMessage();
            InitKeyShare initData = ByteArrayToObject<InitKeyShare>(message);

            BigInteger bgn = BigInteger.ModPow(initData.g, b, initData.n);

            stream.Write(ObjectToByteArray(new KeyShare(bgn)));

            byte[] key = BigInteger.ModPow(initData.abgn, b, initData.n).ToByteArray();
            Array.Copy(key, _Key, _Key.Length);
        }

        public void Close()
        {
            client.Close();
        }

        public async void WriteData(byte[] data)
        {
            await stream.WriteAsync(EncryptByte(data, _Key));
        }

        public byte[] GetMessage()
        {
            byte[] buffer = new byte[256];

            int nBytesRead = stream.Read(buffer);
            int available = client.Available;

            byte[] data = new byte[available + nBytesRead];
            Array.Copy(buffer, data, nBytesRead);
            if (stream.DataAvailable)
                _ = stream.Read(data, nBytesRead, available);

            return data;
        }

        private async void ListenForData()
        {
            byte[] buffer = new byte[256];

            while (client.Connected)
            {
                int nBytesRead = await stream.ReadAsync(buffer);
                int available = client.Available;

                byte[] data = new byte[available + nBytesRead];
                Array.Copy(buffer, data, nBytesRead);
                if (stream.DataAvailable)
                    _ = await stream.ReadAsync(data, nBytesRead, available);
                byte[] decryptedData = DecryptByte(data, _Key);
                RecievedData?.Invoke(this, decryptedData);
            }
        }

        private BigInteger RandomBigInt(int nBits)
        {
            byte[] bytes = new byte[nBits / 8];
            rnd.NextBytes(bytes);

            return new BigInteger(bytes, true);
        }
        private T ByteArrayToObject<T>(byte[] arrBytes) where T : class
        {
            using MemoryStream memStream = new MemoryStream();
            memStream.Write(arrBytes, 0, arrBytes.Length);
            memStream.Seek(0, SeekOrigin.Begin);
            return (T)new BinaryFormatter().Deserialize(memStream);
        }
        private byte[] ObjectToByteArray(object obj)
        {
            using MemoryStream ms = new MemoryStream();
            new BinaryFormatter().Serialize(ms, obj);
            return ms.ToArray();
        }
    }


    [Serializable()]
    internal class KeyShare
    {
        public BigInteger abgn;

        public KeyShare(BigInteger abgn)
        {
            this.abgn = abgn;
        }
    }

    [Serializable()]
    internal class InitKeyShare : KeyShare
    {
        public BigInteger n;
        public BigInteger g;

        public InitKeyShare(BigInteger n, BigInteger g, BigInteger mix) : base(mix)
        {
            this.n = n;
            this.g = g;
        }
    }
}
