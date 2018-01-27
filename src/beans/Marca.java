/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static gestordeinventarios.Principal.con;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JosephAWB
 */
public class Marca {
    
    private String marCod;
    private String marNom;
    
    public Marca(){
        this ("-1", "NULL");
    }
    
    public Marca(String marCod, String marNom){
        this.marCod = marCod;
        this.marNom = marNom;
    }

    /**
     * @return the marCod
     */
    public String getMarCod() {
        return marCod;
    }

    /**
     * @param marCod the marCod to set
     */
    public void setMarCod(String marCod) {
        this.marCod = marCod;
    }

    /**
     * @return the marNom
     */
    public String getMarNom() {
        return marNom;
    }

    /**
     * @param marNom the marNom to set
     */
    public void setMarNom(String marNom) {
        this.marNom = marNom;
    }
    
    public String insertar(){
        String msg = "";
        try{
            con.ejecutar("INSERT INTO MARCA VALUES (?, ?)", new String[] {marCod, marNom}, false);
        }catch (SQLException ex){
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String modificar(){
        String msg = "";
        try{
            con.ejecutar("UPDATE MARCA SET nombre = ? WHERE idMarca = ?", new String[] {marNom, marCod}, false);
        }catch (SQLException ex){
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String eliminar(){
        String msg = "";
        try{
            con.ejecutar("DELETE FROM MARCA WHERE idMarca = ?", new String[] {marCod}, false);
        }catch (SQLException ex){
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public static ArrayList<Marca> getLista(){
        ArrayList<Marca> marcas = new ArrayList<>();
        try{
            ResultSet rs = con.ejecutar("SELECT * FROM MARCA ORDER BY idMarca ASC", null, true);
            while(rs.next()){
                String codigo = rs.getString("idMarca");
                String nombre = rs.getString("nombre");
                Marca marca = new Marca(codigo, nombre);
                marcas.add(marca);
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return marcas;
    }
    
    public static String sgteCodigo()
    {
        String codigo = "000";
        try
        {
            ResultSet rs = con.ejecutar("SELECT LPAD((SELECT COUNT(*) + 1 FROM MARCA), 3, '0') AS nextCod", null, true);
            rs.next();
            codigo = rs.getString("nextCod");
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return codigo;
    }
    
    public static Marca buscar(String codigo)
    {
        Marca m = null;
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM MARCA WHERE idMarca = ?", new String[] {codigo}, true);
            rs.next();
            m = new Marca();
            m.setMarCod(rs.getString("idMarca"));
            m.setMarNom(rs.getString("nombre"));
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return m;
    }
}
