# 1.4 Library system – simple system including HTML5 GUI

## Preliminary research: 1 hr
## Time to complete: 7 hr 30 min

<br> 

## Tools and technologies used:
* VS Code
* XAMPP
* Postman
* Java
* Spring Boot
* Hibernate
* MySQL


<br>

### Problems and solutions:
* **Problem:** First time using Java.
* **Problem:** Dependencies.
    * **Solution:** Install the correct dependencies.
* **Problem:** Endpoints being unreachable (returning 404 not found)
    * **Solution:** The User- and Book Repositories both extend ExtendedRepository, but I referenced all Repositories in my controllers, which meant the ExtendedRepository got initialized too many times, resulting in a confused program.
* **Problem:** Json Serialization on User and Book classes.
     1. **Solution:** Tried with a handmande toString method. Worked, not pretty.
     2. **Solution:** "implements Serializable" on the classes, simply return the object from the controllers.
* **Problem:** Json infinite recursion
    * **Solution:** @JsonIgnore on "Users" property in Book class


# 1.5 Unit tests

## Preliminary research: 0 hr
## Time to complete: 30 min

<br> 

## Tools and technologies used:
* VS Code
* XAMPP
* Java
* Spring Boot
* Hibernate
* MySQL

<br>

### Problems and solutions:
* **Problem:** @Before was acting up.
    * **Solution:** For this scope I chose not to use it.