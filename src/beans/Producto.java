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
public class Producto {
    
    private String proCod;
    private String proDes;
    
    public Producto(){
        this("-1", "NULL");
    }

    public Producto(String proCod, String proDes)
    {
       this.proCod = proCod;
       this.proDes = proDes;
    }

    /**
     * @return the proCod
     */
    public String getProCod() {
        return proCod;
    }

    /**
     * @param proCod the proCod to set
     */
    public void setProCod(String proCod) {
        this.proCod = proCod;
    }

    /**
     * @return the proDes
     */
    public String getProDes() {
        return proDes;
    }

    /**
     * @param proDes the proDes to set
     */
    public void setProDes(String proDes) {
        this.proDes = proDes;
    }
    
    public String insertar(){
        String msg = "";
        try{
            con.ejecutar("INSERT INTO PRODUCTO VALUES (?, ?)", new String[] {proCod, proDes}, false);
        }catch(SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
     }
    
    public String modificar(){
        String msg = "";
        try{
            con.ejecutar("UPDATE PRODUCTO SET descripcion = ? WHERE idProducto = ?", new String[] {proDes, proCod}, false);
        }catch (SQLException ex){
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String modificar(String cantidad){
        String msg = "";
        try
        {
            con.ejecutar("UPDATE PRODUCTO SET cantidad = ? WHERE idProducto = ?", new String [] {cantidad, proCod}, false);
        }catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String eliminar()
    {
        String msg = "";
        try
        {
            con.ejecutar("DELETE FROM PRODUCTO WHERE idProducto = ?", new String[] {proCod}, false);
        }catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public static ArrayList<Producto> getLista(){
        ArrayList<Producto> productos = new ArrayList<>();
        try{
            ResultSet rs = con.ejecutar("SELECT idProducto, descripcion FROM PRODUCTO ORDER BY idProducto ASC", null, true);
            while(rs.next()){
                String codigo = rs.getString("idProducto");
                String descripcion = rs.getString("descripcion");
                Producto producto = new Producto(codigo, descripcion);
                productos.add(producto);
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return productos;
    }
    
    public static Producto buscar(String codigo){
        Producto p = null;
        try{
            ResultSet rs = con.ejecutar("SELECT * FROM PRODUCTO WHERE idProducto = ?", new String[] {codigo}, true);
            rs.next();
            p = new Producto();
            p.setProCod(rs.getString("idProducto"));
            p.setProDes(rs.getString("descripcion"));
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return p;
    }
    
    public static String sgtCodigo()
    {
        String codigo = "000000";
        try
        {
            ResultSet rs = con.ejecutar("SELECT LPAD((SELECT COUNT(*) + 1 FROM PRODUCTO), 6, '0') AS nextCod", null, true);
            rs.next();
            codigo = rs.getString("nextCod");  
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return codigo;
    }
  
    public static String validarCantidad(String cantidad) throws NumberFormatException
    {
        if(Integer.valueOf(cantidad) >= 0)
            return cantidad;
        else
            throw new NumberFormatException("Cantidad inválida");
    }
    
    public static String eliminarCantidad(String cantidad, String canKardex, String operacion) throws NumberFormatException
    {
        int cantTotal_1 = Integer.valueOf(cantidad) - Integer.valueOf(canKardex);
        int cantTotal_2 = Integer.valueOf(cantidad) + Integer.valueOf(canKardex);
        
        if(cantTotal_1 >= 0 && operacion.equals("1"))
        {
            return String.valueOf(cantTotal_1);
        }
        else if(operacion.equals("0"))
        {
            return String.valueOf(cantTotal_2);
        }
        else
        {
            throw new NumberFormatException("No se puede eliminar este registro");
        }
    }
    
    public static String validarCanMod(String cantidad, ArrayList<ArrayList<Kardex>> op) throws NumberFormatException
    {
        if(op.size() > 1)
            return validarCantidad(cantidad);
        else
            throw new NumberFormatException("No puedes tener existecias negativas");
    }
    
}
