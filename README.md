# Java Concurrency Mastery 

A comprehensive, structured repository designed for mastering multithreading, concurrency patterns, and the Java Memory Model (JMM) from core fundamentals to advanced low-latency and lock-free architectures.



## 🗺️ Learning Roadmap & Modules

### 🟢 Completed Modules

* **[Module 01: Java Thread Fundamentals & Lifecycle](src/main/java/com/example/module01_basics/README.md)**
  * Processes vs. Threads (Memory Model & Call Stacks).
  * 4 Ways to create threads (`Runnable`, `Thread`, Lambdas, `Callable`/`Future`).
  * Thread Lifecycle & States (`NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, `TERMINATED`).
  * Cooperative Interruption (`isInterrupted()`, `InterruptedException`).
  * Daemon Threads vs. User Threads.

* **[Module 02: Synchronization & Memory Visibility](src/main/java/com/example/module02_synchronization/README.md)**
  * Race Conditions, Non-atomic compound actions (`count++`), and Critical Sections.
  * Intrinsic Locks (Monitor Pattern & Object Headers).
  * `synchronized` Methods vs. Fine-Grained `synchronized` Blocks.
  * The Java Memory Model (JMM) & Happens-Before relationship.
  * The `volatile` keyword: Visibility and Instruction Reordering guarantees.

* **[Module 03: Explicit Locks & Advanced Synchronization](src/main/java/com/example/module03_locks/README.md)**
  * The `java.util.concurrent.locks.Lock` interface vs. `synchronized`.
  * `ReentrantLock` features: Non-blocking attempts (`tryLock`), timeouts, and interruptible locks.
  * Fairness vs. Unfairness throughput trade-offs.
  * `ReentrantReadWriteLock`: High-throughput concurrent reads with exclusive writes.
  * `Condition` Variables: Multi-condition wait-sets for Bounded Buffers.
  * High-Level Synchronizers: `CountDownLatch` and `Semaphore`.

---

### 🟡 Upcoming Modules

* **Module 04: Atomic Variables & Lock-Free Programming**
* **Module 05: Concurrent Collections**
* **Module 06: Executor Framework & Thread Pools**

---

## 🛠️ Project Setup & Execution

### Prerequisites:
* **JDK:** Version 21 or higher.
* **Build Tool:** Maven.

### Running a Demo:
Each demo class contains a self-contained `main` method that can be executed directly from IntelliJ IDEA


---

## 📚 Documentation Philosophy

Each module folder contains its own dedicated `README.md` following a standardized format:
1. **Core Concepts Overview:** High-level summary and definitions.
2. **Deep-Dive Mechanics:** Memory architecture, lock behavior, or thread scheduling.
3. **Safe Idioms & Best Practices:** Production-ready code templates.
4. **Module Files & Demos Table:** Direct mapping between concepts and implementation classes.
