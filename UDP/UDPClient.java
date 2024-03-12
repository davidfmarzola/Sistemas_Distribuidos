import java.net.*;
import java.io.*;

// O cliente UDP envia uma mensagem para o servidor e obtém uma resposta - fluxo de comunicação entre processos através de uma rede de computadores

public class UDPClient {

	public static void requestAndResponse(String input) throws Exception{
		DatagramSocket aSocket = new DatagramSocket();
		InetAddress aHost = InetAddress.getByName("localhost"); // destination hostname
		int serverPort = 6789;
		byte[] m = input.getBytes(); // message contents
		DatagramPacket request = new DatagramPacket(m, input.length(), aHost, serverPort);
		System.out.println("Enviando pacote UDP para " + "localhost" + ":" + serverPort);
		aSocket.send(request);

		byte[] buffer = new byte[1024];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		aSocket.receive(reply); // está parando aqui porque está aguardando uma resposta do servidor
		System.out.println("Pacote UDP recebido...");
		System.out.println("Reply: " + new String(reply.getData()));
	}

	public static void leituraDaEntrada() throws IOException {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Digite o texto a ser enviado ao servidor: ");
		String input = inFromUser.readLine();
		try {
			requestAndResponse(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void armazenarMensagens() throws IOException {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		leituraDaEntrada();
		int value;
		do {
			System.out.println("1 - Escrever outra mensagem \n0 - Encerrar");
			value = Integer.parseInt(inFromUser.readLine());
			if (value == 1) {
				leituraDaEntrada();
			} else if (value != 0)
				System.out.println("Opção inválida!");
		} while (value != 0);
	}

	public static void main(String args[]) {

		long startTime = System.currentTimeMillis();

		// args give message contents (args[0]) and destination hostname (args[1])
		DatagramSocket aSocket = null; // soquetes udp lado cliente
		try {
			aSocket = new DatagramSocket();

			armazenarMensagens();

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
				long endTime = System.currentTimeMillis();
        		long duration = (endTime - startTime);
				System.out.println("Tempo de execução: " + duration + " milissegundos");
		}
	}
}