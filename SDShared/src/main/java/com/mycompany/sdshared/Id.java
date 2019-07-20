/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sdshared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Cliente
 */
public interface Id extends Remote {
    
   int getId() throws RemoteException;
    
}
