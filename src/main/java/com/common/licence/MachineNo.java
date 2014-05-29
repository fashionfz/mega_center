package com.common.licence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MachineNo {
	
    public static String getOsName() {
        String os = "";
        os = System.getProperty("os.name");
        return os;
    }
    
    
    public static String getMACAddress() {
        String address = "";
        String os = getOsName();
        if (os.startsWith("Windows")) {
            try {
                String command = "cmd.exe /c ipconfig /all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("Physical Address") > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                        break;
                    }
                    else if(line.indexOf("�����ַ") > 0){  //vista
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                        break;
                    }
                }
                br.close();
                return address.trim().replace("-", "");
            } catch (IOException e) {
            }
        } else if (os.startsWith("Linux")) {
            String command = "/bin/sh -c ifconfig -a";
            Process p;
            try {
                p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("HWaddr") > 0) {
                        int index = line.indexOf("HWaddr") + "HWaddr".length();
                        address = line.substring(index);
                        break;
                    }
                }
                br.close();
            } catch (IOException e) {
            }
        }
        address = address.trim().replace(":", "");
        return address;
    }
    
    public static String getMachineCode(){
        DesUtils des;
        try {
            des = new DesUtils("mega");
            String code = getMACAddress();
            return des.encrypt(code).substring(0, 8);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}
