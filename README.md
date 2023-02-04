# Advent of Code 2022 using DMN and Drools

This project is a personal challenge to learn more about DMN (Decision Model and Notation) and Drools by completing https://adventofcode.com/2022

I am fully aware that DMN is in many ways The Wrong Tool For The Job - but I'm doing it anyway. Some say you can learn the most about something by pushing and pulling it in ways that are uncomfortable.

*Warning: This is not an example of "best practices". These models and code are provided for entertainment and amusement only. The horrors you will find within include: Decision Tables with a First hit policy, poorly factored solutions, redundancy, naive algorithms, poor performance, awful and inconsistent variable naming, variables declared as the Any datatype, and many more.*

![Day11](https://user-images.githubusercontent.com/10354739/216742987-afda35d5-5ed2-47a2-8f7c-b67810898fb8.png)

## Prerequisites
- JDK 11+ with JAVA_HOME configured appropriately
- Apache Maven 3.8.6+
- Optionally, an IDE, such as IntelliJ IDEA, VSCode or Eclipse

## Structure

/dmn-project : Maven project containing all DMN models, builds and installs KJAR in local Maven repository

/runner : Maven project containing a single Java class which can execute all DMN models 

## Usage

### Execute the model specified in pom.xml
```
cd runner 
mvn exec:java
```
### Modify what runner will run
Edit runner/pom.xml:
```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    ...
    <configuration>
        <mainClass>com.aeh.aoc2022.App</mainClass>
        <arguments>
            <!-- Arg1: Day to run -->
            <argument>01</argument> 
            
            <!-- Arg2: Run type, options [ sample, input, etc ] -->
            <!-- 1. Determines which data file to load, eg. "input-01.txt", "sample-01.txt" 
                 2. If startsWith("sample") then output full decision results,
                    otherwise, print only outputs of nodes specified in arg3 -->
            <argument>input</argument>
            
            <!-- Arg3: Nodes to evaluate -->
            <argument>Output, Part 2</argument>
            ...
        </arguments>
    </configuration>
</plugin>
```

### Rebuild the KJAR (to pick up changes to DMN models)
```
cd dmn-project
mvn install -DgenerateDMNModel=yes
```
Then run the /runner to use the updated model.
