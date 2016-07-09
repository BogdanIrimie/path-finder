package com.bpathfinder.extractors;

import com.bpathfinder.dto.TrackingDevice;
import com.bpathfinder.dto.TrackingRecord;

import java.util.List;
import java.util.stream.Collectors;

public class ExtractTrackingDevices {

    /**
     * Extract list of tracking devices.
     *
     * @param trackingRecords tracking devices traces.
     * @return list of tracking devices.
     */
    public List<TrackingDevice> extractDevices(List<TrackingRecord> trackingRecords) {
        return trackingRecords.stream().distinct()
                .map(tr -> new TrackingDevice(tr.getxAxis(), tr.getyAxis()))
                .collect(Collectors.toList());}
}
