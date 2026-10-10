# Module 01: Java Thread Fundamentals & Lifecycle

A concise guide to the core building blocks of multithreading in Java.

---

## 📌 Core Concepts Overview

1. **Process vs Thread:**
   - **Process:** An independent program with its own dedicated memory address space and heap.
   - **Thread:** The smallest unit of execution within a process; shares the heap but maintains its own **Call Stack** and **Program Counter (PC)**.

2. **4 Ways to Create & Run Threads:**
   - `extends Thread`: Direct subclassing; simple but prevents extending other classes.
   - `implements Runnable`: Decouples the task logic from execution (Single Responsibility).
   - **Lambda Expressions:** `new Thread(() -> { ... }).start()` for concise, modern Java.
   - `Callable<V>` & `Future<V>`: For async tasks that return a computed result or throw checked exceptions.

---

## 🔄 Thread Lifecycle & States (`Thread.State`)

A Java thread moves through 6 distinct states during its lifetime:
- `NEW`: Instantiated via `new Thread()`, but `start()` has not been called.
- `RUNNABLE`: Executing in the JVM or ready in the OS scheduling queue.
- `BLOCKED`: Waiting to acquire a monitor lock for a `synchronized` block/method.
- `WAITING`: Waiting indefinitely for another thread (via `join()` or `wait()`).
- `TIMED_WAITING`: Waiting for a specified time window (via `sleep(ms)` or `join(ms)`).
- `TERMINATED`: Finished executing `run()` or stopped due to an unhandled exception.

---

## 🛑 Flow Control & Safe Interruption

- **`join()`:** Pauses the calling thread until the target thread finishes execution.
- **`sleep(ms)`:** Temporarily suspends the thread without releasing acquired locks.
- **Cooperative Interruption:** `thread.stop()` is deprecated and unsafe. Use `thread.interrupt()` and check `Thread.currentThread().isInterrupted()`.
- **Handling `InterruptedException`:** Always restore the interrupt status via `Thread.currentThread().interrupt()` when catching this exception.

---

## 👥 Daemon Threads vs User Threads

- **User Threads (Default):** The JVM stays alive as long as at least one user thread is running.
- **Daemon Threads (`setDaemon(true)`):** Background helper threads (e.g., GC). The JVM exits immediately when all user threads finish, forcefully killing active daemon threads.
- **Rule:** `setDaemon(true)` must be called **before** `start()`.

---

## 📂 Module Files & Demos

| File Name | Key Concept Demonstrated |
| :--- | :--- |
| `CustomThreadTask.java` | Thread creation via `extends Thread` |
| `CustomRunnableTask.java` | Task definition via `implements Runnable` |
| `CustomCallableTask.java` | Returning values using `Callable<Integer>` |
| `ThreadCreationDemo.java` | `join()`, thread states, and `ExecutorService` |
| `InterruptibleTask.java` | Graceful cancellation with interrupt flag handling |
| `ThreadInterruptionDemo.java` | Signaling thread interruption |
| `DaemonDemo.java` | Daemon thread lifecycle and Lambda syntax |
| `payment/*` | Practical async payment verification simulation |

---

