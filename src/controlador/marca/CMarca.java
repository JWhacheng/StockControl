/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.marca;

import com.mxrck.autocompleter.TextAutoCompleter;

import beans.Marca;
import controlador.principal.CMenu;
import frame.marca.UIMarca;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JosephAWB
 */
public class CMarca implements IMarca
{
    
    private UIMarca ventana;
    private ArrayList<Marca> marcas;
    
    public CMarca()
    {
        marcas = Marca.getLista();
        ventana = new UIMarca(this);
    }

    @Override
    public void cargar(JTable tblRegistros) 
    {
        DefaultTableModel model = (DefaultTableModel) tblRegistros.getModel();
        model.setRowCount(0);
        int unidadesSize = marcas.size();
        
        for(int i = 0; i < unidadesSize; i++)
            model.addRow(new Object[]{  marcas.get(i).getMarCod(),
                                        marcas.get(i).getMarNom()});
    }

    @Override
    public void menu() 
    {
        new CMenu();
        ventana.dispose();
    }

    @Override
    public void insertar() 
    {
        new CMarcaIns();
        ventana.dispose();
    }

    @Override
    public void modificar(JTable tblRegistros) 
    {
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {
            Marca m = marcas.get(i);
            new CMarcaMod(m.getMarCod());
            ventana.dispose();
        }
        else
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
            Marca m = marcas.get(i);
            if(JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    m.eliminar();
                    new CMarca();
                    ventana.dispose();  
                }
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un registro a eliminar", "ERROR", JOptionPane.ERROR_MESSAGE); 
    }

    @Override
    public void buscarMarca(JTextField buscar, JTable tablaMarca) 
    {
       TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( buscar );
       textAutoAcompleter.setMode(0-1); // Prefijo
       textAutoAcompleter.setCaseSensitive(false); //No sensible a mayúsculas
       TableModel tableModel = tablaMarca.getModel();
       String filtro = "Marca";
       int i;
       
        for(i = 0; i < tableModel.getColumnCount(); i++)
        {
            if(filtro.compareTo(tableModel.getColumnName(i)) == 0)
                break;
        }
        
        for(int k = 0; k < tableModel.getRowCount(); k++)
        {
            textAutoAcompleter.addItem(tableModel.getValueAt(k, i));
        }
       
    }

    @Override
    public void seleccionarFila(JTextField buscar, JTable tablaMarca) 
    {
       TableModel tableModel = tablaMarca.getModel();
       String dato = buscar.getText();
       String filtro = "Marca";
       int col;
       
       for(col = 0; col < tableModel.getColumnCount(); col++)
        {
            if(filtro.compareTo(tableModel.getColumnName(col)) == 0)
                break;
        }
        
        int row;
        try
        {
            for(row = 0; row < tableModel.getRowCount(); row++)
            {
                if(dato.compareTo((String) tableModel.getValueAt(row, col)) == 0)
                    break;
            }

            if(row == 0)
                tablaMarca.changeSelection(0,0,false,true);
            else
                tablaMarca.getSelectionModel().setSelectionInterval(row - 1, row);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "No se encontraron los datos buscados", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
       
    }
}
