package sample;
/**
 * This application allows the user to convert from imperial units to metric units
 * Conversion factors are stored in a LinkedHashMap
 * There is error checking to ensure that suitable values are entered.
 */

// imports

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Main extends Application {

// class definition


    // Imperial
    private Label imperial_label = new Label(" Imperial");;
    private TextField imperial_textField = new TextField();
    private ToggleGroup imperial_toggleGroup = new ToggleGroup();;
    private RadioButton imperial_radioButton_1 = new RadioButton("in");
    private RadioButton imperial_radioButton_2 = new RadioButton("foot");;
    private RadioButton imperial_radioButton_3 = new RadioButton("yard");;

    // Metric
    private Label metric_label = new Label(" Metric");;
    private TextField metric_textField = new TextField() ;
    private ToggleGroup metric_toggleGroup = new ToggleGroup();;
    private RadioButton metric_radioButton_1 = new RadioButton("mm");;
    private RadioButton metric_radioButton_2 = new RadioButton("cm");;
    private RadioButton metric_radioButton_3 = new RadioButton("m");;

    //Accuracy
    private Label accuracy_label = new Label("Accuracy");
    private Slider accuracy_slider = new Slider(0, 10, 0);
    private TextField accuracy_textField  = new TextField();
    private int accuracy;

    //Calculate and Erase
    private Button convert_button = new Button("Convert");
    private Button reset_button = new Button("Reset");;

    // Layout
    private GridPane gp = new GridPane();


    // Conversion Type
    private Label conversion_type_label  = new Label(" Conversion Type ");;
    private ComboBox<String> conversion_type_combobox = new ComboBox();



    // init method
    public void init() {

        convert_button.setStyle("-fx-background-color: #F0591E; -fx-text-fill: white;");
        accuracy_textField.setFont(Font.font ("Georgia"));
        imperial_textField.setFont(Font.font ("Georgia"));
        metric_textField.setFont(Font.font ("Georgia"));
        convert_button.setFont(Font.font ("Georgia"));
        reset_button.setFont(Font.font ("Georgia"));
        metric_radioButton_1.setFont(Font.font ("Georgia"));
        metric_radioButton_2.setFont(Font.font ("Georgia"));
        metric_radioButton_3.setFont(Font.font ("Georgia"));
        imperial_radioButton_3.setFont(Font.font ("Georgia"));
        imperial_radioButton_2.setFont(Font.font ("Georgia"));
        imperial_radioButton_1.setFont(Font.font ("Georgia"));
        imperial_label.setFont(Font.font ("Georgia"));
        metric_label.setFont(Font.font ("Georgia"));
        accuracy_label.setFont(Font.font ("Georgia"));
        conversion_type_combobox.setStyle("-fx-font:  13px \"Georgia\";");
        conversion_type_label.setFont(Font.font ("Georgia"));




        // Conversion Type - ComboBox
        conversion_type_combobox.getItems().addAll("Length", "Mass");
        conversion_type_combobox.setValue("Length");

        // Prevent the Accuracy and Metric TextFields being editable
        metric_textField.setEditable(false);
        accuracy_textField.setEditable(false);

        accuracy_slider.setStyle("-fx-padding: 20 0 0 0;");
        accuracy_slider.setShowTickMarks(true);
        accuracy_slider.setShowTickLabels(true);
        accuracy_slider.setMajorTickUnit(1f);
        accuracy_slider.setBlockIncrement(1f);
        accuracy_slider.setMinorTickCount(1);

        // Set radio buttons to toggle groups
        imperial_radioButton_1.setToggleGroup(imperial_toggleGroup);
        imperial_radioButton_2.setToggleGroup(imperial_toggleGroup);
        imperial_radioButton_3.setToggleGroup(imperial_toggleGroup);
        HBox box = new HBox(20, imperial_radioButton_3, imperial_radioButton_2, imperial_radioButton_1);
        gp.add(box, 1, 3);

        metric_radioButton_1.setToggleGroup(metric_toggleGroup);
        metric_radioButton_2.setToggleGroup(metric_toggleGroup);
        metric_radioButton_3.setToggleGroup(metric_toggleGroup);
        HBox box2 = new HBox(20, metric_radioButton_1, metric_radioButton_2, metric_radioButton_3);


        gp.setStyle("-fx-background-color: cornsilk; -fx-padding: 05;");
        gp.add(box2, 3, 3);

        // How many rows and columns do you want - work this out on paper first
        // My version has 5 rows and 8 columns you can look at the JavaFX API to
        // see how to get controls to span more than one column



        /*---------------------------------------------------------------------------*/
        // Layout controls as per the diagram, feel free to improve the UI.
        gp.add(metric_label, 2, 1);
        gp.add(metric_textField, 3, 1);
        gp.add(accuracy_label, 0, 11);
        accuracy_label.setStyle("-fx-padding: 0 0 0 5;");
        gp.add(accuracy_slider, 1, 11);
        gp.add(convert_button, 0, 15);
        gp.add(reset_button, 1, 15);
        gp.add(accuracy_textField, 3, 11);
        gp.add(conversion_type_label, 0, 0);
        gp.add(conversion_type_combobox, 1, 0, 1, 1);
        gp.add(imperial_label, 0, 1);
        gp.add(imperial_textField, 1, 1);


        // Method call (not declaration!)  to initialize the controls based on the conversion type.
        initalizeControlValues();

        // Method call to perform conversion
        convert();

        //  Listener for accuracy_slider to set the number of decimal places in the metric_textField
        accuracy_slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {

                accuracy_slider.setValue(Math.round(newValue.doubleValue()));


                // Method call to perform conversion
                convert();

                // Put the newValue in the metric_textField
                accuracy_textField.setText(String.format("%.0f", newValue));

                // Format the value in the metric_textField so that it has the correct number of decimal places
            }
        });

        // Listener for imperial_toggleGroup to perform conversion
        this.imperial_toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {

            convert();
            }
        });

        // Listener for metric_toggleGroup to perform conversion
        this.metric_toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {

                convert();
            }
        });

        // Listener for convert_button to perform conversion
        convert_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                convert();
            }
        });

        // Listener for conversion_type_combobox to initialize control values and perform conversion
        conversion_type_combobox.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                initalizeControlValues();
            }
        });

        // Listener for reset_button to initialize control values and perform conversion
        reset_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                initalizeControlValues();


            }
        });


    }

    // Overridden start method
    public void start(Stage primaryStage) {

        // set a title on the window, set a scene, size, and show the window
        primaryStage.setTitle("Unit Converter");
        primaryStage.setScene(new Scene(gp, 520, 170));
        primaryStage.show();


    }

    // Overridden stop method - add a status message to this
    public void stop() {
    }

    // Entry point to our program
    public static void main(String[] args) {

        launch(args);
    }

    // Method to harvest value from imperial_textField, perform calculation and display the results
    // in the metric_textField

    private void convert() {

        imperial_textField.setStyle("-fx-text-inner-color:black;");


        // try/catch to test to see if the value in the imperial_textField is a number before attempting to convert
        try {
            Double.parseDouble(imperial_textField.getText());

        } catch (Exception e) {
            // Output a suitable error message in the imperial_textField and set the metric_textField to blank
            imperial_textField.setText("NAN");
            imperial_textField.setStyle("-fx-text-inner-color:red;");
        }


        double value_imperial = Double.parseDouble(imperial_textField.getText());



        // Get the select keys from the RadioButtons
        String key_imperial = ((RadioButton) this.imperial_toggleGroup.getSelectedToggle()).getText();
        String key_metric = ((RadioButton) this.metric_toggleGroup.getSelectedToggle()).getText();


        // Use these keys to get the matching values from the LinkedHashMap
        Double converstion_factor_metric = this.conversion_map.get(key_imperial);
        Double converstion_factor_metric2 = this.conversion_map.get(key_metric);

        // Calculate the value_metric
        Double value_metric = value_imperial * converstion_factor_metric*converstion_factor_metric2;
        //Double value_metric2 = value_metric * converstion_factor_metric2;

        // Put this new value_metric in the metric_textField

        int i = (int) accuracy_slider.getValue();

        metric_textField.setText(String.format("%."+i+"f", value_metric));





    }


    // Method to initialize the controls based on the selection of the conversion type
    private void initalizeControlValues() {
        // Make an Object array to store all the keys of the LinkedHashMap
        Object[] keys = this.conversion_map.keySet().toArray();


        // Initialization for the controls if the conversion_type_combobox is set to Length
        if (conversion_type_combobox.getValue() == "Length") {

            imperial_radioButton_1.setText("in");;
            imperial_radioButton_2.setText("foot");;
            imperial_radioButton_3.setText("yard");;

            // Metric
            metric_radioButton_1.setText("mm");;
            metric_radioButton_2.setText("cm");;
            metric_radioButton_3.setText("m");;

            //Initialize length to 1.0
            imperial_textField.setText(String.valueOf(1.0));
            metric_textField.setText("");
            metric_textField.setEditable(false);

            // Set slider scale 0 to 6, set slider value to 4 and ticks to 1 unit intervals
            //accuracy_slider.setMajorTickUnit(4);
            accuracy_slider.setMin(0);
            accuracy_slider.setMax(6);
            accuracy_slider.setValue(4);
            accuracy_slider.setBlockIncrement(1);

            // Initialize the radio buttons

            // Set default selections for the radio buttons
            imperial_radioButton_3.setSelected(true);
            metric_radioButton_1.setSelected(true);



            accuracy_textField.setText(String.valueOf(accuracy_slider.getValue()));


        }
        // Initialization for the controls if the conversion_type_combobox is set to Mass
        else if (conversion_type_combobox.getValue() == "Mass") {

            // Initialize the mass to .5
            imperial_textField.setText(String.valueOf(0.5));
            metric_textField.setText("");
            metric_textField.setEditable(false);


            // Set slider scale 0 to 5, set slider value to 3 and ticks to 1 unit intervals
            accuracy_slider.setMin(0);
            accuracy_slider.setMax(5);
            accuracy_slider.setValue(3);
            accuracy_slider.setBlockIncrement(1);

            // Initialize the radio buttons
            // Imperial

            ;
            imperial_radioButton_1.setText("stone");;
            imperial_radioButton_2.setText("lb");;
            imperial_radioButton_3.setText("oz");;

            // Metric
            metric_radioButton_1.setText("g");;
            metric_radioButton_2.setText("kg");;
            metric_radioButton_3.setText("ton");;

            // Set default selections for the radio buttons
            imperial_radioButton_2.setSelected(true);
            metric_radioButton_2.setSelected(true);
            metric_radioButton_2.setSelected(true);

        }

        // Slider display ticks etc


        // Variable declaration. Although it is not correct to do so you may choose to initalize
        // variable here also to avoid a very large init() method

        // Layout
    }

    // Conversion Map - facilitates the conversion from imperial to metric.
    // This LinkedHashMap initialization is complete

    private Map<String,Double> conversion_map = new LinkedHashMap<String, Double>();
    {
        conversion_map.put("in",0.0254); // key:in value:the factor required to convert inches to metres
        conversion_map.put("foot",0.3048);
        conversion_map.put("yard",0.9144);
        conversion_map.put("mm",1000.0); // key:mm value:the factor required to then convert metres to mm
        conversion_map.put("cm",100.0);
        conversion_map.put("m",1.0);
        conversion_map.put("oz",0.0283);
        conversion_map.put("lb",0.453);
        conversion_map.put("stone",6.35);
        conversion_map.put("g",1000.0);
        conversion_map.put("kg",1.0);
        conversion_map.put("ton",0.001);
    }


}

