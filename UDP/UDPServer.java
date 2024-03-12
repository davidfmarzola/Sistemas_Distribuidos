import java.io.IOException;
import java.net.*;

class UDPServer {
	public static void main(String args[]) throws Exception {

		int porta = 6789;
		int numConn = 1;
		
		@SuppressWarnings("resource")
        DatagramSocket serverSocket = new DatagramSocket(porta);

		byte[] receiveData = new byte[1024]; // o tamanho do buffer usado para receber os dados deve ser maior que o tamanho dos dados recebidos
		byte[] sendData = new byte[1024];

		try {
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				System.out.println("Servidor esperando por datagrama UDP na porta " + porta);
				serverSocket.receive(receivePacket); // está parando aqui
				System.out.print("Datagrama UDP [" + numConn++ + "] recebido...");

				String sentence = new String(receivePacket.getData());
				System.out.println(sentence);
				
				InetAddress IPAddress = receivePacket.getAddress();

				int port = receivePacket.getPort();

				String capitalizedSentence = sentence.toUpperCase();

				sendData = capitalizedSentence.getBytes();

				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				
				System.out.print("Enviando " + capitalizedSentence + "...");

				serverSocket.send(sendPacket);
				System.out.println("OK\n");
			}
		} catch (IOException e) {
            System.err.println("Erro ao receber pacote UDP: " + e.getMessage());
        } 

        serverSocket.close();
	}
}