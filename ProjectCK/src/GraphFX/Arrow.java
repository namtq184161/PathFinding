package GraphFX;

import Interfaces.StylableNode;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Arrow extends Path implements StylableNode{
    private final StyleProxy styleProxy;

    public Arrow(int size) {
        /* arrow shape */
        getElements().add(new MoveTo(0, 0));  
        getElements().add(new LineTo(-size, size));
        getElements().add(new MoveTo(0, 0));
        getElements().add(new LineTo(-size, -size));

        styleProxy = new StyleProxy(this);
        styleProxy.addStyleClass("arrow");
    }

    @Override
    public void setStyleClass(String cssClass) {
        styleProxy.setStyleClass(cssClass);
    }

    @Override
    public void addStyleClass(String cssClass) {
        styleProxy.addStyleClass(cssClass);
    }

    @Override
    public boolean removeStyleClass(String cssClass) {
        return styleProxy.removeStyleClass(cssClass);
    }
}