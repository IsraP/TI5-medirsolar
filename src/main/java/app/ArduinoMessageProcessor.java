package app;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ArduinoMessageProcessor implements SerialPortEventListener {
    SerialPort sp;
    StringBuilder message = new StringBuilder();

    public ArduinoMessageProcessor(SerialPort sp){
        this.sp = sp;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        System.out.println("SOCORRO");

        if (event.isRXCHAR() && event.getEventValue() > 1) {
            try {
                byte[] buffer = sp.readBytes();
                for (byte b: buffer) {
                    if ( (b == '\r' || b == '\n') && message.length() > 0) {
                        String toProcess = message.toString();
                        System.out.println(toProcess);

                        DataConsolidator.processData(toProcess);


                        message.setLength(0);
                    }
                    else {
                        message.append((char)b);
                    }
                }
            }
            catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }
}
