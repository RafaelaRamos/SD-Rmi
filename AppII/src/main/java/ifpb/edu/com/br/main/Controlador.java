package ifpb.edu.com.br.main;

import com.mycompany.sdshared.Id;
import ifpb.edu.com.br.usuario.Usuario;
import ifpb.edu.com.br.usuario.UsuarioService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controlador {

    private static ArrayBlockingQueue<Integer> bufferdelete;
    private static ArrayBlockingQueue<Integer> bufferatualizar;
    final long tempo = System.currentTimeMillis();

    //private static Semaphore sem = new Semaphore(1);

    private UsuarioService us = new UsuarioService();
    private Id id;
    Registry registry;

    Controlador() throws RemoteException, NotBoundException {
        this.registry = LocateRegistry.getRegistry(1099);
        this.id = (Id) this.registry.lookup("idUsuario");
        bufferdelete = new ArrayBlockingQueue<Integer>(5);
        bufferatualizar = new ArrayBlockingQueue<Integer>(5);
    }

    Runnable salvar = new Runnable() {

        @Override
        public void run() {
            try {
                //sem.acquire();

                Usuario u = new Usuario(id.getId(), "teste");
                while (true) {
                    if (us.salvar(u)) {
                        break;
                    } else {
                        u.setId(id.getId());
                    }
                }

                //sem.release();
                bufferatualizar.put(u.getId());
            } catch (InterruptedException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    Runnable atualizar = new Runnable() {

        @Override
        public void run() {
            try {
                int id = bufferatualizar.take();
                us.atualizar(id);
                bufferdelete.put(id);
                System.out.println("atualizou: " + id);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    Runnable deletar = new Runnable() {

        @Override
        public void run() {
            try {
                int id = bufferdelete.take();
                us.deletar(id);
                long tempofinal = System.currentTimeMillis();
                long total = tempofinal - tempo;
                System.out.println(total);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    };
}