package xmlrpc;

public class HomematicListener {

    /*
    listener
     */
    public static Listener listener;

    public interface Listener {
        //CCU send the new value of device channel
        void onValueChange(String device, String valueKey, Object value);

        //CCU send after init a list of devices
        void onNewDevice(Object[] object);
    }

    public void setListener(Listener listener){
        HomematicListener.listener = listener;
    }
}
