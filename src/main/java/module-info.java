module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.codec;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}