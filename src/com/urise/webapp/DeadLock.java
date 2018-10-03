package com.urise.webapp;

public class DeadLock {
    private String subjectName;

    public DeadLock(String subjectName) {
        this.subjectName = subjectName;
    }

    public static void main(String[] args) {
        DeadLock subject0 = new DeadLock("subject 0");
        DeadLock subject1 = new DeadLock("subject 1");
        Thread thread0 = new Thread(() -> subject0.doSomething(subject1));
        Thread thread1 = new Thread(() -> subject1.doSomething(subject0));
        thread0.start();
        thread1.start();
    }

    private synchronized void doSomething(DeadLock subject) {
        System.out.println(this.subjectName + ": I'm going to do something");
        try {
            Thread.sleep(123);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.subjectName + ": I've done something");
        this.doSomethingElse(subject);
    }

    private void doSomethingElse(DeadLock subject) {
        System.out.println(this.subjectName + ": I'm going to do something else");
        synchronized (subject) {
            System.out.println(this.subjectName + ": I've done something else");
        }
    }
}