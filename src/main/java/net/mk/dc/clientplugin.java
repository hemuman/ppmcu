package net.mk.dc;

  import java.io.*;
  import java.lang.*;

public class clientplugin 
{
    int i,ind,ind1;
    
    public String processInput(String theInput) throws IOException 
{
            String s=theInput,p11,p2;
            ind=theInput.indexOf(":");                      //reading
            ind1=ind;
            p11=theInput.substring(0,ind);                  //header
            p2=theInput.substring(ind+1);                   //information
           /*  if(p11.equals("PLGLIST"))
    {
        
         System.out.println("---------------------------------------------------------------");
          System.out.println("AVILABLE WORKTYPE"); 
          System.out.println("PRIME");
          System.out.println("MULPL");
          System.out.println("PLGLIST");
          System.out.println("---------------------------------------------------------------");
         
        return null;
        
        
    }*/
    if(p11.equals("PRIME"))
    {
        DataInputStream in = new DataInputStream(System.in);
        int num1,num2,num3,nums,flag,r;
        String s1=new String();
        ind=theInput.indexOf(":",ind+1);
         System.out.print("the a3na3lyser"+p2);
        num1=Integer.parseInt(theInput.substring(ind1+1,ind));
        ind1=ind;
         System.out.println("the "+num1);
        ind=theInput.indexOf(":",ind+1);
        num2=Integer.parseInt(theInput.substring(ind1+1));
        for(int i=num1;i<=num2;i++) {
            flag=0;
            r=(int)Math.sqrt(i);
            for(int j=2;j<=r;j++) {
                if(i%j==0) {
                    flag=1;
                    break;
                }
            }
            if(flag==0)
                s1=s1+":"+i;
            
        }
        //System.out.print("FORMAT: BYTE/PRIME:/>NUM1:NUM2");
        System.out.println(s1);
        
        //num3=(num2-num1)/nums;
        
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
    