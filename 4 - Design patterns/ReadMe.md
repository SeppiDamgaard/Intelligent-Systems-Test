# 4.1 Observer pattern
The observer pattern is a design pattern in which an object, called the subject, maintains a list of its dependents, called observers, and notifies them automatically of any changes to its state. Observer patterns is very useful for reducing coupling in larger projects, or if you have 3rd party data sources.

![alt text](https://github.com/SeppiDamgaard/Intelligent-Systems-Test/blob/master/4%20-%20Design%20patterns/Obersver%20pattern.png)


# 4.2 Singleton Pattern
The singleton pattern is a design pattern in which a class has only one instance throughout the lifetime of an application and provides a global point of access to that instance. Singleton patterns are basically the same as global variables, and I would avoid if possible.

![alt text](https://github.com/SeppiDamgaard/Intelligent-Systems-Test/blob/master/4%20-%20Design%20patterns/Singleton%20pattern.png)

# 4.3 Your favorite pattern
The design pattern I'm most accustomed to is MVC (Model-view-controller). I've always liked the super low coupling, and the exchangability of different layers. If you as an example have a solid DB structure and backend logic, but want another client interface, you just write another client interface, everything else is natively reusable. It may take a bit longer to develop initially, but changing things down the road couldn't be easier. It's useful for basically everything web related, so I'd use it anytime. 

![alt text](https://github.com/SeppiDamgaard/Intelligent-Systems-Test/blob/master/4%20-%20Design%20patterns/MVC%20pattern.png)
