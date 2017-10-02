/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class DTaskMaths extends DistributedTask{
    int[] NumbersToAdd=new int[0];
    long[] NumbersToAddL=new long[0];
    double[] NumbersToAddD=new double[0];
    int OPERATION_TYPE;
    
    public static final int ADD_NUMBERS=1;
    public static final int AVERAGE_OF_NUMBERS=2;
    public static final int MULTIPLICATION_OF_NUMBERS=3;
    
    /**
     *  DTaskMaths.ADD_NUMBERS
     *  DTaskMaths.AVERAGE_OF_NUMBERS
     *  DTaskMaths.MULTIPLICATION_OF_NUMBERS
     * @param NumbersToAdd
     * @param OPERATION_TYPE 
     */
    public DTaskMaths(int[] NumbersToAdd, int OPERATION_TYPE){
        this.NumbersToAdd=NumbersToAdd;
        this.OPERATION_TYPE=OPERATION_TYPE;
    }
     /**
     *  DTaskMaths.ADD_NUMBERS
     *  DTaskMaths.AVERAGE_OF_NUMBERS
     *  DTaskMaths.MULTIPLICATION_OF_NUMBERS
     * @param NumbersToAdd
     * @param OPERATION_TYPE 
     */
     public DTaskMaths( long[] NumbersToAdd, int OPERATION_TYPE){
        this.NumbersToAddL=NumbersToAdd;
         this.OPERATION_TYPE=OPERATION_TYPE;
    }
      /**
     *  DTaskMaths.ADD_NUMBERS
     *  DTaskMaths.AVERAGE_OF_NUMBERS
     *  DTaskMaths.MULTIPLICATION_OF_NUMBERS
     * @param NumbersToAdd
     * @param OPERATION_TYPE 
     */
      public DTaskMaths(double[] NumbersToAdd, int OPERATION_TYPE){
         this.NumbersToAddD=NumbersToAdd;
         this.OPERATION_TYPE=OPERATION_TYPE;
    }
    @Override
    public Object compute() {
        
        if(OPERATION_TYPE==DTaskMaths.ADD_NUMBERS){
        if(NumbersToAdd.length>0)
        { Integer result=0;
        for(int i=0;i<NumbersToAdd.length;i++) result=result+NumbersToAdd[i];
        return result;
        }
        if(NumbersToAddL.length>0)
        {   long resultL=000;
            for(int i=0;i<NumbersToAddL.length;i++) resultL=resultL+NumbersToAddL[i];
            return resultL;
        }
        if(NumbersToAddD.length>0)
        { Double result=0.0;
          for(int i=0;i<NumbersToAddD.length;i++) result=result+NumbersToAddD[i];
          return result;
        }
    }
        
        if(OPERATION_TYPE==DTaskMaths.AVERAGE_OF_NUMBERS){
        if(NumbersToAdd.length>0)
        { Integer result=0;
        for(int i=0;i<NumbersToAdd.length;i++) result=result+NumbersToAdd[i];
        return result/NumbersToAdd.length;
        }
        if(NumbersToAddL.length>0)
        {   long resultL=000;
            for(int i=0;i<NumbersToAddL.length;i++) resultL=resultL+NumbersToAddL[i];
            return resultL/NumbersToAddL.length;
        }
        if(NumbersToAddD.length>0)
        { Double result=0.0;
          for(int i=0;i<NumbersToAddD.length;i++) result=result+NumbersToAddD[i];
          return result/NumbersToAddD.length;
        }
    }
        return null;
   
        
    }
    
}
