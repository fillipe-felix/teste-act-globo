# Usuarios API

## Requirementos


* Git
* Java 11
* Docker
* IntelliJ ou Eclipse
* Spring Boot

## DataBase

### MongoDB

* [MongoDb Docker Hub](https://hub.docker.com/_/mongo)

```shell script
docker run -v ~/docker --name mongodb -p 27017:27017  mongo --noauth
```

## Spring Boot

* [https://start.spring.io/](https://start.spring.io/)

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.5/maven-plugin/reference/html/)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-mongodb)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-developing-web-applications)


# Getting Started

* Instale o [docker](https://docs.docker.com/docker-for-windows/install/) em sua maquina.
* Execute o Script para baixar e executar uma imagem do [mongo](https://www.mongodb.com/)
```shell script
docker run -v ~/docker --name mongodb -p 27017:27017  mongo --noauth
```
* Instale o [java 11](https://www.devmedia.com.br/instalacao-e-configuracao-do-pacote-java-jdk/23749#:~:text=Para%20instalar%20o%20JDK%20no,32%20ou%2064%20bits)%20utilizada.)
* Clone o projeto em uma pasta de preferência e abra em sua IDEA.
```shell script
git clone https://github.com/fillipe-felix/teste-act-globo.git
```
* Execute o projeto em sua IDEA, e acesse a API pelo link http://localhost:8080/api/v1/usuario

## Front-End

* O front-end da aplicação foi desenvolvido em ReactJS, para acessa-lo clique [aqui](https://github.com/fillipe-felix/teste-act-globo-front)
