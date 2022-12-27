module com.company.tsp_solver {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens com.company.tsp_solver to javafx.fxml;
    exports com.company.tsp_solver;
    exports com.company.tsp_solver.methods;
    opens com.company.tsp_solver.methods to javafx.fxml;
    exports com.company.tsp_solver.utilities;
    opens com.company.tsp_solver.utilities to javafx.fxml;
    exports com.company.tsp_solver.point;
    opens com.company.tsp_solver.point to javafx.fxml;
}