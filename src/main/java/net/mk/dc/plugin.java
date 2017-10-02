package net.mk.dc;

import java.io.*;

public class plugin 
{
    int i,ind;
    
    public String processInput(String theInput) throws IOException 
{
            String s=theInput,p11,p2;
            ind=theInput.indexOf(":");                      //reading
            p11=theInput.substring(0,ind);                  //header
            p2=theInput.substring(ind+1);                   //information
             if(p11.equals("PLGLIST"))
    {
        
         System.out.println("---------------------------------------------------------------");
          System.out.println("AVILABLE WORKTYPE"); 
          System.out.println("PRIME");
          System.out.println("MULPL");
          System.out.println("PLGLIST");
          System.out.println("---------------------------------------------------------------");
         
        return null;
        
        
    }
    if(p11.equals("PRIME"))
    {
        DataInputStream in = new DataInputStream(System.in);
        int num1,num2,num3,nums;
         String s1=new String();
        nums=Integer.parseInt(p2);
        System.out.print("FORMAT: BYTE/PRIME:/>NUM1:NUM2");
        System.out.println("BYTE/PRIME:/>");
        System.out.print("First no:");
        num1=Integer.parseInt(in.readLine());
        System.out.print("Second no:");
        num2=Integer.parseInt(in.readLine());
        
        num3=(num2-num1)/(nums-1);
        System.out.println(num1+":"+num2+":"+num3+":"+nums);
        for(int i=0;i<=(nums-2);i+=1)
			{
				 s1=s1+("PRIME:"+(num1+(i*num3))+":"+(num1+((i+1)*num3))+";");
				
				
			}
        
        return s1;
        
        
    }
              if(p11.equals("MULPL"))
    {
        DataInputStream in = new DataInputStream(System.in);
        int num1,num2,num3,num4,nums;
        nums=Integer.parseInt(p2);
        System.out.print("FORMAT: BYTE/PRIME:/>NUM1:NUM2");
        System.out.println("BYTE/PRIME:/>");
        System.out.print("First no:");
        num1=Integer.parseInt(in.readLine());
        System.out.print("Second no:");
        num2=Integer.parseInt(in.readLine());
        System.out.print("Multiple of:");
        num3=Integer.parseInt(in.readLine());
        num4=(num2-num1)/nums;
        
        return "MULPL"+num3+":50:70:"+(nums-1);
        
        
    }
    
        return null+p11;
        
    }

}      
    