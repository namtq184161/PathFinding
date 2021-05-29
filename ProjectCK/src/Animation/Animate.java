package Animation;

import java.util.List;

import Containers.GraphPanel;
import Containers.SpeedControlPane;
import GraphFX.EdgeLine;
import GraphFX.VertexFX;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Animate {
    private static boolean isInited = false;
    private static DoubleProperty speed = null;

    public static void playAnimation(GraphPanel graphview, Animation animation, List<VertexFX> result) {
        playAnimation(graphview, animation, result != null);
    }

    public static void playAnimation(GraphPanel graphview, Animation animation, boolean isShortestPathFound) {
        graphview.setDisable(true);
        
        String message1;
        if (isShortestPathFound) message1 = "Found shortest path !";
        else message1 = "Cannot find shortest path !";
        
        animation.setOnFinished(evt -> {
            graphview.setDisable(false);
            Alert alert = new Alert(isShortestPathFound ? AlertType.INFORMATION : AlertType.WARNING);
            alert.setTitle("Result");
            alert.setHeaderText("Results :");
            alert.setContentText(message1);
            Platform.runLater(alert::showAndWait);
        });

        animation.play();
    }

    public static void setupAnimation() {
        speed = new SimpleDoubleProperty();
        speed.bind(SpeedControlPane.speed);
        isInited = true;
    }

    public static SequentialTransition makeAnimation(List<EdgeLine> steps, List<VertexFX> result) {
        if (isInited) {
            SequentialTransition listAnimation = new SequentialTransition();
            
            listAnimation.getChildren().add(makeAnimationSteps(steps));
            if (result != null) listAnimation.getChildren().add(makeAnimationResult(result));

            return listAnimation;
        } else {
            System.out.println("Animations has not been setup yet, automatically setup by default.");
            setupAnimation();
            return makeAnimation(steps, result);
        }
    }

    public static SequentialTransition makeAnimationSteps(List<EdgeLine> steps) {           // Making animations for steps
        SequentialTransition listAnimation = new SequentialTransition();
        for (int i = 0; i < steps.size(); i++) {
            setAnimateBeingVisited(listAnimation, steps.get(i));
            setAnimateBeingVisited(listAnimation, steps.get(i).endVertex);
        }
        return listAnimation;
    }

    public static SequentialTransition makeAnimationResult(List<VertexFX> result) {         // Making animations for result
        SequentialTransition listAnimation = new SequentialTransition();
        GraphPanel graphview;
        EdgeLine tmpEdge;

        setAnimateVertexInShortestPath(listAnimation, result.get(0));
        graphview = result.get(0).getContainingGP();
        for (int i = 1; i < result.size(); i++) {
            tmpEdge = graphview.getEdge(result.get(i-1), result.get(i));
            setAnimateEdgeInShortestPath(listAnimation, tmpEdge);
            setAnimateVertexInShortestPath(listAnimation, result.get(i));
        }
        return listAnimation;
    }

    public static SequentialTransition finalPath(List<VertexFX> nodes) {
        return null;
    }

    private static void setAnimateBeingVisited(SequentialTransition listAnimation, EdgeLine edge) {                     //Animation for Step : Edges + Arrow
        listAnimation.getChildren().add(animateEdgeBeingVisited(edge));
        listAnimation.getChildren().add(animateArrowBeingVisited(edge));
    }

    public static void setAnimateBeingVisited(SequentialTransition listAnimation, VertexFX vertex) {                    //Animation for Step : Vertices
        listAnimation.getChildren().add(animateBeingVisited(vertex));
    }

    public static void setAnimateVertexInShortestPath(SequentialTransition listAnimation, VertexFX vertex) {            //Animation for Result : Vertices
        listAnimation.getChildren().add(animateVertexInShortestPath(vertex));
    }

    public static void setAnimateEdgeInShortestPath(SequentialTransition listAnimation, EdgeLine edge) {                //Animation for Result : Edges + Arrow
        listAnimation.getChildren().add(animateEdgeInShortestPath(edge));
        listAnimation.getChildren().add(animateArrowInShortestPath(edge));
    }

    public static Animation animateBeingVisited(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(77, 148, 255));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateVertexDoneVisited(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(255, 194, 179));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateVertexInShortestPath(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(255, 51, 0));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateEdgeBeingVisited(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()));
        stroke.setToValue(Color.rgb(0, 179, 60));
        stroke.setShape(edge);

        return stroke;
    }

    public static Animation animateEdgeInShortestPath(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/3));
        stroke.setToValue(Color.rgb(255, 26, 26));
        stroke.setShape(edge);

        return stroke;
    }

    private static Animation animateArrowBeingVisited(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/5));
        stroke.setToValue(Color.rgb(0, 179, 60));
        stroke.setShape(edge.getAttachedArrow());

        return stroke;
    }

    private static Animation animateArrowInShortestPath(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/15));
        stroke.setToValue(Color.rgb(255, 26, 26));
        stroke.setShape(edge.getAttachedArrow());

        return stroke;
    }
}