package com.example.module04_atomics.stack;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {

    private static class Node<T> {

        final T item;
        Node<T> next;

        Node(T item) {
            this.item = item;

        }
    }


        private final AtomicReference<Node<T>> head = new AtomicReference<>();

        public void push(T item) {
            Node<T> newNode = new Node<>(item);
            Node<T> currentHead;

            do{
                currentHead = head.get();
                newNode.next = currentHead;
            }while (!head.compareAndSet(currentHead, newNode));
              return;
            }


        public T pop() {

            Node<T> currentHead;
            Node<T> newHead;

            do {
                currentHead = head.get();
                if (currentHead == null) {
                    return null;
                }
                newHead = currentHead.next;
            } while (!head.compareAndSet(currentHead, newHead));

            return currentHead.item;
        }
        public boolean isEmpty() { return head.get() == null; }

}
