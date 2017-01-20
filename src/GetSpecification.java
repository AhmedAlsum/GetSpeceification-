/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *

 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package javaapplication1;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.sql.*;
import java.util.ArrayList;


public class GetSpecification {

    public static void main(String[] args) throws SocketException {

        
        Connection c = null; //initialize Connection 
        Statement stmt = null;


        long diskSize = new File("/").getTotalSpace(); // path to root Partition 

        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean())
                .getTotalPhysicalMemorySize(); //get RAM Size
        
        //processor Type
        String ProcessorType = System.getProperty("os.arch");
        System.out.println("Processor Type :" + ProcessorType );
      //get size of root Partition 
        System.out.println("Size of partion:=" + diskSize + " Bytes"); 
      //get RAM Size 
        System.out.println("RAM Size=" + memorySize / 1000 / 1000 + " mega"); 
        String networkType ="";
        String ip4 ="";
    //    String ip6 = "";
        
        //get Name and IP of Network Interfaces
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            networkType=netint.getDisplayName();
            System.out.println("Display name: " + netint.getDisplayName());
            //System.out.println("Display name: "+netint.getName());
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            ArrayList<InetAddress> list = Collections.list(inetAddresses);
            ip4 =list.get(1).toString().split("/")[1];
       
            break;
        }
           
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
//            String sql = "CREATE TABLE Machine " +
//                   "(ID INT PRIMARY KEY              NULL," +
//                   " ProcessorType           TEXT    NOT NULL, " + 
//                   " RAMSize                 INT     NOT NULL, " + 
//                   " PartionSize             INT     NOT NULL,"+
//                   " NetworkType             TEXT    NOT NULL, " + 
//                   " ipAddress               TEXT    NOT NULL " 
//              
//              +")"; 
            //Insert machine Speceification to the Table
           String sql = "INSERT INTO Machine (ProcessorType,RAMSize,PartionSize,NetworkType,ipAddress) "
                  + "VALUES ('"+System.getProperty("os.arch")+"','"+memorySize / 1000 / 1000+"','"+diskSize+"','"+networkType+"','"+ip4+"');"; 
//            
            System.out.println(" sql:=" + sql);
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");

        System.out.println();
        
        
        
    }
    





    
}

