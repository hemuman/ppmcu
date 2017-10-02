/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

/**
 *
 * @author PDI
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import json.JSONObject;
import net.mk.dc.DistributedTask;
import net.mk.dc.DistributedTaskService;

public class DCAppFree extends DistributedTask
{
    public DCAppFree(){}
  static public void main( String args[] ) throws Exception {
    System.out.println( "Works! ");
    try {
JSONObject RemoteSysSpec=new JSONObject();
RemoteSysSpec.put("IP_ADDRESS", "127.0.0.1");
if (new DistributedTaskService("AllUsers", RemoteSysSpec, 5559).isServerLive(1000) == DistributedTaskService.READY_FOR_COMPUTING) {
	 System.out.println( "Works too! ");
}} catch (Exception e) {
			System.out.println("Something falied: " + e.getMessage());
			e.printStackTrace();
		}
  }
  @Override
    public String compute() {
       
      System.out.println("Running DCAppFree ");
      System.out.println("Running DCAppFree ");
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	Calendar cal = Calendar.getInstance();
	System.out.println(dateFormat.format(cal.getTime()));
	String result=dateFormat.format(cal.getTime())+" : Running test on " +System.getProperty("os.name");
      return System.getProperty("user.name");
    }
}
