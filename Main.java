package sample;

import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

//Area class
class Area {
     ArrayList<Double> x = new ArrayList<>(4);
     ArrayList<Double> y = new ArrayList<>(4);
    Area() {
        x.add(0, 250d); y.add(0, 250d);
        x.add(1, 250d); y.add(1, 350d);
        x.add(2, 350d); y.add(2, 350d);
        x.add(3, 350d); y.add(3, 250d);
    }
    double x1,x2,y1,y2;
    double l1,l1x,l1y,l2,l2x,l2y,l3,l3x,l3y,l4,l4x,l4y;
    double d1,d2;
    double a1,a2,a3,a4;
    void change()
    {
        for(int i=0;i<4;i++)
        {
            if(Double.compare(x.get(i),x1) == 0 && Double.compare(y.get(i),y1) == 0)
            {
                x.set(i,x2);
                y.set(i,y2);
                break;
            }
        }
    }
    void restart()
    {
        //set all the co-ordinates to default position
        x.set(0, 250d); y.set(0, 250d);
        x.set(1, 250d); y.set(1, 350d);
        x.set(2, 350d); y.set(2, 350d);
        x.set(3, 350d); y.set(3, 250d);
    }
    double totalarea()
    {
        float sum_but_no_result=0;

        sum_but_no_result += x.get(0)*y.get(1) - x.get(1)*y.get(0);
        sum_but_no_result += x.get(1)*y.get(2) - x.get(2)*y.get(1);
        sum_but_no_result += x.get(2)*y.get(3) - x.get(3)*y.get(2);
        sum_but_no_result += x.get(3)*y.get(0) - x.get(0)*y.get(3);

        float sum = (float)Math.abs(sum_but_no_result) / 2.0f;
        return sum;
    }
    void lengths()
    {


        l1 = Math.sqrt((x.get(1)-x.get(0))*(x.get(1)-x.get(0)) + (y.get(1)-y.get(0))*(y.get(1)-y.get(0)));
        l1 = ((double)((int)(l1*100.0)))/100.0;
        l2 = Math.sqrt((x.get(2)-x.get(1))*(x.get(2)-x.get(1)) + (y.get(2)-y.get(1))*(y.get(2)-y.get(1)));
        l2 = ((double)((int)(l2*100.0)))/100.0;
        l3 = Math.sqrt((x.get(3)-x.get(2))*(x.get(3)-x.get(2)) + (y.get(3)-y.get(2))*(y.get(3)-y.get(2)));
        l3 = ((double)((int)(l3*100.0)))/100.0;
        l4 = Math.sqrt((x.get(0)-x.get(3))*(x.get(0)-x.get(3)) + (y.get(0)-y.get(3))*(y.get(0)-y.get(3)));
        l4 = ((double)((int)(l4*100.0)))/100.0;

        d1 = Math.sqrt((x.get(0)-x.get(2))*(x.get(0)-x.get(2)) + (y.get(0)-y.get(2))*(y.get(0)-y.get(2)));
        d2 = Math.sqrt((x.get(1)-x.get(3))*(x.get(1)-x.get(3)) + (y.get(1)-y.get(3))*(y.get(1)-y.get(3)));

        l1x = (x.get(1) + x.get(0)) / 2.0d;
        l2x = (x.get(2) + x.get(1)) / 2.0d;
        l3x = (x.get(3) + x.get(2)) / 2.0d;
        l4x = (x.get(0) + x.get(3)) / 2.0d;

        l1y = (y.get(1) + y.get(0)) / 2.0d;
        l2y = (y.get(2) + y.get(1)) / 2.0d;
        l3y = (y.get(3) + y.get(2)) / 2.0d;
        l4y = (y.get(0) + y.get(3)) / 2.0d;

        a1 = Math.acos(((l1*l1) + (l4*l4) - (d2*d2))/(2*l1*l4));
        a1 = (a1/Math.PI)*180;
        a1 = ((double)((int)(a1*100.0)))/100.0;
        a2 = Math.acos(((l1*l1) + (l2*l2) - (d1*d1))/(2*l1*l2));
        a2 = (a2/Math.PI)*180;
        a2 = ((double)((int)(a2*100.0)))/100.0;
        a3 = Math.acos(((l2*l2) + (l3*l3) - (d2*d2))/(2*l2*l3));
        a3 = (a3/Math.PI)*180;
        a3 = ((double)((int)(a3*100.0)))/100.0;
        a4 = Math.acos(((l3*l3) + (l4*l4) - (d1*d1))/(2*l3*l4));
        a4 = (a4/Math.PI)*180;
        a4 = ((double)((int)(a4*100.0)))/100.0;

//        System.out.println(d1 +" "+ d2);
        System.out.println(a1 +" "+a2 +" "+a3+" "+a4);
    }

 }
