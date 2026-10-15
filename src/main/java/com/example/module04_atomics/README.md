# Module 04: Atomic Variables & Lock-Free Programming

A practical guide to hardware-level atomic operations, the Compare-And-Swap (CAS) mechanism, and building lock-free data structures that eliminate lock contention entirely.

---

## 📌 Core Concepts Overview

1. **Why Atomic Variables?**
   - `synchronized` and `ReentrantLock` solve thread safety by blocking threads (pessimistic locking).
   - Blocking has a cost: context switching, thread scheduling overhead, and potential contention.
   - Atomic variables offer a **non-blocking** alternative: they guarantee thread safety without ever putting a thread to sleep.
   - Package: `java.util.concurrent.atomic`.

2. **Compare-And-Swap (CAS) — The Foundation:**
   - CAS is a single, **atomic** CPU instruction supported natively by modern hardware (e.g., `CMPXCHG` on x86).
   - It takes 3 arguments: the memory address, the **expected** current value, and the **new** value.
   - The CPU executes atomically: "If the value at this address equals the expected value, replace it with the new value and return `true`. Otherwise, do nothing and return `false`."
   - This is the engine powering every class in `java.util.concurrent.atomic`.

3. **The CAS Loop (Optimistic Locking Pattern):**
   - Read the current value.
   - Compute the new value.
   - Attempt CAS. If it fails (another thread changed the value first), retry from step 1.
   - No thread ever blocks. Threads simply retry.

4. **The ABA Problem:**
   - CAS only compares **values**, not **identity or history**.
   - A thread reads value `A`. Another thread changes `A` → `B` → `A`. The first thread's CAS succeeds incorrectly, believing the value was never changed.
   - **Solution:** `AtomicStampedReference<V>` adds an integer **stamp (version counter)** alongside the value. CAS now checks both the value AND the stamp, making invisible modifications detectable.

5. **Lock-Free Data Structures:**
   - By applying CAS loops to pointer/reference updates, entire data structures (stacks, queues, lists) can be made thread-safe without any locks.
   - **Guarantee:** At least one thread always makes progress, eliminating the risk of deadlock entirely.

---

## ⚙️ CAS in Practice

The standard CAS loop pattern using `AtomicInteger`:

```java
AtomicInteger value = new AtomicInteger(0);

public int increment() {
    int existingValue;
    int newValue;
    do {
        existingValue = value.get();          // 1. Read current value
        newValue = existingValue + 1;         // 2. Compute new value
    } while (!value.compareAndSet(existingValue, newValue)); // 3. Attempt CAS, retry on failure
    return newValue;
}
```

---

## 🔍 ABA Problem vs. Stamped Solution

| Approach | Class Used | Detects A→B→A? |
| :--- | :--- | :--- |
| Plain CAS | `AtomicReference<V>` | ❌ No — CAS succeeds incorrectly |
| Stamped CAS | `AtomicStampedReference<V>` | ✅ Yes — stamp mismatch causes CAS to fail |

---

## 📂 Module Files & Demos

| File Name | Key Concept Demonstrated |
| :--- | :--- |
| `cas/LockFreeCounter.java` | Thread-safe counter using manual CAS loop with `AtomicInteger` |
| `cas/LockFreeCounterDemo.java` | Two threads increment 200,000 times each; verifies result is always exactly 400,000 |
| `aba/ABASimulationDemo.java` | Reproduces the ABA problem using `AtomicReference` — CAS succeeds despite hidden state changes |
| `aba/ABAStampedSolutionDemo.java` | Solves the ABA problem using `AtomicStampedReference` — stamp mismatch blocks incorrect CAS |
| `stack/LockFreeStack.java` | Generic lock-free stack built on `AtomicReference` with CAS-based `push()` and `pop()` |
| `stack/LockFreeStackDemo.java` | Two threads push 1,000 items each concurrently; verifies all 2,000 items are present |

---
