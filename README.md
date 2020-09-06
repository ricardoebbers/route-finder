# Route Finder

Mini-projeto/desafio para solucionar um problema de encontrar a rota mais rápida entre
 duas cidades cujas distâncias são mapeadas através de uma lista de tempo que um entregador
 levaria no trajeto de uma cidade para a outra.  

O input/output deve ser feito a partir de arquivos txt na pasta [resources](src/main/resources).

## Entendendo a estrutura do projeto

O projeto está organizado numa estrutura de pacotes inspirada no 
 [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html),
 separando claramente o [domínio](src/main/java/routefinder/domain) dos
 [adaptadores](src/main/java/routefinder/adapter) de input/output.

## Entendendo o domínio

Os [nós](src/main/java/routefinder/domain/entity/Node.java) do
 [grafo](src/main/java/routefinder/domain/entity/Graph.java) são
 [cidades](src/main/java/routefinder/domain/entity/City.java), os vértices entre eles são
 [trechos](src/main/java/routefinder/domain/entity/Path.java), os pesos dos vértices são calculados
 a partir de um [calculador](src/main/java/routefinder/domain/entity/Scorer.java) que considera o
 tempo que um entregador leva entre uma cidade e outra.

## Entendendo o caso de uso

Considerando que o desafio pede o caminho que levaria menos tempo entre uma cidade e outra, fica
 claro que se trata de um problema a solucionar com grafos. Dessa forma podemos criar uma
 [interface](src/main/java/routefinder/domain/usecase/ShortestPath.java) simples para guiar o 
 desenvolvimento.

Também é possível mapear as principais [exceções](src/main/java/routefinder/domain/exception).

A [implementação](src/main/java/routefinder/domain/usecase/astar) do caso de uso foi feita com base
 no [algoritmo de busca A*](https://en.wikipedia.org/wiki/A*_search_algorithm).
