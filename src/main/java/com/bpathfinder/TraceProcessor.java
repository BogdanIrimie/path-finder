package com.bpathfinder;

import com.bpathfinder.csvmanipulation.TracePointsToCsv;
import com.bpathfinder.dto.TracePoint;
import com.bpathfinder.dto.TrackingRecord;
import com.bpathfinder.extractors.ExtractTrackingDevices;
import com.bpathfinder.utils.Plotter;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Process traces from tracking devices.
 */
public class TraceProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TraceProcessor.class);

    public void processTraces(List<TrackingRecord> trackingRecords) {
        flattenTime(trackingRecords);
    }

    /**
     * Consider events happening in 1 second interval as happening at the same time.
     *
     * @param trackingRecords
     */
    public void flattenTime(List<TrackingRecord> trackingRecords) {
        List<TracePoint> pathTraces = new ArrayList<TracePoint>();
        List<TrackingRecord> flatTime = new ArrayList<TrackingRecord>();

        // The idea is to consider event that happen in 1 second interval as happening at the same time
        trackingRecords.stream().forEach( trackingRecord -> {
            if (flatTime.size() >= 1) {
                if (flatTime.get(0).getTime() + 1 > trackingRecord.getTime()) {
                    flatTime.add(trackingRecord);
                }
                else {
                    if (flatTime.stream().distinct().count() >= 2) {
                        // TracePoint tp = computeTrackingPointByAverage(flatTime);
                        TracePoint tp = computeTracePointByTrilateration(flatTime);
                        pathTraces.add(tp);
                    }


                    while (flatTime.size() > 0 && flatTime.get(0).getTime() + 1 < trackingRecord.getTime()) {
                        flatTime.remove(0);
                    }
                    flatTime.add(trackingRecord);
                }
            }
            else {
                flatTime.add(trackingRecord);
            }
        });

        //pathTraces.stream().forEach(System.out::println);

        TracePointsToCsv tracePointsToCsv = new TracePointsToCsv();
        tracePointsToCsv.writePojoToCsv(pathTraces);

        // plot results
        Plotter plotter = new Plotter();
        plotter.plotTrackingDevices(new ExtractTrackingDevices().extractDevices(trackingRecords));
        plotter.plotPath(pathTraces);
    }

    /**
     * Compute TracePoint by using average method.
     *
     * @param trackingRecordsAtSameTime is a list of tracking records that we can consider as happening at the same time.
     * @return TracePoint obtain by averaging method.
     */
    private TracePoint computeTrackingPointByAverage(List<TrackingRecord> trackingRecordsAtSameTime) {
        double x = 0, y = 0, time = 0;
        int counter = 0;

        // Only consider distinct tracking devices in order to not distort average results.
        Iterator<TrackingRecord> distinctPoints = trackingRecordsAtSameTime.stream().distinct().collect(Collectors.toList()).iterator();
        while (distinctPoints.hasNext()) {
            TrackingRecord tr = distinctPoints.next();
            x += tr.getxAxis();
            y += tr.getyAxis();
            time += tr.getTime();
            counter++;
        };

        return new TracePoint(time/counter, x/counter, y/counter);
    }

    /**
     * Compute TracePoint by using trilateration method.
     *
     * @param trackingRecordsAtSameTime is a list of tracking records that we can consider as happening at the same time.
     * @return TracePoint obtain by trilateration.
     */
    private TracePoint computeTracePointByTrilateration(List<TrackingRecord> trackingRecordsAtSameTime) {
        double[][] positions = new double[trackingRecordsAtSameTime.size()][2];
        double[] distances = new double[trackingRecordsAtSameTime.size()];
        int recordCounter = 0;
        double time = 0;

        double[] radiusDistances = computeCircleRadius(trackingRecordsAtSameTime);

        Iterator<TrackingRecord> it = trackingRecordsAtSameTime.iterator();
        while (it.hasNext()) {
            TrackingRecord tr = it.next();
            positions[recordCounter][0] = tr.getxAxis();
            positions[recordCounter][1] = tr.getyAxis();
            distances[recordCounter] = 100 + tr.getRssi();
            // distances[recordCounter] = tr.getRssi();
            // distances[recordCounter] = radiusDistances[recordCounter++];
            time += tr.getTime();
        }

        double[] centroid = new double[] {-1, -1};
        try {
            NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
            LeastSquaresOptimizer.Optimum optimum = solver.solve();
            centroid = optimum.getPoint().toArray();
        }
        catch(TooManyEvaluationsException e) {
            //ignore exception
            //logger.error(e.getMessage(), e);
        }

        return new TracePoint(time/recordCounter, centroid[0], centroid[1]);
    }

    /**
     * Compute the radius for circles that intersect at the same time.
     *
     * @param trackingRecordsAtSameTime tracing records that are supposed to happen at the same time.
     * @return radius values for circles that should intersect at the same time.
     */
    public double[] computeCircleRadius(List<TrackingRecord> trackingRecordsAtSameTime) {
        int trn = trackingRecordsAtSameTime.size();
        int radius = 1;
        double[] radiusDistance = new double[trn];
        double[][] centers = new double[trn][2];

        // obtain all centers
        Iterator<TrackingRecord> it = trackingRecordsAtSameTime.iterator();

        int i = 0;
        while (it.hasNext()) {
            TrackingRecord tr = it.next();
            centers[i][0] = tr.getxAxis();
            centers[i++][1] = tr.getxAxis();
        }

        boolean allCircleIntersect = false;
        while (allCircleIntersect == false) {

            i = 0;
            it = trackingRecordsAtSameTime.iterator();
            while (it.hasNext()) {
                TrackingRecord tr = it.next();
                radiusDistance[i++] = (100 + tr.getRssi()) * radius / 100.0;
            }

            allCircleIntersect = true;
            for (int j = 0; j < trn - 1; j++) {
                for (int k = j + 1; k < trn; k++) {
                    float dist = (float) Math.sqrt(Math.pow(centers[j][0] - centers[k][0], 2) + Math.pow(centers[j][1] - centers[k][1], 2));

                    if (dist > radiusDistance[j] + radiusDistance[k]) {
                        // circles DO NOT intersect
                        allCircleIntersect = false;
                    }
                }
            }
            radius++;
        }

        // we have found the radius for which all circles intersect!
        return radiusDistance;
    }

}
