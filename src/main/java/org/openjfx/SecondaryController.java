package org.openjfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.codec.language.Soundex;

public class SecondaryController {

    @FXML
    private TextArea good_file_text;

    @FXML
    private TextArea bad_file_text;

    @FXML
    private TextField file_name_id;

    @FXML
    private Button read_file_id;

    @FXML
    private TextArea good_file_text_result;

    @FXML
    private TextArea bad_file_text_result;

    @FXML
    private ChoiceBox chose_fonetic_algorithm_id;

    @FXML
    private TextArea result_compare_text;

    @FXML
    private Button clear_button;

    @FXML
    LineChart<Number, Number> recall_precision_line_chart;

    private PhoneticAlgorithms phoneticAlgorithms;

    private MistakesGenerator mistakesGenerator;

    private ArrayList<Double> precision = new ArrayList<>();
    private ArrayList<Double> recall = new ArrayList<>();

    private final static String PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\text\\";

    private final ObservableList<String> algorithmList = FXCollections
            .observableArrayList("Soundex", "Morse", "Metaphone", "DoubleMetaphone", "Nysiis");

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void initialize() {
        phoneticAlgorithms = new PhoneticAlgorithms();

        chose_fonetic_algorithm_id.setItems(algorithmList);
        chose_fonetic_algorithm_id.setValue("-");
    }

    @FXML
    public void read_file() {
        good_file_text.setText(readFromFile(file_name_id.getText()));

        mistakesGenerator = new MistakesGenerator();

        String[] text = readFromFile(file_name_id.getText()).split(" ");
        for(String t: text) {
            if(bad_file_text.getText().isEmpty()) {
                bad_file_text.setText(mistakesGenerator.changeWord(t));
            } else {
                bad_file_text.setText(bad_file_text.getText()+ " " + mistakesGenerator.changeWord(t));
            }
        }
//        bad_file_text.setText(list.toString());
    }

    @FXML
    public void on_action_decode_button() {
        String[] alGood = good_file_text.getText().split(" ");
        String[] alBad = bad_file_text.getText().split(" ");

        if(chose_fonetic_algorithm_id.getValue() == "Soundex"){
            for(String s: alGood) {
                good_file_text_result.setText(good_file_text_result.getText() + phoneticAlgorithms.encodeSoundex(s) + " ");
            }
        }else if(chose_fonetic_algorithm_id.getValue() == "Morse") {
            for(String s: alGood) {
                good_file_text_result.setText(good_file_text_result.getText() + phoneticAlgorithms.encodeMorse(s) + " ");
            }
        }else if(chose_fonetic_algorithm_id.getValue() == "Metaphone") {
            for(String s: alGood) {
                good_file_text_result.setText(good_file_text_result.getText() + phoneticAlgorithms.encodeMetaphone(s) + " ");
            }
        }else if(chose_fonetic_algorithm_id.getValue() == "DoubleMetaphone") {
            for(String s: alGood) {
                good_file_text_result.setText(good_file_text_result.getText() + phoneticAlgorithms.encodeDoubleMetaphone(s) + " ");
            }
        }else if(chose_fonetic_algorithm_id.getValue() == "Nysiis") {
            for(String s: alGood) {
                good_file_text_result.setText(good_file_text_result.getText() + phoneticAlgorithms.encodeNysiis(s) + " ");
            }
        }

        if(chose_fonetic_algorithm_id.getValue() == "Soundex"){
            for(String s: alBad) {
                bad_file_text_result.setText(bad_file_text_result.getText() + phoneticAlgorithms.encodeSoundex(s) + " ");
            }
        }else if(chose_fonetic_algorithm_id.getValue() == "Morse") {
            for(String s: alBad) {
                bad_file_text_result.setText(bad_file_text_result.getText() + phoneticAlgorithms.encodeMorse(s) + " ");
            }
        } else if(chose_fonetic_algorithm_id.getValue() == "Metaphone") {
            for(String s: alBad) {
                bad_file_text_result.setText(bad_file_text_result.getText() + phoneticAlgorithms.encodeMetaphone(s) + " ");
            }
        } else if(chose_fonetic_algorithm_id.getValue() == "DoubleMetaphone") {
            for(String s: alBad) {
                bad_file_text_result.setText(bad_file_text_result.getText() + phoneticAlgorithms.encodeDoubleMetaphone(s) + " ");
            }
        } else if(chose_fonetic_algorithm_id.getValue() == "Nysiis") {
            for(String s: alBad) {
                bad_file_text_result.setText(bad_file_text_result.getText() + phoneticAlgorithms.encodeNysiis(s) + " ");
            }
        }
    }

