/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.com.br;

import com.mycompany.sdshared.Id;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Cliente
 */
public class ServidorId {

    public static void main(String[] args) throws RemoteException, InterruptedException {
     
      
       
       Id identity= new IdentityManager();
       Id id = (Id)UnicastRemoteObject.exportObject(identity,0);
       Registry registry = LocateRegistry.createRegistry(1099);
      
       registry.rebind("idUsuario",id);
       
      
        
        }
    
    
}
