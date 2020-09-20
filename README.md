# Prerequisite
1. Install JDK/JRE 11 or higher
2. Download maven and set your maven to your Environment Variable
## VSCode
1. Install Java Development Extension Pack
2. Install Java Extension Pack
3. Install Spring Boot Extension Pack

# First step after cloning this repo
1. open your terminal/command line
2. cd Java-Spring-Boot-Test
3. code .
4. mvn clean package

# Working with spring boot
1. add configuration below in pom.xml
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
2. mvn clean package
3. mvn spring-boot:run