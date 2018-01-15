# Medical-data-analyzer


Medical-data-analyzer is created to allow Users to vizualize results of the most common dimensionality reduction algorithms.
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
To run the application directly from the source code checkout master branch:



### Support or Contact

Having trouble with Pages? Check out our [documentation](https://help.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.
