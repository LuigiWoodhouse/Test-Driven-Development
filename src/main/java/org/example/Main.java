package org.example;

import org.example.concurrency.Client;
import org.example.concurrency.LoadBalancer;
import org.example.concurrency.RandomLoadBalancer;
import org.example.concurrency.RoundRobinLoadBalancer;
import org.example.concurrency.WeightedRoundRobinLoadBalancer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        int NUM_OF_REQUESTS = 15;
        Client client = new Client();

        ArrayList<String> ipPool = new ArrayList <>();
        ipPool.add("192.168.0.1");
        ipPool.add("192.168.0.2");
        ipPool.add("192.168.0.3");
        ipPool.add("192.168.0.4");
        ipPool.add("192.168.0.5");

        Map<String, Integer> ipPoolWeighted = new HashMap<>();
        ipPoolWeighted.put("192.168.0.1",  6);
        ipPoolWeighted.put("192.168.0.2",  6);
        ipPoolWeighted.put("192.168.0.3",  3);

        client.printNextTurn("Random");
        LoadBalancer random = new RandomLoadBalancer(ipPool);
        client.simulateConcurrentClientRequest(random, NUM_OF_REQUESTS);

        client.printNextTurn("Round-Robin");
        LoadBalancer roundRobbin = new RoundRobinLoadBalancer(ipPool);
        client.simulateConcurrentClientRequest(roundRobbin, NUM_OF_REQUESTS);

        client.printNextTurn("Weighted-Round-Robin");
        LoadBalancer weightedRoundRobin = new WeightedRoundRobinLoadBalancer(ipPoolWeighted);
        client.simulateConcurrentClientRequest(weightedRoundRobin, NUM_OF_REQUESTS);

        System.out.println("Main exits");
    }
}