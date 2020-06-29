# Homematic-XML-RPC
 Homematic-XML-RPC API
 
 Some helpfull links:
 
 https://www.eq-3.de/Downloads/eq3/download%20bereich/hm_web_ui_doku/HM-Script_4-Datenpunkte.pdf
 https://www.eq-3.de/Downloads/eq3/download%20bereich/hm_web_ui_doku/HmIP_Device_Documentation.pdf
 
 How to use:
 
 /*
Homematic Api example
*/

public class Main implements HomematicListener.valueChangeListener, HomematicListener.deviceListener{
    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        new HomematicServer(12345).start();

        new HomematicListener().setValueChangeListener(this);
        new HomematicListener().setdeviceListener(this);

        sendinit();
    }

    /*
    Send init to CCU

    This command started a client on the CCU who sent the new values​from the actuators (e.g. switching status).
    The command must be send to the port for Homematic and Homematic IP deposits.
    */
    private void sendinit(){
        String ipccu = "192.168.0.11";         //IP CCu
        String yourIP = "192.168.0.10";        //your own IP

        //Send int for Homematic IP port 2010
        new HomematicClient().init(ipccu, "2010", yourIP, "hmIPClient");

        //Send int for Homematic port 2001
        new HomematicClient().init(ipccu, "2001", yourIP, "hmClient");
    }


    @Override
    public void onNewDevice(Object[] objects) {
        for (Object o: objects){
            String type = "";
            String address = "";

            String[] strings = o.toString().split(",");

            for(String s: strings){
                if (s.contains("PARENT=")){
                    address = s.substring(s.indexOf('=') + 1);
                }

                if (s.contains("PARENT_TYPE=")){
                    type = s.substring(s.indexOf('=') + 1);
                }
            }

            System.out.println("The device " + address + " is a " + type);
        }
    }

    @Override
    public void onValueChange(String device, String valueKey, Object value) {
        System.out.println(device + "has a new state " + value);
    }

    private void setValue(){
        String ip = "192.168.0.11";         //IP CCu
        String port = "2010";               //port 2001 for Homematic / port 2010 for Homematic IP

        String device = "000AB16A0:4";      //actuator
        String valueKey = "State";
        boolean value = true;

        String user = "user";
        String pwd = "12345678";

        //without authentication
        new HomematicClient().setValue(ip, port, device, valueKey, value);

        //with authentication
        new HomematicClient().setValue(ip, port, device, valueKey, value, user, pwd);
    }

    private Object getValue() {
        String ip = "192.168.0.11";         //IP CCu
        String port = "2010";               //port 2001 for Homematic / port 2010 for Homematic IP

        String device = "000AB16A0:4";      //actuator
        String valueKey = "State";

        String user = "user";
        String pwd = "12345678";

        //without authentication
        //new HomematicClient().setValue(ip, port, device, valueKey, value);

        //with authentication
        return new HomematicClient().getValue(ip, port, device, valueKey, user, pwd);
    }
}
