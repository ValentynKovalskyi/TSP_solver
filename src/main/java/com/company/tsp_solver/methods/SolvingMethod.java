package com.company.tsp_solver.methods;

import com.company.tsp_solver.utils.TimeDistance;

public interface SolvingMethod {
    String getName();
    TimeDistance execute();
}
