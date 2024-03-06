package org.example.concurrency;

import java.util.stream.IntStream;

public class Client {
    public void simulateConcurrentClientRequest(LoadBalancer loadBalancer, int numOfCalls) {

        IntStream
                .range(0, numOfCalls)
                .parallel()
                .forEach(i ->
                        System.out.println(
                                "IP: " + loadBalancer.getIpAddressList()
                                        + " --- Request from Client: " + i
                                        + " --- [Thread: " + Thread.currentThread().getName() + "]")
                );
    }

    public void printNextTurn(String name) {
        System.out.println("---");
        System.out.println("Clients starts to send requests to " + name + " Load Balancer");
        System.out.println("---");
    }
}