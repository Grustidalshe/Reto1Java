package principal;

import java.util.Scanner;
import java.util.Arrays;
import utils.Imprimir;
import utils.Controlador;

public class Cliente {

	public static Scanner sc = new Scanner(System.in);
	static String[][] carrito = new String[0][4]; // Carrito de compras vacío inicialmente
	
	/* Método principal para seleccionar tipo de producto */
	static void seleccionar() {

		System.out.println("\nSelecciona tipo de producto:");
		Imprimir.mostrarTipos();
		System.out.print("Escribe el nombre del tipo: ");

		String tipo = sc.nextLine();
		// Valida que el tipo exista en el catálogo
		while (!Controlador.tipoValido(tipo)) {
			System.out.print("Tipo no encontrado. Escribe el nombre del tipo: ");
			tipo = sc.nextLine();
		}
		seleccionarProducto(tipo); // Pasa a seleccionar producto 
	}

	/* Busca un producto por ID en un subcatálogo */
	static String[] getProdcuto(String id, String[][] subcatalogo) {
		for (int i = 0; i < subcatalogo.length; i++) {
			if (subcatalogo[i][0] != null && subcatalogo[i][0].equals(id)) {
				return subcatalogo[i];
			}
		}
		return null; // Retorna null si no encuentra el producto
	}

	/* Filtra y muestra productos por tipo seleccionado */
	static void seleccionarProducto(String tipo) {
		// Crea un subcatálogo para productos del tipo seleccionado
		String[][] productosFiltrados = new String[Inicio.catalogo.length][4];
		int j = 0;
		// Rellena el subcatálogo con productos del tipo seleccionado
		for (int i = 0; i < Inicio.catalogo.length; i++) {
			if (Inicio.catalogo[i][1].equalsIgnoreCase(tipo)) {
				productosFiltrados[j] = Inicio.catalogo[i];
				j++;
			}
		}
		// Muestra los productos filtrados
		System.out.println("\nProductos disponibles de tipo " + tipo.substring(0, 1).toUpperCase()
				+ tipo.substring(1).toLowerCase() + ": ");
		System.out.println("---------------------------------------------");
		Imprimir.mostrarProductos(productosFiltrados);
		System.out.print("Selecciona el producto por su numero: ");

		/* Busca un producto por ID en un subcatálogo */
		String productoId = sc.nextLine();
		String[] producto = getProdcuto(productoId, productosFiltrados);

		while (producto == null) {
			System.out.print("Producto no encontrado. Selecciona el producto por su numero: ");
			productoId = sc.nextLine();
			producto = getProdcuto(productoId, productosFiltrados);
		}
		System.out.println("\n----------------------------------------------");
		System.out.println("Has seleccionado: " + producto[2] + "  " + producto[3] + "€");
		anadirCarrito(producto); // Añade el producto al carrito
	}

	public static void anadirCarrito(String[] producto) {
		// Crea un nuevo carrito 
		String[][] nuevoCarrito = new String[carrito.length + 1][4];
		
		// Copia productos existentes al nuevo carrito
		for (int i = 0; i < carrito.length; i++) {
			nuevoCarrito[i] = carrito[i];
		}

		// Añade el nuevo producto al final del carrito
		nuevoCarrito[nuevoCarrito.length - 1] = producto;
		carrito = nuevoCarrito;

		// Muestra estado actual del carrito
		System.out.println("----------------------------------------------");
		System.out.println("Su Carrito:\n");
		Imprimir.mostrarProductos(carrito);
		System.out.println("----------------------------------------------");
		
		// Flujo de navegación para añadir más productos
		System.out.print("Añadir mas productos de mismo tipo? (si/no): ");
		String confirmacion = Controlador.confirma();
		if (confirmacion.equalsIgnoreCase("si")) {
			seleccionarProducto(producto[1]);
		} else {
			System.out.print("Añadir mas productos de otro tipo? (si/no): ");
			String confirmacion2 = Controlador.confirma();
			if (confirmacion2.equalsIgnoreCase("si")) {
				seleccionar();
			} else {
				comprar(carrito);
			}
		}

	}

