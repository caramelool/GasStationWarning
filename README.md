# GasStationWarning
FIAP - Trabalho Android 14mob

<p>
  <img width="200" height="400" src="screens/splash.png">
  <img width="200" height="400" src="screens/home_stations.png">
  <img width="200" height="400" src="screens/station_detail.png">
  <img width="200" height="400" src="screens/station_map.png">
</p>

> A ideia foi criar um app para verificar informações referente 
a postos de combustiveis, preço da gasolina, ver se tem gasolina, onde fica, detalhe, telefone, adicionar comentarios...

## Tecnologias utilizadas
- Kotlin
- Android Architecture Components
  - Lifecycle
  - LiveData
  - ViewModel
- KODEIN KOtlin DEpendency INjectio
- Firebase
  - Firestore
  - Authentication
    - Facebook
    - Anonymous
  - Crashlytics
- Facebook

## Login
<img width="300" height="600" src="screens/login.png">

Possivel realizar o login utilizando o `facebook` ou como `anonimo`

## Home - Lista de Postos
<img width="300" height="600" src="screens/home_stations.png">

Lista todos os postos cadastrados no `firestore` e ordenando 
por posto que possuem combustivel

## Posto - Detalhe

<p>
  <img width="300" height="600" src="screens/station_detail.png">
  <img width="300" height="600" src="screens/station_without_fuel.png">
</p>

Algumas informações sobre o posto como nome, endereço, telefone, descrição, link.
Possibilita compartilhamento das informações

## Posto - Mapa

<p>
  <img width="300" height="600" src="screens/station_map.png">
</p>

Mapa com a localização do posto

## Posto - Comentarios

<p>
  <img width="300" height="600" src="screens/station_comment.png">
  <img width="300" height="600" src="screens/station_comment_delete.png">
</p>

Lista de comentarios realizado por usuarios.
Possibilita exclusão do comentario feito.

## Home - Sobre
<img width="300" height="600" src="screens/home_about.png">

Exibe informações sobre o desenvolvedor, versão do app e da a possibilidade de deslogar

## License

    Copyright 2018 Lucas Caramelo, Inc.
