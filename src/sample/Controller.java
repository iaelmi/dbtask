package sample;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;


public class Controller implements Initializable {

    @FXML
    private JFXComboBox<String> comboF;

    @FXML
    private JFXListView<Patient> list;

    @FXML
    private JFXButton btnC;

    @FXML
    private JFXButton btnP;

    final String hostname = "database-1.cf34qivtkmye.us-east-1.rds.amazonaws.com";
    final String dbname = "Homework_2";
    final String port = "3306";
    final String username = "iaelmi";
    final String password = "saida123";

    final String AWSURL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;

    String tempURL = "jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboF.getItems().addAll("All Patient","Greater Age then 25","Greater Height than 180","Female Patient","Male Patient");
        comboF.getSelectionModel().select(0);  // selecting the combo box first item

        btnC.setOnAction(e->{
            // calling the connect function to create a connection
            Connection con = null;
            try {
                con = DriverManager.getConnection(AWSURL);//////////////////////////// ///////////////////
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                // creating the statement
                Statement s = con.createStatement();
                // executing the query
                s.execute("CREATE DATABASE if not exists patients");
                String query = "CREATE TABLE if not exists patients.tablepatients (id varchar(255) NOT NULL, name varchar(255) NOT NULL,age varchar(255) NOT NULL, gender varchar(255) NOT NULL, blood varchar(255) NOT NULL, weight varchar(255) NOT NULL,height varchar(255) NOT NULL,PRIMARY KEY (id));";
                s.execute(query);
                System.out.println("SUCCESSFULLY CONNECTED");
                con.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });

        btnP.setOnAction(e->{
            list.getItems().clear();
            //Creating the array object of patients
            Patient[] object;
            object = new Patient[10];
            object[0]  = new Patient(UUID.randomUUID(),"Saif", 24, "male", "a-", 245, 184);
            object[1] = new Patient(UUID.randomUUID(),"aliza", 34, "female", "b+", 149, 174);
            object[2] = new Patient(UUID.randomUUID(),"zainab", 21, "female", "o-", 238, 186);
            object[3] = new Patient(UUID.randomUUID(),"laiba", 23, "female", "ab+", 132, 169);
            object[4] = new Patient(UUID.randomUUID(),"imtiaz", 14, "male", "b+", 143, 164);
            object[5] = new Patient(UUID.randomUUID(),"saleem", 23, "male", "o+", 144, 194);
            object[6] = new Patient(UUID.randomUUID(),"nadeem", 24, "male", "a-", 134, 139);
            object[7] = new Patient(UUID.randomUUID(),"usama", 35, "male", "b+", 212, 134);
            object[8] = new Patient(UUID.randomUUID(),"akbar", 19, "male", "ab+", 223, 187);
            object[9] = new Patient(UUID.randomUUID(),"faisal", 34, "male", "b+", 165, 167);

            try {
                Connection con = null;
                try {
                    con = DriverManager.getConnection(AWSURL);/////////////////////////
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                Statement statement = con.createStatement();
                statement.execute("use patients");
                statement.executeUpdate("delete from patients.tablepatients");
                //inserting all the patient to database
                for (Patient patient : object) {
                    String s = "INSERT INTO tablepatients (id, name , age, gender, blood, weight, height) VALUES ('" + patient.getId().toString() + "','" + patient.getName() + "','" + patient.getAge() + "','" + patient.getGender() + "','" + patient.getB_type() + "','" + patient.getWeight() + "','" + patient.getHeight() + "')";
                    statement.execute(s);
                }
                con.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());

            }
            comboF();

        });

        comboF.setOnAction(e -> {
            comboF();
        });
    }

    private void comboF() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(AWSURL);///////////////////////////////////////////
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(con!=null) try {

            Statement s = con.createStatement();
            ResultSet rs = null;
            s.execute("use patients");
            String select = comboF.getSelectionModel().getSelectedItem();
            String query = "";
            if ("All Patient".equals(select)) {
                query = "SELECT * FROM tablepatients";
            } else if ("Greater Age then 25".equals(select)) {
                query = "SELECT * FROM Patients.tablepatients WHERE age > 25";
            } else if ("Female Patient".equals(select)) {
                query = "Select * from Patients.tablepatients where gender Like 'female'";
                System.out.println(query);
            } else if ("Male Patient".equals(select)) {
                query = "Select * from Patients.tablepatients where gender Like 'male'";
                System.out.println(query);
            } else if ("Greater Height than 180".equals(select)) {
                query = "SELECT * FROM Patients.tablepatients WHERE height > 180";
            }
            //calling the display data function by passing the query
            s.execute("use Patients");
            System.out.println(query);
            rs = s.executeQuery(query);
            list.getItems().clear();
            if (rs.next()) {
                do {     // getting the data from db to listview
                    UUID id = UUID.fromString(rs.getString("id"));
                    String n = rs.getString("name");
                    String g = rs.getString("gender");
                    float w = Float.parseFloat(rs.getString("weight"));
                    float h = Float.parseFloat(rs.getString("height"));
                    String b = rs.getString("blood");
                    int a = Integer.parseInt(rs.getString("age"));
                    Patient p = new Patient(id, n, a, g, b, w, h);
                    list.getItems().add(p);
                } while (rs.next());
            }

        } catch (SQLException ex) {

        }
    }
}
