package io.horizon.ctp.service.monitor;

import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class SystemInfoPrint {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		SystemInfo si = new SystemInfo();
		System.out.println(si);
		HardwareAbstractionLayer hal = si.getHardware();
		System.out.println(hal);
		OperatingSystem os = si.getOperatingSystem();
		System.out.println(os);
		PlatformEnum currentPlatform = SystemInfo.getCurrentPlatform();
		System.out.println(currentPlatform);
		CentralProcessor cpu = hal.getProcessor();
		System.out.println(cpu);
		
	}

}
