Semaphore exercise
==================

Semaphores are generally used to restrict access to a resource. While Java offers an implementation with the Semaphore class [<https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Semaphore.html>] it can also easily be implemented using basic synchronization constructs in Java.

## Exercise
Implement the class Semaphore in `CountingSemaphore.java` only using `synchronized`, `Object.wait()` and `Object.notify()`.

As a hint `CountingSemaphore_jdk.java` shows the desired behaviour using a standard JDK `Semaphore`.
