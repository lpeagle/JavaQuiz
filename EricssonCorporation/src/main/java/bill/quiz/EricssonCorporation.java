package bill.quiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * This class claculates total pay of employees based on base rate and working hours
 * <p>
 * Created by bill on 1/4/16.
 */
public class EricssonCorporation {
    Logger logger = LoggerFactory.getLogger(EricssonCorporation.class);
    BigDecimal overTimeLimit, maximumHourLimit, minimumBasePay;

    public EricssonCorporation(BigDecimal overTimeLimit, BigDecimal maximumHourLimit,
                               BigDecimal minimumBasePay) {
        this.overTimeLimit = overTimeLimit;
        this.maximumHourLimit = maximumHourLimit;
        this.minimumBasePay = minimumBasePay;

    }

    public EricssonCorporation() {
        this(new BigDecimal("40.00"), new BigDecimal("60.00"), new BigDecimal("8.00"));
    }

    public BigDecimal calculateTotalPay(BigDecimal basePay, BigDecimal hours) {
        BigDecimal totalPay = new BigDecimal("0.00");

        if ((hours.compareTo(maximumHourLimit) > 0) || (hours.compareTo(new BigDecimal("0.0")) < 0)) //error, invalid hours
            throw new RuntimeException("Invalid working hours:" + hours);
        else if (basePay.compareTo(minimumBasePay) < 0)
            throw new RuntimeException("Invalid base rate:" + basePay);
        else if (hours.compareTo(overTimeLimit) > 0)
            totalPay = basePay.multiply(overTimeLimit).add(
                    hours.subtract(overTimeLimit).multiply(new BigDecimal("1.5")).multiply(basePay));
        else
            totalPay = basePay.multiply(hours);
        return totalPay.setScale(2, BigDecimal.ROUND_HALF_UP);

    }

    public static void main(String[] args) throws Exception {
        EricssonCorporation ericssonCorporation = new EricssonCorporation();
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/bill/tmp/test/ericsson_input1.txt"));  //System.in
             BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/bill/tmp/test/ericsson_out.txt")))) {
        /*
        Example input(no leading spaces):

        Employee    Base Pay  Hours Worked
        Employee 1  $7.50     35
        Employee 2  $8.20     47
        Employee 3  $10.00    73
         */
            reader.readLine();  //skip first line
            String line = null;
            writer.write("Employee    Total Pay\n");
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s+");
                if (fields.length < 4) {
                    writer.write("Input line error\n");
                    continue;
                }
                writer.write(fields[0] + " " + fields[1]);
                try {
                    BigDecimal totalPay = ericssonCorporation.calculateTotalPay(new BigDecimal(fields[2].replace("$","")),
                            new BigDecimal(fields[3]));
                    writer.write(" " + totalPay + "\n");
                }catch (Exception e){
                    writer.write(" "+e.getMessage()+"\n");
                }
            }
        }

    }
}
