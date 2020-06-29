package xmlrpc;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.URL;
import java.util.Vector;

public class HomematicClient {

    /*
    Set Value in CCU

    ip = ip address CCU
    port = port 2001 or 2010
    actor = address (device) + channel to set new Value
    valueKey = the type of value ("STATE" or "LEVEL" ...)
    set = new value

    the user and password is the authentication of the CCU
     */

    //without authentication
    public synchronized void setValue(String ip, String port, String actuator, String valueKey, Object value){
        setValue(ip, port, actuator, valueKey, value,null, null);
    }

    //with authentication
    public synchronized void setValue(String ip, String port, String actuator, String valueKey, Object value, String user, String password){

        try {
            XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();
            xmlRpcClientConfig.setServerURL(new URL("http://" + ip + ":" + port + "/"));

            if(user != null && password != null) {
                xmlRpcClientConfig.setBasicUserName(user);
                xmlRpcClientConfig.setBasicPassword(password);
            }

            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(xmlRpcClientConfig);

            Vector parms = new Vector();
            parms.add(actuator);
            parms.add(valueKey);
            parms.add(value);

            client.execute("setValue", parms);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    Get Value from CCU

    ip = ip address CCU
    port = port 2001 or 2010
    actor = address (device) + channel to get Value
    valueKey = the type of value ("STATE", "ACTIVITY_STATE" or "LEVEL" ...)
    set = new value

    the user and password is the authentication of the CCU
     */

    //without authentication
    public synchronized Object getValue(String ip, String port, String actuator, String valueKey){
        return getValue(ip, port, actuator, valueKey, null, null);
    }

    //with authentication
    public synchronized Object getValue(String ip, String port, String actuator, String valueKey, String user, String password){

        Object value = null;

        try {
            XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();

            if(user != null && password != null) {
                xmlRpcClientConfig.setBasicUserName(user);
                xmlRpcClientConfig.setBasicPassword(password);
            }

            xmlRpcClientConfig.setServerURL(new URL("http://" + ip + ":" + port + "/"));

            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(xmlRpcClientConfig);

            Vector parms = new Vector();
            parms.add(actuator);
            parms.add(valueKey);

            value = client.execute("getValue", parms);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return value;
    }

    /*
    Send init to CCU

    This command started a client on the CCU who sent the new values​from the actuators (e.g. switching status).

    ip = ip address CCU
    port = port 2001 or 2010
    ipServer = local IP ("http://192.168.0.11:12345/")
    valueKey = choose a name of you server (empty stop the client)

    the user and password is the authentication of the CCU
     */

    //without authentication
    public synchronized void init(String ip, String port, String ipServer, String valueKey){
        init(ip, port, ipServer, valueKey, null, null);
    }

    //with authentication
    public synchronized void init(String ip, String port, String ipServer, String valueKey, String user, String password){

        try {
            XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();

            if(user != null && password != null) {
                xmlRpcClientConfig.setBasicUserName(user);
                xmlRpcClientConfig.setBasicPassword(password);
            }

            xmlRpcClientConfig.setServerURL(new URL("http://" + ip + ":" + port + "/"));

            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(xmlRpcClientConfig);

            Vector parms = new Vector();
            parms.add(ipServer);
            parms.add(valueKey);

            client.execute("init", parms);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
