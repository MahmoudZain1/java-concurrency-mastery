package com.example.module05_collections.notification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InAppNotificationService {

    private final Map<String, CopyOnWriteArrayList<NotificationListener>> emitters = new ConcurrentHashMap<>();


    public void subscribe(String userId, NotificationListener listener) {
        emitters.compute(userId, (key, listeners) -> {
            if (listeners == null) {
                listeners = new CopyOnWriteArrayList<>();
            }
            listeners.addIfAbsent(listener);
            return listeners;
        });
    }

    public void unsubscribe(String userId, NotificationListener listener) {
        emitters.computeIfPresent(userId, (key, listeners) -> {
            listeners.remove(listener);
            return listeners.isEmpty() ? null : listeners;
        });
    }

    public void sendNotification(String userId, String message){
        CopyOnWriteArrayList<NotificationListener> listeners = emitters.get(userId);
        if (listeners != null && !listeners.isEmpty()) {
            for (NotificationListener listener : listeners) {
                listener.onMessage(message);
            }
        }
    }


    public void broadcastNotification(String message){
        emitters.keySet().forEach(userId -> sendNotification(userId, message));
    }
}
