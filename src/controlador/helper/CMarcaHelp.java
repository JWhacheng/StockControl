/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.helper;

import com.mxrck.autocompleter.TextAutoCompleter;
import beans.Marca;
import frame.helper.UIMarcaHelp;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JosephAWB
 */
public class CMarcaHelp implements IMarcaHelp
{
    private UIMarcaHelp ventana;
    private ArrayList<Marca> marcas;
    private JTextField txtMarCod;
    private JTextField txtMarDes;
    
    public CMarcaHelp(JFrame owner, JTextField txtMarCod, JTextField txtMarDes)
    {
        marcas = Marca.getLista();
        this.txtMarCod = txtMarCod;
        this.txtMarDes = txtMarDes;
        ventana = new UIMarcaHelp(owner, true, this);
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
    public boolean aceptar(JTable tblRegistros) 
    {
        boolean seleccionado = false;
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {        
            Marca m = marcas.get(i);
            txtMarCod.setText(m.getMarCod());
            txtMarDes.setText(m.getMarNom());
            seleccionado = true;
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);
        return seleccionado;
    }

    @Override
    public void seleccionarFila(JTextField buscar, JTable tblRegistros) 
    {
        TableModel tableModel = tblRegistros.getModel();
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
                tblRegistros.changeSelection(0,0,false,true);
            else
                tblRegistros.getSelectionModel().setSelectionInterval(row - 1, row);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "No se encontraron los datos buscados", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void buscarMarca(JTextField buscar, JTable tblRegistros) 
    {
       TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( buscar );
       textAutoAcompleter.setMode(0-1); // Prefijo
       textAutoAcompleter.setCaseSensitive(false); //No sensible a mayúsculas
       TableModel tableModel = tblRegistros.getModel();
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
}
