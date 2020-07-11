package xmlrpc;

import org.apache.xmlrpc.XmlRpcException;

import java.util.Map;

public class Handler {

    public Object listMethods(String i) {
        return new Object[]{""};
    }

    public Object listDevices(String i) {
        return new Object[]{};
    }

    public Object[] newDevices(String i, Object[] obj) throws XmlRpcException {

        if(HomematicListener.listener != null){
            HomematicListener.listener.onNewDevice(obj);
        }

        return new Object[]{""};
    }

    public Object event(String address, String value_key, boolean value) throws XmlRpcException{//, ) {
        return new Object[]{};
    }

    public Object[] multicall(Object[] objs) {
        String stateName = "";
        String stateType = "";
        String stateValue = "";
        Object[] var8 = objs;
        int var7 = objs.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            Object obj = var8[var6];

            try {
                Map<String, Object> m = (Map)obj;
                String childName = (String)m.get("methodName");
                Object[] childParams = (Object[])m.get("params");

                for(int x = 0; x < childParams.length; ++x) {
                    switch(x) {
                        case 1:
                            stateName = this.getString(childParams[x]);
                            break;
                        case 2:
                            stateType = this.getString(childParams[x]);
                            break;
                        case 3:
                            stateValue = this.getString(childParams[x]);
                    }
                }

                if (HomematicListener.listener != null){
                    HomematicListener.listener.onValueChange(stateName, stateType, stateValue);
                }
            } catch (NullPointerException var13) {
                var13.printStackTrace();
            }
        }

        return objs;
    }

    private String getString(Object object) {
        String i = "";
        if (object instanceof String) {
            i = object.toString().trim();
        } else {
            i = object.toString().trim();
        }

        return i;
    }
}
