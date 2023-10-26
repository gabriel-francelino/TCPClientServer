package org.example;

import java.net.*; // Importa classes para suporte a redes
import java.io.*; // Importa classes para suporte a entrada/saída

public class TCPEchoServer {
    private static final int BUFSIZE = 32; // Tamanho do buffer de recepção
    public static void main (String[] args) throws IOException {
        if (args.length != 1)
            throw new IllegalArgumentException("Parâmetro(s): <Porta>");

        // Tenta obter informações sobre o endereço local (host)
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Local Host:");
            System.out.println("\t" + address.getHostName()); // Nome do host
            System.out.println("\t" + address.getHostAddress()); // Endereço IP do host
        } catch (UnknownHostException e) {
            System.out.println("Não foi possível determinar o endereço deste host");
        }

        int servPort = Integer.parseInt(args[0]); // Lê a porta do servidor a partir dos argumentos da linha de comando

        // Cria um socket de servidor para aceitar solicitações de conexão do cliente
        ServerSocket servSock = new ServerSocket(servPort);
        int recvMsgSize; // Tamanho da mensagem recebida
        byte[] byteBuffer = new byte[BUFSIZE]; // Buffer de recepção

        for (;;) { // Executa indefinidamente, aceitando e atendendo conexões
            Socket clntSock = servSock.accept(); // Obtém a conexão do cliente
            System.out.println("Lidando com o cliente em " +
                    clntSock.getInetAddress().getHostAddress() + " na porta " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream(); // Obtém a entrada de dados do cliente
            OutputStream out = clntSock.getOutputStream(); // Obtém a saída de dados para o cliente

            // Recebe dados do cliente até que ele feche a conexão (indicado por -1 de retorno)
            while ((recvMsgSize = in.read(byteBuffer)) != -1)
                out.write(byteBuffer, 0, recvMsgSize); // Envia de volta os dados recebidos para o cliente

            clntSock.close(); // Fecha o socket do cliente
            // Terminamos com este cliente
        }
        /* NÃO ALCANÇADO - Este comentário indica que o código nunca alcançará esta linha, pois o loop anterior é executado indefinidamente. */
    }
}
