package sample;

import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

//Area class
class Points {
     private ArrayList<Pair<Double,Double>> points = new ArrayList<>(4);
     private double area;
    Points() {
        points.add(0,new Pair<>(250d,250d));
        points.add(1,new Pair<>(250d,350d));
        points.add(2,new Pair<>(350d,350d));
        points.add(3,new Pair<>(350d,250d));
    }

    private double x1,x2,y1,y2;

    public void oldPoints(double x1,double y1) {
        this.x1 = x1;
        this.y1 = y1;
    }
    public void newPoints(double x2,double y2){
        this.x2 = x2;
        this.y2 = y2;
    }

    void change()
    {
        for(int i=0;i<4;i++)
        {
            if(Double.compare(points.get(i).getKey(),x1) == 0 && Double.compare(points.get(i).getValue(),y1) == 0)
            {
                points.set(i,new Pair<>(x2,y2));
                break;
            }
        }
    }

    double getX(int i)
    {
        return points.get(i).getKey();
    }
    double getY(int i)
    {
        return points.get(i).getValue();
    }

    void calculateArea()
    {
        float sum_but_no_result=0;
        for(int i=0;i<3;i++)
        {
            sum_but_no_result += points.get(i).getKey()*points.get(i+1).getValue() - points.get(i+1).getKey()*points.get(i).getValue();
        }
        sum_but_no_result += points.get(3).getKey()*points.get(0).getValue() - points.get(0).getKey()*points.get(3).getValue();

        float sum = (float)Math.abs(sum_but_no_result) / 2.0f;
        this.area = sum;
    }

    public double getArea() {
        return area;
    }
}
 class Length extends Points{
    private ArrayList<Double> lengths = new ArrayList<>();
    private ArrayList<Pair<Double,Double>> lengthpoints= new ArrayList<>();
    private double d1,d2;

    Length(){
        super();
        lengths.add(0,100d);
        lengths.add(1,100d);
        lengths.add(2,100d);
        lengths.add(3,100d);

        lengthpoints.add(0,new Pair<>(250d,300d));
        lengthpoints.add(1,new Pair<>(300d,350d));
        lengthpoints.add(2,new Pair<>(250d,300d));
        lengthpoints.add(3,new Pair<>(300d,250d));

        d1 = 100*Math.sqrt(2.0d);
        d2 = 100*Math.sqrt(2.0d);
    }

    void calculateLength()
    {
        double temp;
        double tempx,tempy;
        for(int i=0;i<3;i++) {
            temp = Math.sqrt((super.getX(i+1) - super.getX(i)) * (super.getX(i+1) - super.getX(i)) + (super.getY(i+1) - super.getY(i)) * (super.getY(i+1) - super.getY(i)));
            temp = ((double) ((int) (temp * 100.0))) / 100.0;
            lengths.set(i,temp);

            tempx =(super.getX(i+1) + super.getX(i))/2.0d;
            tempy =(super.getY(i+1)+super.getY(i))/2.0d;

            lengthpoints.set(i,new Pair<>(tempx,tempy));

        }
        temp = Math.sqrt((super.getX(0)-super.getX(3))*(super.getX(0)-super.getX(3)) + (super.getY(0)-super.getY(3))*(super.getY(0)-super.getY(3)));
        temp = ((double)((int)(temp*100.0)))/100.0;
        lengths.set(3,temp);

        tempx = (super.getX(0)+super.getX(3))/2.0d;
        tempy = (super.getY(0)+super.getY(3))/2.0d;
        lengthpoints.set(3,new Pair<>(tempx,tempy));

        d1 = Math.sqrt((super.getX(1)-super.getX(3))*(super.getX(1)-super.getX(3)) + (super.getY(1)-super.getY(3))*(super.getY(1)-super.getY(3)));
        d2 = Math.sqrt((super.getX(0)-super.getX(2))*(super.getX(0)-super.getX(2)) + (super.getY(0)-super.getY(2))*(super.getY(0)-super.getY(2)));

    }

    double getLength(int i){
        return lengths.get(i);
    }
    double getLengthx(int i){
        return lengthpoints.get(i).getKey();
    }
    double getLengthy(int i){
        return lengthpoints.get(i).getValue();
    }
    double getD1(){
        return d1;
    }
    double getD2(){
        return d2;
    }

 }
 class Angle extends Length{
    private ArrayList<Double> angles = new ArrayList<>();
    Angle(){
        super();
        angles.add(0,90.0d);
        angles.add(1,90.0d);
        angles.add(2,90.0d);
        angles.add(3,90.0d);
    }
    void calculateAngle(){

        double a0,a1,a2,a3;

        a0 = Math.acos(((super.getLength(0)*super.getLength(0)) + (super.getLength(3)*super.getLength(3)) - (super.getD1()*super.getD1()))/(2*super.getLength(0)*super.getLength(3)));
        a0 = (a0/Math.PI)*180;
        a0 = ((double)((int)(a0*100.0)))/100.0;

        a1 = Math.acos(((super.getLength(0)*super.getLength(0)) + (super.getLength(1)*super.getLength(1)) - (super.getD2()*super.getD2()))/(2*super.getLength(1)*super.getLength(0)));
        a1 = (a1/Math.PI)*180;
        a1 = ((double)((int)(a1*100.0)))/100.0;

        a2 = Math.acos(((super.getLength(1)*super.getLength(1)) + (super.getLength(2)*super.getLength(2)) - (super.getD1()*super.getD1()))/(2*super.getLength(2)*super.getLength(1)));
        a2 = (a2/Math.PI)*180;
        a2 = ((double)((int)(a2*100.0)))/100.0;

        a3 = Math.acos(((super.getLength(3)*super.getLength(3)) + (super.getLength(2)*super.getLength(2)) - (super.getD2()*super.getD2()))/(2*super.getLength(3)*super.getLength(2)));
        a3 = (a3/Math.PI)*180;
        a3 = ((double)((int)(a3*100.0)))/100.0;

        angles.add(0,a0);
        angles.add(1,a1);
        angles.add(2,a2);
        angles.add(3,a3);
//        System.out.println(a0+" "+a1+" "+a2+" "+a3);
    }
    public double getAngle(int i){
        return angles.get(i);
    }


 }

