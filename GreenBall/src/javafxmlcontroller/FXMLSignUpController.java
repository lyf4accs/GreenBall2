/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;


/**
 *
 * @author svalero
 */
public class FXMLSignUpController implements Initializable {


 
    //properties to control valid fieds values. 
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;  

    //private BooleanBinding validFields;
    
    //When to strings are equal, compareTo returns zero
    private final int EQUALS = 0;  
    @FXML
    private TextField eemail;
    @FXML
    private PasswordField psswd;
    private PasswordField psswd2;
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private Label lInvalidPsswd;
    private Label lIncorrectPsswd;
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;
    @FXML
    private Label labelPsswd;
    @FXML
    private ImageView whiteEye1;
    @FXML
    private ImageView blackEye1;
    private Label labelPsswd2;
    private ImageView whiteEye2;
    private ImageView blackEye2;
    @FXML
    private ToggleButton button1;
    private ToggleButton button2;
    @FXML
    private TextField eemail1;
    @FXML
    private Label lIncorrectPsswd1;
    @FXML
    private PasswordField psswd1;
    @FXML
    private Label labelPsswd1;
    @FXML
    private ToggleButton button11;
    @FXML
    private ImageView whiteEye11;
    @FXML
    private ImageView blackEye11;
    @FXML
    private Label lInvalidPsswd1;
    @FXML
    private Label lInvalidPsswd2;
    @FXML
    private Button bAccept1;
   
    

