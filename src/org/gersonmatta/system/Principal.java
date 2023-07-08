package org.gersonmatta.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.gersonmatta.controller.ClienteController;
import org.gersonmatta.controller.EmpleadoController;
import org.gersonmatta.controller.MenuPrincipalController;

/**
 *
 * @author
 *
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/gersonmatta/view/";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("JavaEEVentas");
        escenarioPrincipal.getIcons().add(new Image("/org/gersonmatta/image/Icono.png"));
        
        ventanaMenuPrincipal();
        escenarioPrincipal.show();

    }
    
    public void ventanaMenuPrincipal(){
    
        try{
            
            MenuPrincipalController ventanaMenuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",1300,650);
            ventanaMenuPrincipal.setEscenarioPrincipal(this);
            
        
        }catch(Exception e){
            
            e.printStackTrace();
        
        }
    
    }
    
    public void ventanaCliente(){
    
        try{
        
            ClienteController vistaCliente = (ClienteController)cambiarEscena("ClienteView.fxml",1300,650);
            vistaCliente.setEscenarioPrincipal(this);
            
        }catch(Exception e){
        
            e.printStackTrace();
        
        }
    
    }
    
    public void ventanaEmpleado(){
    
        try{
        
            EmpleadoController vistaEmpleado = (EmpleadoController)cambiarEscena("EmpleadoView.fxml",1300,650);
            vistaEmpleado.setEscenarioPrincipal(this);
        
        }catch(Exception e){
        
            e.printStackTrace();
        
        }
    
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception {

        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();

        return resultado;

    }

}
