package net.mk.dc;

import java.io.*;
import java.util.*;

class imp
{
    public static void main(String args[])
    {
        Runtime rt = Runtime.getRuntime();
        Process p1=null;
        try{
            p1=rt.exec("winamp.exe -maoj");
            
            p1.waitFor();
        }
        
        catch(Exception e){ System.out.println("error");
        }
        
    }
}
        