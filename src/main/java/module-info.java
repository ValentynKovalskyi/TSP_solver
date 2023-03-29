module com.company.tsp_solver {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens com.company.tsp_solver to javafx.fxml;
    exports com.company.tsp_solver;
    exports com.company.tsp_solver.methods;
    opens com.company.tsp_solver.methods to javafx.fxml;
    exports com.company.tsp_solver.utils;
    opens com.company.tsp_solver.utils to javafx.fxml;
    exports com.company.tsp_solver.point;
    opens com.company.tsp_solver.point to javafx.fxml;
    exports com.company.tsp_solver.controllerview;
    opens com.company.tsp_solver.controllerview to javafx.fxml;
}