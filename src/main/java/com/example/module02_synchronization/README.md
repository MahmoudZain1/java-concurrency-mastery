# Module 02: Synchronization & Memory Visibility in Java

A deep dive into thread safety, mutual exclusion, critical sections, and the Java Memory Model (JMM).

---

##  Core Concepts Overview

1. **Race Conditions & Critical Sections:**
    - **Critical Section:** A piece of code that accesses shared mutable state.
    - **Race Condition (Read-Modify-Write):** Occurs when multiple threads concurrently execute a non-atomic operation (such as `count++`), resulting in lost updates.

2. **Intrinsic Locks (Monitors):**
    - Every Java object has an implicit internal monitor lock.
    - `synchronized` guarantees **Mutual Exclusion** (only one thread enters at a time) and establishes a **Happens-Before** memory visibility boundary.

---

##  Synchronized: Methods vs. Blocks

- **Synchronized Method:** Acquires the monitor of `this` (for instance methods) or `Class<?>` (for static methods) for the entire method body.
- **Synchronized Block:** Allows finer-grained locking on a dedicated lock object (`private final Object lock = new Object()`), reducing contention and preventing external classes from hijacking the lock.

---

##  Memory Visibility & The `volatile` Keyword

- **CPU Caching vs Main Memory:** Threads may cache variable values in CPU registers or L1/L2 caches. Changes made by one thread might not be immediately visible to another.
- **`volatile` Guarantees:**
    1. **Visibility:** Writes to a volatile variable are immediately flushed to main memory, and reads always fetch from main memory.
    2. **Ordering:** Prevents instruction reordering around the read/write (Memory Barrier).
- ** Important Distinction:** `volatile` guarantees **Visibility**, NOT **Atomicity**. It is ideal for state flags (e.g., `isRunning`), but unsafe for compound operations like `count++`.

---

##  Module Files & Demos

| File Name | Key Concept Demonstrated |
| :--- | :--- |
| `SynchronizedCounter.java` | Compares unsafe increment with synchronized method and private lock block |
| `SynchronizationDemo.java` | Demonstrates race conditions (lost updates) vs thread-safe counter execution |
| `VolatileVisibilityWorker.java` | Demonstrates thread execution loop controlled by a `volatile boolean` flag |
| `VolatileVisibilityDemo.java` | Demonstrates memory visibility across threads when halting a background worker |

---