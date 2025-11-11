package principal;

import java.util.Arrays;
import java.util.Scanner;

import utils.Controlador;
import utils.Imprimir;

public class Admin {

	static Scanner sc = new Scanner(System.in);

	/* Método de acceso administrativo - verifica credenciales */
	public static void adminAccess() {
		System.out.print("Usuario: ");
		String utente = sc.nextLine(); // usuario
		System.out.print("\nClave: ");
		String password = sc.nextLine(); // contraseña
		boolean admin;

		admin = utente.equalsIgnoreCase("admin") && password.equals("1234");
		if (admin) {
			boolean salir = false;
			while (!salir) {
				salir = adminConsol(); // Si es correcto, entra a la consola admin
			}
		} else {
			System.out.println("Usuario o clave incorrecto.");
		}
	}

	/* Busca un producto por ID en el catálogo */
	public static int getProductIndexById(int id) {
		int posicionencontrada = -1;

		for (int i = 0; i < Inicio.catalogo.length; i++) {

			String idString = String.valueOf(id);

			if (idString.equalsIgnoreCase(Inicio.catalogo[i][0])) { // Recorre el catálogo comparando ID
				posicionencontrada = i;
			}
		}
		return posicionencontrada; // Retorna la posición del producto o -1 si no lo encuentra
	}

	/* Interfaz para elegir un producto por ID  */
	public static int elegirProducto() {
		boolean encontrado = false;
		int posicionencontrada = -1;

		while (!encontrado) {

			System.out.println("\nID usados:");
			for (int i = 0; i < Inicio.catalogo.length - 1; i++) { // Muestra IDs utilizados
				System.out.print(Inicio.catalogo[i][0] + ", ");
			}
			System.out.println(Inicio.catalogo[Inicio.catalogo.length - 1][0] + ".");

			System.out.print("\nEscribe ID o \"volver\" para volver: ");

			int select = Controlador.controlnumeros(true);

			if (select == -1) {
				return -1; // opcion volver
			}

			posicionencontrada = getProductIndexById(select);  // buscar ID

			if (posicionencontrada == -1) {
				System.out.println("Error, el ID no existe.");
			} else {
				encontrado = true;
			}
		}
		return posicionencontrada;
	}

	/* Consola principal de administración */
	public static boolean adminConsol() {

		int op = 0;
		boolean salir = false;

		while (op != -1) {
			// Menú con opciones: nuevo, modificar, eliminar producto
			System.out.println("\n===== Consola Admin =======");
			System.out.println("\n1. Nuevo producto");
			System.out.println("2. Modificar producto");
			System.out.println("3. Eliminar producto");
			System.out.println("===========================");
			System.out.print("\nElige numero de opcion o escribe \"volver\" para volver a la Bienvenida: ");

			op = Controlador.controlnumeros(true); // Controla la navegación entre funciones
			switch (op) {
			case 1:
				System.out.println("\n========= Nuevo Producto ============");
				anadirProducto(); // Opcion 1: añadir producto
				break;
			case 2:
				System.out.println("\n========= Modificar Producto ============");
				modificarProducto(); // Opcion 2: modificar producto
				break;
			case 3:
				System.out.println("\n========= Eliminar Producto ============");
				eliminarProducto(); // Opcion 3: eliminar producto	
				break;
			case -1: // Opcion volver
				salir = true;
				System.out.println("Volviendo a la Bienvenida...");
				break;

			default:
				System.out.println("\nOpcion no valida!");
			}
		}
		return salir;
	}