/** Drag the anchors around to change a polygon's points. */
public class Main extends Application {
    public static void main(String[] args) throws Exception { launch(args); }

    // main application layout logic.
    private Angle area = new Angle();
    private javafx.scene.control.Label areaLabel;
    private ArrayList<javafx.scene.control.Label> lengthLabels = new ArrayList<>(4);
    private ArrayList<javafx.scene.control.Label> angleLabels = new ArrayList<>(4);
    @Override public void start(final Stage stage) throws Exception {
        Polygon square = createStartingSquare();

        areaLabel = new javafx.scene.control.Label("Area = 10000.0");
        areaLabel.setTextFill(Color.rgb(176,48,176));
        areaLabel.setFont(Font.font("Courier", 20));
        areaLabel.setStyle("-fx-border-color:white; -fx-padding:10px;");

        javafx.scene.control.Label temp;
        for(int i = 0; i<4; i++) {
            temp = new javafx.scene.control.Label("100");
            temp.setTextFill(Color.rgb(176, 48, 176));
            temp.setFont(Font.font("Courier", 15));
            lengthLabels.add(i, temp);
        }

        for(int i=0;i<4;i++) {
            temp = new javafx.scene.control.Label("90\u00B0");
            temp.setTextFill(Color.rgb(176, 48, 176));
            temp.setFont(Font.font("Courier", 10));
            angleLabels.add(i, temp);
        }


        Group root = new Group();
        root.getChildren().addAll(areaLabel);
        root.getChildren().addAll(lengthLabels);
        root.getChildren().addAll(angleLabels);

        areaLabel.setLayoutX(25);
        areaLabel.setLayoutY(25);

        lengthLabels.get(0).setLayoutX(250); lengthLabels.get(0).setLayoutY(300);
        lengthLabels.get(1).setLayoutX(300); lengthLabels.get(1).setLayoutY(350);
        lengthLabels.get(2).setLayoutX(350); lengthLabels.get(2).setLayoutY(300);
        lengthLabels.get(3).setLayoutX(300); lengthLabels.get(3).setLayoutY(250);

        angleLabels.get(0).setLayoutX(252); angleLabels.get(0).setLayoutY(252);
        angleLabels.get(1).setLayoutX(252); angleLabels.get(1).setLayoutY(352);
        angleLabels.get(2).setLayoutX(352); angleLabels.get(2).setLayoutY(352);
        angleLabels.get(3).setLayoutX(352); angleLabels.get(3).setLayoutY(252);


        root.getChildren().add(square);

        root.getChildren().addAll(createControlAnchorsFor(square.getPoints()));

        stage.setTitle("Interactive Polygon Area Calculator");
        stage.setScene(new Scene(root,1080, 720, Color.rgb(32,32,64)));
        stage.show();
    }

