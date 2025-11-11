 package utils;

import static principal.Inicio.tipos;

public class Imprimir {
/*metodo para qeu se muestren solo los producto de un determinado tipo*/
   public static void mostrarProductos(String[][] subcatalogo) {

    System.out.printf("%3s  %-18s %10s €%n", "ID", "Nombre", "Precio");
    System.out.println("");

       for (int i = 0; i < subcatalogo.length; i++) {
    	   if (subcatalogo[i][0] != null) {
    		   System.out.printf("%3s  %-18s %8s €%n", subcatalogo[i][0], subcatalogo[i][2], subcatalogo[i][3]);
    		  
    	   }
       }
       System.out.println();
   }
/*metodo para mostrar todos los productos*/
   public static void mostrarProductosConTipo(String[][] newsubcatalogo) {

    System.out.printf(" %3s  %-18s %-24s %8s €%n", "ID", "Tipo", "Nombre", "Precio");
    System.out.println("");

       for (int i = 0; i < newsubcatalogo.length; i++) {
    	   if (newsubcatalogo[i][0] != null) {
                System.out.printf(" %3s  %-18s %-24s %8s €%n", newsubcatalogo[i][0], newsubcatalogo[i][1], newsubcatalogo[i][2], newsubcatalogo[i][3]);
           }
       }
       System.out.println();
   }
/*metodo para mostrar solo los tipos de producto*/
   public static void mostrarTipos() {

       for (int i = 0; i < tipos.length; i++) {
           System.out.println("- " + tipos[i]);
       }
       System.out.println(); 
   }

}