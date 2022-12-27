package com.company.tsp_solver.methods;

import com.company.tsp_solver.utilities.TimeDistance;

public interface SolvingMethod {
    String getName();
    TimeDistance apply();
}
