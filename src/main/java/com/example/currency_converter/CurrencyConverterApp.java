package com.example.currency_converter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;

public class CurrencyConverterApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        //Create UI Components
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        Label fromLabel = new Label("From Currency:");
        Label toLabel = new Label("To Currency:");
        Button convertButton = new Button("Convert");
        Label resultLabel = new Label();

        // Add components to the gridPane
        gridPane.add(amountLabel, 0, 1);
        gridPane.add(amountField, 1, 1);
        gridPane.add(fromLabel, 0, 2);
        gridPane.add(toLabel, 0, 3);
        gridPane.add(convertButton, 0, 4, 2, 1);
        gridPane.add(resultLabel, 0, 5, 2, 1);

        // Create ComboBoxes for selecting currencies
        ComboBox<String> fromCurrencyComboBox = createCurrencyComboBox();
        ComboBox<String> toCurrencyComboBox = createCurrencyComboBox();

        // Add ComboBoxes to the gridPane
        gridPane.add(fromCurrencyComboBox, 3, 2);
        gridPane.add(toCurrencyComboBox, 3, 3);

        // Set action for the convertButton
        convertButton.setOnAction(e -> {

            double exchangeRate = ExchangeRateService.getExchangeRate(fromCurrencyComboBox.getValue(), toCurrencyComboBox.getValue());

            if (exchangeRate != -1) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    double convertedAmount = amount * exchangeRate;
                    resultLabel.setText("Conversion Result: " + performConversion(convertedAmount));
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter a valid number.");
                }
            } else {
                resultLabel.setText("Error fetching exchange rate. Please try again later.");
            }
        });

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private ComboBox<String> createCurrencyComboBox() {
        // Create a ComboBox with a list of currency codes
        ObservableList<String> currencies = FXCollections.observableArrayList(
                "USD","AUD","CAD","PLN","MXN"
        );

        ComboBox<String> comboBox = new ComboBox<>(currencies);
        comboBox.setValue("EUR"); // Set a default value

        return comboBox;
    }

    // Implement your conversion logic here
    private String performConversion(double convertedAmount) {
        return String.format("%.2f", convertedAmount); //formats amount with 2 decimal places
    }
}
