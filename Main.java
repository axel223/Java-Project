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
    private ArrayList<Double> x = new ArrayList<>(4);
    private ArrayList<Double> y = new ArrayList<>(4);
    Area() {
        x.add(0, 250d); y.add(0, 250d);
        x.add(1, 250d); y.add(1, 350d);
        x.add(2, 350d); y.add(2, 350d);
        x.add(3, 350d); y.add(3, 250d);
    }
    double x1,x2,y1,y2;

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
 }
/** Drag the anchors around to change a polygon's points. */
public class Main extends Application {
    public static void main(String[] args) throws Exception { launch(args); }

    // main application layout logic.
    Area area = new Area();
    javafx.scene.control.Label l;
    @Override public void start(final Stage stage) throws Exception {
        Polygon square = createStartingSquare();
        l = new javafx.scene.control.Label("Area = 10000.0");
        l.setTextFill(Color.rgb(176,48,176));
        l.setFont(Font.font("Courier", 20));
        DropShadow shadow = new DropShadow();

        Group root = new Group();
        root.getChildren().addAll(l);
        l.setLayoutX(25);
        l.setLayoutY(550);
        root.getChildren().add(square);
        root.getChildren().addAll(createControlAnchorsFor(square.getPoints()));

        stage.setTitle("Interactive Polygon Area Calculator");
        stage.setScene(new Scene(root,600, 600, Color.rgb(32,32,64)));
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
        square.setFill(Color.rgb(32,32,85));

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
            super(x.get(), y.get(), 7);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
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
