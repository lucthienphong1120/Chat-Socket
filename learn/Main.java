/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learn;

/**
 *
 * @author Phong
 */
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;

public class Main {

    public static void main(String s[])
            throws Exception {
        try {
            Properties systemSettings
                    = System.getProperties();
            systemSettings.put("proxySet", "true");
            systemSettings.put("http.proxyHost",
                    "proxy.mycompany1.local");
            systemSettings.put("http.proxyPort", "80");
            URL u = new URL("http://www.google.com");
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            System.out.println(con.getResponseCode()
                    + " : " + con.getResponseMessage());
            System.out.println(con.getResponseCode()
                    == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(false);
        }
        System.setProperty("java.net.useSystemProxies",
                "true");
        Proxy proxy = (Proxy) ProxySelector.getDefault().
                select(new URI("http://www.yahoo.com/")).iterator().
                next();;
        System.out.println("proxy hostname : " + proxy.type());
        InetSocketAddress addr = (InetSocketAddress) proxy.address();
        if (addr == null) {
            System.out.println("No Proxy");
        } else {
            System.out.println("proxy hostname : "
                    + addr.getHostName());
            System.out.println("proxy port : "
                    + addr.getPort());
        }
    }
}
