package client.ChatUI;

import domain.Message;
import javafx.geometry.VPos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomListCell extends ListCell<Message> {
    private double width;
    public CustomListCell(double width) {
        this.width = width;
    }
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);

        Pane pane = null;
        if (!empty) {
            pane = new Pane();
            if (item.getReceiver()) {
                final Text leftText = new Text(item.getUsername() + ": " + item.getMsg());
                leftText.setFont(Font.font("STYLE_BOLD",16));
                //leftText.setFont(_itemFont);
                leftText.setTextOrigin(VPos.TOP);
                leftText.relocate(10, 0);
                pane.getChildren().add(leftText);
            }
            else
            {
                // right-aligned text at position 8em
                final Text rightText = new Text(item.getMsg());
                //rightText.setFont(_itemFont);
                rightText.setFont(Font.font(14));
                rightText.setTextOrigin(VPos.TOP);
                double text = rightText.getLayoutBounds().getWidth();
                rightText.relocate(width - text - 30, 0);
                pane.getChildren().add(rightText);
            }
        }
        setText("");
        setGraphic(pane);
    }
}
