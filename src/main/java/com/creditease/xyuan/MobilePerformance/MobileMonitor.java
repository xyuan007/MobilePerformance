package com.creditease.xyuan.MobilePerformance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MobileMonitor 
{
    public static void main( String[] args ) throws Exception
    {
    	DeviceMonitor();
    }
    
    public static void DeviceMonitor() throws Exception{
    	System.out.println("begin monitor package");
    	
    	//建立结果目录及文件 
    	String dir = "D:\\MobileMonitor";
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
    	String file = String.format("%s\\Monitor%s.csv", dir,df.format(new Date()));
    	MonitorUtil.file = file;
    	File f = new File(file);
    	if(!f.exists())
    		f.createNewFile();
    	
    	//异步调用监控
    	DeviceMonitor dm = new DeviceMonitor();
    	Thread t = new Thread(dm);
    	t.start();
    	
    	//等待结束
    	InputStreamReader isr = new InputStreamReader(System.in);
    	BufferedReader br = new BufferedReader(isr);
    	String s = "";
    	try {
    		while(!s.equals("end"))
    			s = br.readLine();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	finally{
    		MonitorUtil.stop = true;
    	}
    	
    }
}
