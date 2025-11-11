# Reto1Java
# codigo java


Proyecto Java con dos paquetes principales:

  - `principal`: clases principales de la aplicación ( `Inicio`, `Admin`, `Cliente`).
         `Inicio` es el punto de entrada y muestra el menú principal.

  - `utils`: utilidades y controladores auxiliares (`controlador`, `imprimir`) para validar entradas, imprimen listados.


Qué hace `Inicio`
- Contiene el método main que arranca la aplicación.
- Muestra la bienvenida y permite elegir entre modo cliente o administrador.
- Todas las operaciones de la app (añadir/modificar/eliminar productos, carrito, compra) se inician desde aquí.

Qué hace `Admin`
- Clase: `principal.Admin`.
- Acceso: menú Admin desde `Inicio` (puede incluir autenticación simple).
- Funcionalidades:
  - Añadir producto: tipo, ID, nombre, precio.
  - Modificar producto: tipo, nombre, precio.
  - Eliminar producto.

Qué hace `Cliente`
- Clase: `principal.Cliente`.
- Funcionalidades:
  - Navegar por tipos/categorías.
  - Ver productos por tipo.
  - Seleccionar producto(s) y cantidad.
  - Añadir al carrito (representado como String[][] o similar).
  - Ver/editar carrito (aumentar/quitar/eliminar).
  - Finalizar compra: calcular subtotal, IVA y total.



```
{
    cliente ->
                -> seleccionar tipo -> seleccionar producto -> Listado de productos -> elegir producto

                -> carrito -> mostrar carrito -> comprar o quitar ->
                pagar []
                precio con la IVA(pagar)
                -> volver a pagina principal


    administrador ->
                  -> entrar con contraseña -> mostrar menu de opciones -> trabajar con este opciones

}
```

### Carrito

```java
carrito = []

anadir(carrito)
    select_categoria(lista_cat !!!)
        select_producto(lista_product) <->

        -> mostrar



mostrar(carrito)
    -> anadir / -> comprar

comprar(carrito)
    finalizar()

-----------------------

anadir_catgoria(lista_cat !!!)


lista_cat = [frutas, verduras]
lista_frutas = [ (manzana, 3), (banana, 5) ]
```
