Barrier Synchronization
=======================

## Exercises

* What syncronization barrier implements are available in the JDK? See <https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/package-summary.html>

`ExpensiveComputation.java` uses threads to do computation in parallel but it is missing a proper synchronization mechanims to merge the computation results.

* Use `FutureTask` to finish the implementation.
* Use `CountDownLatch` to finish the implementation.