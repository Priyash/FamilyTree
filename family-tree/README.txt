# FAMILY TREE

This is a Java project which implements the Family-Tree problem , the program initially builds the family tree with given data and
also finds the blood relations of a given person as a query .
This program also allows adding child through wife from external file .

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.


### Prerequisites

The code has been developed with Java JDK 1.8 
Download link : https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

The IDE which has been used for development of the code is Visual Studio Code 
Download link : https://code.visualstudio.com/download

Maven has been used as a build automation tool .
Download link : https://maven.apache.org/download.cgi




### Installing

1)Its a maven project , import as a maven project .
Go to the commandLine and set the project directory .

2)Execute the below commands :
"mvn clean install" which will install neccessary dependencies for the project .
End with an example of getting some data out of the system or using it for a little demo

## Running the tests

For running the test need the below dependency snippet in pom.xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>

Then execute the command in commandLine :
"mvn clean compile test"

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [SureFire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/) - Used to generate test result reports

## Code Design
A common factory-method pattern is being used in the code keeping in the mind with SOLID principle .

The class Graph.java is being used for building the family tree with pre-requisite data , which can be found on Resources/build.txt file
A common singleton pattern is being used for this class so that multiple family-tree object won't be created multiple times .
example :

To update a family-tree below is the code has been used :
Graph.getInstance().update_family_tree(existingFamilyMember, newFamilyMember, relation);

To get a relationship of a family-member as a query below code can be used :
params : Fetchable class has been used for different types of relation.

For an example :
To fetch a son for a given person below is the relation :
Fetchable son = new FetchSons();
Graph.getInstance().getRelationShip(son)

Exach node in a graph is being presented as IFamilyMember interface which holds a mapping of blood-related person and their
corresponding relationship as a mapping list;
And base class FamilyMember has been used to represent this nodes and being used heavily in the Graph class .

It can be written as below :
IFamilyMember familyMember = new FamilyMember();
param1 = another family-member object of type IFamilyMember
param2 = relation object of that family member which mentioned below .
familyMember.addRelationShip(param1, param2)

Relationship is presented through an interface called IRelationship which holds two attributes relationship-type i.e son, father etc
and gender type male or female.
Multiple class has been built which implements this above interface instead of giving multiple if-else statement
Example:
IRelationship relation = new Son();
IRelationship relation = new Husband();

Entire code has been written based on this two interfaces heavily one is IFamilyMember and another is IRelationship


Last one is the technique which has been used so that SOLID principle won't get violated.
In most of the time interface has been used to get the appropriate class instance at runtime , to do that Class.ForName("full class path")
has been used .
For an example to fetch a specific relation at runtime like son usually below code have done the job :
Fetchable fetchSons = new FetchSons();
but everytime code has to be written like this whenever there is a need of new relation , instead of that Class.ForName("classpath").newInstance() has
been used for specific classpath it will created specific Java object which needed to be casted as Fetchable but with different class type .

classpath_string = "com.path.FetchSons.java"
Object object  = Class.ForName("classpath_string").newInstance();
Fetchable fetch = (Fetchable)object;
note that this Fetchable is an interface but here its been used after casting as a type of FetchSons class .

Constants.java is a class where all the project variables has been declared constants and also has a map of all the classpaths and indexes
so that at runtime only through indexes classpath can be delivered and with only interfaces those newly instantiated classes can be used, so SOLID principle 
won't be violated.



