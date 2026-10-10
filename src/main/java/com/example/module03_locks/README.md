# Module 03: Explicit Locks &  Synchronization

A comprehensive guide to `java.util.concurrent.locks`, advanced lock mechanisms, condition variables, and thread synchronizers.

---

## 📌 Core Concepts Overview

1. **Why Explicit Locks (`Lock` Interface)?**
   - Overcomes the limitations of `synchronized`: supports timed lock attempts (`tryLock`), interruptible locks (`lockInterruptibly`), and non-block-structured lock acquisition.
   - Requires explicit lock management: always acquire outside `try` and release in `finally`.

2. **`ReentrantLock` & Fairness Policies:**
   - **Reentrant:** A thread holding the lock can re-acquire it without deadlock.
   - **Unfair (Default):** Maximizes throughput by allowing barging (threads may skip the wait queue).
   - **Fair (`new ReentrantLock(true)`):** Enforces strict FIFO order, reducing thread starvation at the cost of throughput.

3. **Read/Write Locking (`ReentrantReadWriteLock`):**
   - Separates read operations from write operations.
   - Allows multiple concurrent readers (Shared Lock) or a single writer (Exclusive Lock).
   - Dramatically boosts throughput for read-heavy workloads.

4. **Condition Variables (`Condition` Interface):**
   - Replaces traditional `Object.wait()` and `Object.notify()`.
   - Allows multiple independent wait-sets per lock (e.g., `notFull` and `notEmpty` in a bounded buffer).

5. **High-Level Synchronizers:**
   - **`CountDownLatch`:** Allows one or more threads to wait until a set of operations being performed in other threads completes.
   - **`Semaphore`:** Controls access to a shared resource through a set of permits (rate limiting / resource pooling).

---

## 🔒 Safe Locking Idiom

Always follow the standard `try-finally` pattern:

```java
Lock lock = new ReentrantLock();

lock.lock(); // 1. Acquire outside try
try {
    // 2. Critical Section
    doWork();
} finally {
    lock.unlock(); // 3. Guarantee release in finally
}
```

---

## 🔄 Deadlock Prevention with `tryLock()`

Avoid indefinite thread blocking during multi-resource acquisition (e.g., bank account transfers):

```java
if (fromAccount.getLock().tryLock(1, TimeUnit.SECONDS)) {
    try {
        if (toAccount.getLock().tryLock(1, TimeUnit.SECONDS)) {
            try {
                // Execute transfer safely
            } finally {
                toAccount.getLock().unlock();
            }
        }
    } finally {
        fromAccount.getLock().unlock();
    }
}
```

---

## 📂 Module Files & Demos

| File Name | Key Concept Demonstrated |
| :--- | :--- |
| `explicitlocks/Account.java` | Thread-safe account domain model using `ReentrantLock` and `tryLock()` |
| `explicitlocks/AccountDemo.java` | Basic balance operations guarded by explicit locks |
| `explicitlocks/BankTransferDemo.java` | Deadlock-free account transfers using timed `tryLock()` |
| `explicitlocks/FairLockDemo.java` | Contrasting Fair vs. Unfair lock scheduling order |
| `explicitlocks/InterruptibleLockDemo.java` | Canceling blocked threads via `lockInterruptibly()` |
| `explicitlocks/LockContentionDemo.java` | Measuring lock contention and thread waiting under load |
| `readwritelock/ReadWriteLockDemo.java` | High-throughput concurrent reads with exclusive writes |
| `conditions/ConditionBufferDemo.java` | Producer-Consumer bounded buffer using `Condition.await()` & `signal()` |
| `synchronizers/CountDownLatchDemo.java` | Coordinating service startup using a countdown gate |
| `synchronizers/SemaphoreDemo.java` | Restricting concurrent access using permit acquisition |

---
