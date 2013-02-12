package net.jakobnielsen.imagga.crop_slice.bean;

import java.util.Date;
import java.util.Map;

public class ApiUsage {

    private final Date startTime;

    private final Date endTime;

    private final Map<String, Usage> usageMap;

    private final Double totalPayable;

    public ApiUsage(Date startTime, Date endTime, Map<String, Usage> usageList, Double totalPayable) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.usageMap = usageList;
        this.totalPayable = totalPayable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Map<String, Usage> getUsageMap() {
        return usageMap;
    }

    public Double getTotalPayable() {
        return totalPayable;
    }


}
