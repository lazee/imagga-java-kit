/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
