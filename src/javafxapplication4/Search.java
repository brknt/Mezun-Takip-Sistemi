
package javafxapplication4;


import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
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


public class Search extends Application {
    int count1=0;
   private Label label1;
    private Label label2;
    //public TextField NameText;
    private TextField text2;
    private Button btn4;
    private Button btn5;
    private ComboBox combo1;
    private String combos;
     private ObservableList<ObservableList> data1;
    private TableView tableview1;
   
     
    @Override
    public void start(final Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("DataBase Project");
      
        GridPane grid=new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(5);
        grid.setHgap(5);
        
        
           Image ımage1=new Image("file:search.png");
         ImageView ıv1=new ImageView();
         ıv1.setImage(ımage1);
         ıv1.setTranslateX(170);
          ıv1.setTranslateY(15);
        
        
        Font LabelFont=new Font(11);
        Font LabelFont1=new Font(15);
        
        label1=new Label("Select Area:");
        label1.setFont(LabelFont1); 
        label1.setTranslateY(90);
        GridPane.setConstraints(label1, 0, 1);
       
          combo1=new ComboBox();
          combo1.setMinSize(300, 20);
         combo1.setTranslateX(30);
        combo1.setTranslateY(90);
          combo1.setPromptText("select area...");
          combo1.setEditable(true);    
          combo1.getItems().addAll("TC","NAME","SURNAME");
            GridPane.setConstraints(combo1, 1, 1);
          
         
        label2=new Label("Keyword");
        label2.setFont(LabelFont1);
        label2.setTranslateY(95);
        GridPane.setConstraints(label2, 0, 2);
        
        text2=new TextField();
        text2.setMinSize(300, 20);
         text2.setTranslateX(30);
        text2.setTranslateY(95);
        GridPane.setConstraints(text2, 1, 2);
       /* NameText=new TextField();
        NameText.setMinSize(300, 20);
        NameText.setTranslateX(30);
        NameText.setTranslateY(90);
        GridPane.setConstraints(NameText, 1, 1);*/
        
        btn5=new Button("Back");
        btn5.setFont(LabelFont);
        btn5.setMinSize(30, 30);
        btn5.setTranslateX(10);
        btn5.setTranslateY(20);
         btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               try{
        JavaFXApplication4 b=new JavaFXApplication4();
        b.start(primaryStage);
        primaryStage.show();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
               

            }     

            
        });
        
        btn4=new Button("Search");
        btn4.getStyleClass().add("button1-blue");
        btn4.setMinSize(100, 50);
        btn4.setTranslateX(50);
        btn4.setTranslateY(110);
        GridPane.setConstraints(btn4, 3, 1);
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               count1++;
              
              tablodoldur1();
               

            }     
        });
        
        grid.getChildren().addAll(label1,label2,text2,btn4,combo1);
        tableview1=new TableView();
        
        tableview1.setMinSize(500,200);
        tableview1.setMaxSize(550,150);
        tableview1.setTranslateX(15);
        tableview1.setTranslateY(220);

        
        Group root = new Group();
                 
      root.getChildren().addAll(grid,tableview1,btn5,ıv1);

        Scene scene2=new Scene(root,590,470);
       scene2.getStylesheets().add(getClass().getResource("Viper2.css").toExternalForm());
        primaryStage.setScene(scene2);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        
    }
    public void tablodoldur1(){
      
       Connection c ;
          data1 = FXCollections.observableArrayList();
          try{
            c = DBConnect.connect();
            
            String text=text2.getText();
             combos=combo1.getValue().toString();
           
            String SQL = "SELECT * FROM diploma WHERE "+combos+" LIKE '%"+text+"%'";
            
            
            ResultSet rs = c.createStatement().executeQuery(SQL);

         
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
             
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
               if(count1<2){
                   tableview1.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
               }
                
            }

          
            while(rs.next()){
               
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                  
                    row.add(rs.getString(i));
                      
                            }
                System.out.println("Row [1] added "+row );
                data1.add(row);

            }
            

        
            tableview1.setItems(data1);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
       
   }
    
    
}
