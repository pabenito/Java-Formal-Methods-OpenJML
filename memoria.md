# Maude en Java 

## Problemas encontrados

- Problemas con la instalación de OpenJML
- Problemas al usar OpenJML. Nos ha dado probelmas que no hemos conseguido solucionar.

## Cambio de objetivo

Debido a los problemas que hemos encontrado, hemos decidido pivotar el objetivo inicial del proyecto de hacer uso de OpenJML, y nos hemos centrado en implementar en java los comandos típicos de Maude (rewrite, frewrite y search).

## Module - ¿Cómo lo hemos hecho?

Hemos definido una clase abstracta _Module_, de la cual pueda heredar una clase java. Así poder usar estos comandos desde un _main_.

### Rewrite 

Dado que cada módulo tiene reglas específicas, hemos tenido que definir esta como método abstracto y que cada módulo implemente este, aplicando tantas regas como sean posibles. 

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

INCLUIR MAIN 
---
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
---

## Análisis 

RAZONAR CONFLUENCIA, TERMINACION Y COHERENCIA

## Conceptos Teoría 

DESARROLLAR: Logica de reescritura con reglas, etc. LO QUE SE TE OCURRA
