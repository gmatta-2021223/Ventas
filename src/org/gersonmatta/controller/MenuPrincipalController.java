/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gersonmatta.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.gersonmatta.system.Principal;

/**
 *
 * @author TheSn
 */
public class MenuPrincipalController implements Initializable{

    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaCliente(){
    
        escenarioPrincipal.ventanaCliente();
    
    }
    
    public void ventanaEmpleado(){
    
        escenarioPrincipal.ventanaEmpleado();
    
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
}
