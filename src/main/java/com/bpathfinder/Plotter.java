package com.bpathfinder;

import com.bpathfinder.dto.TracePoint;
import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.POI;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;
import javafx.animation.PathTransition;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;

public class Plotter {


    public void plot(java.util.List<TracePoint> pathTraces) {
        MapWindow window = new MapWindow();
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
                        Color.RED));
            }
            //window.addPOI(new POI(new Point(current.getxAxis(), current.getyAxis()), current.getApproximatedTime()+""));
            window.addPOI(new POI(new Point(current.getxAxis(), current.getyAxis()), ""));
            previous = current;

            window.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
