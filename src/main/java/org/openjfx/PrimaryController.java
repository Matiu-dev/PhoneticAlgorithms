package org.openjfx;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.codec.language.bm.BeiderMorseEncoder;

public class PrimaryController {

    @FXML
    private Button accept_button_decode_id;

    @FXML
    private Button clear_button_decode_id;

    @FXML
    private TextField name_id;

    @FXML
    private TextField result_name_id;

    @FXML
    private ChoiceBox chose_fonetic_algorithm_id;

    private Soundex soundex;
    private BeiderMorseEncoder morse;

    private final ObservableList<String> algorithmList =FXCollections
            .observableArrayList("Soundex", "Morse");

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    void initialize() {
        soundex = new Soundex();
        morse = new BeiderMorseEncoder();

        chose_fonetic_algorithm_id.setItems(algorithmList);
        chose_fonetic_algorithm_id.setValue("-");
    }

    @FXML
    public void decode_button() {
        try {
            if(chose_fonetic_algorithm_id.getValue() == "Soundex"){
                result_name_id.setText(result_name_id.getText() + " " +soundex.encode(name_id.getText()));
            }else if(chose_fonetic_algorithm_id.getValue() == "Morse") {
                result_name_id.setText(result_name_id.getText() + " " +morse.encode(name_id.getText()));
            }
        }catch (EncoderException e) {
            System.out.println(e.getCause());
        }
    }

    @FXML
    public void clear_button() {
        result_name_id.clear();
    }

}
