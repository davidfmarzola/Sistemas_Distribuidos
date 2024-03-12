import java.net.*;
import java.io.*;

// Posso executar o programa, fornecendo dois argumentos - args[0] e args [1]
// java TCPClient "Mensagem para o servidor" "localhost" 
public class TCPClient { // o código estabelece uma conexão TCP com um servidor, envia uma mensagem e exibe a resposta recebida do servidor.

    public static void leituraDaEntrada(DataOutputStream out) throws IOException{
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Digite o texto a ser enviado ao servidor: ");
        String input = inFromUser.readLine();
        out.writeUTF(input);
        System.out.println("Received: " + input);
    }

    public static void armazenarMensagens(DataOutputStream out) throws IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        leituraDaEntrada(out);
        int value;

        do {
            System.out.println("1 - Escrever outra mensagem \n0 - Encerrar");
            value = Integer.parseInt(inFromUser.readLine());
            if(value == 1){
                leituraDaEntrada(out);
            } else if(value != 0)
                System.out.println("Opção inválida!");
        } while(value != 0);
        
        
    }

    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        
        Socket s = null;
        try {
            int serverPort = 7896;
            String host = "localhost";          
            s = new Socket(host, serverPort); // Cria um socket TCP e tenta se conectar à porta ao servidor especificado - se conecta apenas se o servidor estiver em execução
            // DataInputStream in = new DataInputStream(s.getInputStream()); // fluxo de entrada
            DataOutputStream out = new DataOutputStream(s.getOutputStream()); // fluxo de saída
            armazenarMensagens(out);
        // tratamento dos possíveis erros durante a comunicação
        } catch (UnknownHostException e) { 
            System.out.println("Sock: " + e.getMessage());
        } catch (EOFException eof) {
            System.out.println("EOF: " + eof.getMessage());
        } catch (IOException io) {
            System.out.println("IO: " + io.getMessage());
        } finally { 
            if (s != null) {
                try {
                    s.close();
                    long endTime = System.currentTimeMillis();
                    long duration = (endTime - startTime);
                    System.out.println("Tempo de execução: " + duration + " milissegundos");
                } catch (IOException io) {
                    System.out.println("IO: " + io.getMessage());
                }
            }
        }
    }
}
