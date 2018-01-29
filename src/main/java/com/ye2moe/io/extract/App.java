package com.ye2moe.io.extract;

import com.ye2moe.io.core.ExtractWork;
import com.ye2moe.io.core.Work;

public class App 
{ 
    public static void main( String[] args )
    {  
        Work extract = new ExtractWork("/Users/ye2moe/Movies/橘子/2016/04/2016年4月作品合集").setMaxTaskNum(2); 
        extract.start(); 
    }
}
