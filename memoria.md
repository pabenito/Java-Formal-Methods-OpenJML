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

EXPLICAR COMO SE HA IMPLEMENTADO, QUE OPERACIONES HAY Y QUE DIFERENCIA HAY CON RESPECTO A LA IMPLEMENTACION EN MAUDE (MAS COMPLICADO QUE MAUDE)

## Ejecución 

INCLUIR MAIN 

## Análisis 

RAZONAR CONFLUENCIA, TERMINACION Y COHERENCIA

## Conceptos Teoría 

DESARROLLAR: Logica de reescritura con reglas, etc. LO QUE SE TE OCURRA