    /**
     * Updates the boolProp to false.Changes to red the background of the edit. 
     * Makes the error label visible and sends the focus to the edit. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
        if (psswd.isFocused()){
            labelPsswd.setVisible(false);
        } else if (psswd2.isFocused()){
            labelPsswd2.setVisible(false);
        }
    }
    /**
     * Updates the boolProp to true. Changes the background 
     * of the edit to the default value. Makes the error label invisible. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    /**
     * Changes to red the background of the edit and
     * makes the error label visible
     * @param errorLabel
     * @param textField 
     */
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    /**
     * Changes the background of the edit to the default value
     * and makes the error label invisible.
     * @param errorLabel
     * @param textField 
     */
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }

    

    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       lIncorrectEmail.setText("");
       lInvalidPsswd.setText("");
       lIncorrectPsswd.setText("");
       
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
       
        eemail.focusedProperty().addListener((a,b,c)->{//observable, oldValue, newValue
            if(!c){//focus lost
                checkEditEmail();
            }
        });
        
        psswd.focusedProperty().addListener((a,b,c)->{//observable, oldValue, newValue
            if(!c){//focus lost
                checkEditPsswd();
            }
        });
        
        psswd2.focusedProperty().addListener((a,b,c)->{//observable, oldValue, newValue
            if(!c){//focus lost
                checkEquals();
            }
        });
        
        psswd.textProperty().addListener((a,b,c)->{
            labelPsswd.setText(c);
        });
        
        psswd2.textProperty().addListener((a,b,c)->{
            labelPsswd2.setText(c);
        });
        
        BooleanBinding validFields = Bindings.and(validEmail, validPassword)
                 .and(equalPasswords);
       // usamos la clase Bindings para hacer un and lógico sobre las tres propiedades que guardan
       //si el valor introducido de los campos es válido y guardamos el resultado en una variable
       //de tipo BooleanBinding
       
        bAccept.disableProperty().bind(Bindings.not(validFields));
      //hacemos que la disableProperty() del botón esté conectada al valor de la BooleanBinding anterior. 
      //Solo se habilitará cuando todas las propiedades de control sean ciertas. 
        
        bCancel.setOnAction((event)->{bCancel.getScene().getWindow().hide();
        }); //accede a la escena en la cual está el botón
        //desde la escena accede al stage o la ventana
        //llama al método hide() que la cierra
    } 
    
    private void checkEditEmail(){
        if (!Utils.checkEmail(eemail.textProperty().getValueSafe())){//Incorrect email
            lIncorrectEmail.setText("Incorrect Email");
            manageError(lIncorrectEmail, eemail, validEmail);
        }else{
            manageCorrect(lIncorrectEmail, eemail, validEmail);
        }
    }
    
    private void checkEditPsswd(){
        if (!Utils.checkPassword(psswd.textProperty().getValueSafe())){//Incorrect email
            lInvalidPsswd.setText("Incorrect. It must be a combination of 8 to 15 numbers or letters, without blank spaces.");
            manageError(lInvalidPsswd, psswd, validPassword);
        }else{
            manageCorrect(lInvalidPsswd, psswd, validPassword);
        }
    }
    
    private void checkEquals(){
        if (psswd.textProperty().getValueSafe().compareTo(psswd2.textProperty().getValueSafe())!= EQUALS){//Incorrect email
            lIncorrectPsswd.setText("Passwords don't match");
            showErrorMessage(lIncorrectPsswd,psswd2);
            equalPasswords.setValue(Boolean.FALSE);
            psswd.textProperty().setValue("");
            psswd2.textProperty().setValue(""); //borrar el valor de los campos de edición que contienen la contraseña
            psswd.requestFocus();//mandar al usuario al primer campo de la contraseña
        }else{
            manageCorrect(lIncorrectPsswd, psswd2, equalPasswords);
        }
    }

    @FXML
    private void accepted(ActionEvent event) {
       //limpia el contenido de los text edits
        eemail.textProperty().setValue("");
        psswd.textProperty().setValue("");
        psswd2.textProperty().setValue("");
        //pone a FALSE las propiedades creadas para guardar si el valor introducido es válido
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        labelPsswd.setVisible(false);
        labelPsswd2.setVisible(false);
        blackEye1.setVisible(false);
        blackEye2.setVisible(false);
        whiteEye1.setVisible(true);
        whiteEye2.setVisible(true);
    }

    @FXML
    private void cancelled(ActionEvent event) {
    }
   

    @FXML
    private void emailNext(KeyEvent event) {
        KeyCode pulsadoEnter = event.getCode();
        switch(pulsadoEnter){
            case ENTER: psswd.requestFocus();
        }
    }

    @FXML
    private void psswdNext(KeyEvent event) {
         KeyCode pulsadoEnter = event.getCode();
        switch(pulsadoEnter){
            case ENTER: psswd2.requestFocus();
        }
    }

    private void psswd2Next(KeyEvent event) {
         KeyCode pulsadoEnter = event.getCode();
        switch(pulsadoEnter){
            case ENTER: eemail.requestFocus();
            bAccept.requestFocus();
            
        }
    }

    @FXML
    private void acceptPulsado(KeyEvent event) {
     if(bAccept.isFocused()){
        KeyCode pulsadoEnter = event.getCode();
        switch(pulsadoEnter){
            case ENTER: //limpia el contenido de los text edits
        eemail.textProperty().setValue("");
        psswd.textProperty().setValue("");
        psswd2.textProperty().setValue("");
        //pone a FALSE las propiedades creadas para guardar si el valor introducido es válido
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        labelPsswd.setVisible(false);
        labelPsswd2.setVisible(false);
        blackEye1.setVisible(false);
        blackEye2.setVisible(false);
        whiteEye1.setVisible(true);
        whiteEye2.setVisible(true);
        }
     }
    }

    @FXML
    private void visualizePsswd(MouseEvent event) {
       if (button1.isSelected()){
           blackEye1.setVisible(true);
           whiteEye1.setVisible(false);
           labelPsswd.setVisible(true);
       } else if (!(button1.isSelected())) {
           blackEye1.setVisible(false);
           whiteEye1.setVisible(true);
           labelPsswd.setVisible(false);
       }
       
    }

    private void visualizedPsswd2(MouseEvent event) {
        if (button2.isSelected()){
            blackEye2.setVisible(true);
            whiteEye2.setVisible(false);
            labelPsswd2.setVisible(true);
       } else if (!(button2.isSelected())){
           blackEye2.setVisible(false);
           whiteEye2.setVisible(true);
           labelPsswd2.setVisible(false);
       }
    }
    
    public static  Boolean checkEmail (String email)
    {   if(email == null){
          return false; 
        }
       // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
     /**
     * 
     * A password is considered valid if it is combination of 8 to 15 numbers or 
     * letters, without blank spaces.
     *
     * @param password String which contains the password to check  
     * @return  True if the password is valid. False otherwise.   
     */ 
    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        // Regex to check valid password. 
        String regex =  "^[A-Za-z0-9]{8,15}$"; 
  
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    
    }
  }


