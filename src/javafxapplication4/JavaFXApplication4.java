
package javafxapplication4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafxapplication4.DBConnect;
import javafxapplication4.Search;
import javax.swing.JOptionPane;


public class JavaFXApplication4 extends Application {
    int count=0;
    Label []label=new Label[8]; 
    TextField []text=new TextField[6];
    Button btn1;
    Button btn2;
   Button btn3;
    private ObservableList<ObservableList> data;
    private TableView tableview;
   
  
     ComboBox combo;
    @Override
    public void start(final Stage primaryStage) throws Exception {
       
    //fillCombobox();
    
        primaryStage.setTitle("DataBase Project");
      
        GridPane grid=new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        Image ımage1=new Image("file:egitim1.png");
         ImageView ıv=new ImageView();
         ıv.setImage(ımage1);
         ıv.setTranslateX(450);
          ıv.setTranslateY(10);
        
        
                  
                  
        combo=new ComboBox();
          combo.setMaxSize(150,30);
          combo.setPromptText("operation type..");
          combo.setEditable(true);    
          combo.getItems().addAll("Degree cancellation");
            GridPane.setConstraints(combo, 1, 7);
                  grid.getChildren().addAll(combo);
                  
         
         
         
        
        Font LabelFont1=new Font(12);
        //------------------------------------------

       //buton   
        btn1=new Button("Save");
        btn1.getStyleClass().add("button-green");
        btn1.setMinSize(100,30);
        btn1.setTranslateX(300);
        btn1.setTranslateY(70);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               count++;
               if(kontrol()) ekle();tablodoldur();

            }     
        });

        btn2=new Button("Clean");
       
        btn2.setMinSize(100, 30);
        btn2.setTranslateX(300);
        btn2.setTranslateY(120);
        
        //pencereye buton ekler
        
         
         btn2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < 6; i++) {
                    text[i].setText("");
                    combo.setValue("");
                    
                }
         
            }     
        });
         
        btn3=new Button("Search");
        btn3.getStyleClass().add("button-blue");
        btn3.setMinSize(100,30);
        btn3.setTranslateX(300);
        btn3.setTranslateY(170);
        
        
               btn3.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
        Search a=new Search();
      a.start(primaryStage);
        primaryStage.show();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
         
            }     
        });
        
        
          grid.getChildren().addAll(btn1,btn2,btn3);
        
        //------------------------------------------
        
        //label
         label[0]=new Label("*T.C");
         label[1]=new Label("*Name");
         label[2]=new Label("*Surname");
         label[3]=new Label("*University");
         label[4]=new Label("*Decision No");
         label[5]=new Label("*Decision Date");
         label[6]=new Label(" Operation");

         for (int i = 0; i < 7; i++) {
            GridPane.setConstraints(label[i], 0, i+1);
            label[i].setFont(LabelFont1);
        }
          
         for (int i = 0; i < 7; i++) {
            grid.getChildren().addAll(label[i]);
        }
        //------------------------------------------
         //textler
            text[0]=new TextField();
            GridPane.setConstraints(text[0], 1, 0+1);
            
             text[1]=new TextField();
            GridPane.setConstraints(text[1], 1, 1+1);
            
             text[2]=new TextField();
            GridPane.setConstraints(text[2], 1, 2+1);
            
             text[3]=new TextField();
            GridPane.setConstraints(text[3], 1, 3+1);
            
             text[4]=new TextField();
            GridPane.setConstraints(text[4], 1, 4+1);
            
            text[5]=new TextField();
              GridPane.setConstraints(text[5], 1, 5+1); 
              
             for (int i = 0; i < 6; i++) {
            grid.getChildren().addAll(text[i]);
        }
       
         
          //---------------------------------------
        tableview=new TableView();
        
        tableview.setMinSize(640,0);
        tableview.setMaxSize(700,250);
        tableview.setTranslateX(15);
        tableview.setTranslateY(280);

         Group root = new Group();
                 
      root.getChildren().addAll(grid,tableview,ıv);

        Scene scene=new Scene(root,715,540);
      scene.getStylesheets().add(getClass().getResource("Viper.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }
    public static void main(String[] args){
        launch(args);
        
    }
     private void ekle(){
    
        try {
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,"An error occurred in the driver"+ex);
            }
            Connection conn=null;
            try {
                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/database","root","");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error on connection"+e);
            }
            
            try{
                PreparedStatement prepared=conn.prepareStatement("INSERT INTO diploma(TC,NAME,SURNAME,UNIVERSITY,DECISIONNO,DECISIONDATE,OPERATION)" +
                        "VALUES(?,?,?,?,?,?,?);");
                prepared.setString(1,text[0].getText());
                prepared.setString(2,text[1].getText());
                prepared.setString(3,text[2].getText());
                prepared.setString(4,text[3].getText());
                prepared.setString(5,text[4].getText());
                prepared.setString(6,text[5].getText());
                prepared.setString(7,combo.getValue().toString());
                
                
                
                
                int donut=prepared.executeUpdate();
                if(donut>0){
                    JOptionPane.showMessageDialog(null,"successful");
                    
                }else JOptionPane.showMessageDialog(null,"error!!!");
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"An error occurred during sql commands"+ex);
            }
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE,null, ex);
        }

}
    private boolean kontrol(){
        boolean donut=false;
        String mesaj="";
        
        
            
          if(text[1].getText().isEmpty())mesaj+="Please do not leave Name blank";
          if(text[2].getText().isEmpty())mesaj+="Please do not leave Surname blank";
          if(text[3].getText().isEmpty())mesaj+="Please do not leave University blank";
          if(text[4].getText().isEmpty())mesaj+="Please do not leave DecisionNo blank";
          if(text[5].getText().isEmpty())mesaj+="Please do not leave DecisionDate blank";
          
          JOptionPane.showMessageDialog(null, mesaj);
        
       if(mesaj=="")donut=true;
        
       return donut;
       
    }
  public void tablodoldur(){
      
       Connection c ;
          data = FXCollections.observableArrayList();
          try{
            c = DBConnect.connect();
            
            String SQL = "SELECT * from diploma";
            
            ResultSet rs = c.createStatement().executeQuery(SQL);

         
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
             
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
               if(count<2){
                   tableview.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
               }
                
            }

          
            while(rs.next()){
               
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                  
                    row.add(rs.getString(i));
                      
                            }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }
            

        
            tableview.setItems(data);
          }catch(Exception e){
              System.out.println("Error on Building Data");             
          }
       
   }

   /*public void fillCombobox(){
       Connection c ;
          
          try{
            c = DBConnect.connect();
            
            String SQL = "SELECT * FROM universiteler";
            PreparedStatement pst=c.prepareStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               options.add(rs.getString("isim"));
              
            }      
            pst.close();
            rs.close();
          }catch(Exception e){
              JOptionPane.showMessageDialog(null, "comboBox ta hata meydana geldi");
          }
 
        }*/

}