	/* Añadir nuevo producto al catálogo */
	public static void anadirProducto() {
		String conf;

		String[][] copiarray = new String[Inicio.catalogo.length + 1][4]; // Copia del array con espacio extra

		for (int i = 0; i < Inicio.catalogo.length; i++) {
			copiarray[i] = Inicio.catalogo[i]; // copiar elementos
		}

		System.out.println("\n--------------------------------------------");
		System.out.println("Tipos validos:\n ");
		Imprimir.mostrarTipos(); // mostrar tipos
		System.out.println("---------------------------------------------");
		System.out.print("Escribe el tipo de producto: ");

		String tipo = sc.nextLine();

		while (!Controlador.tipoValido(tipo)) {
			System.out.print("Tipo no encontrado! Escribe el NOMBRE de tipo: ");
			tipo = sc.nextLine();
		}

		tipo = tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase();

		copiarray[copiarray.length - 1][1] = tipo; /* Guardar tipo
													 * en -1 sirve porque el lenght nos inidica el numero de indices
													 * pero no su valor, entonces si queremos que los atributos sean
													 * guardados en el array 4 siento el lentght con valor 5 con el -1
													 * hacemos que los datos de guarden en el array 4.
													 */
		System.out.println("\nProductos disponibles de tipo " + tipo + ": ");
		System.out.println("---------------------------------------------");
		String[][] productosTipo = new String[Inicio.catalogo.length][4];
		int j = 0;
		for (int i = 0; i < Inicio.catalogo.length; i++) {
			if (Inicio.catalogo[i][1].equalsIgnoreCase(tipo)) {
				productosTipo[j++] = Inicio.catalogo[i]; // filtrar por tipo
			}
		}
		Imprimir.mostrarProductos(productosTipo); // mostrar productos del tipo seleccionado

		System.out.println("ID usados:");
		for (int i = 0; i < Inicio.catalogo.length - 1; i++) {
			System.out.print(Inicio.catalogo[i][0] + ", ");
		}
		System.out.println(Inicio.catalogo[Inicio.catalogo.length - 1][0] + ".");

		System.out.print("\nEscribe nuevo ID para anadir el producto: ");

		int codigo = Controlador.controlnumeros(false); // Controla que el ID sea numérico

		while (getProductIndexById(codigo) != -1) {
			System.out.print("ID ya usado! Escribe otro ID: ");
			codigo = Controlador.controlnumeros(false);
		}

		String codigoString = String.valueOf(codigo);
		copiarray[copiarray.length - 1][0] = codigoString; /*guardar ID 
											tenemos que trasformar los valores numericos en String
											porque nuestro array es de string */

		System.out.print("\nEscribe NOMBRE de producto: ");
		String nombre = sc.nextLine();
		while (nombre.trim().isEmpty()) {
			System.out.print("Nombre no puede estar vacío! Escribe el NOMBRE del producto: ");
			nombre = sc.nextLine();
		}

		nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
		copiarray[copiarray.length - 1][2] = nombre; // Guardar nombre

		System.out.print("\nEscribe PRECIO de producto: ");
		double precio = Controlador.controlnumerosDouble(); // Controla que el precio sea numérico

		String precioString = String.format("%.2f", precio);
		copiarray[copiarray.length - 1][3] = precioString; // Guardar precio

		System.out.println("\nDatos de nuevo producto: ");

		Imprimir.mostrarProductosConTipo(new String[][] { copiarray[copiarray.length - 1] });

		System.out.print("Guardar? (si/no): "); // Confirma antes de guardar en catálogo
		conf = Controlador.confirma();

		if (conf.equalsIgnoreCase("si")) {
			System.out.println("Producto \"" + nombre + "\" añadido correctamente!");
			Inicio.catalogo = copiarray; // Guardar copia en catálogo original 
		} else {
			System.out.println("Datos descartados.");
		}

	}

