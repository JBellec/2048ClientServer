package Client;

import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Client {

    public static void main (String [] args)
            throws IOException, InterruptedException {

        InetSocketAddress hostAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8189);
        SocketChannel client = SocketChannel.open(hostAddress);

        System.out.println("Client sending messages to server...");

        // Send messages to server
		
        String [] messages = new String [] {"Time goes fast.", "What now?", "quit"};

        for (int i = 0; i < messages.length; i++) {

            byte [] message = new String(messages [i]).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);

            System.out.println(messages [i]);
            buffer.clear();
            Thread.sleep(3000);
        }
        
        // lecture sur la socketchannel
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		  int nBytes = client.read(rcvbuf);
		  rcvbuf.flip();
		  Charset charset = Charset.forName("us-ascii");
		  CharsetDecoder decoder = charset.newDecoder();
		  String res = decoder.decode(rcvbuf).toString();
		  System.out.println(res);
    }
}