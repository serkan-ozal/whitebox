package sun.hotspot.whitebox.demo;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import com.sun.management.HotSpotDiagnosticMXBean;

import sun.hotspot.WhiteBox;
import sun.hotspot.WhiteBoxProvider;

public class ChangeImmutableVmFlagViaWhiteBoxDemo {

    public static void main(String[] args) throws IOException {
        MBeanServer mbserver = ManagementFactory.getPlatformMBeanServer();
        HotSpotDiagnosticMXBean mxbean = 
                ManagementFactory.newPlatformMXBeanProxy(
                        mbserver, 
                        "com.sun.management:type=HotSpotDiagnostic", 
                        HotSpotDiagnosticMXBean.class);
        
        System.out.println("[BEFORE] UnlockDiagnosticVMOptions: " + mxbean.getVMOption("UnlockDiagnosticVMOptions"));
        
        WhiteBox wb = WhiteBoxProvider.getWhiteBox();
        wb.setBooleanVMFlag("UnlockDiagnosticVMOptions", true);
        
        System.out.println("[AFTER] UnlockDiagnosticVMOptions: " + mxbean.getVMOption("UnlockDiagnosticVMOptions"));
    }

}
