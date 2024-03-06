package org.example.concurrency;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer {

    //Random load balancing chooses the next instance of a server(in this case, ip address) at random and serves it to the client
    public RandomLoadBalancer(List<String> ipAddressList) {
        super(ipAddressList);
    }

    @Override
    public String getIpAddressList() {
        Random random = new Random();
        return ipAddressList.get(random.nextInt(ipAddressList.size()));
    }
}