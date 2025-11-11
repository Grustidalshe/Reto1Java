package principal;

import java.util.Scanner;
import java.util.Locale;


 /*Play program*/
public class Inicio {

	static Scanner sc = new Scanner(System.in);

	static {
		Locale.setDefault(Locale.US); // Forzar formato numérico y separador decimal '.'
	}

    /* catálogo de todos los productos */
	public static String[][] catalogo = { 
		{"1",  "Salados","Patats Fritas", "1.50" }, 
		{"2",  "Salados", "Frutos secos mix", "1.00" },
		{"3",  "Salados", "Gusanitos", "1.00" }, 
		{"4",  "Salados", "Jumpers", "1.20" }, 
		{"5",  "Salados", "Apetinas", "1.35" }, 
		{"6",  "Dulces", "Donuts", "1.50" },
		{"7",  "Dulces", "Galletas de Chcolate", "1.00" }, 
		{"8",  "Dulces", "Donetes", "1.20" }, 
		{"9",  "Dulces", "Chupa Chups", "0.85" },
		{"10", "Dulces", "Caramelos Surtidos", "1.00" }, 
		{"11", "Bebidas", "Coca Cola", "1.50" }, 
		{"12", "Bebidas", "Fanta Naranja", "1.35" }, 
		{"13", "Bebidas", "Red Bull", "1.20" },
		{"14", "Bebidas", "Radler", "1.20" }, 
		{"15", "Bebidas", "Caffe", "1.50" }, 
		{"16", "Sandwiches", "Jamon y Queso", "1.75" }, 
		{"17", "Sandwiches", "Vegetal", "1.20" },
		{"18", "Sandwiches", "Nocilla","2.00" }, 
		{"19", "Sandwiches", "Pate", "1.45" }, 
		{"20", "Sandwiches", "Atun", "1.55" } 
	};

	public static String[] tipos = { "Salados", "Dulces", "Bebidas", "Sandwiches" };
   
	
	
	/*Función para verificar la corrección de la entrada*/
	public static boolean checkInput(String input) {
		if (input.equals("") || input.equals("admin")) {
	        return true;
	     } else {
	        return false;
	     }
	}

	/*Inicio de programa, con opciones entrar como admin o cliente*/
	static void mostrarBienvenida() {

		System.out.println("=================================");
		System.out.println("	  Bienvenido  ");
		System.out.println("   Pulse ENTER para entrar");
		System.out.println("   	o escribe admin");
		System.out.println("=================================");

		// Borrar el carrito
		clearCarrito();

		// Leer entrada del usuario
		String input = sc.nextLine().trim().toLowerCase();
			
		 // Validar entrada; pedir de nuevo si no es correcta
		while (!checkInput(input)) {
			System.out.println("");
			System.out.println("     Opción no válida!\nPulse ENTER o entra como admin");
			input = sc.nextLine().trim().toLowerCase();
		}

		// Logica de entrada. Opción entradas: cliente o administrador
		switch (input) {
			case "":
				System.out.println("---------------------------------");
				System.out.println("      Entrada Cliente");
				System.out.println("---------------------------------");
				Cliente.seleccionar(); // Ir a la clase Cliente
				break;
			case "admin":
				System.out.println("---------------------------------");
				System.out.println("	 Entrada Admin");
				System.out.println("---------------------------------");
				Admin.adminAccess(); // Ir a la clase Admin
				break;
		}
	}

	/* Bucle principal: mostrar bienvenida repetidamente */
	public static void main(String[] args) {
		while (true) {

			mostrarBienvenida();
		}
    }
	
	/*limpiar el carrito del clientem */
	public static void clearCarrito() {
			Cliente.carrito = new String[0][4];
		}
}