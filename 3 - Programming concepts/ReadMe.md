# 3.1 Object vs. class
## What is the difference between an object and a class. Please explain.
A class is like a template for objects. You define some properties, say name and address, and then all objects you make from said class, will have a name and an address you can make whatever you want.

Change the class, and all subsequent objects will have the new changes.

If you have a function tied to a class, you can't call it directly, you need to make an instance (object) of the class first.

# 3.2 Global variables
## Some say that global variables should be avoided. Why should they be avoided?

Poor modularity and high coupling. If make a function that's dependant on a global variable, exporting, moving or deleting said function becomes exceedingly more difficult.

There's also the problem that a global variable is not only global, but also a variable. Meaning that the variable might not be what you need it to be at a said time, because another piece of code could have changed it.

# 3.3 Thread safe
## Please explain what thread safe means, and how it can be achieved.

Locking a piece of code or a specific function, so that only one thread can use it at any time. This is useful for multi-threaded software, where you need 100% accuracy and stability.


There are a myriad of ways to achieve thread safety, depending on what language you're using. There is typically alot of libraries and frameworks that can help you, or you could simply "lock" your function.

# 3.4 Encapsulation
## Please explain what encapsulation means, and how it can be achieved.

Encapsulation is hiding and blocking access to an objects properties and functions. 

In C# or Java as an example, encapsulation is achieved by declaring it when making a class, function etc., and there's a few to choose from:
* Public: Global access
* Private: Access only to the same class
* Protected: Access only to the same class and children of the class

etc.

# 3.5 Asynchronous vs synchronous programming
## Please explain what asynchronous means in programming and explain or illustrate how it differs from synchronous programming.

Synchronous programming happens in the order you've written your code, one line at a time. Asynchronous programming doesn't have the same order to it, and can be executed at the same time as other pieces of code.

# 3.6 Transaction concept
## Please explain what the concept of transaction means and what it is used for in programming.

"Transaction" is most typically used when talking about databases. In its broadest sense, a transaction is a group of actions that should be performed as if they were a single "bulk" action. 

