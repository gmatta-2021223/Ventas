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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.gersonmatta.bean.Cliente;
import org.gersonmatta.db.Conexion;
import org.gersonmatta.system.Principal;

public class ClienteController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, ELIMINAR, EDITAR, GUARDAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cliente> listaCliente;

    @FXML
    private TextField txtCodigoCliente;
    @FXML
    private TextField txtDPICliente;
    @FXML
    private TextField txtNombresCliente;
    @FXML
    private TextField txtDireccionCliente;
    @FXML
    private TextField txtEstadoCliente;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    @FXML
    private GridPane grpFechas;

    @FXML
    private TableView tblCliente;

    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colDPICliente;
    @FXML
    private TableColumn colNombresCliente;
    @FXML
    private TableColumn colDireccionCliente;
    @FXML
    private TableColumn colEstadoCliente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cargarDatos();
        desactivarControles();

    }

    public void cargarDatos() {

        tblCliente.setItems(getCliente());
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
        colDPICliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("DPICliente"));
        colNombresCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombresCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccionCliente"));
        colEstadoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("estado"));

    }

    public ObservableList<Cliente> getCliente() {

        ArrayList<Cliente> lista = new ArrayList<Cliente>();

        try {

            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {

                lista.add(new Cliente(
                        resultado.getInt("codigoCliente"),
                        resultado.getString("DPICliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("estado")
                ));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return listaCliente = FXCollections.observableArrayList(lista);

    }

    public void seleccionarElemento() {

        if (tblCliente.getSelectionModel().getSelectedItem() != null) {

            txtCodigoCliente.setText(String.valueOf(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtDPICliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getDPICliente());
            txtNombresCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getNombresCliente());
            txtDireccionCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getDireccionCliente());
            txtEstadoCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getEstado());

        }else{
        
            JOptionPane.showMessageDialog(null, "El registro seleccionado está vacío.", "Fila vacia", JOptionPane.WARNING_MESSAGE);
            
        
        }

    }

    public void nuevo() {
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                
                txtCodigoCliente.setEditable(false);
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                
                tblCliente.setDisable(true);
                
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
                
                tblCliente.setDisable(false);
                
                cargarDatos();
                
                tipoDeOperacion = operaciones.NINGUNO;
                
                break;
        
        
        }

    }

    public void guardar() {
        
        Cliente registro = new Cliente();
        registro.setDPICliente(txtDPICliente.getText());
        registro.setNombresCliente(txtNombresCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setEstado(txtEstadoCliente.getText());
        
        try{
        
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?)}");
            procedimiento.setString(1, registro.getDPICliente());
            procedimiento.setString(2,registro.getNombresCliente());
            procedimiento.setString(3, registro.getDireccionCliente());
            procedimiento.setString(4, registro.getEstado());
            procedimiento.execute();
            listaCliente.add(registro);
        
        }catch(Exception e){
        
            e.printStackTrace();
        
        }

    }

    public void eliminar() {
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    
                    try{
                    
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                        procedimiento.setInt(1, ((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
                        procedimiento.execute();
                        listaCliente.remove(tblCliente.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                    
                    }catch(Exception e){
                    
                        e.printStackTrace();
                        System.out.println("Error al eliminar el Cliente");
                    
                    }
                    
                }
                
                break;
                
            case GUARDAR:
                
                limpiarControles();
                desactivarControles();
                
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                
                tblCliente.setDisable(false);
                
                cargarDatos();
                
                tipoDeOperacion = operaciones.NINGUNO;
                
                break;
        
        
        }

    }

    public void editar() {
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                activarControles();
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                
                tipoDeOperacion = operaciones.ACTUALIZAR;
                
                break;
                
            case ACTUALIZAR:
                
                actualizar();
                
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                
                cargarDatos();
                
                tipoDeOperacion = operaciones.NINGUNO;
                
                break;
        
        
        }

    }

    public void actualizar() {
        
        try{
        
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?,?,?,?,?)}");
            Cliente registro = (Cliente)tblCliente.getSelectionModel().getSelectedItem();
            
            registro.setDPICliente(txtDPICliente.getText());
            registro.setNombresCliente(txtNombresCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setEstado(txtEstadoCliente.getText());
            
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getDPICliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getEstado());
            procedimiento.execute();
            
        
        }catch(Exception e){
        
            e.printStackTrace();
            System.out.println("Error al actualizar el Cliente");
        
        }

    }

    public void reporte() {
        
        switch(tipoDeOperacion){
            case NINGUNO:
                break;
                
            case ACTUALIZAR:
                
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                
                cargarDatos();
                
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        
        }

    }

    public void imprimirReporte() {

    }

    public void activarControles() {

        txtCodigoCliente.setEditable(true);
        txtDPICliente.setEditable(true);
        txtNombresCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtEstadoCliente.setEditable(true);

    }

    public void desactivarControles() {

        txtCodigoCliente.setEditable(false);
        txtDPICliente.setEditable(false);
        txtNombresCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtEstadoCliente.setEditable(false);

    }

    public void limpiarControles() {

        txtCodigoCliente.clear();
        txtDPICliente.clear();
        txtNombresCliente.clear();
        txtDireccionCliente.clear();
        txtEstadoCliente.clear();
        tblCliente.getSelectionModel().clearSelection();

    }

    public void ventanaMenuPrincipal() {

        escenarioPrincipal.ventanaMenuPrincipal();

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
