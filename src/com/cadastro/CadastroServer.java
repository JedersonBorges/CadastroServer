package com.cadastro;

import java.io.*;
import java.net.*;
import java.util.*;

public class CadastroServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            System.out.println("Servidor iniciado na porta 4321...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Cliente conectado!");

                new CadastroThread(clientSocket).start(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CadastroThread extends Thread {
    private Socket clientSocket;

    public CadastroThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String login = (String) in.readObject();
            String senha = (String) in.readObject();


            if (validarCredenciais(login, senha)) {
                out.writeObject("Usuário conectado com sucesso");
                out.flush();

                String comando = (String) in.readObject();
                if ("L".equals(comando)) {
                    List<String> produtos = Arrays.asList("Banana", "Laranja", "Manga");
                    for (String produto : produtos) {
                        out.writeObject(produto);
                        out.flush();
                    }
                }
            } else {
                out.writeObject("Credenciais inválidas");
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean validarCredenciais(String login, String senha) {
        return "op1".equals(login) && "op1".equals(senha);
    }
}
