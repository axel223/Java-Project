package sample;

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
import javafx.stage.Stage;

/** Drag the anchors around to change a polygon's points. */
public class Main extends Application {
    public static void main(String[] args) throws Exception { launch(args); }

    // main application layout logic.
    @Override public void start(final Stage stage) throws Exception {
        Polygon square = createStartingTriangle();

        Group root = new Group();
        root.getChildren().add(square);
        root.getChildren().addAll(createControlAnchorsFor(square.getPoints()));

        stage.setTitle("Java Project");
        stage.setScene(
                new Scene(
                        root,
                        600, 600, Color.ALICEBLUE
                )
        );
        stage.show();

    }

    // creates a square.
    private Polygon createStartingTriangle() {
        Polygon square = new Polygon();

        square.getPoints().setAll(
                100d, 100d,
                100d, 200d,
                200d, 200d,
                200d, 100d
        );

        square.setStroke(Color.FORESTGREEN);
        square.setStrokeWidth(2);
        square.setStrokeLineCap(StrokeLineCap.ROUND);
        square.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));

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

            anchors.add(new Anchor(Color.GOLD, xProperty, yProperty));
        }


        return anchors;
    }

    // a draggable anchor displayed around a point.
    class Anchor extends Circle {
        private final DoubleProperty x, y;

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

        // records relative x and y co-ordinates.
        private class Delta { double x, y; }
    }
}
