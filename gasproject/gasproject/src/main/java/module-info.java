module ru.qurati.gasproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;
    requires javafx.swing;

    opens ru.qurati.gasproject to javafx.fxml;
    opens ru.qurati.gasproject.model to org.hibernate.orm.core;
    exports ru.qurati.gasproject;
    exports ru.qurati.gasproject.controller.compressorstation;
    opens ru.qurati.gasproject.controller.compressorstation to javafx.fxml;
    exports ru.qurati.gasproject.controller.pipeclass;
    opens ru.qurati.gasproject.controller.pipeclass to javafx.fxml;
    exports ru.qurati.gasproject.controller.pipesegment;
    opens ru.qurati.gasproject.controller.pipesegment to javafx.fxml;
    exports ru.qurati.gasproject.controller.pressuremeasurement;
    opens ru.qurati.gasproject.controller.pressuremeasurement to javafx.fxml;
}