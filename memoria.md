# Maude en Java 

## Problemas encontrados

- Problemas con la instalación de OpenJML
- Problemas al usar OpenJML. Nos ha dado probelmas que no hemos conseguido solucionar.

## Cambio de objetivo

Debido a los problemas que hemos encontrado, hemos decidido pivotar el objetivo inicial del proyecto de hacer uso de OpenJML, y nos hemos centrado en implementar en java los comandos típicos de Maude (rewrite, frewrite y search).

## Module - ¿Cómo lo hemos hecho?

Hemos definido una clase abstracta _Module_, de la cual pueda heredar una clase java. Así poder usar estos comandos desde un _main_.

### Rewrite 

Dado que cada módulo tiene reglas específicas, hemos tenido que definir esta como método abstracto y que cada módulo implemente este, aplicando tantas reglas como sean posibles. 

### FairRewrite

Igual que **Rewrite** pero que la aplicación de las reglas sea de forma aleatorioa.

### Search 

En Maude el comando search tiene dos variantes, una para buscar estados finales y otra para buscar estados que hagan _match_ con un cierto patrón. Para implementar esto hemos dividido esto en dos métodos: _searchBlockStates_ y _search_, respectivamente.

Ambos lo hemos podido implementarlos en la clase Module, delegando únicamente la implementación de la función _next_ que devuelve todos los posibles sucesores a partir del estado actual. 

Para ello hemos usado una búsqueda en árbol en profundidad recursiva, añadiendo en cada nodo la solución de sus sucesores y a sí mismo en caso de ser una solución. 

En caso de tener un espacio de búsqueda infinito la ejecución no terminará, al igual que en maude. **Nota**: Realmente acaba al quedarse sin memoria. 

### _searchBlockStates_

En _searchBlockStates_ consideramos ser una solución a no tener sucesores.
  
### _search_ 

En _search_ se considera ser solución a que el estado actual haga _match_ con el patron de entrada.

### Match

Para definir el _match_ consideramos el patron como una expresión regular. De modo que si la representación de nuestro estado (`toString()`) sigue la expresión regular del patrón, entonces decimos que el estado actual hace match con el patrón.

## Traza

Demás para poder hacer una representación parecida a la de Maude, hemos definido una traza (un log). En la que cada regla guarda una representación textual de la acción que se ha realizado.

De esta forma tras cualquier llamda a un comando de _Module_ podemos llamar a `printTrace()` para imprimir por pantalla la traza.

## Rabbit Hop

Rabbit Hop se ha implementado comparándolo a su implementación con maude. En la implementación de Maude, el módulo RabbitHop importa junto a las listas predefinidas, una vista que usa el módulo funcional Rabbit, pudiendo definir List{RabbitView}. En comparación a esa implementación, la clase RabbitHop de Java importa la clase (en realidad, es una enumeración) Rabbit y las clases para poder usar listas, de tal manera que se puede definir un atributo `List<Rabbit>`. Para cubrir las operaciones que se realizan en maude, hacemos que la clase RabbitHop herede de Module, definida anteriormente, siendo el constructor una llamada a `super()` y la inicialización del resto de atributos. Además, se tienen que redefinir varios métodos implementados en la clase de la que hereda. Por tanto, debido a todo esto, se puede considerar que esta implementación es capaz de llegar a ser más complicada que la de Maude, junto a que hay que implementar las operaciones y reglas a continuación.

### _placeRabbits_

Es un método privado que se llama en el constructor para inicializar los atributos de la clase RabbitHop. El constructor puede recibir dos argumentos como entrada, un número que representa el número de conejos 'x' y conejos 'o' que hay, es decir, si n=3, el estado inicial será `xxx_ooo`; y la cadena de conejos directamente, es decir, que la entrada sea por ejemplo `xxx_ooo`. Debido a esto, hay dos funciones definidas para este método, las cuales reciben su correspondiente argumento desde el constructor.

### _checkRabbitInList_

Un método privado para comprobar si el id de un conejo (pasado como argumento) está dentro de `rabbitList`. En caso de que no esté, se lanza una excepción.

### _canAdvance_

Este método nos dice si un conejo determinado (pasado como argumento en forma de numero entero, representando el id del conejo) es capaz de avanzar. Lo primero que se hace es llamar a `checkRabbitInList()`. En caso de que sea correcto, se comprueba que tipo de conejo es y si puede avanzar o no, siendo esto el resultado que devuelve el método.

