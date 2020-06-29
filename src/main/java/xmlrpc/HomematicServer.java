package xmlrpc;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

public class HomematicServer {

    WebServer webServer;

    //XML-RPC server
    public HomematicServer(int port){
        webServer = new WebServer(port);

        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new MyHandlerMapping();

        try {
            //add handler class
            phm.addHandler("methods", Handler.class);
            phm.addHandler("system", Handler.class);

            xmlRpcServer.setHandlerMapping(phm);

            //server config
            XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
    }

    //start listening
    public void start(){
        if (webServer != null){
            try {
                webServer.start();
                System.out.println("Server is ready");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    MyHandlerMapping is for the Homematic IP client. The port 2010 don't send the handler class!
     */
    private class MyHandlerMapping extends PropertyHandlerMapping {

        public XmlRpcHandler getHandler(String pHandlerName) throws XmlRpcException {

            //System.out.println("somebody asked for a handler for " + pHandlerName);

            XmlRpcHandler result = null;

            try {
                result = super.getHandler(pHandlerName);
            } catch (Exception ex) {
                //ignore
            }

            if (result == null) {
                result = super.getHandler("system." + pHandlerName);
            }

            if (result == null) {
                System.out.println("no handler found: " + pHandlerName);
            }

            return result;
        }
    }
}