	/* Modificar producto existente */
	public static void modificarProducto() {
		System.out.println("---------------------------------------------------------");
		System.out.println("Productos disponibles:\n ");
		Arrays.sort(Inicio.catalogo, (a, b) -> Integer.parseInt(a[0]) - Integer.parseInt(b[0]));// ordenar por ID

		Imprimir.mostrarProductosConTipo(Inicio.catalogo); // muestra todos los productos
		System.out.println("---------------------------------------------------------");

		int posicionencontrada = elegirProducto(); // elegir producto

		if (posicionencontrada == -1) {
			return; // opcion volver
		}

		String[] productoSelectionado = new String[4];
		for (int i = 0; i < 4; i++) {
			productoSelectionado[i] = Inicio.catalogo[posicionencontrada][i]; // copiar fila
		}

		String respuesta = "si";
		int adminop;
		System.out.println("\nHas selecionado: ");
		System.out.println("---------------------------------------------------------");
		Imprimir.mostrarProductosConTipo(new String[][] { productoSelectionado }); /* Muestra el producto que hemos elegido*/																	
		System.out.println("---------------------------------------------------------");

		boolean error = false;
		// Permite modificar: tipo, nombre o precio
		do {
			System.out.println("\nOpciones de modificacion:");
			System.out.println("  1. Tipo");
			System.out.println("  2. Nombre");
			System.out.println("  3. Precio");
			System.out.print("\nEscribe el número de la opción: ");
			adminop = Controlador.controlnumeros(false);

			switch (adminop) {
			case 1:// modificar tipo
				System.out.println("---------------------------------------------");
				System.out.println("	Modificar tipo");
				System.out.println("---------------------------------------------");
				System.out.println("Tipos validos:\n ");
				Imprimir.mostrarTipos(); // mostrar tipos 
				System.out.print("\nElige el tipo: ");

				String newTipo = sc.nextLine();

				while (!Controlador.tipoValido(newTipo)) {
					System.out.print("Tipo no encontrado! Escribe el NOMBRE de tipo: ");
					newTipo = sc.nextLine();
				}

				newTipo = newTipo.substring(0, 1).toUpperCase() + newTipo.substring(1).toLowerCase();

				productoSelectionado[1] = newTipo; // Guardar nuevo tipo

				error = false;
				break;
			case 2:// modificar nombre
				System.out.println("---------------------------------------------");
				System.out.println("	Modificar nombre");
				System.out.println("---------------------------------------------");
				System.out.print("\nEscribe el nuevo NOMBRE: ");

				String newNombre = sc.nextLine();

				while (newNombre.trim().isEmpty()) {
					System.out.print("Nombre no puede estar vacío! Escribe el NOMBRE del producto: ");
					newNombre = sc.nextLine();
				}

				newNombre = newNombre.substring(0, 1).toUpperCase() + newNombre.substring(1).toLowerCase();

				productoSelectionado[2] = newNombre; // Guardar nuevo nombre
				error = false;
				break;
			case 3:// modificar precio
				System.out.println("---------------------------------------------");
				System.out.println("	Modificar precio");
				System.out.println("---------------------------------------------");
				System.out.print("\nEscribe el nuevo PRECIO: ");

				double newPrecio = Controlador.controlnumerosDouble(); // Controla que el precio sea numérico

				productoSelectionado[3] = String.format("%.2f", newPrecio); // Guardar nuevo precio
				error = false;
				break;
			default:
				error = true;
				System.out.println("\nOpcion no valida!");
			}

			if (!error) {// Si no hay errores se sigue y se guarda la modifica

				System.out.println("\nDatos despues de la modificacion: ");
				System.out.println("---------------------------------------------");
				Imprimir.mostrarProductosConTipo(new String[][] { productoSelectionado }); // Muestra el producto modificado
				System.out.println("---------------------------------------------");
				System.out.print("Quieres hacer otra modificacion con este producto? (si/no): "); 
				respuesta = Controlador.confirma(); // Confirmar otra modificación

				if (respuesta.equalsIgnoreCase("no")) {
					System.out.println("\nDatos finales del producto: ");
					System.out.println("---------------------------------------------");
					Imprimir.mostrarProductosConTipo(new String[][] { productoSelectionado }); // Muestra el producto final
				}

			}

		} while (respuesta.equalsIgnoreCase("si"));

		System.out.print("Guardar? (si/no): "); // Confirma si los datos son correcto
		String conf = Controlador.confirma();

		if (conf.equalsIgnoreCase("si")) {
			System.out.println("Producto modificado correctamente!");
			Inicio.catalogo[posicionencontrada] = productoSelectionado; // si elige guardar los cambios dire que en la
																		// fila del array habra lo que hemos modificado
		} else {
			System.out.println("Datos descartados.");
			// si no elegio guardar los cambios hace nada
		}

	}

	/* Eliminar producto del catálogo */
	public static void eliminarProducto() {
		String conf;

		System.out.println("---------------------------------------------");
		System.out.println("Productos disponibles:\n ");
		Imprimir.mostrarProductosConTipo(Inicio.catalogo); // Muestra los productos disponibles
		System.out.println("---------------------------------------------");

		int posicionencontrada = elegirProducto(); // Selecciona producto a eliminar
		if (posicionencontrada == -1) {
			return; // Opción no válida
		}

		System.out.print(
				"Estas seguro de eliminar producto \"" + Inicio.catalogo[posicionencontrada][2] + "\"? (si/no): ");
		conf = Controlador.confirma(); // Confirmar eliminación

		if (posicionencontrada >= 0 && conf.equalsIgnoreCase("si")) {
			String[][] copiarray = new String[Inicio.catalogo.length - 1][4];// Copia del array sin el producto eliminado

			String nombreEliminado = "";

			for (int i = 0; i < Inicio.catalogo.length; i++) {
				if (i < posicionencontrada) {
					copiarray[i] = Inicio.catalogo[i]; // Copia elementos antes del eliminado
				} else if (i > posicionencontrada) {
					copiarray[i - 1] = Inicio.catalogo[i]; // Copia elementos después del eliminado
				} else {
					nombreEliminado = Inicio.catalogo[i][2]; // Guardar nombre del producto eliminado
				}
			}

			Inicio.catalogo = copiarray; // Actualiza el catálogo

			System.out.println("\nProducto \"" + nombreEliminado + "\" eliminado correctamente!");
			System.out.println();
		}
	}
}