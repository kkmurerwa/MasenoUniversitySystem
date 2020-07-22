package projectfirst;

import javafx.scene.image.*;
import java.sql.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javax.swing.JOptionPane;


public class Projectfirst extends Application{
    String window;
    String usnm;
    String passw;
    Boolean bool = false;
    Boolean added = false,added1 = false;
    public static Connection conn;
    public static Statement state;
    @Override
    public void start (Stage pm){
        second();//calls the method second which implements the login page
    }
    public void second(){
        Stage pm = new Stage();
        BorderPane bd = new BorderPane();
        VBox hb = new VBox();
        VBox hb2 = new VBox();
        Label lb1 = new Label("Enter Your Login Credentials");
        lb1.setStyle("-fx-font:20 cooper_black");
        
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Student","Lecturer","Admin"));//created a choice box and sets values
        cb.getSelectionModel().selectFirst();//sets default selection
        cb.setStyle("-fx-font:15 cooper_black");
        
        Label lb2 = new Label("Username ");
        lb2.setStyle("-fx-font:15 cooper_black");
        Label lb3 = new Label("Password ");
        lb3.setStyle("-fx-font:15 cooper_black");
        TextField txf = new TextField();
        txf.setPrefSize(300,50);
        txf.setStyle("-fx-border-radius: 150px");
        Label message1 =  new Label("Enter admission number as username e.g CI/00000/010");
        Label message = new Label("Your default password is your national id number");
        txf.setStyle("-fx-font:18 cooper_black");
        txf.setPromptText("Enter Username");
        PasswordField pf = new PasswordField();
        pf.setPrefSize(300,50);
        pf.setPromptText("Enter password");
        pf.setStyle("-fx-font:18 cooper_black");
        Button submit = new Button("SUBMIT");
        submit.setPrefSize(150,30);
        submit.setStyle("-fx-font:18 cooper_black");
        submit.setOnAction((ActionEvent) -> {
            String usnm1 = txf.getText();
            usnm = usnm1.toUpperCase();
            passw = pf.getText();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaminiproject?zeroDateTimeBehavior=convertToNull","root","");
                state = conn.createStatement();
                
                String sql;
                Boolean flag = false;
                if (cb.getValue().equals("Student")){
                    sql = "SELECT * FROM stud_details";
                }
                else if (cb.getValue().equals("Lecturer")){
                    sql = "SELECT * FROM lec_details";
                }
                else{
                    sql = "SELECT * FROM admin";
                }
                ResultSet rs = state.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String passWord = rs.getString("password");
                    String userName = rs.getString("admno");
                    if (usnm.equals(userName)){
                        if(passw.equals(passWord)){
                            txf.clear();
                            pf.clear();
                            flag = true;
                            ///add code for the admin account
                            if (cb.getValue().equals("Student")){
                                homeWindow(userName);
                            }
                            else if (cb.getValue().equals("Lecturer")){
                                lecWindow(userName);
                            }
                            else{
                                admin(userName);
                            }
                            pm.close();
                        }
                        else{
                            alert("Your password is incorrect!");
                            break;
                        }
                    }
                    else{
                        continue;
                    } 
                }
                if (flag == false && !rs.next()){
                    alert("Username not found");
                }
            }
            catch(Exception e){
                alert(e+" Error connecting to database");
            }
        
        });
        GridPane gp = new GridPane();
        hb.setAlignment(Pos.CENTER);
        gp.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        hb.setPadding(new Insets(5,5,5,5));
        hb.getChildren().addAll(lb1,cb);
        hb2.setPadding(new Insets(5,5,50,5));
        hb2.getChildren().addAll(submit);
        gp.add(lb2, 0, 0);
        gp.add(txf,1,0);
        gp.add(message1,1,1);
        gp.add(lb3,0,2);
        gp.add(pf,1,2);
        gp.add(message,1,3);
        bd.setTop(hb);
        bd.setCenter(gp);
        bd.setBottom(hb2);
        Scene scn = new Scene(bd,600,300);
        lb3.requestFocus();
        pm.setTitle("Login");
        pm.setScene(scn);
        pm.show();
    }
    public static void main(String [] args){
        Application.launch(args);
    }
    public void alert(String n){//used to output alerts and warnings
        Stage bh = new Stage();
        Label lb = new Label();
        lb.setText(n+"!");
        lb.setStyle("-fx-font: 20 calibri");
        lb.setTextFill(Color.RED);
        BorderPane bd = new BorderPane();
        bd.setCenter(lb);
        Scene scn =  new Scene(bd,250,150);
        bh.setScene(scn);
        bh.setTitle("Alert");
        bh.setResizable(true);
        bh.show();
    }
    public void homeWindow(String key){//holds data that is displayed when a student logs in        
        Stage home = new Stage();
        BorderPane bp = new BorderPane();
        Scene scnHome = new Scene(bp,800,700);
        HBox hbTop = new HBox();
        hbTop.setPadding(new Insets(10,12,10,12));//insets are set as top,right,bottom,left
        hbTop.setSpacing(10);
        hbTop.setAlignment(Pos.CENTER);
        
        HBox hbBottom = new HBox();
        hbBottom.setPadding(new Insets(5,12,10,12));
        hbBottom.setSpacing(10);
        hbBottom.setAlignment(Pos.CENTER);
        
        
        
        //create items for the home gridpane here
        
        
        
        
        GridPane gpHome = new GridPane();
        gpHome.setPadding(new Insets(10,50,20,50));
        gpHome.setAlignment(Pos.CENTER);
        
        Label homeTitle = new Label(); 
        homeTitle.setText("Student Details Window");
        homeTitle.setStyle("-fx-font:22 cooper_black");
        //homeTitle.requestFocus();
        
        Label x = new Label(); 
        x.setText("   ");
        x.setStyle("-fx-font:22 cooper_black");
        
        gpHome.add(homeTitle,1,0);
        gpHome.add(x,2,0);
        gpHome.setAlignment(Pos.CENTER);
        
        Label studName = new Label();
        studName.setText("Name");
        studName.setStyle("-fx-font:18 cooper_black");
        studName.setPadding(new Insets(25,100,5,0));

        TextField tfName = new TextField();
        tfName.setEditable(bool);
        tfName.setPrefSize(300,10);
        tfName.setPadding(new Insets(15,20,10,10));
        tfName.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
            
        Label studAdm = new Label();
        studAdm.setText("Reg Number");
        studAdm.setStyle("-fx-font:18 cooper_black");
        studAdm.setPadding(new Insets(5,100,25,0));
        
        TextField tfAdm = new TextField();
        tfAdm.setEditable(bool);
        tfAdm.setPrefSize(300,10);
        tfAdm.setPadding(new Insets(15,20,10,10));
        tfAdm.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label studAge = new Label();
        studAge.setText("Age");
        studAge.setStyle("-fx-font:18 cooper_black");
        studAge.setPadding(new Insets(5,100,25,0));
        
        TextField tfAge = new TextField();
        tfAge.setEditable(bool);
        tfAge.setPrefSize(300,10);
        tfAge.setPadding(new Insets(15,20,10,10));
        tfAge.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        
        Label studSex = new Label();
        studSex.setText("Sex");
        studSex.setStyle("-fx-font:18 cooper_black");
        studSex.setPadding(new Insets(5,100,25,0));
        
        TextField tfSex = new TextField();
        tfSex.setEditable(bool);
        tfSex.setPrefSize(300,10);
        tfSex.setPadding(new Insets(15,20,10,10));
        tfSex.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label studYear = new Label();
        studYear.setText("Year");
        studYear.setStyle("-fx-font:18 cooper_black");
        studYear.setPadding(new Insets(5,100,25,0));
        
        TextField tfYear = new TextField();
        tfYear.setEditable(bool);
        tfYear.setPrefSize(300,10);
        tfYear.setPadding(new Insets(15,20,10,10));
        tfYear.setStyle("-fx-font:14 cooper_black");
        
        Label studSch = new Label();
        studSch.setText("School");
        studSch.setStyle("-fx-font:18 cooper_black");
        studSch.setPadding(new Insets(5,100,25,0));
        
        TextField tfSch = new TextField();
        tfSch.setEditable(bool);
        tfSch.setPrefSize(300,10);
        tfSch.setPadding(new Insets(15,20,10,10));
        tfSch.setStyle("-fx-font:14 cooper_black");
        
        //add code to retreive name from database and print it in the textfield here
        
        Label studUnits = new Label();
        studUnits.setText("Units");
        studUnits.setStyle("-fx-font:18 cooper_black");
        studUnits.setPadding(new Insets(5,100,25,0));
        
        TextArea tfUnits = new TextArea();
        tfUnits.setEditable(bool);
        tfUnits.setPrefSize(400,200);
        tfUnits.setPadding(new Insets(1,0,1,0));
        tfUnits.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Button edit = new Button("Edit");
        edit.setPrefSize(150,10);
        edit.setStyle("-fx-font:16 cooper_black");
        gpHome.setVgap(10);
        edit.requestFocus();
        edit.setOnAction((ActionEvent) ->{
            bool = true;
            tfName.setEditable(bool);
            tfAge.setEditable(bool);
            tfSex.setEditable(bool);
            tfYear.setEditable(bool);
            tfSch.setEditable(bool);
            tfUnits.setEditable(bool);
            //set code to save the input data to database table
        });
        
        Button save = new Button("Save");
        save.setPrefSize(150,10);
        save.setStyle("-fx-font:16 cooper_black");
        gpHome.setVgap(10);
        save.setOnAction((ActionEvent) ->{
            bool = false;
            tfName.setEditable(bool);
            tfAge.setEditable(bool);
            tfSex.setEditable(bool);
            tfYear.setEditable(bool);
            tfSch.setEditable(bool);
            tfUnits.setEditable(bool);
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String updateStatement = "update stud_details set studname = ?,age = ?,yearofstudy = ?,school = ?,sex = ?,units= ?"
                        +"where admno = ?";
                PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                updateStuff.setString(1,tfName.getText());//updates query with first int is no of parameters
                updateStuff.setInt(2,Integer.parseInt(tfAge.getText()));
                updateStuff.setInt(3,Integer.parseInt(tfYear.getText()));
                updateStuff.setString(4,tfSch.getText());
                updateStuff.setString(5,tfSex.getText());
                updateStuff.setString(6,tfUnits.getText());
                updateStuff.setString(7,key);
                updateStuff.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record was saved");
                
                //STEP 5: Extract data from result set
                
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
            
            //set code to save the input data to database table
        });
        try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String sql;
                int counter = 1;
                sql = "SELECT * FROM stud_details";
                ResultSet rs = state.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String Name = rs.getString("studname");
                    String admNo = rs.getString("admno");
                    String age = rs.getString("age");
                    String sex = rs.getString("sex");
                    String school = rs.getString("school");
                    String year = rs.getString("yearofstudy");
                    String units = rs.getString("units");
                    if (key.equals(admNo)){
                        tfName.setText(Name);
                        tfAdm.setText(admNo);
                        tfAge.setText(age);
                        tfSex.setText(sex);
                        tfYear.setText(year);
                        tfSch.setText(school);
                        tfUnits.setText(units);
                    }
                    else{
                        continue;
                    } 
                }
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        
        HBox hbBut = new HBox();
        hbBut.setSpacing(5);
        hbBut.getChildren().addAll(edit,save);
        
        
        gpHome.add(studName,0,1);
        gpHome.add(tfName,1,1);
        gpHome.add(studAdm,0,2);
        gpHome.add(tfAdm,1,2);
        gpHome.add(studAge,0,3);
        gpHome.add(tfAge,1,3);
        gpHome.add(studSex,0,4);
        gpHome.add(tfSex,1,4);
        gpHome.add(studYear,0,5);
        gpHome.add(tfYear,1,5);
        gpHome.add(studSch,0,6);
        gpHome.add(tfSch,1,6);
        gpHome.add(studUnits,0,7);
        gpHome.add(tfUnits,1,7);
        gpHome.add(hbBut,1,8);
        bp.setCenter(gpHome);
        
        
        
        //create items for the lecturer's gridpane here
        
