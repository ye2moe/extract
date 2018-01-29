package com.ye2moe.io.extract;

import com.ye2moe.io.util.extract.AbstractExtract;
import com.ye2moe.io.util.extract.Extract;
import com.ye2moe.io.util.extract.ZipExtract;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    public void testApp2()
    {
        Extract zip = new ZipExtract();
        zip.extract("/Users/ye2moe/Movies/橘子/2016/04/2016年4月作品合集/4月封面合集.zip", 
        		"/Users/ye2moe/Movies/橘子/2016/04/2016年4月作品合集");
    }
    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
      //AbstractExtract.unZipEntry("/Users/ye2moe/Movies/橘子/2016/04/2016年4月作品合集/4月封面合集.zip", "/Users/ye2moe/Movies/橘子/2016/04/2016年4月作品合集");

    }
}
