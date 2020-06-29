package xmlrpc;

public class HomematicListener {

    /*
    valuechangelistener

    CCU send the new value of device channel
     */
    public static valueChangeListener valueChangelistener;

    public interface valueChangeListener{
        void onValueChange(String device, String valueKey, Object value);
    }

    public void setValueChangeListener(valueChangeListener listener){
        valueChangelistener = listener;
    }

    /*
    devicelistener

    CCU send after init a list of devices
     */
    public static deviceListener devicelistener;

    public interface deviceListener{
        void onNewDevice(Object[] object);
    }

    public void setdeviceListener(deviceListener listener){
        devicelistener = listener;
    }
}
