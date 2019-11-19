package sample;

import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;

/** Drag the anchors around to change a polygon's points. */
public class Main extends Application {
    public static void main(String[] args) throws Exception { launch(args); }

    // main application layout logic.
    @Override public void start(final Stage stage) throws Exception {
        Polygon square = createStartingSquare();
        javafx.scene.control.Label l = new javafx.scene.control.Label("Area = ");
        javafx.scene.control.Button b = new javafx.scene.control.Button("Reset");
        l.setTextFill(Color.rgb(176,48,176));
        l.setFont(Font.font("Courier", 20));
        b.setCursor(Cursor.HAND);
        DropShadow shadow = new DropShadow();
        b.setEffect(shadow);
        b.setStyle("-fx-base: b030b0");


        Group root = new Group();
        root.getChildren().addAll(b,l);
        b.setLayoutX(10);
        b.setLayoutY(10);
        l.setLayoutX(80);
        l.setLayoutY(10);
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
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                    System.out.println(getCenterX()+","+getCenterY());
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

    class Coordinates {
        double x1=250,y1=250,x2=250,y2=350,x3=350,y3=350,x4=350,y4=250;
    }
}