	/* Procesa la compra final con cálculo de IVA y totales */
	public static void comprar(String[][] carrito) {

		// Ordenar por ID (columna 0)
		Arrays.sort(carrito, (a, b) -> Integer.parseInt(a[0]) - Integer.parseInt(b[0]));

		double totalConIva = 0.00;
		int cantidad = 1;

		// Imprime encabezado del ticket
		System.out.println("\n====================================================================================");
		System.out.println("     			    Su carrito con 21% iva ");
		System.out.println("====================================================================================");
		System.out.printf(" %3s  %-24s %12s %12s %12s %11s %n", "ID", "Nombre", "Precio", "Precio IVA", "Cant",
				"Subtotal");
		System.out.println("------------------------------------------------------------------------------------");
		for (int i = 0; i < carrito.length; i++) {

			// Calcula precio con IVA
			double precioSinIva = Double.parseDouble(carrito[i][3]);
			double precioConIva = Math.round(precioSinIva * 1.21 * 100.0) / 100.0;
			totalConIva += precioConIva;

			// Si el siguiente producto es igual, incrementa cantidad
			if ((i < carrito.length - 1) && (carrito[i][0].equals(carrito[i + 1][0]))) {
				cantidad++;
			} else {
				
				// Si la cantidad es mayor a 1, muestra "xN" junto al nombre
				System.out.printf(" %3s  %-24s %12s %12s %12s %11s %n", carrito[i][0], carrito[i][2],
						String.format("%.2f", precioSinIva) + "€", String.format("%.2f", precioConIva) + "€", cantidad,
						String.format("%.2f €", precioConIva * cantidad));
				cantidad = 1;
			}
		}
		// Imprime total final
		System.out.println("------------------------------------------------------------------------------------");
		System.out.printf(" %-60s %20s %n", "Total:", String.format("%.2f €", totalConIva));
		System.out.println();

		// Confirmación final antes del pago
		System.out.print("De acuerdo con la compra? (si/no): ");
		String confirmacion3 = Controlador.confirma();

		if (confirmacion3.equalsIgnoreCase("no")) {
			Controlador.graciasBienvenida(2); // Vuelve al menú principal con 2 segundos de espera
		} else {

			System.out.println("\nPrecio total con IVA: " + String.format("%.2f", totalConIva) + "€");

			PagoDinero(totalConIva); // Maneja el proceso de pago
		}
	}

	/* Controla el proceso de pago y cálculo de cambio*/
	static void PagoDinero(double total) {

		// Convierte total a centimos para evitar errores de punto flotante
		int totalCent = (int) Math.round(total * 100);
		int aportadoCent = 0; // Dinero introducido por el cliente
		
		// Bucle para pedir dinero que falte hasta cubrir el total
		while (aportadoCent < totalCent) {
			double entrada = 0.0;
			System.out.print("Introduce la cantidad dinero (faltan "
					+ String.format("%.2f", (totalCent - aportadoCent) / 100.0) + "€): ");
			entrada = Controlador.controlnumerosDouble();
			int entradaCent = (int) Math.round(entrada * 100);
			aportadoCent += entradaCent;
		}

		// Calcula y muestra cambio
		int cambioCent = aportadoCent - totalCent;
		System.out.println("---------------------------------------------");
		System.out.println("Su cambio es: " + String.format("%.2f", cambioCent / 100.0) + "€");
		System.out.println("---------------------------------------------");
		
		// Array de denominaciones en centimos (de mayor a menor)
		int[] denomCent = { 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1 };
		// Calcula y muestra billetes/monedas del cambio
		for (int d : denomCent) {
			int cnt = cambioCent / d; // Cantidad de esta denominación
			if (cnt > 0) {
				cambioCent %= d; // Actualiza cambio restante
				if (d >= 500) { // >=5€ => billete
					System.out.printf("%d billetes de %.2f €%n", cnt, d / 100.0);
				} else { // <5€ => moneda
					System.out.printf("%d monedas de %.2f €%n", cnt, d / 100.0);
				}
			}
		}
		// Vuelve al menú principal después de 10 segundos
		Controlador.graciasBienvenida(10);
	}
}