package com.bpathfinder.utils;

import com.bpathfinder.dto.TracePoint;
import com.bpathfinder.dto.TrackingDevice;
import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.POI;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Draw path.
 */
public class Plotter {

    public static final Logger logger = LoggerFactory.getLogger(Plotter.class);
    private MapWindow window = new MapWindow();

    /**
     * Print computed path path.
     *
     * @param pathTraces list containing computed path.
     */
    public void plotPath(List<TracePoint> pathTraces) {
        // at least 1 segment is necessary because of minigeo library bug.
        window.addSegment(
                new Segment(
                        new Point(0, 0),
                        new Point(100, 100),
                        Color.WHITE));
        window.setVisible(true);

        TracePoint previous = null;
        Iterator<TracePoint> it = pathTraces.iterator();

        while (it.hasNext()) {
            TracePoint current = it.next();
            if (previous != null) {
                window.addSegment(new Segment(
                        new Point(previous.getxAxis(), previous.getyAxis()),
                        new Point(current.getxAxis(), current.getyAxis()),
                        Color.ORANGE));
            }
            //window.addPOI(new POI(new Point(current.getxAxis(), current.getyAxis()), current.getApproximatedTime()+""));
            window.addPOI(new POI(new Point(current.getxAxis(), current.getyAxis()), ""));
            previous = current;

            window.repaint();

            // Add small delay between segment prints for better visualisation experience.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }

        }
    }

    /**
     * Print tracking device location.
     *
     * @param trackingDevices list containing tracking devices.
     */
    public void plotTrackingDevices(List<TrackingDevice> trackingDevices) {
        trackingDevices.stream().forEach(trackingDevice -> {
            window.addPOI(new POI(new Point(
                    trackingDevice.getxAxis(),
                    trackingDevice.getyAxis()),
                    String.format("(%.2f %.2f)", trackingDevice.getxAxis(), trackingDevice.getyAxis())));
        });
        window.setVisible(true);
    }
}
