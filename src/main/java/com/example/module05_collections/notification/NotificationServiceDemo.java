package com.example.module05_collections.notification;

public class NotificationServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        InAppNotificationService notificationService = new InAppNotificationService();

        String userMahmoud = "mahmoud";
        String userZain = "zain";

        NotificationListener listenerMahmoud = mes -> System.out.println("Mahmoud received notification: " + mes);
        NotificationListener listenerZain = mes-> System.out.println("Zain received notification: " + mes);

        notificationService.sendNotification(userMahmoud, "Hello Mahmoud!");


        notificationService.subscribe(userMahmoud, listenerMahmoud);
        notificationService.subscribe(userZain, listenerZain);

        Thread notificationThread = new Thread(() -> {
            for(int i =0; i<=3; i++){
                notificationService.sendNotification(userMahmoud, "Failed login attempt " + i);
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread disconnectThread = new Thread(() -> {

            try{
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(">>> Mahmoud unsubscribed <<<");
            notificationService.unsubscribe(userMahmoud, listenerMahmoud);
        });

        notificationThread.start();
        disconnectThread.start();

        notificationThread.join();
        disconnectThread.join();

        System.out.println("Broadcast a notification to all connected users");
        notificationService.broadcastNotification("The System Will undergo maintenance tonight at 12:00 AM");


    }

}
