import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application{

    public TabPane tabParqueadero = new TabPane();
    Tab tab_ingresar_vehiculo = new Tab("Ingresar Vehiculo");
    Tab tab_sacar_vehiculo = new Tab("Sacar Vehiculo");
    
    public String [] autos = new String[10];
    public int tamano = 0;

    public  void start(Stage primaryStage){
        
        //tab formulario
        tab_ingresar_vehiculo.setContent(this.getIngresar());
        //tab formulario
        tab_sacar_vehiculo.setContent(this.getSacar());
        //meter todo en el tab pane
        tabParqueadero.getTabs().addAll(tab_ingresar_vehiculo, tab_sacar_vehiculo);

       //presentacion del esenario
       Scene esena = new Scene(tabParqueadero,1150,410);
       primaryStage.setTitle("Parqueadero");
       esena.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
       primaryStage.setScene(esena);
       primaryStage.show();

   }

    private GridPane getIngresar(){

        GridPane gp_ingresar = new GridPane();
        gp_ingresar.getStyleClass().add("fondo");
        gp_ingresar.setGridLinesVisible(false);

        Label lb_matricula= new Label("Matricula:  ");
        TextField tf_matricula = new TextField();
        tf_matricula.setMinWidth(100);
        tf_matricula.setMaxWidth(100);
        Button btn_ingresar = new Button("Ingresar");

        btn_ingresar.setOnAction(event -> {

           if(tamano<10){

                String n_matricula = tf_matricula.getText().trim();
                n_matricula=n_matricula.replaceAll("\\s", "");

                boolean repetido = false;

                for(int i=0; i<tamano; i++){

                    if(n_matricula.equalsIgnoreCase(autos[i])){

                        repetido = true;

                    }

                }

                if(repetido==false){

                    if((!n_matricula.equalsIgnoreCase(""))&&(!n_matricula.isEmpty())){
                        
                        this.autos[this.tamano] = n_matricula;
                        this.tamano=this.tamano+1;
                        Actualizar(gp_ingresar, 0);
                        tab_sacar_vehiculo.setContent(getSacar());
                        

                        gp_ingresar.add(lb_matricula, 0,5);
                        gp_ingresar.add(tf_matricula, 1,5);
                        gp_ingresar.add(btn_ingresar, 2,5);
                        tf_matricula.clear();
        
                    }else{
                        Alert alerta = new Alert(AlertType.ERROR);
                        alerta.setContentText("La Matricula Está Vacia");
                        alerta.show();
                    }

                }else{
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setContentText("La Matricula Ya Está Registrada");
                    alerta.show();
                }
           }else{
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setContentText("Parqueadero Lleno");
            alerta.show();
        }

        });

        tf_matricula.clear();

        if(this.tamano>0)
        {

            Actualizar(gp_ingresar, 0);

        }else{
            gp_ingresar.getChildren().clear();
        }
        gp_ingresar.add(new Label(""), 0,2);
        gp_ingresar.add(new Label(""), 0,3);
        gp_ingresar.add(new Label(""), 0,4);
        gp_ingresar.add(lb_matricula, 0,5);
        gp_ingresar.add(tf_matricula, 1,5);
        gp_ingresar.add(btn_ingresar, 2,5);

        gp_ingresar.setHgap(20);
        gp_ingresar.setVgap(5);
        gp_ingresar.setPadding(new Insets(195, 10, 10, 10));

        return gp_ingresar;

    }

    private GridPane getSacar(){

        GridPane gp_sacar= new GridPane();
        gp_sacar.getStyleClass().add("fondo");
        gp_sacar.setGridLinesVisible(false);

        Label lb_matricula= new Label("Matricula:  ");
        TextField tf_matricula = new TextField();
        Button btn_sacar = new Button("Sacar");
        tf_matricula.setMinWidth(100);
        tf_matricula.setMaxWidth(100);
        TextArea salida = new TextArea();
        salida.setEditable(false);
        salida.minWidth(200);
        salida.setMinHeight(120);
        salida.setMaxHeight(125);

        btn_sacar.setOnAction(event -> {

            if(tamano!=0){

                String matricula = tf_matricula.getText();
                
                boolean encontrado=false;
                int pos =-1;
                String impresion="";

                for(int i=0; i<this.tamano; i++){
                    if(autos[i].equalsIgnoreCase(matricula)){

                        encontrado=true;
                        pos=i;

                    }
                }

                if(encontrado==true){


                    for(int i=(tamano-1); i>pos; i--){

                        impresion=impresion+"SALE EL AUTO EN LA POSICION "+(i+1)+" CON MATRICULA "+autos[i]+"\n";

                    }

                impresion=impresion+"AHORA PUEDE SALIR EL AUTO "+(pos+1)+" CON MATRICULA "+autos[pos]+"\n";

                    for(int i=pos+1; i<tamano; i++){

                        autos[i-1]=autos[i];
                        impresion=impresion+"INGRESA EL AUTO EN LA POSICION "+(i)+" CON MATRICULA "+autos[i]+"\n";

                    }

                    salida.clear();
                    salida.setText(impresion);

                    this.tamano=this.tamano-1;
                    if(tamano>0){

                        Actualizar(gp_sacar, 2);
                        tab_ingresar_vehiculo.setContent(getIngresar());

                        gp_sacar.add(salida, 0, 0,this.tamano+3,1);
                        gp_sacar.add(btn_sacar, 2, 3);
                        gp_sacar.add(lb_matricula, 0,3);
                        gp_sacar.add(tf_matricula, 1,3);
                        gp_sacar.setVgap(35);

                    }else{
                        
                        gp_sacar.getChildren().clear();
                        tab_ingresar_vehiculo.setContent(getIngresar());

                        gp_sacar.add(salida, 0, 0,this.tamano+3,1);
                        gp_sacar.add(btn_sacar, 2, 3);
                        gp_sacar.add(lb_matricula, 0,3);
                        gp_sacar.add(tf_matricula, 1,3);
                        gp_sacar.setVgap(35);
                    }
                    Alert alerta = new Alert(AlertType.CONFIRMATION);
                    alerta.setContentText("Auto Retirado Con Exito");
                    alerta.show();


                }else{
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setContentText("Matricula No Registrada");
                    alerta.show();
                }
                

            }else{
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setContentText("Parqueadero Vacio");
                alerta.show();
            }

        });
        
        if(this.tamano>0)
        {
            Actualizar(gp_sacar, 2);

        }else{
            gp_sacar.getChildren().clear();
        }
        gp_sacar.add(salida, 0, 0,this.tamano+3,1);
        gp_sacar.add(btn_sacar, 2, 3);
        gp_sacar.add(lb_matricula, 0,3);
        gp_sacar.add(tf_matricula, 1,3);
        gp_sacar.setVgap(35);

        return gp_sacar;

    }

    private void Actualizar(GridPane lista,int a) {

        lista.getChildren().clear();

        HBox parqueados = new HBox();

        for(int i=0; i<this.tamano; i++){
            
            VBox carros = new VBox();
            carros.setPadding(new Insets(1,10,10,10));
            Image imgauto = new Image("/imagenes/auto.png",75, 40, false, false);
            ImageView vewauto= new ImageView(imgauto);

            Label matriculas = new Label(autos[i]);
            matriculas.getStyleClass().add("matriculas");

            carros.getChildren().add(vewauto);
            carros.getChildren().add(matriculas);
            parqueados.getChildren().add(carros);
        }
        lista.add(parqueados, 0, a, this.tamano, 1);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
