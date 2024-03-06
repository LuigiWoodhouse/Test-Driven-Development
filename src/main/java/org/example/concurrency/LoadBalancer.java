package org.example.concurrency;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public abstract class LoadBalancer {
    final List<String> ipAddressList;

    public LoadBalancer(List <String> ipAddressList) {
        this.ipAddressList = Collections.unmodifiableList(ipAddressList);
    }

    public abstract String getIpAddressList();
}
