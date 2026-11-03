# Module 05: Concurrent Collections

A practical guide to thread-safe collections in `java.util.concurrent` — concurrent maps and copy-on-write lists.

---

## 📌 Core Concepts

### 1. Why Concurrent Collections?

- `ArrayList` and `HashMap` are **not thread-safe**. Unsynchronized concurrent modification can lead to inconsistent behavior or data races.
- `Collections.synchronizedList()` / `synchronizedMap()` wrap a collection with a single shared lock, which can limit concurrency under contention.
- Synchronized wrappers do not make compound operations atomic, and iteration still requires manual synchronization on the wrapper.
- `java.util.concurrent` offers collections built on different strategies: CAS, fine-grained locking, and copy-on-write.

### 2. `ConcurrentHashMap<K, V>` — The Concurrent Map

A high-concurrency alternative to `HashMap`.

- **Non-blocking reads (generally):** `get()` generally does not block and can overlap with updates.
- **Fine-grained updates:** Different hash-table bins can be updated concurrently.
- **Atomic compound operations:** `putIfAbsent()`, `compute()`, `computeIfAbsent()`, and `merge()` are atomic per key.
- **Restriction:** `null` keys and `null` values are not allowed.

### 3. `CopyOnWriteArrayList<E>` — The Snapshot List

A thread-safe `ArrayList` variant for read-heavy, write-rare workloads.

- **Copy-on-write:** Each content-changing mutation copies the backing array, applies the change, and publishes the new array.
- **Snapshot iterators:** An iterator reads the array captured at creation and never reflects later modifications.
- **No `ConcurrentModificationException`** from concurrent modification.
- **Write cost:** Every mutation takes `O(n)` time and extra memory.

---

## ⚙️ Deep-Dive Mechanics

### Copy-on-Write Snapshot Semantics

- In OpenJDK, the backing array reference is stored in a `volatile` field.
- Writers are coordinated by an internal lock, so concurrent mutations do not overwrite one another.
- A writer creates a new array, applies the change, and publishes the new reference.
- Existing iterators keep their old array snapshot; new iterators see the new one.
- Snapshot iterators are read-only: `remove()`, `set()`, and `add()` throw `UnsupportedOperationException`. Modify the list itself instead.
- Readers traverse their snapshots without acquiring the writer lock.
- The old array becomes eligible for garbage collection once nothing references it.

```text
Before modification:   List -> [A, B, C]
After adding D:        List -> [A, B, C, D]

Old Iterator -> [A, B, C]
New Iterator -> [A, B, C, D]
```

### ConcurrentHashMap Bin-Level Concurrency

- The table is an array of bins; different bins can be updated concurrently.
- In OpenJDK, inserting the first node into an empty bin can use CAS.
- Updates to a non-empty bin generally synchronize on the bin's first node.
- A bin with many collisions may be converted into a balanced tree (TreeBin).
- Resizing and other internal operations may involve additional coordination.

**Important:** These are implementation details, not guarantees for every Java implementation.

### Atomic Operations with `merge()`

`ConcurrentHashMap.merge()` performs the whole mapping operation atomically.

```java
map.merge(key, 1L, Long::sum);
```

- If the key does not exist, the supplied value is associated with it.
- If the key exists, the remapping function combines the existing and supplied values.
- If the remapping function returns `null`, the mapping is removed.
- The supplied value must not be `null` (a `NullPointerException` is thrown).
- Concurrent calls on the same key cannot cause a lost update.

```java
map.put("a", 1L);
map.merge("a", 1L, (oldVal, newVal) -> null); // key "a" is removed
```

Sample output from the demo:

```text
10 threads × 1,000 merges = 10,000 atomic updates
Final count: 10,000
```

Keep the remapping function short, and do not modify other mappings of the same map from inside it.

---

## 🔒 Safe Idioms & Best Practices

### Use Atomic Compound Operations

```java
// ❌ Not atomic as a compound operation
Long current = map.get(key);
map.put(key, current == null ? 1L : current + 1L);

// ✅ Atomic read-modify-write
map.merge(key, 1L, Long::sum);
```

Individual `get()` and `put()` calls are thread-safe, but combining them does not make the sequence atomic.

### External Synchronization

- Do not wrap every individual operation in an external lock; the collection is already thread-safe for those.
- An external lock may still be needed for a larger business operation spanning multiple collection operations or shared resources. All relevant code paths must use the same lock.
- For `Collections.synchronizedList()` / `synchronizedMap()`, synchronize manually on the wrapper when using an iterator, spliterator, or stream.

### Respect Snapshot Semantics

- A `CopyOnWriteArrayList` iterator never reflects modifications made after its creation; create a new iterator for a newer view.
- A direct `get(index)` reads the list's current array, not an existing iterator's snapshot.

---

## 📂 Module Files & Demos

| File Name | Key Concept Demonstrated |
| :--- | :--- |
| `copyonwrite/CopyOnWriteArrayListDemo.java` | A reader iterates a subscriber list while a writer adds a subscriber — snapshot iteration without `ConcurrentModificationException`. |
| `map/ConcurrentHashMapDemo.java` | 10 threads × 1,000 atomic `merge(key, 1L, Long::sum)` calls on one key; the final count is exactly 10,000. |