module com.example.currency_converter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.json;
    requires org.slf4j;


    opens com.example.currency_converter to javafx.fxml;
    exports com.example.currency_converter;
}