### _canHope_

Este método nos dice si un conejo determinado (pasado como argumento en forma de numero entero, representando el id del conejo) es capaz de saltar. Lo primero que se hace es llamar a `checkRabbitInList()`. En caso de que sea correcto, se comprueba que tipo de conejo es y si puede saltar o no, siendo esto el resultado que devuelve el método.

### _canMove_

Este método nos dice si un conejo determinado (pasado como argumento en forma de numero entero, representando el id del conejo) es capaz de moversere, ya sea avanzando o saltando. Comprueba si puede avanzar o si puede saltar, usando los dos métodos anteriores. En caso de que sea pueda hacer al menos una de las dos acciones, devuelve true.

### _advance_

Este método realiza la acción de avanzar de un conejo determinado (pasado como argumento en forma de numero entero, representando el id del conejo). Lo primero que se hace es comprobar si realmente puede avanzar mediante `canAdvance`, y en caso contrario, se lanza una excepción. Una vez comprobado que se puede avanzar, se comprueba que tipo de conejo es y se realiza la acción de avanzar.

### _hop_

Este método realiza la acción de saltar de un conejo determinado (pasado como argumento en forma de numero entero, representando el id del conejo). Lo primero que se hace es comprobar si realmente puede avanzar mediante `canHope`, y en caso contrario, se lanza una excepción. Una vez comprobado que se puede saltar, se comprueba que tipo de conejo es y se realiza la acción de saltar.

### _move_

Este método hace que un conejo determinado (pasado como argumento en forma de numero entero, representando el id del conejo) se mueva en caso de que sea capaz de moversere, ya sea avanzando o saltando. Primero Comprueba si puede avanzar, y en caso de que pueda, avanza, acabando ahí el método. En caso de que no pueda avanzar, se comprueba si puede saltar, y en caso de que pueda, este salta. En casao de que no pueda realizar ninguna de las dos acciones, se lanza una expeción.

## Ejecución 

Para probar si funciona lo realizado anteriormente, hemos realizado el siguiente ejemplo:

~~~
public static void main(String[] args) {
    Module rabbitHop = new RabbitHop(3);
    System.out.printf("Rewrite:\n\n");
    rewrite(rabbitHop.clone());
    System.out.printf("\nFair Rewrite:\n\n");
    fairRewrite(rabbitHop.clone());
    System.out.printf("\nTest Next:\n\n");
    testNext("xx_o_ox_");
    System.out.printf("\nSearch:\n\n");
    search(rabbitHop.clone(), TARGET_STATE);
    System.out.printf("\nSearch Block States:\n\n");
    searchBlockStates(new RabbitHop(2)); // With RabbitHop(2) because RabbitHop(3) gives too many states
}
~~~

Los métodos que se llaman en este main, están definidos en la propia clase, los cuales lo único que hacen es llamar al respectivo método del objeto rabbitHop que se le pasa por argumento y organizar el texto mejor para mostrarlo por consola. Por ejemplo, para el `rewrite`:

~~~
public static void rewrite(Module module){
    String initialState = module.toString();
    module.rewrite();
    System.out.printf("%s ---> %s\n\n", initialState, module);
     module.printTrace();
}
~~~

~~~
xxx_ooobruh ---> _xxxooo

RIGHT rabbit at 1 hop to 3:
	xxx_ooo ---[hop]---> x_xxooo
RIGHT rabbit at 0 advance to 1:
	x_xxooo ---[advance]---> _xxxooo
~~~

## Análisis 

Antes de empezar, hay que tener en cuenta que con reglas nos referimos a los métodos que definen las posibles acciones que pueden realizar los conejos (por ejemplo, `advance`, que realiza la operación de avanzar), y con operaciones nos referimos a los métodos que nos devuelven algo (por ejemplo, `canAdvance`, que nos devuelve true o false dependiendo de si el conejo puede avanzar o no).

