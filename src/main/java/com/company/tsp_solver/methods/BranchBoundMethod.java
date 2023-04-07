package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utils.*;

import java.util.*;

public class BranchBoundMethod implements SolvingMethod{

    private WayDrawer drawer = new WayDrawer();

    private DistanceCalculator calculator = new DistanceCalculator();

    private DistanceMatrixGenerator matrixGenerator = new DistanceMatrixGenerator();
    @Override
    public String getName() {
        return "Branches and Bounds Method";
    }

    @Override
    public TimeDistance execute() {
        long startTime = System.currentTimeMillis();
        N = Model.MODEL.points.size();
        final_path = new int[N + 1];
        visited = new boolean[N];
        final_res = Integer.MAX_VALUE;
        TSP(matrixGenerator.generateMatrix(Model.MODEL.points));
        for (int i = 0; i <= N; i++)
        {
            System.out.printf("%d ", final_path[i]);
        }
        List<Point> result = getWay(final_path);
        drawer.drawWay(result);
        return new TimeDistance(System.currentTimeMillis() - startTime,final_res);
    }
    private int N;
    private int[] final_path;
    private boolean[] visited;
    private double final_res;
    private void copyToFinal(int[] curr_path)
    {
        for (int i = 0; i < N; i++)
            final_path[i] = curr_path[i];
        final_path[N] = curr_path[0];
    }
    private double firstMin(double[][] adj, int i)
    {
        double min = Double.MAX_VALUE;
        for (int k = 0; k < N; ++k)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }
    private double secondMin(double[][] adj, int i)
    {
        double first = Double.MAX_VALUE, second = Double.MAX_VALUE;
        for (int j = 0; j < N; ++j)
        {
            if (i == j)
                continue;

            if (adj[i][j] <= first)
            {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }
    private void TSPRec(double[][] adj, int curr_bound, int curr_weight,
                        int level, int[] curr_path)
    {
        if (level == N)
        {
            if (adj[curr_path[level - 1]][curr_path[0]] != 0)
            {
                double curr_res = curr_weight +
                        adj[curr_path[level-1]][curr_path[0]];

                if (curr_res < final_res)
                {
                    copyToFinal(curr_path);
                    final_res = curr_res;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++)
        {
            if (adj[curr_path[level-1]][i] != 0 &&
                    !visited[i])
            {
                int temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];

                if (level==1)
                    curr_bound -= ((firstMin(adj, curr_path[level - 1]) +
                            firstMin(adj, i))/2);
                else
                    curr_bound -= ((secondMin(adj, curr_path[level - 1]) +
                            firstMin(adj, i))/2);

                if (curr_bound + curr_weight < final_res)
                {
                    curr_path[level] = i;
                    visited[i] = true;

                    TSPRec(adj, curr_bound, curr_weight, level + 1,
                            curr_path);
                }

                curr_weight -= adj[curr_path[level-1]][i];
                curr_bound = temp;

                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }
    private void TSP(double[][] adj)
    {
        int[] curr_path = new int[N + 1];
        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);

        for (int i = 0; i < N; i++)
            curr_bound += (firstMin(adj, i) +
                    secondMin(adj, i));

        curr_bound = (curr_bound==1)? curr_bound/2 + 1 :
                curr_bound/2;

        visited[0] = true;
        curr_path[0] = 0;

        TSPRec(adj, curr_bound, 0, 1, curr_path);
    }

    private List<Point> getWay(int[] way) {
        List<Point> result = new LinkedList<>();
        for (int index: way) {
            result.add(Model.MODEL.points.get(index));
        }
        return result;
    }
   /* private TimeDistance launch() {
        long startTime = System.currentTimeMillis();
        startPoint = unvisited.get(Utils.random.nextInt(unvisited.size()));
        List<Point> resultWay = new LinkedList<>(List.of(startPoint));
        unvisited.remove(startPoint);
        getMinWay(startPoint,resultWay, unvisited);
        drawer.drawWay(resultWay);
        double resultWayLength = calculator.calculateDistance(resultWay);
        return new TimeDistance(System.currentTimeMillis() - startTime, resultWayLength);
    }

    private List<Point> getMinWay(Point startPoint,List<Point> result, List<Point> unvisited) {
        if(unvisited.size() == 1) {
            result.add(unvisited.get(0));
            return result;
        }
        Map<Point,Double> lowerBounds = new HashMap<>();

        unvisited.forEach(point -> {
            List<Point> unvisitedPoints = new LinkedList<>(List.copyOf(unvisited));
            unvisitedPoints.remove(point);
           lowerBounds.put(point,lowerBound(point, unvisitedPoints,calculator.calculateLineLength(result)));
        });
        Point minLBPoint = lowerBounds.entrySet().stream().min((e1,e2) -> (int) (e1.getValue() - e2.getValue())).get().getKey();
        result.add(minLBPoint);
        unvisited.remove(minLBPoint);
        return getMinWay(minLBPoint,result,unvisited);
    }

    private double lowerBound(Point startPoint, List<Point> points, double curLength) {
        double result = curLength;
        List<Point> unvisitedPoints = new LinkedList<>(points);
        double minway = curLength;
        Point currentPoint = startPoint;
        do{
            Point nearestPoint = getNearestPoint(currentPoint,unvisitedPoints);
            unvisitedPoints.remove(nearestPoint);
            minway += currentPoint.distance(nearestPoint);
            currentPoint = nearestPoint;
        } while (!unvisitedPoints.isEmpty());

        return minway;
    }

    private Point getNearestPoint(Point start,List<Point> points) {
        return points.stream()
                .min(Comparator.comparingDouble(start::distance))
                .get();
    }*/
}
