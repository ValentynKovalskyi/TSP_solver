package com.company.tsp_solver.utils;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.methods.SolvingMethod;
import javafx.util.StringConverter;

public class MethodNameConverter extends StringConverter<SolvingMethod> {

    @Override
    public String toString(SolvingMethod solvingMethod) {
        return (solvingMethod == null)? "Method" :solvingMethod.getName();
    }

    @Override
    public SolvingMethod fromString(String s) {
        return Model.MODEL.getMethods().stream()
                .filter(solvingMethod -> solvingMethod.getName().equals(s)).findFirst().get();
    }
}