/** Drag the anchors around to change a polygon's points. */
public class Main extends Application {
    public static void main(String[] args) throws Exception { launch(args); }

    // main application layout logic.
    Area area = new Area();
    javafx.scene.control.Label l,l1,l2,l3,l4,a1,a2,a3,a4;
    @Override public void start(final Stage stage) throws Exception {
        Polygon square = createStartingSquare();
        l = new javafx.scene.control.Label("Area = 10000.0");
        l.setTextFill(Color.rgb(176,48,176));
        l.setFont(Font.font("Courier", 20));

        l1 = new javafx.scene.control.Label("100");
        l1.setTextFill(Color.rgb(176,48,176));
        l1.setFont(Font.font("Courier", 15));

        l2 = new javafx.scene.control.Label("100");
        l2.setTextFill(Color.rgb(176,48,176));
        l2.setFont(Font.font("Courier", 15));

        l3 = new javafx.scene.control.Label("100");
        l3.setTextFill(Color.rgb(176,48,176));
        l3.setFont(Font.font("Courier", 15));

        l4 = new javafx.scene.control.Label("100");
        l4.setTextFill(Color.rgb(176,48,176));
        l4.setFont(Font.font("Courier", 15));

        a1 = new javafx.scene.control.Label("90\u00B0");
        a1.setTextFill(Color.rgb(176,48,176));
        a1.setFont(Font.font("Courier", 10));

        a2 = new javafx.scene.control.Label("90\u00B0");
        a2.setTextFill(Color.rgb(176,48,176));
        a2.setFont(Font.font("Courier", 10));

        a3 = new javafx.scene.control.Label("90\u00B0");
        a3.setTextFill(Color.rgb(176,48,176));
        a3.setFont(Font.font("Courier", 10));

        a4 = new javafx.scene.control.Label("90\u00B0");
        a4.setTextFill(Color.rgb(176,48,176));
        a4.setFont(Font.font("Courier", 10));

        DropShadow shadow = new DropShadow();

        Group root = new Group();
        root.getChildren().addAll(l);
        root.getChildren().addAll(l1,l2,l3,l4);
        root.getChildren().addAll(a1,a2,a3,a4);

        l.setLayoutX(25);
        l.setLayoutY(25);

        l1.setLayoutX(250); l1.setLayoutY(300);
        l2.setLayoutX(300); l2.setLayoutY(350);
        l3.setLayoutX(350); l3.setLayoutY(300);
        l4.setLayoutX(300); l4.setLayoutY(250);

        a1.setLayoutX(252); a1.setLayoutY(252);
        a2.setLayoutX(252); a2.setLayoutY(352);
        a3.setLayoutX(352); a3.setLayoutY(352);
        a4.setLayoutX(352); a4.setLayoutY(252);


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
                    area.x1 = getCenterX();
                    area.y1 = getCenterY();
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                    //for changing area
                    area.x2 = getCenterX();
                    area.y2 = getCenterY();
                    area.change();
                    //changing the layout
                    l.setText("Area = " + area.totalarea());
                    area.lengths();
                    l1.setText(area.l1+"");
                    l1.setLayoutX(area.l1x);
                    l1.setLayoutY(area.l1y);

                    l2.setText(area.l2+"");
                    l2.setLayoutX(area.l2x);
                    l2.setLayoutY(area.l2y);

                    l3.setText(area.l3+"");
                    l3.setLayoutX(area.l3x);
                    l3.setLayoutY(area.l3y);

                    l4.setText(area.l4+"");
                    l4.setLayoutX(area.l4x);
                    l4.setLayoutY(area.l4y);

                    a1.setText(area.a1+"\u00B0");
                    a1.setLayoutX(area.x.get(0)+2);
                    a1.setLayoutY(area.y.get(0)+2);

                    a2.setText(area.a2+"\u00B0");
                    a2.setLayoutX(area.x.get(1)+2);
                    a2.setLayoutY(area.y.get(1)+2);

                    a3.setText(area.a3+"\u00B0");
                    a3.setLayoutX(area.x.get(2)+2);
                    a3.setLayoutY(area.y.get(2)+2);

                    a4.setText(area.a4+"\u00B0");
                    a4.setLayoutX(area.x.get(3)+2);
                    a4.setLayoutY(area.y.get(3)+2);

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
