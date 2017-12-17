package com.thinkgem.jeesite.modules.test.service;
import LinuxAPI.LinuxVerifyAPI;

public class LinuxAPItextsample {

	public static void main(String[] args)
    {          
       //调用LinuxAPIsample jar包
    	//LinuxVerifyAPI sample = new LinuxVerifyAPI();
       //API初始化密钥，通过客户端传入
    	//组2
    	//String strKey = ("0102030405060708090a0b0c0d0e0f10");
    	//组1
        String strKey = ("12345678901234567890123456789012");
	    //认证等级（1——4可选），通过客户端传入
	    int level = 2; 
	    
		//核对用模板（Base64转码后），通过客户端传入 核对用模板不受键值控制
	    //linux 
	    //组1 键值取得模板
	    String strVerify = "TAMAACAUNwAEAWQCAAAAQDcABAHgBwwWEyUEAAAAAAA3AAQBAAAAAAAAAAAAAAAAAAAAABQDAAACAwAAcUz3jyqpS9I sQz9kyNLW1Bao80UQX  lCu1mxhUQfmE3BrN3ej9vlQiPa6HI2ih30tQbe3gNYzs2rAZvyXpkcmpXVKEXcvnyJVG6vg oTGYZtcbFw7F Cb3XiW547H6kt7yMrmipQhohNq5ztc21uLca/lH384VDyLmmg9fHtry2Qh4qgVWX4s1y DamPs8Zw1juFrhKIkIEAxSuulJAhJIMg5mQ YJdKeN9UMvl4sU7AhH6tgKMinohLrRrMVuf8UeniFHQk9Sv5x2YM5acT3pxSLSZ q6M1bZlpvcUAJwCIiGZZOBURdXvNXdsXTlqE05FtRKF 8xOtBu9Kmsy9qYGkvCoNRNvGKVP7baB5S2YDfYWo5k4I4NuLUv9YfyXZNp6xZLaCUPqnVrqM9QKrtBEMF/dpNq3lC VIxhU EN2YX0NIa6yfnLu9Mrhk9pJzmMz45tsH8MnAM7 lUZbGF wrfDUiB FKIZ4XdewuYkEusuti2Fq8P0ljg/wwB4F2Z97UYkmq5APfos7FCqF6wBqE au8/kI1AR35EvYmbbGNtXIZbCvQId6TFPH2YTQ096Wl7uD2la79TmUascBxP97G7CQf1L4V7ci6kvi1E/gASTZi 4fIYw5OxPPwyavdFFKjXlXrFr7rZb9nit2Vcg6X71nd9STgFW9ujAEZD6vH/lmKCx8R3TE1QZUeTdIBSAdJghZlEW4RQL0h5A UmInj kB8s62 R//TMDpEZhsJd8h7/7HrGA xIiPwPGof1rs4/R1J7ZegWQ2eya4oTLzhxsw9bTWM52HXZRZg/UNZoXx kLScvKqqBqNBvZxmCJ6Ss3VX04PJFDpm hBGtS/GVPPmzZ2lJS2qPGO5wt4qthDwWPXF4t3h0IgVnuJFJPsel1cfszP2C1Qv8cEYbo186SWF9TVUQlM2k05O iiQwSGaEdEemLQsTCr2QD0G0e6eQCRXMbyvRtw0NagPfqpwTQ5go73DNmbECd3fRa9ZIh/5c Pc7nS5AHSwZ2k lK8kQ6rDaq4WsmXkWT2w==";
	    //组2 键值取得模板
	    //String strVerify = "TAMAACAUNwAEAWQCAAAAQDcABAHfBwMQDykTAAAAAAA3AAQBAAAAAAAAAAAAAAAAAAAAABQDAAACAwAAZsehUDGAKYFi/Vwy84taH/tkMifPmLg+wHuyx8HF+hJTylK2vpkAw/XIZYqxihCbBLmws271oajFGaHViHuwCcWj+y0kM0Sas+jGT+e12PFzxSsmiUaFzh45u4XkEkMaKCfhTFTEF0xC/s84fArPT5kTHSZEKqpV/va+dcKABoUMMabnj7h8f+21fEa0Hq1Ciaf+PTgN51rQtSWxNnYjHHI9+XXxkWgMHf26NLTQwa1XKEcf8v41RUKec49oglCjiAJIXB8ove+BHF7yStuCRGUgzVbDX4Pft6y+6fLVtgzQRLQIBLFXPPn+n2Zp4u8gBHSYlw+0qiLhjkD2SAh/rmce/r1zFrNZXsFU89hSfF8LpZdFndlgrN469YgFcKBIqlCJEFfAC8tcg6f/T6qztM1xlU4/MG54ipeCcES6QVgsuG7L2ld/DzjyWwyGi3WdkghQNxP2OW0kABYj2uek1v+lPVn5ZdIiN0XX4Mbj9XOB18MKw4oV5+sTamVC6JCs/xZ6SIbjhsDGA5xBrq5lzLgHSzoFWq7NNknQua/EupjA1FzOT3wPHmZwcYHUSt5ZiBk2NwYATr5NNRCQ9djXW8ASCwZNcmspz0NDVdkgajyiGze8lZ6Ug8AlYluO6hou2PFbKHjkYYDH04T4dA8o9XleBtnXzNUvlwDCWo4sg6N7fzXKDn55afaSsyUmxKmVydTaxHNf5fc5MGd+CCkhwB92J2H+EslT26Kdah7u4N7T5wg2gPVS5SJbh/nf7S+92XsVNg5e3kXEZxKXJchXE6RPeptdroTxlbeA4ciPPmgcohZu8GQfNer2VkCQQmIPoMgCb0zd4C8e7pEUOnjTJy3KDpf4Rl85S2sayk0m8XuQuIMzHnpYo2A90BQBbPr8IBC8xSPPqLUSiRN15xjJ0yNYTOst3rr/jN7CG1GZzw+SnkDUAFN+0dCFV7MS0iUXrAk98HSUuxPcYjGYfC9P8zyIFsK1I3CngwekicTercYK5aYe8SDgTp/HNPVeE5aYbLrDfyWf/OUCtRIH9edw4A==";
	    //登陆用模板（Base64转码后），通过客户端传入 登陆用模板受键值控制
	    //Linux 采集 组1
	    //String strRegist = "AQQVAwADAQAAAAAAAgC3eG+rnk3j37c8alOq8w5biC58LmqJrUJtjXP+UwNJF93N7QY5wgM3vpMmngVoNNOpmiMCCpcZ3MypxfLAVO7mTxyjPWzBPATocyeWOXX6YTFv24fnPnOKYZTZjNqTJvHHMXGXSGMt24qAkgZf19ko2eEKcN1jtCMWMm0DVS32XOdMsZMyOhul8gBspqxeShSK35vK1wyiL8fbksW2qnsLLP58abKhRtGJiDI7LuOIPyWqeQUHhKVTibF5NeTDkWt6uYBEVblvdVZ03ABdenA2GWuwlsyYxr/4AGRCdrd08n4uNDsuh1Bqr/p4ue+QXtFwWvVT5YQs/nkQ8SDRMdm73/JKv9iRWB/mR7+hVl9eu4/oSDIdISM+RgCT0JJdN2P40r3Z5OaeVIISI3NQ+h65tRJBP8eRdN1wxFRmyc+SHubuuClMHzODDMPb8rQw/B+4kXQW6QUqu936PhOACc7tnBsU2T2hIC6H8vEQVgbBzmsFxrjz+j7ywGeTib0gPbhH7EcgcqYxwWGH9FLZZghmvYKXA6VcvHGvLcRSJ5ybBpi3VPkpYDMtnWnSRJTwAus7nf4CLUJdj9adbF5yT9smfGIX+Lg+HiVuGzSOg9kDvWROe5+oCQv/KmBktIUpF6aYSpa6/47SbpC7csAmxM3ivLRrl8pNnudbqbS9XnTOKCGF";
	    //windows 采集 组2
	    //String strRegist = "AQQVsQADAQAAAAAAAgCJqJtGyKqKM8z1xlx51AqVYLi0drspl7sjqggh2XZ4PGcjY4gjLbZAmdfpwNNBtpZbMrVtkWSODY8qOItVT+Zj4aeIfCnhU7JMjL7eWUSLoySh5+AebDWlLXh/0eXNyhzMQfFcIMDhvBezSMqqVxXXWJSuagog8vC4Qjeye35hPHWrs4eTMXOXb01LTMUQ7pi5ucO0/aYPo2mx+ztDT3NXHRGEBZ3AbMqO3vwNoXsRJ3e4W2PngmgkPTgihX6N2Y3HOT9U97vkoL7vYW88lZVFM8yWVMnHbKX3RD+svXMMobzJ3vcVjd7ZXCsMFVHBvLbSM5QyJui32GT8eLP9ScjWQunmKLCYdPkSxFb2oyS0vY4sfRCTgri6HoDXvFR2Ic8VmH+TV3FUfWM+v5EfA1pH3EnGxIWIvJYEo37TWU2l3mASVEJ8R26gPbhEAGGIolEkRp4+pACf8m5kh8Hh0TBKla/Eg9vRJne7ACA4keHjZ22LZ6Phh1hxtCy5YyEyxOvZ/jIo0/p9rxZ+5H2e97ABXOa4u7IhYq0opmnwDe96G7ZBdB5htGboD3kiYh3dPqLpsGIUDoQ9430N/d9zW70cxVVOKvbh5ZYDRzkSmlfcpmKVJFE7h/vyrFMW8kJBGLUaGPiy4FA/Cf8yKQsRiP/3sMI/xejSFQP1DuLSV93TZnVW";
	    //android 采集 组2
	    String strRegist = "AQQVygADAQAAAAAAAgAjlT1W5h+lY1dCDr7lrs6nrixOA2sAq9oL4C2l01I1ILW/0LYWPfAAy04ebCeMq3fnNPICnUrhVgu0CnHhMA+EYgSc2KtMqNOa9B+igki6c5+8pRzDgiuqadXOdr1BzQaabjBIRcrBu9YR8zcFFUYGtWwGR+YaKAY+WtxH1bp+K37iHi7FGOw6ppcCWUxQH8n4+4glLlr/wbvCGl0gNVoTFZUj2mbsmx7w5k/gDJzTmbwCNXbRlGmg5BE87tvmWFt7xQQ7Yt7t1KwH9rk2QCA2E843wsumggpwffNO2AsxgDwo4XGDVfAJ2mIjOxnHNuhyFlnaU1MfHJRUxbKW/gtlt4Cyb4JDP8XCSuhOyMGqP55BgNHkNkIWzxvkX8sxRSQxXbzlF8x0+YoeMnGAZIQAQwPVmeOUFK5H5qP7l3eiqkLlAs7P53xLqGyRBGLmWWa3+acjVl+eZQ6TEmHiq/nsipusZ+n9D6fptzE44tjkobOPvEK5bHiBLi+p/xQyyinq9b12lLQxgDcH+kOEJjYQD3+dSt0Ppz6gmmUCbSptEplKgW2H0TfZ+jF354TmQFkMemHDLSNy9G0dATTaSq/b9BYTt5F0jtOQj0LpOr+bBS3uzd8QuVKiGQQj9yyFDgR3iuTGLx4UtWK2OYjNZM06pEb9dXhP1eUKK5E+mCBYeDHx";
	    
	    //1:N认证比对模板组（Base64转码后），通过客户端传入   
	    String[]  strRegistN = new String[3];
	    strRegistN[0] = "AQQYOAADAQAAAAAAAgDkpmnx0X+ELA8EM8tLBed0Nw+i2jH5gAxDyiC4v88PV2YAPfmzAX2t93yi0HZIi3uf+Pm11G03zKXF4FTOyEx/PL+Sy4rhZ+VZ3RKtN3NWhL0I0WqviCZwEVNoUCanMIaeutB5MM5H71BqzcKbP0JifCV/9h/HBd0opJQhzW8ElpT6+z07AvkirzD2BdP3pN7AOf6jdsSa1Hi6y709JH2Y8/VFxvTQqca9mfLpNpWaUm1ThIjHeY0YcXKawB2Hqv23h4jw7SeX5KY1R+kuzlPsg028vdFIQ2WMmvvMf6bM4VE1MEfj0yMTyNzfiV6HI0S9cwgjjyidFs8KMtQwIXyEuADAr/lrZjOHO2cmprNPtgunXZD20CLK/4eeUldYXyLIOItHCpzJI5jYJed5YhpsuQM6ltvPeP6UJ550XdQgtUxFVXxP+xSaL38uRDSRqsWSi3QEI8z1ee40+CXnjK9UVKXVQHYS1AOnv7fy89QbDHYtIdT6yGe2j8d17yDFi011U58IDGVPctycAeNCdMpSItai6Fg2YO8GSoI++YoNw7aqtlRbi/9/huoL063Ff8nV4UOqCopmFjcXyMS/iqJNiyFWz3mYrFnQEfTsuXCNbfwqU/MVr5UfsAqbCcVGnBSJXkttbOwMnREh3QMgKu8pL9qpEMji166T1BIftaFZWZf9";
	    strRegistN[1] = "AQQYOAADAQAAAAAAAgCfwTElhkT8SdhRu2EykEd7p4pK/Ay0BL9hU1siYST5ywW1f81S5Db1I3A6x9OfFeLVEwR+uCqvNYTpEgGIqJHXmmU6jklw8laEMEV5SbhpqwCa5ZIL+ItOdWhynplN5HiFPWB9AD+IEzhHqfJA+6G33ZiXUxBd2GDjPrUka3qj48GLzJv/pQLE01vPZGCaNtAD6bFyFjeKrdWV+ZizNNFOQFQiHN98ENOfCx1x/gZ8SnTpOeJt4alh/vze3l7xK1zrjzG8uobuNGSxVsBIF08P561XIsgbs68/H+5+/uJ75uXTD9IYpsTL0+epyXxS2jahDhYrXB14xQ8635JCVukurIh8IShPzoIZkdc4ZeYxs6TDba2knH8lwg/0YgO5TZpA3g2pLZ2mgDFEa0/HdeOi/dJ5JvvaigNsJ1n5NmWPBFHpIjnDNlCV6sQK2lALqXOFntfqvU48EMENT8/nYV5yk6jiyolzWC9nTN06/zrVI8mblGg1I1pmURbzH1z/zs2HHlznQDeEzhV3GtugZ+cb3BSWcprwCCaJNqECtb1yg+5q+t9DCjs8DZ8gPeXQ50t/uON54tm5HL5PKT4O/3GqHOZ85BzK9ZdByJbNU2RlYmUuawJRX8GiwgJXBbWFTDalEV0r/WX75MhEuOVLJEbI7j2wSIHARzm8nnJTNESfKJOx";
		strRegistN[2] = "AQQYOAADAQAAAAAAAgAT/S9l5MyBqVsQ8dn5rCtSAkpF7DqT2i/XsCG5/J2VaxFyT6CXvuEbCr+Udo+FL646VuizWeTvrSCyF7cFIOiK8P6YhPp9FEDvJuX8+JadwGY1JG42lydv/gNYcL54/nzqo2Les5IrkYCxVu6XgARQ3lmG0Rk4Um30laehJNCfckSHZQstLPysOLk45FfXIAY+1KE18WtT3ksLvfDxF+zTr3hS2xh/NTYe7rmH0CNJRdmTjOP0+73URGoB+YX2TWGpUnZYqGw8UK2hBxwhMhMzSOaZ9ClaB4Ysv4bcuaJzDbIfGZbC60TWeKtK5TXJ8Z/uhSg9c6Wfee6+l9GTrQvIIRYK7BQxWC7imS5tj90KZLso720l3l5ihuaVIpRGyVz6uelVMlRnQg3SOzZKXDqKELzF8k0GyFU5fvt+Ncaq3rk62g559IrEvSBuTq3t+tUue9NyCjmrxVNrsgE4tuthwLL6g/XtHFX13pjxE8u5LL15LHrdWhPEGPBtk+hGGuAasbxt+Qygrda8uuyMIlVi1lsKLk+cEOTaMsU0YRbqi1W0Xvyw6bW195Igtu4mcX+o1IH3VHFwuI7HPBPfFowA/OwC9l8kSt6kx5b8u5WzGRnKNZQXqKJVdFJRVdcl1GWxrUunsbQwOTcwE7XXXMehw670gE1ZJs+Zc9gh7E+UkX9b";
       
       //执行1:1认证
//	    String rtn ="qqq";
//	    for(int i =0;i < 1000;i++)
//	    {
	    //String rtn = sample.InitandVerify(strKey, strRegist, strVerify,level);
	    	//System.out.println(i);
	    //}
	   
	    //输出返回值
       //System.out.println(rtn);
       
       //执行1:N认证

       String[] Idenre = new String[2];

		System.out.println(strRegistN[0].length());
		System.out.println(strRegistN[1].length());
		System.out.println(strRegistN[2].length());

      // Idenre = sample.InitandIdentify(strKey, strRegistN, strVerify,level);
       //输出返回值



       System.out.printf("%s  %s", Idenre[0], Idenre[1]);
       
    }          

}
