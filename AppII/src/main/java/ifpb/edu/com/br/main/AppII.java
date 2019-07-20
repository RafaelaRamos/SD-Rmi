/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.edu.com.br.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Cliente
 */
public class AppII {
    private static Semaphore sem = new Semaphore(1);
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        Controlador controlador = new Controlador();

        for (int i = 0; i < 100; i++) {
            System.out.println("new thread");
            Thread salvar = new Thread(controlador.salvar);
            Thread atualizar = new Thread(controlador.atualizar);
            Thread deletar = new Thread(controlador.deletar);

            sem.acquire();
            salvar.start();
            sem.release();
            //Thread.sleep(5000);
            atualizar.start();
            deletar.start();
        }
    }
}
