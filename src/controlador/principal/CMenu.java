/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.principal;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import frame.principal.UIMenu;
import static gestordeinventarios.Principal.user;
import controlador.consulta.CEntrada;
import controlador.consulta.CExistencia;
import controlador.consulta.CSalida;
import controlador.kardex.CKardex;
import controlador.marca.CMarca;
import controlador.producto.CProducto;
import controlador.usuario.CUsuario;

/**
 *
 * @author JosephAWB
 */
public class CMenu implements IMenu{
    
    private UIMenu ventana;

    public CMenu()
    {
        ventana = new UIMenu(this);
    }
    
    @Override
    public void cargar(JTextField txtNombre, JTextField txtDni, JLabel lblPermisos, JButton btnUsuario, JButton btnExistencia, JButton btnEntrada, JButton btnSalida, JButton btnMarca, JButton btnKardex, JButton btnProducto) 
    {
        txtNombre.setText(user.getUsrNom() + " " + user.getUsrApe());
        txtDni.setText("DNI N° " + user.getUsrDni());
        String permiso = "";
        if (user.getUsrPer().equals("1"))
            permiso = "Administrador";
        else
            permiso = "Usuario";
        lblPermisos.setText(permiso);
        if (user.getUsrPer().equals("0"))
        {
            btnUsuario.setEnabled(false);
            btnMarca.setEnabled(false);
            btnKardex.setEnabled(false);
            btnProducto.setEnabled(false);
        }
        
    }

    @Override
    public void cerrarSesion() 
    {
        new CLogin();
        ventana.dispose();
    }

    @Override
    public void marca() 
    {
        new CMarca();
        ventana.dispose();
    }

    @Override
    public void usuario() 
    {
        new CUsuario();
        ventana.dispose();
    }

    @Override
    public void configuracion() 
    {
        new CConfiguracion(ventana);
    }

    @Override
    public void kardex() 
    {
        new CKardex();
        ventana.dispose();
    }

    @Override
    public void existenciaProducto() 
    {
        new CExistencia();
        ventana.dispose();
    }

    @Override
    public void salida() 
    {
        new CSalida();
        ventana.dispose();
    }

    @Override
    public void entrada() 
    {
        new CEntrada();
        ventana.dispose();
    }
    
    public void producto()
    {
        new CProducto();
        ventana.dispose();
    }
    
}
