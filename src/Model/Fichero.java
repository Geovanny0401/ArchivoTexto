/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Geovanny Mendoza
 */
public class Fichero {
    
    private JFileChooser filechooser;
    //Declaramos el atributo filtro donde tendra como funcion filtrar las extesion con el formato *.txt
    private FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo de texto", "txt");
    //Almacena lo registro leido en el .txt
    private ArrayList contenido = new ArrayList(); 
    //Para Saber si se abrio el abrio el archivo o no
    private boolean isOpen=false;
    //
    private File file=null;
    //
    private int index=0;
    //Controles Swing
    private JTextField doc;
    private JTextField nom;
    private JTextField ape;
    
            
    public Fichero()
    {
        
    }
    
    public Fichero(JTextField doc,JTextField nom,JTextField ape)
    {
        this.doc=doc;
        this.nom=nom;
        this.ape=ape;
    }
    
    //Metodo Guardar
    public void Guardar(String texto) 
    {
         filechooser = new JFileChooser();
         filechooser.setFileFilter(filtro);
         int resultado=filechooser.showSaveDialog(null);
         if (resultado==JFileChooser.APPROVE_OPTION)
         {
             this.isOpen=false;
             this.contenido.clear();
             this.index=1;
             try {
                 if(Escribir(filechooser.getSelectedFile(),texto))
                 {
                     JOptionPane.showConfirmDialog(null,"Archivo '"+ filechooser.getSelectedFile().getName() + "'guardado");
                     this.isOpen=true;
                 }
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         
    }
    
    //Metodo Escribir
    
    private boolean Escribir(File fichero, String texto) throws FileNotFoundException
    {
        boolean r=false;
        PrintWriter writer=null;
        try
        {
           String f = fichero.toString();
           //Valida si la extesion existe de lo contrario la agrega
           if(!f.substring(f.length()-4,f.length()).equals(".txt") )
           {
               f=f + ".txt";
               fichero=new File(f);
           }
           writer = new PrintWriter(fichero);
           //si hay un archivo abierto
           if(this.isOpen)
           {
               //Añade primero linea por linea el contenido anterior
               Iterator i=contenido.iterator();
               while(i.hasNext())
               {
                   writer.println(i.next());
               }
               //Añade el fila de texto al archivo
               writer.println(texto);
               this.contenido.add(texto);
           }
           //Si esta guardado por primera vez
           else 
           {
               this.contenido.add(texto);
               writer.println(texto);
           }
           this.file=fichero;
           writer.close();
           r=true;
        }catch(FileNotFoundException ex)
        //catch(NullPointerException ex)
        {
            System.out.println("Error a leer archivo" + ex);
        }finally
        {
            writer.close();
        }
        return false;
    }
}
