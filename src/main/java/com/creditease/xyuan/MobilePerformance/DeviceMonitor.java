package com.creditease.xyuan.MobilePerformance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeviceMonitor implements Runnable{

	public void run() {
    	FileWriter fw = null;
    	BufferedWriter bw = null;
		try {
			fw = new FileWriter(MonitorUtil.file);
	    	bw = new BufferedWriter(fw);
	    	String packageName = MonitorUtil.getProp("packagename");
	    	int inteval = Integer.parseInt( MonitorUtil.getProp("inteval"));
	    	
	    	bw.write("time,cpu(%),memory(KB),flowin(KB),flowout(KB)\r\n");
	    	
	    	while(!MonitorUtil.stop){
		    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		    	String time = df.format(new Date());
		    	String cpu = MonitorUtil.GetCPU(packageName);
		    	String mem = MonitorUtil.GetMemory(packageName);
		    	String flow = MonitorUtil.GetFlow(packageName);
				bw.write(String.format("%s,%s,%s,%s\r\n", time,cpu,mem,flow));
				
				Thread.sleep(inteval*100);
	    	}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
}