Un conjunto de ecuaciones es `confluente` si estas se pueden reescribir de todas las maneras posibles llegando siempre al mismo resultado.

    Para empezar, las reglas no son confluentes, ya que si alteras el orden de los conejos, cambian las posibles acciones que pueden realizar. Las operaciones sí son confluentes, ya que todas reciben un solo argumento, y es un número que representa la id del conejo, por lo que no es posible cambiar la forma de este.

    Dicho esto, si se inicializa el objeto RabbitHop usando el constructor al que se le pasa un número, como solo se le pasa un argumento, no hay más formas que no sea el propio número, por tanto, este constructor sí es confluente. Sin embargo, si se usa el constructor al que se le pasa una cadena de caracteres, como el orden de los caracteres (los cuales representan a los conejos y los huecos) se puede alterar y es significativo, es decir, que el resultado final puede alterar dependiendo del estado inicial, no es confluente.

Es `terminante` si la expresión de entrada de la ecuación no se puede simplificar infinitamente, es decir, se llega a un valor en el que la solución no es recursiva.

    Teniendo esto en cuenta, las reglas sí son terminantes, ya que no son recursivas y no es posible reducir la entrada que se le pasa.

    En el caso de las operaciones ocurre lo mismo que con las reglas, que no son recursivas y no es posible reducir la entrada que se le pasa, y por tanto, son terminantes.

Una especificación es `coherente` si en alguna ecuación se toma como parámetro una expresión con una regla de reescritura aplicada y el resultado de esta es el mismo que si se hubiera resuelto la ecuación con esa misma expresión pero sin aplicar la regla de reescritura anterior.

    En este caso, tal y como dice dice la definición, las especificaciones son coherentes ya que tras aplicar cualquier regla de reescritura, desde ese estado alcanzamos el mismo resultado.

## Conceptos Teoría

### Logica de reescritura con reglas

Tal y como hemos mencionado antes, el conjunto de reglas no es confluente (en este caso, sí es terminante), por lo que al hacer una reescritura (rewrite) me dará uno de los posibles caminos a seguir. Maude es un lenguaje determinista, por lo que siempre dará la misma ejecución.

Esto hay que simularlo en Java, de tal manera que cada vez que se tendria que usar rewrite, que siempre de la misma salida. Esto lo hemos realizado con el método `rewrite` de la clase Module, ya que la clase va a heredar de esta, teniendo que implementar este método. En nuestro caso, para la clase RabbitHop, la implementación es la siguiente:

~~~
@Override
public void rewrite() {
    Integer rabbit;
    while ((rabbit = anyWhoCanMove()) != null)
        move(rabbit);
}
~~~

El método auxiliar `anyWhoCanMove` devuelve el primer conejo de izquierda a derecha que se pueda mover. Viendo esta implementación, cualquier objeto creado que llame a este método va a dar siempre el mismo resultado, da igual cuantas veces lo ejecute.
Esto lo podemos ver por ejemplo en la siguiente ejecución:

~~~
public static void main(String[] args) {
    Module rabbitHop = new RabbitHop(3);
        
    System.out.printf("Rewrite:\n\n");
    rewrite(rabbitHop.clone());
        
    System.out.printf("\nSecond Rewrite:\n\n");
    rewrite(rabbitHop.clone());
}
~~~
~~~
Rewrite:

xxx_ooo ---> _xxxooo

RIGHT rabbit at 1 hop to 3:
	xxx_ooo ---[hop]---> x_xxooo
RIGHT rabbit at 0 advance to 1:
	x_xxooo ---[advance]---> _xxxooo

Second Rewrite:

xxx_ooo ---> _xxxooo

RIGHT rabbit at 1 hop to 3:
	xxx_ooo ---[hop]---> x_xxooo
RIGHT rabbit at 0 advance to 1:
	x_xxooo ---[advance]---> _xxxooo
~~~

## OpenJML

OpenJML es una herramienta de verificación de programas Java que nos permite comprobar las especificaciones de programas anotados en JML.

### Sintaxis

Para definir una especificación, se debe usando comentarios de Java, ya sea usando // para una línea, o /* */ para un bloque.
Además, antes de la especificación es necesario escribir @ para que se identifique como una especificación, quedando de la forma //@ o /*@ */.

Sin entrar casi nada en detalle, las dos más etiquetas básicas son las siguientes:

- @ requires _: se asume inicialmente la condición que se escriba (donde _). Es equivalente a la precondición.

- @ ensures _: se garantiza al final de la ejecución la condición que se escriba (donde _). Es equivalente a la postcondición.