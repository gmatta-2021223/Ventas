package org.gersonmatta.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.gersonmatta.bean.Empleado;
import org.gersonmatta.db.Conexion;
import org.gersonmatta.system.Principal;

public class EmpleadoController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,GUARDAR,CANCELAR,ACTUALIZAR,NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    private ObservableList<Empleado> listaEmpleado;
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaMenuPrincipal(){
    
        escenarioPrincipal.ventanaMenuPrincipal();
    
    }
    
    
    
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtDPIEmpleado;
    @FXML private TextField txtNombresEmpleado;
    @FXML private TextField txtTelefonoEmpleado;
    @FXML private TextField txtEstadoEmpleado;
    @FXML private TextField txtUsuarioEmpleado;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @FXML private TableView tblEmpleado;
    
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colDPIEmpleado;
    @FXML private TableColumn colNombresEmpleado;
    @FXML private TableColumn colTelefonoEmpleado;
    @FXML private TableColumn colEstadoEmpleado;
    @FXML private TableColumn colUsuarioEmpleado;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        desactivarControles();
        cargarDatos();

    }
    
    public void cargarDatos(){
    
        tblEmpleado.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,Integer>("codigoEmpleado"));
        colDPIEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("DPIEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoEmpleado"));
        colEstadoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("estado"));
        colUsuarioEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("usuario"));
         
    }
    
    public void seleccionarElemento(){
    
        txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtDPIEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getDPIEmpleado());
        txtNombresEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtTelefonoEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
        txtEstadoEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getEstado());
        txtUsuarioEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getUsuario());
    
    }
    
    public ObservableList<Empleado> getEmpleado(){
    
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        
        try{
        
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
            
                lista.add(new Empleado(
                
                        resultado.getInt("codigoEmpleado"),
                        resultado.getString("DPIEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("telefonoEmpleado"),
                        resultado.getString("estado"),
                        resultado.getString("usuario")
                        
                
                ));
            
            }
        
        }catch(Exception e){
        
            e.printStackTrace();
        
        }
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
        
    
    }
    
    public void nuevo(){
    
       switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                txtCodigoEmpleado.setEditable(false);
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                
                tblEmpleado.setDisable(true);
                
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                
                tblEmpleado.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
           
           
       }
        
    
    }
    
    public void guardar(){
    
        Empleado registro = new Empleado();
        
        
        registro.setDPIEmpleado(txtDPIEmpleado.getText());
        registro.setNombresEmpleado(txtNombresEmpleado.getText());
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setEstado(txtEstadoEmpleado.getText());
        registro.setUsuario(txtUsuarioEmpleado.getText());
        
        try{
        
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?)}");
            
            procedimiento.setString(1, registro.getDPIEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getTelefonoEmpleado());
            procedimiento.setString(4, registro.getEstado());
            procedimiento.setString(5, registro.getUsuario());
            
            procedimiento.execute();
            
            listaEmpleado.add(registro);
            
        
        }catch(Exception e){
        
            e.printStackTrace();
            System.out.println("Error al agregar el Empleado");
        
        }
    
    }
    
    public void eliminar(){
    
        switch(tipoDeOperacion){
            case NINGUNO:
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el registro?", "Eliminar registro", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    
                    try{
                    
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                        procedimiento.setInt(1, Integer.parseInt(txtCodigoEmpleado.getText()));
                        procedimiento.execute();
                        listaEmpleado.remove(tblEmpleado.getSelectionModel().getFocusedIndex());
                        
                    }catch(Exception e){
                    
                        e.printStackTrace();
                        System.out.println("No se pudo eliminar el Empleado");
                        
                    }
                    
                }
                
               break;
            case GUARDAR:
                break;
           
           
       }
    
    }
    
    public void editar(){
    
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                txtCodigoEmpleado.setEditable(false);
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                
                tipoDeOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                
                tipoDeOperacion = operaciones.NINGUNO;
                
                cargarDatos();
                
                break;
           
           
       }
    
    }
    
    public void actualizar(){
    
        try{
        
            Empleado registro = (Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
            
            registro.setDPIEmpleado(txtDPIEmpleado.getText());
            registro.setNombresEmpleado(txtNombresEmpleado.getText());
            registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
            registro.setEstado(txtEstadoEmpleado.getText());
            registro.setUsuario(txtUsuarioEmpleado.getText());
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?,?,?,?,?,?)}");
            
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getDPIEmpleado());
            procedimiento.setString(3, registro.getNombresEmpleado());
            procedimiento.setString(4, registro.getTelefonoEmpleado());
            procedimiento.setString(5, registro.getEstado());
            procedimiento.setString(6, registro.getUsuario());
            
            procedimiento.execute();
            
        
        }catch(Exception e){
        
            e.printStackTrace();
            System.out.println("No se pudo actualizar el Empleado");
        
        }
    
    }
    
    public void reporte(){
    
        switch(tipoDeOperacion){
            case NINGUNO:
               break;
            case GUARDAR:
                break;
           
           
       }
    
    }
    
    public void imprimirReporte(){}
    
    
    public void limpiarControles(){
    
        txtCodigoEmpleado.clear();
        txtDPIEmpleado.clear();
        txtNombresEmpleado.clear();
        txtTelefonoEmpleado.clear();
        txtEstadoEmpleado.clear();
        txtUsuarioEmpleado.clear();
    
    }
    
    public void activarControles(){
    
        txtCodigoEmpleado.setEditable(true);
        txtDPIEmpleado.setEditable(true);
        txtNombresEmpleado.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        txtEstadoEmpleado.setEditable(true);
        txtUsuarioEmpleado.setEditable(true);
    
    }
    
    public void desactivarControles(){
    
        txtCodigoEmpleado.setEditable(false);
        txtDPIEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtTelefonoEmpleado.setEditable(false);
        txtEstadoEmpleado.setEditable(false);
        txtUsuarioEmpleado.setEditable(false);
    
    }
    
    
    


    
    
    
}
