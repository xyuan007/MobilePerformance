package com.creditease.xyuan.MobilePerformance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class MonitorUtil {
	public static boolean stop = false;
	public static String file = "";
	
    public static void main( String[] args ) throws Exception
    {

    }
    
    public static String getProp(String key) throws Exception{
    	Properties prop = new Properties();
    	FileInputStream fis = new FileInputStream("config.properites");//属性文件流      
    	prop.load(fis);
    	
    	return prop.getProperty(key);
    }
	
    public static String GetCPU(String PackageName) throws IOException {
    	String str3="";
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("adb shell \" dumpsys cpuinfo | grep " + PackageName + "\"");
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line+" ");       
            }
	        String str1=stringBuffer.toString(); 
	        String str2=str1.substring(str1.indexOf(PackageName),str1.indexOf(PackageName)+28);
	        str3 = str2.split(":")[1].trim();
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            try {
                proc.destroy();
            } catch (Exception e2) {  }
        }
        return str3;
    }
    
    public static String GetFlow(String PackageName) throws IOException {
    	String send="";
    	String receive = "";
        String Pid=PID(PackageName);
	    try{
	        Runtime runtime = Runtime.getRuntime();
	     /*   Process proc2 = runtime.exec("");*/
	        Process proc = runtime.exec("adb shell cat /proc/"+Pid+"/net/dev");
	        try {
	            if (proc.waitFor() != 0) {
	                System.err.println("exit value = " + proc.exitValue());
	            }
	            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	            StringBuffer stringBuffer = new StringBuffer();
	            String line = null;
	            while ((line = in.readLine()) != null) {
	                stringBuffer.append(line+" ");                  
	            }
	
		        String str1=stringBuffer.toString();
		        String str2=str1.substring(str1.indexOf("wlan0:"),str1.indexOf("wlan0:")+150);
		        String[] str3=str2.split("\\s+");
		        receive = String.valueOf( Integer.parseInt(str3[1])/1024);
		        send = String.valueOf( Integer.parseInt(str3[9])/1024);
	        } catch (Exception e) {
	            System.err.println(e);
	        }finally{
	            try {
	                proc.destroy();
	            } catch (Exception e2) {
	            }
	        }
	    }
	    catch (Exception StringIndexOutOfBoundsException){}   
        return receive+","+send;
    }
    
    
    public static String GetAvailableBattery() throws IOException {
    	String batt="";
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("adb shell dumpsys battery");
        String str3;
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line+" ");
            }
	        String str1=stringBuffer.toString();
	        String str2=str1.substring(str1.indexOf("level"),str1.indexOf("level")+10);
//	        str3=str2.substring(6,10);
	        batt = str2.trim();
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            try {
                proc.destroy();
            } catch (Exception e2) {}
        }
        return batt;
	}
    
    public static String GetMemory(String packageName) throws Exception {
        String str3=null;
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("adb shell dumpsys meminfo "+packageName);
        try {
        	if (proc.waitFor() != 0) {
            	System.err.println("exit value = " + proc.exitValue());
        	}
        	BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        	StringBuffer stringBuffer = new StringBuffer();
        	String line = null;
        	while ((line = in.readLine()) != null) {
        		stringBuffer.append(line+" ");
        	}
        	String str1=stringBuffer.toString();
        	int begin = str1.indexOf("TOTAL");
        	int end = str1.indexOf("Objects");
        	String str2=str1.substring(begin,end);     
        	str3 = String.valueOf( Integer.parseInt(str2.split("\\s+")[2])/1024);
        } catch (Exception e) {
        	System.err.println(e);
        }finally{
        	try {
        		proc.destroy();
        	} catch (Exception e2) {}
        }
        return str3 ;
    }

    
    //PID
    public static String PID(String PackageName) throws IOException {
          String PID=null;
          Runtime runtime = Runtime.getRuntime();
          Process proc = runtime.exec("adb shell ps |grep "+PackageName);
          try {
              if (proc.waitFor() != 0) {
                  System.err.println("exit value = " + proc.exitValue());
              }
              BufferedReader in = new BufferedReader(new InputStreamReader(
                      proc.getInputStream()));
              StringBuffer stringBuffer = new StringBuffer();
              String line = null;
              while ((line = in.readLine()) != null) {
                  stringBuffer.append(line+" ");                                  
              }
          String str1=stringBuffer.toString();
          String str2=str1.substring(str1.indexOf(" "+PackageName)-46,str1.indexOf(" "+PackageName));
          String str3 =str2.substring(0,7);
          str3 = str3.trim();
          PID=str3;  
          } catch (Exception e) {
              System.err.println(e);
          }finally{
              try {
                  proc.destroy();
              } catch (Exception e2) {
              }
          }
          return PID;
    }         
}
