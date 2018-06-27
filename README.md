# Medical-data-analyzer

Medical-data-analyzer is created to allow Users to visualize results of the most common dimensionality reduction algorithms.
Currently there are two of them available:

### T-SNE -> 
  [Algorithm description](https://lvdmaaten.github.io/tsne/)
  
  [Implementation](https://github.com/lejon/T-SNE-Java)

### PCA -> 
  [Algorithm description](https://en.wikipedia.org/wiki/Principal_component_analysis)
  
  [Implementation](https://github.com/mkobos/pca_transform)
   
## Using application
There are two option to run the application:

### Docker images
To run the application you will need two docker images (available on the [official docker site](https://hub.docker.com/u/tszczesn/)):
 ```markdown
 - medical_data_database -> containing mysql database with created schema 
 - medical_data_analyzer -> image with web application which is using previous docker image as a database 
```
To pull the images from docker repository use the commands below:
```
docker pull tszczesn/medical_data_database
docker pull tszczesn/medical_data_analyzer
```
and run both of them using the commands (NOTE: mind the order as the medical_data_database needs to be run first):
```
docker run --name=medical_data_database -d tszczesn/medical_data_database
docker run --link medical_data_database -p 8080:8080 --name=analyzer -d tszczesn/medical_data_analyzer
```
The application is available under [localhost:8080](localhost:8080) address


### Maven build the source code
- install [mysql database](https://dev.mysql.com/doc/refman/5.7/en/windows-installation.html)
- run [init.sql](https://github.com/TomekSzcz/Medical-data-analyzer/tree/master/medical-data-analyzer/db_schema) script from repository
- checkout [master branch](https://github.com/TomekSzcz/Medical-data-analyzer)
- build the application using command:
  ```mvn build```
- run the the application using command:
  ``` java -jar medical-data-analyzer-1.0-SNAPSHOT.jar```
  

# Important
This work is created in cooperation with [The Institute of Radioelectronics and Multimedia Technology](http://www.elka.pw.edu.pl/eng/Faculty/Faculty-Institutes2/The-Institute-of-Radioelectronics-and-Multimedia-Technology)
as a part of final dissertation.
Supervised by [dr inż. Piotr Płoński](http://www.ire.pw.edu.pl/~pplonski/index.html)

### Support or Contact

Having any trouble? Please, contact me directly using [e-mail](tomasz.szczesniak11@gmail.com)
