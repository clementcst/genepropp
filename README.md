# Genepropp


This website lets you generate and view family trees. Each user can add members to his or her tree. A written chat system allows users to exchange information, validate users in their tree, etc...
Users can modify their account information, as well as the information on the nodes that make up their tree.

## What's in this folder 

### genealogic-tree-back 
  Spring Boot Java Project containing the Back 
### genealogic-tree-front 
  Angular Project containing the Front
### docs 
  Documentation Folder
### Rapport.pdf 
  Project's Report, containing the présentation and at the same time some technical documentation 
### REST_API_DOCUMENTATION.xlsx  
  REST API endpoint list and technical doc

## Execution
  ### Build
     To launch the project you need to build it first. 
     Launch the build-and-run.bat
     Or check README.md of the two (back and front) projects
  ### Run 
    To Run it launch run.bat 
    Or check README.md of the two (back and front) projects
## Prerequisites

OS Windows, may work with Linux.
Make sure you have installed [```nodeJS```](https://nodejs.org/en), [```Tomcat```](https://tomcat.apache.org/), [```Springboot```](https://spring.io/projects/spring-boot/) and [```git``` ](https://git-scm.com/downloads)


## Installing

Clone this repository:

```
git clone https://github.com/clementcst/Website_Genepropp.git
```

Then, make sure you have all dependencies


## Dependencies

To have a good Angular environment, type in your console :

- Move to your angular project
  ```
  cd genealogic-tree-front
  ```

- Install angular environment
  ```
  npm install
  ```

- Install angular tools
  ```
  npm add @angular/material
  ```

- Install familytree depency
  ```
  npm i @balkangraph/familytree.js
  ```

- Install Cookie Service depency
  ```
  npm install ngx-cookie-service
  ```



## Execution

First, launch the [GenTreeApp file](genealogic-tree-back/src/main/java/com/acfjj/app/GenTreeApp.java) with your JEE environment.
Make sure your have [Springboot installed](README.md#Prerequisites)

Then, run angular project
  ```
  npm start
  ```

Then, [open the website](http://localhost:4200/)
```
http://localhost:4200/
```



## Authors

* **BOUHRARA Adam** _alias_ [@Adamou02](https://github.com/Adamou02)
* **CASSIET Clement** _alias_ [@Clément](https://github.com/clementcst)
* **CERF Fabien** _alias_ [@Fab780](https://github.com/Fab780)
* **GAUTIER Jordan** _alias_ [@JordanG2](https://github.com/JordanG2)
