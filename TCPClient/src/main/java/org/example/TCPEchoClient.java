package org.example;

import java.net.*; // Importa classes para suporte a redes
import java.io.*; // Importa classes para suporte a entrada/saída

public class TCPEchoClient {
    public static void main (String[] args) throws IOException {
        if ((args.length < 2) || (args.length > 3))
            throw new IllegalArgumentException("Parâmetro(s): <Servidor> <Palavra> [<Porta>]");
        String server = args[0]; // Lê o endereço do servidor a partir dos argumentos da linha de comando

        // Converte a String de entrada em bytes usando a codificação de caracteres padrão
        byte[] byteBuffer = args[1].getBytes();
        int servPort=(args.length == 3) ? Integer.parseInt(args[2]) : 7; // Lê a porta do servidor (se especificada) ou usa a porta padrão 7
        System.out.println("Antes de conectar "+server+" "+servPort);

        // Cria um socket que está conectado ao servidor na porta especificada
        Socket socket = new Socket(server, servPort);
        System.out.println("Conectado ao servidor... enviando string de eco");
        InputStream in = socket.getInputStream(); // Obtém a entrada de dados do servidor
        OutputStream out = socket.getOutputStream(); // Obtém a saída de dados para o servidor
        out.write(byteBuffer); // Envia a string codificada para o servidor

        // Recebe a mesma string de volta do servidor
        int totalBytesRcvd = 0;
        int bytesRcvd;
        while (totalBytesRcvd < byteBuffer.length) {
            if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd, byteBuffer.length - totalBytesRcvd)) == -1){
                socket.close();
                throw new SocketException("Conexão fechada prematuramente");
            }
            totalBytesRcvd += bytesRcvd;
        }
        System.out.println("Recebido: " + new String(byteBuffer)); // Exibe a string recebida do servidor
        socket.close(); // Fecha o socket
    }
}