    @FXML
    public void on_action_compare_button() {
        ArrayList<String> good = new ArrayList<>(Arrays.asList(good_file_text_result.getText().split(" ")));
        ArrayList<String> bad = new ArrayList<>(Arrays.asList(bad_file_text_result.getText().split(" ")));

//        precision = new double[bad.size()];
        double precisionNumerator = 0.0;
        double precisionDenominator = 0.0;

//        recall = new double[bad.size()];
        double recallNumerator = 0.0;
        double recallDenominator = 0.0;

        if(chose_fonetic_algorithm_id.getValue() != "Morse") {
            for(String b: bad){
                if(good.contains(b)) {
                    precisionNumerator++;
                    recallNumerator++;
                }

                recallDenominator++;
                precisionDenominator++;

                if(precisionNumerator!=0 && precisionDenominator!=0){
                    precision.add((double)precisionNumerator/(double)precisionDenominator);
                } else {
                    precision.add(0.0);
                }

                if(recallNumerator!=0 && recallDenominator!=0){
                    recall.add((double)recallNumerator/(double)recallDenominator);
                }else {
                    recall.add(0.0);
                }

                precisionNumerator=0;
                precisionDenominator=0;
                recallNumerator=0;
                recallDenominator=0;
            }
        } else {
            for (int i = 0; i < good.size(); i++) {
                //.println(good.get(i));
                ArrayList<String> listGood = new ArrayList<>(Arrays.asList(good.get(i).split("[|]")));
                ArrayList<String> listBad = new ArrayList<>(Arrays.asList(bad.get(i).split("[|]")));

                for (String b : listBad) {
                    if (listGood.contains(b)) {
                        precisionNumerator++;
                        recallNumerator++;
                    }
                }

                recallDenominator = listBad.size() + listGood.size() - recallNumerator;
                precisionDenominator=listBad.size();

                System.out.println("Precision " + (double)precisionNumerator + " / " + (double)precisionDenominator);
                System.out.println("Recall " + (double)recallNumerator + " / " + (double)recallDenominator);

                if(precisionNumerator!=0 && precisionDenominator!=0){
                    precision.add((double)precisionNumerator/(double)precisionDenominator);
                } else {
                    precision.add(0.0);
                }

                if(recallNumerator!=0 && recallDenominator!=0){
                    recall.add((double)recallNumerator/(double)recallDenominator);
                }else {
                    recall.add(0.0);
                }

                precisionNumerator=0;
                precisionDenominator=0;
                recallNumerator=0;
                recallDenominator=0;
            }
        }
        result_compare_text.setText("precision/recall \n");

        for(int i =0; i<precision.size(); i++){
            result_compare_text.setText(result_compare_text.getText() + precision.get(i)+ "/"+ recall.get(i) + "\n");
        }
    }

    @FXML
    public void on_action_draw_chart() {

        XYChart.Series seriesPrecision = new XYChart.Series();
        seriesPrecision.setName("Precision");

        XYChart.Series seriesRecall = new XYChart.Series();
        seriesRecall.setName("Recall");

        for(int i = 0; i < precision.size(); i++) {
            seriesPrecision.getData().add(new XYChart.Data(i,precision.get(i)));
        }

        for(int i = 0; i < recall.size(); i++) {
            seriesRecall.getData().add(new XYChart.Data(i, recall.get(i)));
        }

        recall_precision_line_chart.getData().addAll(seriesPrecision, seriesRecall);
    }

    @FXML
    public void on_action_clear_button() {
        good_file_text.clear();
        bad_file_text.clear();
        good_file_text_result.clear();
        bad_file_text_result.clear();

        precision.clear();
        recall.clear();
        recall_precision_line_chart.getData().clear();
    }

    public String readFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            File myObj = new File(PATH+fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                sb.append(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found.");
            e.printStackTrace();
        }

        return sb.toString();
    }
}

//precision
//stosunek liczby dobrych kodow dla slowa do calkowitej liczby kodow dla tego slowa

//recall
//suma wszystkich poprawnych kodow dla slowa do sumy wszystkich  kodow dla obu slowa