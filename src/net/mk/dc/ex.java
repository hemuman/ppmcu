package net.mk.dc;

import java.lang.String;
class ex
	{
		public static void main(String args[])
		{
			
			int a=3,b=100,c=7,d;
			int e;
			d=(b-a)/c;
			e=(int) d;
			if(e<=0)
			{
				System.out.println(1);
			}
			else
			{
			System.out.println(e);
			}
                        String s=new String();
                         String s1;
			for(int i=0;i<=c;i+=1)
			{
				 s=s+("PRIME:"+(a+(i*e))+":"+(a+((i+1)*e))+";");
				
				
			}
                         s1=s.replace('.','0');
                                //s1=s1.replace('0',':');
				System.out.println(s);
                                System.out.println(s1);
		}
	}
	

		