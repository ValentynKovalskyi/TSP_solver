package com.company.tsp_solver.methods;

import java.util.ArrayList;
import java.util.List;

public interface SolvingMethod {
    String getName();
    double apply();
}
