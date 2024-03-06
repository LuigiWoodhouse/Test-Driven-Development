package org.example.concurrency;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RoundRobinLoadBalancer extends LoadBalancer {

    //Round Robin Load Balancing ensure each incoming request is distributed to the next server in line (like a queue)
    //This approach ensures that the load is shared equally among each server
    private int counter = 0;
    private final ReentrantLock lock;

    public RoundRobinLoadBalancer(List<String> list) {
        super(list);
        lock = new ReentrantLock();
    }

    @Override
    public String getIpAddressList() {
        lock.lock();
        try {
            String ip = ipAddressList.get(counter);
            counter += 1;
            if (counter == ipAddressList.size()) {
                counter = 0;
            }
            return ip;
        } finally {
            lock.unlock();
        }
    }
}