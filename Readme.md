# CranFieldSearchEngine

CranFieldSearchEngine is an information retrieval system built using Apache Lucene on Java. It is developed during the Information Retrivel module of Trinity College Dublin, University of Dublin

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Maven 3.6
JDK 
Gcc
```

### Installing


```
cd CranSearch
mvn install
```

```
mvn exec:java -Dexec.mainClass="com.utk.ir.tcd.MainCranSearcher"
```
The Result file will be stored in the Docs folder "/Docs/results"

```
./runner.sh
```
or 

```
cd trec_eval
make
./trec_eval ./QRelsCorrectedforTRECeval ./Docs/results
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Utkarsh Bhardwaj** 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


 