//        Label lecTitle = new Label(); 
//        lecTitle.setText("Maseno University Lecturers");
//        lecTitle.setStyle("-fx-font:22 cooper_black");
//        lecTitle.setPadding(new Insets(0,0,0,0));
        
        

        
        
        GridPane gpLec = new GridPane();
        gpLec.setPadding(new Insets(10,50,50,50));
        gpLec.setAlignment(Pos.TOP_CENTER);
        gpLec.setHgap(10);
        gpLec.setVgap(10);
        
        TextField tfSearch = new TextField();
        tfSearch.setPrefSize(300,10);
        tfSearch.setPadding(new Insets(15,20,10,10));
        tfSearch.setPromptText("Search lecturer name");
        tfSearch.setStyle("-fx-font:14 cooper_black");   
        
        Label lecName = new Label(); 
        lecName.setText("Name");
        lecName.setStyle("-fx-font:18 cooper_black");
        lecName.setPadding(new Insets(0,0,0,0));
        
        TextField tfNamelec = new TextField();
        tfNamelec.setEditable(bool);
        tfNamelec.setPrefSize(300,10);
        tfNamelec.setPadding(new Insets(15,20,10,10));
        tfNamelec.setStyle("-fx-font:14 cooper_black");
        
        Label lecAdm = new Label();
        lecAdm.setText("Reg Number");
        lecAdm.setStyle("-fx-font:18 cooper_black");
        lecAdm.setPadding(new Insets(0,0,0,0));//insets are set as top,right,bottom,left
        
        TextField tfAdmlec = new TextField();
        tfAdmlec.setEditable(bool);
        tfAdmlec.setPrefSize(300,10);
        tfAdmlec.setPadding(new Insets(15,20,10,10));
        tfAdmlec.setStyle("-fx-font:14 cooper_black");
        
        Label lecSch = new Label();
        lecSch.setText("School");
        lecSch.setStyle("-fx-font:18 cooper_black");
        lecSch.setPadding(new Insets(5,100,25,0));
        
        TextField tfLecSch = new TextField();
        tfLecSch.setEditable(bool);
        tfLecSch.setPrefSize(300,10);
        tfLecSch.setPadding(new Insets(15,20,10,10));
        tfLecSch.setStyle("-fx-font:14 cooper_black");
        
        Label lecSex = new Label();
        lecSex.setText("Sex");
        lecSex.setStyle("-fx-font:18 cooper_black");
        lecSex.setPadding(new Insets(0,0,0,0));
        
        TextField tfSexlec = new TextField();
        tfSexlec.setEditable(bool);
        tfSexlec.setPrefSize(300,10);
        tfSexlec.setPadding(new Insets(15,20,10,10));
        tfSexlec.setStyle("-fx-font:14 cooper_black");
        
        Label lecUnits = new Label();
        lecUnits.setText("Sex");
        lecUnits.setStyle("-fx-font:18 cooper_black");
        lecUnits.setPadding(new Insets(0,0,0,0));
        
        TextArea tfUnitslec = new TextArea();
        tfUnitslec.setEditable(bool);
        tfUnitslec.setPrefSize(300,150);
        tfUnitslec.setPadding(new Insets(1,0,1,0));
        tfUnitslec.setStyle("-fx-font:14 cooper_black");
        
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(70,50);
        searchButton.setStyle("-fx-font:14 cooper_black");
        
        searchButton.setOnAction((ActionEvent) ->{
            String schKey = tfSearch.getText();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                int counter = 1;
                
                String sql = "SELECT * FROM lec_details where name like ?";
                PreparedStatement retreive = conn.prepareStatement(sql);
                
                retreive.setString(1,"%"+schKey+"%");
                ResultSet rs = retreive.executeQuery();
                
                Boolean available = false;
                while(rs.next()){
                    //Retrieve by column name
                    String sname = rs.getString("name");
                    String lecadm = rs.getString("admno");
                    String sex = rs.getString("sex");
                    String units = rs.getString("units");
                    String lecscho = rs.getString("schools");
                    tfNamelec.setText(sname);
                    tfAdmlec.setText(lecadm);
                    tfSexlec.setText(sex);
                    tfLecSch.setText(lecscho);
                    tfUnitslec.setText(units);
                    added1 = true;
                    available = true;
                }
                if (available == false){
                    alert("Lecturer Not found");
                }
                
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        });
        
        HBox hbButt = new HBox();
        hbButt.setSpacing(5);
        hbButt.getChildren().addAll(tfSearch,searchButton);
        

        lecName.setText("Lecturer");
        lecAdm.setText("Reg NO");
        lecSex.setText("Sex");
        lecUnits.setText("Units");
        
        gpLec.add(hbButt,1,0);
        gpLec.add(lecName,0,1);
        gpLec.add(tfNamelec,1,1);
        gpLec.add(lecAdm,0,2);
        gpLec.add(tfAdmlec,1,2);
        gpLec.add(lecSex,0,4);
        gpLec.add(tfSexlec,1,4);
        gpLec.add(lecSch,0,5);
        gpLec.add(tfLecSch,1,5);
        gpLec.add(lecUnits,0,6);
        gpLec.add(tfUnitslec,1,6);

        
        
        //create items for the school's grid pane
        
        
        Label studName1 = new Label();
        studName1.setText("Name");
        studName1.setStyle("-fx-font:18 cooper_black");
        studName1.setPadding(new Insets(25,100,5,0));

        TextField tfName1 = new TextField();
        tfName1.setEditable(bool);
        tfName1.setPrefSize(300,10);
        tfName1.setPadding(new Insets(15,20,10,10));
        tfName1.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
            
        Label studAdm1 = new Label();
        studAdm1.setText("Reg Number");
        studAdm1.setStyle("-fx-font:18 cooper_black");
        studAdm1.setPadding(new Insets(5,100,25,0));
        
        TextField tfAdm1 = new TextField();
        tfAdm1.setEditable(bool);
        tfAdm1.setPrefSize(300,10);
        tfAdm1.setPadding(new Insets(15,20,10,10));
        tfAdm1.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label studAge1 = new Label();
        studAge1.setText("Age");
        studAge1.setStyle("-fx-font:18 cooper_black");
        studAge1.setPadding(new Insets(5,100,25,0));
        
        TextField tfAge1 = new TextField();
        tfAge1.setEditable(bool);
        tfAge1.setPrefSize(300,10);
        tfAge1.setPadding(new Insets(15,20,10,10));
        tfAge1.setStyle("-fx-font:14 cooper_black");
        
        Label studYear1 = new Label();
        studYear1.setText("Year");
        studYear1.setStyle("-fx-font:18 cooper_black");
        studYear1.setPadding(new Insets(5,100,25,0));
        
        TextField tfYear1 = new TextField();
        tfYear1.setEditable(bool);
        tfYear1.setPrefSize(300,10);
        tfYear1.setPadding(new Insets(15,20,10,10));
        tfYear1.setStyle("-fx-font:14 cooper_black");
        
        Label studUnits1 = new Label();
        studUnits1.setText("Units");
        studUnits1.setStyle("-fx-font:18 cooper_black");
        studUnits1.setPadding(new Insets(5,100,25,0));
        
        TextArea tfUnits1 = new TextArea();
        tfUnits1.setEditable(bool);
        tfUnits1.setPrefSize(400,200);
        tfUnits1.setPadding(new Insets(1,0,1,0));
        tfUnits1.setStyle("-fx-font:14 cooper_black");
        
        
        GridPane gpSch = new GridPane();
        gpSch.setPadding(new Insets(10,50,50,50));//insets are set as top,right,bottom,left
        gpSch.setAlignment(Pos.TOP_CENTER);
        gpSch.setVgap(10);
        gpSch.setHgap(10);
        
        ObservableList<String> options2 = 
                FXCollections.observableArrayList(
                        "Computing and Informatics",
                        "Medicine",
                        "Public Health",
                        "Arts",
                        "Earth Sciences"
                );
        ComboBox comboSch = new ComboBox(options2);
        comboSch.setPrefSize(300,10);
        comboSch.setPadding(new Insets(15,20,10,10));
        comboSch.setStyle("-fx-font:16 cooper_black");
        
        
        
        Button searchButton2 = new Button("Search");
        searchButton2.setPrefSize(70,50);
        searchButton2.setStyle("-fx-font:14 cooper_black");
        searchButton2.setOnAction((ActionEvent) ->{
            String schKey = (String) comboSch.getValue();
            
            if (added == true){
                gpSch.getChildren().removeAll(studName1,tfName1,studAdm1,tfAdm1,studYear1,tfYear1,
                        studAge1,tfAge1,studUnits1,tfUnits1);
            }
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String sql;
                int counter = 1;
                sql = "SELECT * FROM schools";
                ResultSet rs = state.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String sname = rs.getString("schname");
                    String scode = rs.getString("schid");
                    String sHOD = rs.getString("HOD");
                    String nooflecs = rs.getString("nooflecs");
                    String courses = rs.getString("courses");
                    if (schKey.equals(sname)){
                        studName1.setText("School Name");
                        tfName1.setText(sname);
                        studAdm1.setText("School Code");
                        tfAdm1.setText(scode);
                        studAge1.setText("Head of Department");
                        tfAge1.setText(sHOD);
                        studYear1.setText("No of Lecturers");
                        tfYear1.setText(nooflecs);
                        studUnits1.setText("courses");
                        tfUnits1.setText(courses);
                        
                        gpSch.add(studName1,0,1);
                        gpSch.add(tfName1,1,1);
                        gpSch.add(studAdm1,0,2);
                        gpSch.add(tfAdm1,1,2);
                        gpSch.add(studAge1,0,3);
                        gpSch.add(tfAge1,1,3);
                        gpSch.add(studYear1,0,4);
                        gpSch.add(tfYear1,1,4);
                        gpSch.add(studUnits1,0,5);
                        gpSch.add(tfUnits1,1,5);
                        added = true;
                    }
                    else{
                        continue;
                    } 
                }
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        });
        HBox hbButts = new HBox();
        hbButts.setSpacing(5);
        hbButts.getChildren().addAll(comboSch,searchButton2);
        
        gpSch.add(hbButts,1,0);
        
        //end of grid panes
        
        Button logout = new Button("LOGOUT");
        logout.setPrefSize(150,30);
        logout.setStyle("-fx-font:18 cooper_black");
        logout.setOnAction((ActionEvent) -> {
            Object [] options={"YES","NO"};//creates object to be called upon by JOptionPane
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to log out?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                second();
                home.close();
            }
            else{
                ;;
            }
            
        });
        
        
        Button close = new Button("EXIT");
        close.setPrefSize(150,30);
        close.setStyle("-fx-font:18 cooper_black");
        close.setOnAction((ActionEvent) -> {
            Object [] options={"YES","NO"};//creates object to be called upon by the button
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to exit?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                System.exit(0);
            } 
        });
        
        
        
        
        Button homeButton = new Button("Student Details");
        homeButton.setPrefSize(150,30);
        homeButton.setStyle("-fx-font:16 cooper_black");
        homeButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpHome);
        });
        
        Button lecButton = new Button("Lecturers");
        lecButton.setPrefSize(150,30);
        lecButton.setStyle("-fx-font:16 cooper_black");
        lecButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpLec);
        });
        
        Button schButton = new Button("Schools");
        schButton.setPrefSize(150,30);
        schButton.setStyle("-fx-font:16 cooper_black");
        schButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpSch);
        });
        
        
        hbTop.getChildren().addAll(homeButton,lecButton,schButton);
        hbBottom.getChildren().addAll(logout,close);
        bp.setTop(hbTop);
        bp.setBottom(hbBottom);
        homeTitle.requestFocus();
        home.setScene(scnHome);
        home.setTitle("Maseno University App");
        home.show();
    }
    public void lecWindow(String key){//holds data that is displayed when a lecturer logs in
        bool = false;
        Stage home = new Stage();
        BorderPane bp = new BorderPane();
        Scene scnHome = new Scene(bp,800,700);
        HBox hbTop = new HBox();
        hbTop.setPadding(new Insets(10,12,10,12));//insets are set as top,right,bottom,left
        hbTop.setSpacing(10);
        hbTop.setAlignment(Pos.CENTER);
        
        HBox hbBottom = new HBox();
        hbBottom.setPadding(new Insets(5,12,10,12));
        hbBottom.setSpacing(10);
        hbBottom.setAlignment(Pos.CENTER);
        
        
        
        //create items for the home gridpane here
        
        
        
        
        GridPane gpHome = new GridPane();
        gpHome.setPadding(new Insets(10,50,20,50));
        gpHome.setAlignment(Pos.CENTER);
        
        Label homeTitle = new Label(); 
        homeTitle.setText("You are not allowed to view this content");
        homeTitle.setStyle("-fx-font:24 cooper_black");
        //homeTitle.requestFocus();
        
        Label x = new Label(); 
        x.setText("   ");
        x.setStyle("-fx-font:22 cooper_black");
        
        gpHome.add(homeTitle,1,0);
        gpHome.add(x,2,0);
        gpHome.setAlignment(Pos.CENTER);
        
        
        
        
        
        
        //create items for the lecturer's gridpane here
        
        Label lecTitle = new Label(); 
        lecTitle.setText("Maseno University Lecturers");
        lecTitle.setStyle("-fx-font:22 cooper_black");
        lecTitle.setPadding(new Insets(0,0,0,0));
        
        

        
        
        GridPane gpLec = new GridPane();
        gpLec.setPadding(new Insets(10,50,20,50));
        gpLec.setAlignment(Pos.TOP_CENTER);
        gpLec.setHgap(10);
        
        Label lecName = new Label();
        lecName.setText("Name");
        lecName.setStyle("-fx-font:18 cooper_black");
        lecName.setPadding(new Insets(25,100,5,0));

        TextField tfName = new TextField();
        tfName.setEditable(bool);
        tfName.setPrefSize(300,10);
        tfName.setPadding(new Insets(15,20,10,10));
        tfName.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
            
        Label lecAdm = new Label();
        lecAdm.setText("Reg Number");
        lecAdm.setStyle("-fx-font:18 cooper_black");
        lecAdm.setPadding(new Insets(5,100,25,0));
        
        TextField tfAdm = new TextField();
        tfAdm.setEditable(bool);
        tfAdm.setPrefSize(300,10);
        tfAdm.setPadding(new Insets(15,20,10,10));
        tfAdm.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label lecAge = new Label();
        lecAge.setText("Age");
        lecAge.setStyle("-fx-font:18 cooper_black");
        lecAge.setPadding(new Insets(5,100,25,0));
        
        TextField tfAge = new TextField();
        tfAge.setEditable(bool);
        tfAge.setPrefSize(300,10);
        tfAge.setPadding(new Insets(15,20,10,10));
        tfAge.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        
        Label lecSch = new Label();
        lecSch.setText("School");
        lecSch.setStyle("-fx-font:18 cooper_black");
        lecSch.setPadding(new Insets(5,100,25,0));
        
        TextField tfSch = new TextField();
        tfSch.setEditable(bool);
        tfSch.setPrefSize(300,10);
        tfSch.setPadding(new Insets(15,20,10,10));
        tfSch.setStyle("-fx-font:14 cooper_black");
        
        Label lecSex = new Label();
        lecSex.setText("Sex");
        lecSex.setStyle("-fx-font:18 cooper_black");
        lecSex.setPadding(new Insets(5,100,25,0));
        
        TextField tfSex = new TextField();
        tfSex.setEditable(bool);
        tfSex.setPrefSize(300,10);
        tfSex.setPadding(new Insets(15,20,10,10));
        tfSex.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label lecUnits = new Label();
        lecUnits.setText("Units");
        lecUnits.setStyle("-fx-font:18 cooper_black");
        lecUnits.setPadding(new Insets(5,100,25,0));
        
        TextArea tfUnits = new TextArea();
        tfUnits.setEditable(bool);
        tfUnits.setPrefSize(400,200);
        tfUnits.setPadding(new Insets(1,0,1,0));
        tfUnits.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Button edit = new Button("Edit");
        edit.setPrefSize(150,10);
        edit.setStyle("-fx-font:16 cooper_black");
        gpLec.setVgap(10);
        edit.requestFocus();
        edit.setOnAction((ActionEvent) ->{
            bool = true;
            tfName.setEditable(bool);
            tfAge.setEditable(bool);
            tfSch.setEditable(bool);
            tfSex.setEditable(bool);
            tfUnits.setEditable(bool);
            //set code to save the input data to database table
        });
        
        Button save = new Button("Save");
        save.setPrefSize(150,10);
        save.setStyle("-fx-font:16 cooper_black");
        gpHome.setVgap(10);
        save.setOnAction((ActionEvent) ->{
            bool = false;
            tfName.setEditable(bool);
            tfAge.setEditable(bool);
            tfSch.setEditable(bool);
            tfSex.setEditable(bool);
            tfUnits.setEditable(bool);
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String updateStatement = "update lec_details set age = ?,schools = ?,sex = ?,units= ?"
                        +"where admno = ?";
                PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                updateStuff.setInt(1,Integer.parseInt(tfAge.getText()));
                updateStuff.setString(2,tfSch.getText());
                updateStuff.setString(3,tfSex.getText());
                updateStuff.setString(4,tfUnits.getText());
                updateStuff.setString(5,key);
                updateStuff.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record was saved");
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        });
        
        try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String sql;
                int counter = 1;
                sql = "SELECT * FROM lec_details";
                ResultSet rs = state.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String Name = rs.getString("name");
                    String admNo = rs.getString("admno");
                    String age = rs.getString("age");
                    String sch =  rs.getString("schools");
                    String sex = rs.getString("sex");
                    String units = rs.getString("units");
                    if (key.equals(admNo)){
                        tfName.setText(Name);
                        tfAdm.setText(admNo);
                        tfAge.setText(age);
                        tfSch.setText(sch);
                        tfSex.setText(sex);
                        tfUnits.setText(units);
                    }
                    else{
                        continue;
                    } 
                }
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        
        HBox hbBut = new HBox();
        hbBut.setSpacing(10);
        hbBut.getChildren().addAll(edit,save);
        gpLec.add(lecTitle,1,0);
        gpLec.add(lecName,0,1);
        gpLec.add(tfName,1,1);
        gpLec.add(lecAdm,0,2);
        gpLec.add(tfAdm,1,2);
        gpLec.add(lecAge,0,3);
        gpLec.add(tfAge,1,3);
        gpLec.add(lecSch,0,4);
        gpLec.add(tfSch,1,4);
        gpLec.add(lecSex,0,5);
        gpLec.add(tfSex,1,5);
        gpLec.add(lecUnits,0,6);
        gpLec.add(tfUnits,1,6);
        gpLec.add(hbBut,1,8);
        bp.setCenter(gpLec);
        
        
        
        
        //create items for the school's grid pane
        
        
        
        
        Label studName1 = new Label();
        studName1.setText("Name");
        studName1.setStyle("-fx-font:18 cooper_black");
        studName1.setPadding(new Insets(25,100,5,0));

        TextField tfName1 = new TextField();
        tfName1.setEditable(bool);
        tfName1.setPrefSize(300,10);
        tfName1.setPadding(new Insets(15,20,10,10));
        tfName1.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
            
        Label studAdm1 = new Label();
        studAdm1.setText("Reg Number");
        studAdm1.setStyle("-fx-font:18 cooper_black");
        studAdm1.setPadding(new Insets(5,100,25,0));
        
        TextField tfAdm1 = new TextField();
        tfAdm1.setEditable(bool);
        tfAdm1.setPrefSize(300,10);
        tfAdm1.setPadding(new Insets(15,20,10,10));
        tfAdm1.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label studAge1 = new Label();
        studAge1.setText("Age");
        studAge1.setStyle("-fx-font:18 cooper_black");
        studAge1.setPadding(new Insets(5,100,25,0));
        
        TextField tfAge1 = new TextField();
        tfAge1.setEditable(bool);
        tfAge1.setPrefSize(300,10);
        tfAge1.setPadding(new Insets(15,20,10,10));
        tfAge1.setStyle("-fx-font:14 cooper_black");
        
        Label studYear1 = new Label();
        studYear1.setText("Year");
        studYear1.setStyle("-fx-font:18 cooper_black");
        studYear1.setPadding(new Insets(5,100,25,0));
        
        TextField tfYear1 = new TextField();
        tfYear1.setEditable(bool);
        tfYear1.setPrefSize(300,10);
        tfYear1.setPadding(new Insets(15,20,10,10));
        tfYear1.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label studUnits1 = new Label();
        studUnits1.setText("Units");
        studUnits1.setStyle("-fx-font:18 cooper_black");
        studUnits1.setPadding(new Insets(5,100,25,0));
        
        TextArea tfUnits1 = new TextArea();
        tfUnits1.setEditable(bool);
        tfUnits1.setPrefSize(400,200);
        tfUnits1.setPadding(new Insets(1,0,1,0));
        tfUnits1.setStyle("-fx-font:14 cooper_black");
        
        
        GridPane gpSch = new GridPane();
        gpSch.setPadding(new Insets(10,50,50,50));//insets are set as top,right,bottom,left
        gpSch.setAlignment(Pos.TOP_CENTER);
        gpSch.setVgap(10);
        gpSch.setHgap(10);
        
        ObservableList<String> options2 = 
                FXCollections.observableArrayList(
                        "Computing and Informatics",
                        "Medicine",
                        "Public Health",
                        "Arts",
                        "Earth Sciences"
                );
        ComboBox comboSch = new ComboBox(options2);
        comboSch.setPrefSize(300,10);
        comboSch.setPadding(new Insets(15,20,10,10));
        comboSch.setStyle("-fx-font:16 cooper_black");
        
        
        
        Button searchButton2 = new Button("Search");
        searchButton2.setPrefSize(70,50);
        searchButton2.setStyle("-fx-font:14 cooper_black");
        searchButton2.setOnAction((ActionEvent) ->{
            String schKey = (String) comboSch.getValue();
            
            if (added == true){
                gpSch.getChildren().removeAll(studName1,tfName1,studAdm1,tfAdm1,studYear1,tfYear1,
                        studAge1,tfAge1,studUnits1,tfUnits1);
            }
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String sql;
                int counter = 1;
                sql = "SELECT * FROM schools";
                ResultSet rs = state.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String sname = rs.getString("schname");
                    String scode = rs.getString("schid");
                    String sHOD = rs.getString("HOD");
                    String nooflecs = rs.getString("nooflecs");
                    String courses = rs.getString("courses");
                    if (schKey.equals(sname)){
                        studName1.setText("School Name");
                        tfName1.setText(sname);
                        studAdm1.setText("School Code");
                        tfAdm1.setText(scode);
                        studAge1.setText("Head of Department");
                        tfAge1.setText(sHOD);
                        studYear1.setText("No of Lecturers");
                        tfYear1.setText(nooflecs);
                        studUnits1.setText("courses");
                        tfUnits1.setText(courses);
                        
                        gpSch.add(studName1,0,1);
                        gpSch.add(tfName1,1,1);
                        gpSch.add(studAdm1,0,2);
                        gpSch.add(tfAdm1,1,2);
                        gpSch.add(studAge1,0,3);
                        gpSch.add(tfAge1,1,3);
                        gpSch.add(studYear1,0,4);
                        gpSch.add(tfYear1,1,4);
                        gpSch.add(studUnits1,0,5);
                        gpSch.add(tfUnits1,1,5);
                        added = true;
                    }
                    else{
                        continue;
                    } 
                }
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        });
        HBox hbButts = new HBox();
        hbButts.setSpacing(5);
        hbButts.getChildren().addAll(comboSch,searchButton2);
        
        gpSch.add(hbButts,1,0);

        
        
        
        
        
        //end of grid panes
        
        Button logout = new Button("LOGOUT");
        logout.setPrefSize(150,30);
        logout.setStyle("-fx-font:18 cooper_black");
        logout.setOnAction((ActionEvent) -> {
            Object [] options={"YES","NO"};//creates object to be called upon by JOptionPane
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to log out?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                second();
                home.close();
            }
            else{
                ;;
            }
            
        });
        
        
        Button close = new Button("EXIT");
        close.setPrefSize(150,30);
        close.setStyle("-fx-font:18 cooper_black");
        close.setOnAction((ActionEvent) -> {
            Object [] options={"YES","NO"};//creates object to be called upon by the button
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to exit?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                System.exit(0);
            } 
        });
        
        
        
        
        Button homeButton = new Button("Student Details");
        homeButton.setPrefSize(150,30);
        homeButton.setStyle("-fx-font:16 cooper_black");
        homeButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpHome);
        });
        
        Button lecButton = new Button("Lecturers");
        lecButton.setPrefSize(150,30);
        lecButton.setStyle("-fx-font:16 cooper_black");
        lecButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpLec);
        });
        
        Button schButton = new Button("Schools");
        schButton.setPrefSize(150,30);
        schButton.setStyle("-fx-font:16 cooper_black");
        schButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpSch);
        });
        
        
        hbTop.getChildren().addAll(homeButton,lecButton,schButton);
        hbBottom.getChildren().addAll(logout,close);
        bp.setTop(hbTop);
        bp.setBottom(hbBottom);
        lecButton.requestFocus();
        home.setScene(scnHome);
        home.setTitle("Maseno University App");
        home.show();
    }   
    public void admin(String key){//holds data that is displayed when an administrator logs in
        Stage home = new Stage();
        bool = true;
        BorderPane bp = new BorderPane();
        Scene scnHome = new Scene(bp,800,700);
        HBox hbTop = new HBox();
        hbTop.setPadding(new Insets(10,12,10,12));//insets are set as top,right,bottom,left
        hbTop.setSpacing(10);
        hbTop.setAlignment(Pos.CENTER);
        
        HBox hbBottom = new HBox();
        hbBottom.setPadding(new Insets(5,12,10,12));
        hbBottom.setSpacing(10);
        hbBottom.setAlignment(Pos.CENTER);
        
        
        
        //create items for the home gridpane here
        
        
        
        
        GridPane gpHome = new GridPane();
        gpHome.setPadding(new Insets(10,50,20,50));
        gpHome.setAlignment(Pos.CENTER);
        
        Label homeTitle = new Label(); 
        homeTitle.setText("Student Details Window");
        homeTitle.setStyle("-fx-font:22 cooper_black");
        //homeTitle.requestFocus();
        
        Label x = new Label(); 
        x.setText("   ");
        x.setStyle("-fx-font:22 cooper_black");
        
        gpHome.add(homeTitle,1,0);
        gpHome.add(x,2,0);
        gpHome.setAlignment(Pos.CENTER);
        
        Label studName = new Label();
        studName.setText("Name");
        studName.setStyle("-fx-font:18 cooper_black");
        studName.setPadding(new Insets(25,100,5,0));

        TextField tfName = new TextField();
        tfName.setEditable(bool);
        tfName.setPrefSize(300,10);
        tfName.setPadding(new Insets(15,20,10,10));
        tfName.setStyle("-fx-font:14 cooper_black");
            
        Label studAdm = new Label();
        studAdm.setText("Reg Number");
        studAdm.setStyle("-fx-font:18 cooper_black");
        studAdm.setPadding(new Insets(5,100,25,0));
        
        TextField tfAdm = new TextField();
        tfAdm.setPromptText("This is a required field");
        tfAdm.setEditable(bool);
        tfAdm.setPrefSize(300,10);
        tfAdm.setPadding(new Insets(15,20,10,10));
        tfAdm.setStyle("-fx-font:14 cooper_black");
        
        Label studAge = new Label();
        studAge.setText("Age");
        studAge.setStyle("-fx-font:18 cooper_black");
        studAge.setPadding(new Insets(5,100,25,0));
        
        TextField tfAge = new TextField();
        tfAge.setEditable(bool);
        tfAge.setPrefSize(300,10);
        tfAge.setPadding(new Insets(15,20,10,10));
        tfAge.setStyle("-fx-font:14 cooper_black");
        
        Label studSex = new Label();
        studSex.setText("Sex");
        studSex.setStyle("-fx-font:18 cooper_black");
        studSex.setPadding(new Insets(5,100,25,0));
        
        TextField tfSex = new TextField();
        tfSex.setEditable(bool);
        tfSex.setPrefSize(300,10);
        tfSex.setPadding(new Insets(15,20,10,10));
        tfSex.setStyle("-fx-font:14 cooper_black");
        
        Label studYear = new Label();
        studYear.setText("Year");
        studYear.setStyle("-fx-font:18 cooper_black");
        studYear.setPadding(new Insets(5,100,25,0));
        
        TextField tfYear = new TextField();
        tfYear.setEditable(bool);
        tfYear.setPrefSize(300,10);
        tfYear.setPadding(new Insets(15,20,10,10));
        tfYear.setStyle("-fx-font:14 cooper_black");
        
        Label studSch = new Label();
        studSch.setText("School");
        studSch.setStyle("-fx-font:18 cooper_black");
        studSch.setPadding(new Insets(5,100,25,0));
        
        TextField tfSch = new TextField();
        tfSch.setEditable(bool);
        tfSch.setPrefSize(300,10);
        tfSch.setPadding(new Insets(15,20,10,10));
        tfSch.setStyle("-fx-font:14 cooper_black");
        
        Label studPass = new Label();
        studPass.setText("Password");
        studPass.setStyle("-fx-font:18 cooper_black");
        studPass.setPadding(new Insets(5,100,25,0));
        
        TextField tfPass = new TextField();
        tfPass.setEditable(bool);
        tfPass.setPrefSize(300,10);
        tfPass.setPadding(new Insets(15,20,10,10));
        tfPass.setStyle("-fx-font:14 cooper_black");
        
        Label studUnits = new Label();
        studUnits.setText("Units");
        studUnits.setStyle("-fx-font:18 cooper_black");
        studUnits.setPadding(new Insets(5,100,25,0));
        
        TextArea tfUnits = new TextArea();
        tfUnits.setEditable(bool);
        tfUnits.setPrefSize(400,200);
        tfUnits.setPadding(new Insets(1,0,1,0));
        tfUnits.setStyle("-fx-font:14 cooper_black");
        
        Button save = new Button("Save");
        save.setPrefSize(150,10);
        save.setStyle("-fx-font:16 cooper_black");
        save.setOnAction((ActionEvent) ->{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String updateStatement = "INSERT INTO `javaminiproject`.`stud_details` "
                        + "(`admno`, `age`, `yearofstudy`, `sex`, `password`, `school`, `studname`, `units`) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                updateStuff.setString(1,tfAdm.getText());//updates query with first int is no of parameters
                updateStuff.setInt(2,Integer.parseInt(tfAge.getText()));
                updateStuff.setInt(3,Integer.parseInt(tfYear.getText()));
                updateStuff.setString(4,tfSex.getText());
                updateStuff.setString(5,tfPass.getText().toUpperCase());
                updateStuff.setString(6,tfSch.getText());
                updateStuff.setString(7,tfName.getText());
                updateStuff.setString(8,tfUnits.getText());
                updateStuff.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record was saved");
                //STEP 5: Extract data from result set
                
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
            
            //set code to save the input data to database table
        });
        
        Button delete = new Button("Delete");
        delete.setPrefSize(150,10);
        delete.setStyle("-fx-font:16 cooper_black");
        delete.setOnAction((ActionEvent) ->{
            Object [] options={"YES","NO"};//creates object to be called upon by the button
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to delete this record?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                    state = conn.createStatement();
                    String updateStatement = "delete from stud_details where admno = ?;";
                    PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                    updateStuff.setString(1,tfAdm.getText());
                    updateStuff.executeUpdate();   
                    tfName.clear();
                    tfAdm.clear();
                    tfAge.clear();
                    tfSex.clear();
                    tfYear.clear();
                    tfSch.clear();
                    tfPass.clear();
                    tfUnits.clear();
                    JOptionPane.showMessageDialog(null,"Record was deleted");
                }
                catch(Exception e){
                    alert(e+" error connecting to database");
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Record was NOT deleted");
            }
        });        
        HBox hbBut = new HBox();
        hbBut.setSpacing(5);
        hbBut.getChildren().addAll(save,delete);
        
        
        gpHome.add(studName,0,1);
        gpHome.add(tfName,1,1);
        gpHome.add(studAdm,0,2);
        gpHome.add(tfAdm,1,2);
        gpHome.add(studAge,0,3);
        gpHome.add(tfAge,1,3);
        gpHome.add(studSex,0,4);
        gpHome.add(tfSex,1,4);
        gpHome.add(studYear,0,5);
        gpHome.add(tfYear,1,5);
        gpHome.add(studPass,0,6);
        gpHome.add(tfPass,1,6);
        gpHome.add(studSch,0,7);
        gpHome.add(tfSch,1,7);
        gpHome.add(studUnits,0,8);
        gpHome.add(tfUnits,1,8);
        gpHome.add(hbBut,1,9);
        bp.setCenter(gpHome);
        
        
        
        //create items for the lecturer's gridpane here
        

        
        
        Label lecTitle = new Label(); 
        lecTitle.setText("Maseno University Lecturers");
        lecTitle.setStyle("-fx-font:22 cooper_black");
        lecTitle.setPadding(new Insets(0,0,0,0));
        
        

        
        
        GridPane gpLec = new GridPane();
        gpLec.setPadding(new Insets(10,50,20,50));
        gpLec.setAlignment(Pos.TOP_CENTER);
        gpLec.setHgap(10);
        
        Label lecName = new Label();
        lecName.setText("Name");
        lecName.setStyle("-fx-font:18 cooper_black");
        lecName.setPadding(new Insets(25,100,5,0));

        TextField tfLecName = new TextField();
        tfLecName.setEditable(bool);
        tfLecName.setPrefSize(300,10);
        tfLecName.setPadding(new Insets(15,20,10,10));
        tfLecName.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
            
        Label lecAdm = new Label();
        lecAdm.setText("Reg Number");
        lecAdm.setStyle("-fx-font:18 cooper_black");
        lecAdm.setPadding(new Insets(5,100,25,0));
        
        TextField tfLecAdm = new TextField();
        tfLecAdm.setPromptText("This is a required field");
        tfLecAdm.setEditable(bool);
        tfLecAdm.setPrefSize(300,10);
        tfLecAdm.setPadding(new Insets(15,20,10,10));
        tfLecAdm.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label lecAge = new Label();
        lecAge.setText("Age");
        lecAge.setStyle("-fx-font:18 cooper_black");
        lecAge.setPadding(new Insets(5,100,25,0));
        
        TextField tfLecAge = new TextField();
        tfLecAge.setEditable(bool);
        tfLecAge.setPrefSize(300,10);
        tfLecAge.setPadding(new Insets(15,20,10,10));
        tfLecAge.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label lecSex = new Label();
        lecSex.setText("Sex");
        lecSex.setStyle("-fx-font:18 cooper_black");
        lecSex.setPadding(new Insets(5,100,25,0));
        
        TextField tfLecSex = new TextField();
        tfLecSex.setEditable(bool);
        tfLecSex.setPrefSize(300,10);
        tfLecSex.setPadding(new Insets(15,20,10,10));
        tfLecSex.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label lecSch = new Label();
        lecSch.setText("School");
        lecSch.setStyle("-fx-font:18 cooper_black");
        lecSch.setPadding(new Insets(5,100,25,0));
        
        TextField tfLecSch = new TextField();
        tfLecSch.setEditable(bool);
        tfLecSch.setPrefSize(300,10);
        tfLecSch.setPadding(new Insets(15,20,10,10));
        tfLecSch.setStyle("-fx-font:14 cooper_black");
        
        Label lecPass = new Label();
        lecPass.setText("Password");
        lecPass.setStyle("-fx-font:18 cooper_black");
        lecPass.setPadding(new Insets(5,100,25,0));
        
        TextField tfLecPass = new TextField();
        tfLecPass.setEditable(bool);
        tfLecPass.setPrefSize(300,10);
        tfLecPass.setPadding(new Insets(15,20,10,10));
        tfLecPass.setStyle("-fx-font:14 cooper_black");
        
        Label lecUnits = new Label();
        lecUnits.setText("Units");
        lecUnits.setStyle("-fx-font:18 cooper_black");
        lecUnits.setPadding(new Insets(5,100,25,0));
        
        TextArea tfLecUnits = new TextArea();
        tfLecUnits.setEditable(bool);
        tfLecUnits.setPrefSize(400,200);
        tfLecUnits.setPadding(new Insets(1,0,1,0));
        tfLecUnits.setStyle("-fx-font:14 cooper_black");
        
        Button save1 = new Button("Save");
        save1.setPrefSize(150,10);
        save1.setStyle("-fx-font:16 cooper_black");
        save1.setOnAction((ActionEvent) ->{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String updateStatement = "INSERT INTO `javaminiproject`.`lec_details` "
                        + "(`name`, `admno`, `age`, `sex`, `password`,`units`, `schools`) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement updateStuff = conn.prepareStatement(updateStatement);//creates a prepared statement that uses variables to set textfield data
                updateStuff.setString(1,tfLecName.getText());
                updateStuff.setString(2,tfLecAdm.getText());
                updateStuff.setInt(3,Integer.parseInt(tfLecAge.getText()));
                updateStuff.setString(4,tfLecSex.getText());
                updateStuff.setString(5,tfLecPass.getText().toUpperCase());
                updateStuff.setString(6,tfLecUnits.getText());
                updateStuff.setString(7,tfLecSch.getText());
                updateStuff.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record was saved");                
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
        });
        
        Button delete1 = new Button("Delete");
        delete1.setPrefSize(150,10);
        delete1.setStyle("-fx-font:16 cooper_black");
        delete1.setOnAction((ActionEvent) ->{
            Object [] options={"YES","NO"};//creates object to be called upon by the button
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to delete this record?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                    state = conn.createStatement();
                    String updateStatement = "delete from lec_details where admno = ?;";
                    PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                    updateStuff.setString(1,tfLecAdm.getText());
                    updateStuff.executeUpdate();   
                    tfLecName.clear();
                    tfLecAdm.clear();
                    tfLecAge.clear();
                    tfLecSex.clear();
                    tfLecSch.clear();
                    tfLecPass.clear();
                    tfLecUnits.clear();
                    JOptionPane.showMessageDialog(null,"The record was deleted");
                }
                catch(Exception e){
                    alert(e+" error connecting to database");
                }
            }
        });
        
        HBox hbButLec = new HBox();
        hbButLec.setSpacing(10);
        hbButLec.getChildren().addAll(save1,delete1);
        gpLec.add(lecTitle,1,0);
        gpLec.add(lecName,0,1);
        gpLec.add(tfLecName,1,1);
        gpLec.add(lecAdm,0,2);
        gpLec.add(tfLecAdm,1,2);
        gpLec.add(lecAge,0,3);
        gpLec.add(tfLecAge,1,3);
        gpLec.add(lecSex,0,4);
        gpLec.add(tfLecSex,1,4);
        gpLec.add(lecSch,0,5);
        gpLec.add(tfLecSch,1,5);
        gpLec.add(lecPass,0,6);
        gpLec.add(tfLecPass,1,6);
        gpLec.add(lecUnits,0,7);
        gpLec.add(tfLecUnits,1,7);
        gpLec.add(hbButLec,1,8);

        
        
        //create items for the school's grid pane
        
        
        Label schName = new Label();
        schName.setText("School Name");
        schName.setStyle("-fx-font:18 cooper_black");
        schName.setPadding(new Insets(25,100,5,0));

        TextField tfSchName = new TextField();
        tfSchName.setEditable(bool);
        tfSchName.setPrefSize(300,10);
        tfSchName.setPadding(new Insets(15,20,10,10));
        tfSchName.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
            
        Label schId = new Label();
        schId.setText("School ID");
        schId.setStyle("-fx-font:18 cooper_black");
        schId.setPadding(new Insets(5,100,25,0));
        
        TextField tfschId = new TextField();
        tfschId.setEditable(bool);
        tfschId.setPrefSize(300,10);
        tfschId.setPromptText("This is a required field");
        tfschId.setPadding(new Insets(15,20,10,10));
        tfschId.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label schHOD = new Label();
        schHOD.setText("H.O.D.");
        schHOD.setStyle("-fx-font:18 cooper_black");
        schHOD.setPadding(new Insets(5,100,25,0));
        
        TextField tfSchHOD = new TextField();
        tfSchHOD.setEditable(bool);
        tfSchHOD.setPrefSize(300,10);
        tfSchHOD.setPadding(new Insets(15,20,10,10));
        tfSchHOD.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label schNoLecs = new Label();
        schNoLecs.setText("No of Lecs");
        schNoLecs.setStyle("-fx-font:18 cooper_black");
        schNoLecs.setPadding(new Insets(5,100,25,0));
        
        TextField tfSchNoLecs = new TextField();
        tfSchNoLecs.setEditable(bool);
        tfSchNoLecs.setPrefSize(300,10);
        tfSchNoLecs.setPadding(new Insets(15,20,10,10));
        tfSchNoLecs.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label schLecs = new Label();
        schLecs.setText("Lecs");
        schLecs.setStyle("-fx-font:18 cooper_black");
        schLecs.setPadding(new Insets(5,100,25,0));
        
        TextArea tfSchLecs = new TextArea();
        tfSchLecs.setEditable(bool);
        tfSchLecs.setPrefSize(300,10);
        tfSchLecs.setPadding(new Insets(1,0,1,0));
        tfSchLecs.setStyle("-fx-font:14 cooper_black");
        //add code to retreive name from database and print it in the textfield here
        
        Label schCourses = new Label();
        schCourses.setText("Courses");
        schCourses.setStyle("-fx-font:18 cooper_black");
        schCourses.setPadding(new Insets(5,100,25,0));
        
        TextArea tfSchCourses = new TextArea();
        tfSchCourses.setEditable(bool);
        tfSchCourses.setPrefSize(400,200);
        tfSchCourses.setPadding(new Insets(1,0,1,0));
        tfSchCourses.setStyle("-fx-font:14 cooper_black");
        
        
        Button save2 = new Button("Save");
        save2.setPrefSize(150,10);
        save2.setStyle("-fx-font:16 cooper_black");
        save2.setOnAction((ActionEvent) ->{
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                state = conn.createStatement();
                String updateStatement = "INSERT INTO `javaminiproject`.`schools` "
                        + "(`schname`, `schid`, `hod`, `nooflecs`,`lecs`, `courses`) "
                        + "VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                updateStuff.setString(1,tfSchName.getText());//updates query with first int is no of parameters
                updateStuff.setString(2,tfschId.getText());
                updateStuff.setString(3,tfSchHOD.getText());
                updateStuff.setInt(4,Integer.parseInt(tfSchNoLecs.getText()));
                updateStuff.setString(5,tfSchLecs.getText());
                updateStuff.setString(6,tfSchCourses.getText());
                updateStuff.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record was saved");
                
                //STEP 5: Extract data from result set
                
            }
            catch(Exception e){
                alert(e+" error connecting to database");
            }
            
            //set code to save the input data to database table
        });
        
        Button delete2 = new Button("Delete");
        delete2.setPrefSize(150,10);
        delete2.setStyle("-fx-font:16 cooper_black");
        delete2.setOnAction((ActionEvent) ->{
            Object [] options={"YES","NO"};//creates object to be called upon by the button
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to delete this record?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaminiproject?zeroDateTimeBehavior=convertToNull","root","toor");
                    state = conn.createStatement();
                    String updateStatement = "delete from schools where schid = ?;";
                    PreparedStatement updateStuff = conn.prepareStatement(updateStatement);
                    updateStuff.setString(1,tfschId.getText());
                    updateStuff.executeUpdate();  
                    
                    tfSchName.clear();
                    tfschId.clear();
                    tfSchHOD.clear();
                    tfSchNoLecs.clear();
                    tfSchLecs.clear();
                    tfSchCourses.clear();
                    JOptionPane.showMessageDialog(null,"Record was deleted");
                }
                catch(Exception e){
                    alert(e+" error connecting to database");
                }
            }
        });
        
        HBox hbSch = new HBox();
        hbSch.setSpacing(10);
        hbSch.getChildren().addAll(save2,delete2);
        
        
        GridPane gpSch = new GridPane();
        gpSch.setPadding(new Insets(10,50,50,50));//insets are set as top,right,bottom,left
        gpSch.setAlignment(Pos.TOP_CENTER);
        gpSch.setVgap(5);
        gpSch.setHgap(10);

        gpSch.add(schName,0,1);
        gpSch.add(tfSchName,1,1);
        gpSch.add(schId,0,2);
        gpSch.add(tfschId,1,2);
        gpSch.add(schHOD,0,3);
        gpSch.add(tfSchHOD,1,3);
        gpSch.add(schNoLecs,0,4);
        gpSch.add(tfSchNoLecs,1,4);
        gpSch.add(schLecs,0,5);
        gpSch.add(tfSchLecs,1,5);
        gpSch.add(schCourses,0,6);
        gpSch.add(tfSchCourses,1,6);
        gpSch.add(hbSch,1,7);
        
        //end of grid panes
        
        Button logout = new Button("LOGOUT");
        logout.setPrefSize(150,30);
        logout.setStyle("-fx-font:18 cooper_black");
        logout.setOnAction((ActionEvent) -> {
            Object [] options={"YES","NO"};//creates object to be called upon by JOptionPane
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to log out?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                second();
                home.close();
            }
            else{
                ;;
            }
            
        });
        
        
        Button close = new Button("EXIT");
        close.setPrefSize(150,30);
        close.setStyle("-fx-font:18 cooper_black");
        close.setOnAction((ActionEvent) -> {
            Object [] options={"YES","NO"};//creates object to be called upon by the button
            int response=JOptionPane.showOptionDialog(null,"Are you sure you want to log out?\n",
                "Confirm Dialog",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
            if (response == 0){
                System.exit(0);
            } 
        });
        
        
        
        
        Button homeButton = new Button("Student Details");
        homeButton.setPrefSize(150,30);
        homeButton.setStyle("-fx-font:16 cooper_black");
        homeButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpHome);
        });
        
        Button lecButton = new Button("Lecturers");
        lecButton.setPrefSize(150,30);
        lecButton.setStyle("-fx-font:16 cooper_black");
        lecButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpLec);
        });
        
        Button schButton = new Button("Schools");
        schButton.setPrefSize(150,30);
        schButton.setStyle("-fx-font:16 cooper_black");
        schButton.setOnAction((ActionEvent) ->{
            bp.setCenter(gpSch);
        });
        
        
        hbTop.getChildren().addAll(homeButton,lecButton,schButton);
        hbBottom.getChildren().addAll(logout,close);
        bp.setTop(hbTop);
        bp.setBottom(hbBottom);
        homeTitle.requestFocus();
        home.setScene(scnHome);
        home.setTitle("Maseno University App");
        home.show();
    }
        
}