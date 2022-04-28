package com.vlinklink.sync.test;

import java.net.InetSocketAddress;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;

/**
 * 单机模式的测试例子--官方---https://github.com/alibaba/canal/
 * 
 * @author jianghang 2013-4-15 下午04:19:20
 * @version 1.0.4
 */
public class SimpleCanalClientTest extends AbstractCanalClientTest {

    public SimpleCanalClientTest(String destination){
        super(destination);
    }

    public static void main(String args[]) {
        // 根据ip，直接创建链接，无HA的功能
        String destination = "example";
        String ip = AddressUtils.getHostIp();
        CanalConnector connector = CanalConnectors.
                newSingleConnector(new InetSocketAddress
                                ("192.168.120.110", 11111),
            destination,
            "canal",
            "canal");

        final SimpleCanalClientTest clientTest = new SimpleCanalClientTest(destination);
        clientTest.setConnector(connector);
        clientTest.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("## stop the canal client");
                clientTest.stop();
            } catch (Throwable e) {
                System.out.println("##something goes wrong when stopping canal:"+e.getMessage());
            } finally {
                System.out.println("## canal client is down.");
            }
        }));
    }

}
