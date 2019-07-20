/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.com.br;

import com.mycompany.sdshared.Id;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cliente
 */
public class IdentityManager implements Id {

    private UsuarioService dao = new UsuarioService();
    private static Semaphore sem = new Semaphore(1);

    @Override
    public synchronized int getId() {

        try {

            sem.acquire();
            int idUsuario = dao.IdUsuario();
            sem.release();
            return idUsuario;

        } catch (SQLException ex) {
            Logger.getLogger(IdentityManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(IdentityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
