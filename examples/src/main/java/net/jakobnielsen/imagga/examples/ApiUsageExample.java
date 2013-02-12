package net.jakobnielsen.imagga.examples;

import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.client.CropSliceAPIClient;

public class ApiUsageExample {

    public static void main(String[] args) {
        CropSliceAPIClient client = new CropSliceAPIClient(args[0], args[1], args[2]);
        ApiUsage apiUsage = client.apiUsage(0, 0);
        System.out.println("startTime : " + apiUsage.getStartTime().toString());
        System.out.println("endTime : " + apiUsage.getEndTime().toString());
        for (String k : apiUsage.getUsageMap().keySet()) {
            System.out.println(k + " : " + apiUsage.getUsageMap().get(k));
        }
        System.out.println("totalPayable : " + apiUsage.getTotalPayable().toString());
    }
}
