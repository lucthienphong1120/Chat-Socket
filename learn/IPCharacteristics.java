/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class IPCharacteristics {

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("192.168.1.10");
            if (address.isAnyLocalAddress()) {
                System.out.println(address + " is a wildcard address");
            }
            if (address.isLoopbackAddress()) {
                System.out.println(address + " is a loopback address");
            }
            if (address.isLinkLocalAddress()) {
                System.out.println(address + " is a link local address");
            } else if (address.isSiteLocalAddress()) {
                System.out.println(address + " is a site local address");
            } else {
                System.out.println(address + " is a global address");
            }
            if (address.isMulticastAddress()) {
                if (address.isMCGlobal()) {
                    System.out.println(address + " is a global multicast address");
                } else if (address.isMCOrgLocal()) {
                    System.out.println(address + " is an organization wide multicast address");
                } else if (address.isMCSiteLocal()) {
                    System.out.println(address + " is a site wide multicast address");
                } else if (address.isMCLinkLocal()) {
                    System.out.println(address + " is a subnet wide multicast address");
                } else if (address.isMCNodeLocal()) {
                    System.out.println(address + " is an interface local multicast address");
                }
            } else {
                System.out.println(address + " is an unicast address");
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(IPCharacteristics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
