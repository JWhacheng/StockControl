/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import beans.Usuario;
import controlador.principal.CMenu;
import frame.usuario.UIUsuario;
import static gestordeinventarios.Principal.user;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JosephAWB
 */
public class CUsuario implements IUsuario{
    
    private UIUsuario ventana;
    private ArrayList<Usuario> usuarios;
    
    public CUsuario()
    {
        usuarios = Usuario.getLista();
        ventana = new UIUsuario(this);
    }

    @Override
    public void menu() 
    {
        new CMenu();
        ventana.dispose();
    }

    @Override
    public void registrar() 
    {
        new CUsuarioIns();
        ventana.dispose();
    }

    @Override
    public void cargar(JTable tblRegistros) 
    {
        DefaultTableModel model = (DefaultTableModel) tblRegistros.getModel();
        model.setRowCount(0);
        
        String permiso = "";
        for(int i = 0; i < usuarios.size(); i++)
        {
            if (usuarios.get(i).getUsrPer().equals("1"))
                permiso = "Administrador";
            else
                permiso = "Usuario";
            
            model.addRow(new Object[]{  usuarios.get(i).getUsrDni(),
                                        usuarios.get(i).getUsrIde(),
                                        usuarios.get(i).getUsrNom(),
                                        usuarios.get(i).getUsrApe(),
                                        permiso});
        }
    }

    @Override
    public void modificar(JTable tblRegistros) 
    {
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {
            CUsuarioMod us;
            Usuario u = usuarios.get(i);
            if(user.getUsrDni().equals("70582570"))
            {
                us = new CUsuarioMod(u.getUsrDni());
                ventana.dispose();
            }
            else if(user.getUsrDni().equals("02671798"))
            {
                if(!(u.getUsrDni().equals("70582570")))
                {
                    us = new CUsuarioMod(u.getUsrDni());
                    ventana.dispose();
                }else
                    JOptionPane.showMessageDialog(null, "No puede modificar este registro", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(!(u.getUsrDni().equals("70582570") || u.getUsrDni().equals("02671798")))
                {
                    us = new CUsuarioMod(u.getUsrDni());
                    ventana.dispose();   
                }else
                    JOptionPane.showMessageDialog(null, "No puede modificar este registro", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }else
        {
            JOptionPane.showMessageDialog(null, "Seleccione un registro a modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
        }   
    }

    @Override
    public void eliminar(JTable tblRegistros) 
    {
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {
            if (!(usuarios.get(i).getUsrDni().equals(user.getUsrDni()) || usuarios.get(i).getUsrDni().equals("70582570") || usuarios.get(i).getUsrDni().equals("02671798")))
            {
                Usuario u = usuarios.get(i);
                if(JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    u.eliminar();
                    ventana.dispose();
                    new CUsuario();
                }
            }else
            {
                JOptionPane.showMessageDialog(null, "No puedes eliminar este registro", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }else
        {
            JOptionPane.showMessageDialog(null, "Seleccione un registro a eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
  
    public void seleccionarFila(JTextField buscar, JComboBox tipo, JTable tblRegistros)
    {
        TableModel tableModel = tblRegistros.getModel();
        String dato = buscar.getText();
        String filtro = String.valueOf(tipo.getSelectedItem());
        System.out.println(String.valueOf(tipo.getSelectedItem()));
              
        int col;
        int column = tableModel.getColumnCount();
        for(col = 0; col < column; col++)
            if(filtro.compareTo(tableModel.getColumnName(col)) == 0)
                break;
        int row;
        try
        {
            int rowC = tableModel.getRowCount();
            for(row = 0; row <= rowC; row++)
                if(dato.toLowerCase().compareTo(((String) tableModel.getValueAt(row, col)).toLowerCase()) == 0)
                    break;
                
            if(row == 0)
                tblRegistros.changeSelection(0,0,false,true);
            else
                tblRegistros.getSelectionModel().setSelectionInterval(row - 1, row);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "No se encontraron los datos buscados", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
