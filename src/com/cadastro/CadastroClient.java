package com.cadastro;

import java.io.*;
import java.net.*;

public class CadastroClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4321);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Conectado ao servidor...");


            out.writeObject("op1");
            out.writeObject("op1");
            out.flush();


            String response = (String) in.readObject();
            System.out.println(response);

            if ("Usuário conectado com sucesso".equals(response)) {

                out.writeObject("L");
                out.flush();

 
                Object produto;
                while ((produto = in.readObject()) != null) {
                    System.out.println(produto);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
