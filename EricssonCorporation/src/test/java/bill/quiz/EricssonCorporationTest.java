package bill.quiz;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by bill on 1/4/16.
 */
public class EricssonCorporationTest {
    Logger logger= LoggerFactory.getLogger(EricssonCorporationTest.class);

    EricssonCorporation ericssonCorporation;

    @Before
    public void setUp(){
        ericssonCorporation=new EricssonCorporation();
    }

    @Test(expected=RuntimeException.class)
    public void invalidWorkHourWillBeCaught(){
        ericssonCorporation.calculateTotalPay(new BigDecimal("10.00"),new BigDecimal("60.01"));
    }

    @Test(expected=RuntimeException.class)
    public void minusWorkHourWillBeCaught(){
        ericssonCorporation.calculateTotalPay(new BigDecimal("10.00"),new BigDecimal("-0.01"));
    }

    @Test(expected=RuntimeException.class)
    public void invalidBasePayWillBeCaught(){
        ericssonCorporation.calculateTotalPay(new BigDecimal("7.99"),new BigDecimal("40.00"));
    }

    @Test
    public void totoalPayWithoutOverTimeCanBeCaculated(){
        BigDecimal totalPay=ericssonCorporation.calculateTotalPay(new BigDecimal("20.00"), new BigDecimal("40.00"));
        assertTrue(new BigDecimal("800.00").compareTo(totalPay) == 0);

    }

    @Test
    public void totoalPayWithOverTimeCanBeCaculated(){
        BigDecimal totalPay=ericssonCorporation.calculateTotalPay(new BigDecimal("20.00"), new BigDecimal("45.00"));
        logger.debug("Total:{}", totalPay);
        assertTrue(new BigDecimal("950.00").compareTo(totalPay)==0);

    }



}
