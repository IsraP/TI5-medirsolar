package app;

import data.AppDAO;
import jssc.SerialPort;
import jssc.SerialPortException;
import model.Medicao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DataConsolidator {
    static SerialPort sp;
    static AppDAO<Medicao> medicaoDAO;

    public static void main(String[] args) {
        sp = new SerialPort("/dev/ttyACM0");
        medicaoDAO = new AppDAO<>(Medicao.class);
        int secs = 20;

        try{
            sp.openPort();
            sp.setParams(9600, 8, 1, 0);

            startProcessing(secs);

            sp.closePort();
        }
        catch (SerialPortException e){
            e.printStackTrace();
        }
    }

    private static void startProcessing(int secs) throws SerialPortException {
        String dataStr;

        try{
            while(secs != 0){
                dataStr = mountString();
                Medicao m = processData(dataStr);

                if(m != null)
                    medicaoDAO.insert(m);

                secs--;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // A ideia desse método é ficar ignorando string/char até encontrar um "$", pegar todos os caracteres entre esse "$" e um "&" e retornar a string quando encontrar o "&"
    private static String mountString() {
        boolean hasObtained = false;
        boolean hasData = false;
        StringBuilder sb = new StringBuilder();
        String c;

        try {
            while (!hasObtained) {
                c = sp.readString(1); // Pega uma string de 1 de tamanho (um char basicamente)

                if(c.length() == 1 && c.charAt(0) == '$') { // O "$" marca o começo de uma linha
                    hasData = true;
                    continue;
                }
                else if(hasData && c.length() == 1 && c.charAt(0) == '&') { // O "&" marca o final de uma linha
                    hasData = false;
                    hasObtained = true;
                }

                if(hasData)
                    sb.append(c);
            }
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static Medicao processData(String dataStr) {
        if(dataStr == null || dataStr.length() < 15)
            return null;

        dataStr = dataStr.replaceAll(" ", "");
        String[] splitData = dataStr.split("-");

        if(splitData.length < 3)
            return null;

        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

        return new Medicao(ZonedDateTime.ofInstant(now, zoneId), splitData[0], Float.parseFloat(splitData[1]), Float.parseFloat(splitData[2]));
    }

}