    // creates a square.
    private Polygon createStartingSquare() {
        Polygon square = new Polygon();

        square.getPoints().setAll(
                250d, 250d,
                250d, 350d,
                350d, 350d,
                350d, 250d
        );

        square.setStroke(Color.rgb(96,32,128));
        square.setStrokeWidth(2);
        square.setStrokeLineCap(StrokeLineCap.ROUND);
        square.setFill(Color.rgb(32,32,85,0.3));

        return square;
    }

    // @return a list of anchors which can be dragged around to modify points in the format [x1, y1, x2, y2...]
    private ObservableList<Anchor> createControlAnchorsFor(final ObservableList<Double> points) {
        ObservableList<Anchor> anchors = FXCollections.observableArrayList();

        for (int i = 0; i < points.size(); i+=2) {
            final int idx = i;

            DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
            DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

            xProperty.addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {
                    points.set(idx, (double) x);
                }
            });


            yProperty.addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {
                    points.set(idx + 1, (double) y);
                }
            });

            anchors.add(new Anchor(Color.rgb(176,48,176), xProperty, yProperty));
        }


        return anchors;
    }

    // a draggable anchor displayed around a point.
    class Anchor extends Circle {
        private final DoubleProperty x, y;      //x and y are of observable type Double.

        Anchor(Color color, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 5);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(1);
            setStrokeType(StrokeType.OUTSIDE);

            this.x = x;
            this.y = y;

            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        // make a node movable by dragging it around with the mouse.
        private void enableDrag() {
            final Delta dragDelta = new Delta();
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    // record a delta distance for the drag and drop operation.
                    dragDelta.x = getCenterX() - mouseEvent.getX();
                    dragDelta.y = getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);

                    //for changing area
                    area.oldPoints(getCenterX(),getCenterY());
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                    //for changing area
                    area.newPoints(getCenterX(),getCenterY());

                    area.change();
                    area.calculateArea();
                    area.calculateLength();
                    area.calculateAngle();

                    //changing the layout
                    areaLabel.setText("Area = " + area.getArea());
                    for(int i=0;i<4;i++)
                    {
                        lengthLabels.get(i).setText(area.getLength(i)+"");
                        lengthLabels.get(i).setLayoutX(area.getLengthx(i));
                        lengthLabels.get(i).setLayoutY(area.getLengthy(i));

                        angleLabels.get(i).setText(area.getAngle(i)+"");
                        angleLabels.get(i).setLayoutX(area.getX(i)+2);
                        angleLabels.get(i).setLayoutY(area.getY(i)+2);
                    }

                }
            });
            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getX() + dragDelta.x;
                    if (newX > 0 && newX < getScene().getWidth()) {
                        setCenterX(newX);
                    }
                    double newY = mouseEvent.getY() + dragDelta.y;
                    if (newY > 0 && newY < getScene().getHeight()) {
                        setCenterY(newY);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.HAND);
                    }
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            });
        }

        // records RELATIVE x and y co-ordinates.
        private class Delta { double x, y; }
    }
}
