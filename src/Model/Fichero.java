/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        return r;
    }
    
    //Metodo actualizar registro en el caso realiza la insercion 
    //lo hace en el sgte datos 
    public void Actualizar(String texto)
    {
        //Si existe el archivo abierto
        if(this.file!=null)
        {
            try {
                if(Escribir(this.file,texto))
                {
                      JOptionPane.showMessageDialog(null,"Archivo '" +this.file.getName() + "' Actualizado" );
                }
              }catch (FileNotFoundException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Crea un nuevo archivo
        else
        {
            Guardar(texto);
        }
        
    }
  
 //Metodo Abrir    
 public void Abrir()
 {
     filechooser = new JFileChooser();
     filechooser.setFileFilter(filtro);
     int r=filechooser.showOpenDialog(null);
    if(r==JFileChooser.APPROVE_OPTION)
    {
        this.file=filechooser.getSelectedFile();
        Leer(this.file);
        this.isOpen=true;
    }
 }
 
 //Metodo Leer
 public boolean Leer(File fichero)
 {
     BufferedReader reader = null;
     try
     {
         reader=new BufferedReader(new FileReader(fichero));
                
         this.contenido.clear();
         String linea;
         while((linea=reader.readLine())!=null)
         {
             this.contenido.add(linea);
         }
         Siguiente();
         return true;
     }catch(IOException ex)
     {
         System.out.println("Error a leer el archivo" + ex); 
     }
     finally{
         try{
             reader.close();
         }catch(IOException ex)
         {
             System.out.println("Error al cerrar el archivo" +ex );
         }
     }
     return false;
 }
    
 //Metodo que avanza al siguiente registro del ArrayList
 // y lo visualiza en el formulario
 public void Siguiente()
 {
     this.index=(index>=contenido.size())?1:index+1;
     int count=1;
     Iterator i=contenido.iterator();
     //Comienza en la busqueda
     while(i.hasNext())
     {
         String tmp=i.next().toString();
         if(count==index)
         {
             String[] datos=tmp.split(",");
             this.doc.setText(datos[0]);
             this.nom.setText(datos[1]);
             this.ape.setText(datos[2]);
             break;
         }
         count++;
     }
 }
}
