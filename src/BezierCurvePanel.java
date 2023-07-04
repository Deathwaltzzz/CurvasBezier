import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BezierCurvePanel extends JPanel {

    private final Point[] controlPoints;
    private double scalingFactor;

    public BezierCurvePanel(Point[] controlPoints, double scalingFactor) {
        this.controlPoints = controlPoints;
        this.scalingFactor = scalingFactor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke stroke = new BasicStroke(3.0f);
        g2d.setStroke(stroke);

        /*La formula matematica para la creacion de curvas de Bezier es la siguiente:
        *P = (1−t)^3P1 + 3(1−t)^2tP2 +3(1−t)t^2P3 + t^3P4
        * La t representa que tantas veces se creara un nuevo punto entre cada punto de control establecido por el usuario*/

        for (double t = 0; t <= 1; t += 0.001) {
            int x = (int) (scalingFactor * (Math.pow(1 - t, 3) * controlPoints[0].x +
                    3 * Math.pow(1 - t, 2) * t * controlPoints[1].x +
                    3 * (1 - t) * Math.pow(t, 2) * controlPoints[2].x +
                    Math.pow(t, 3) * controlPoints[3].x));

            int y = (int) (scalingFactor * (Math.pow(1 - t, 3) * controlPoints[0].y +
                    3 * Math.pow(1 - t, 2) * t * controlPoints[1].y +
                    3 * (1 - t) * Math.pow(t, 2) * controlPoints[2].y +
                    Math.pow(t, 3) * controlPoints[3].y));
            g2d.drawLine(x, y, x, y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Curvas de Bezier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JTextField xCoord1 = new JTextField("x"), yCoord1 = new JTextField("y"), xCoord2 = new JTextField("x"), yCoord2 = new JTextField("y"), xCoord3 = new JTextField("x"), yCoord3 = new JTextField("y"), xCoord4 = new JTextField("x"), yCoord4 = new JTextField("y");


        Object[] labels = {
                "Coordenadas del primer punto", xCoord1, yCoord1,
                "Coordenadas del 2do punto", xCoord2, yCoord2,
                "Coordenadas del tercer punto", xCoord3, yCoord3,
                "Coordenadas del cuarto punto", xCoord4, yCoord4
        };

        JOptionPane.showMessageDialog(null, labels);

        // Define control points
        Point[] controlPoints = {
                new Point(Integer.parseInt(xCoord1.getText()), Integer.parseInt(yCoord1.getText())),
                new Point(Integer.parseInt(xCoord2.getText()), Integer.parseInt(yCoord2.getText())),
                new Point(Integer.parseInt(xCoord3.getText()), Integer.parseInt(yCoord3.getText())),
                new Point(Integer.parseInt(xCoord4.getText()), Integer.parseInt(yCoord4.getText()))
        };
        JLabel label = new JLabel(String.format("Coordenadas: %s, %s, %n%s, %s, %n%s, %s, %n%s, %s",xCoord1.getText(), yCoord1.getText(), xCoord2.getText(),
                yCoord2.getText(),xCoord3.getText(),yCoord3.getText(),xCoord4.getText(),yCoord4.getText()));

        double factorDeEscala = 2.0; // Adjust the scaling factor as desired

        BezierCurvePanel bezierCurvePanel = new BezierCurvePanel(controlPoints, factorDeEscala);
        frame.setLayout(new BorderLayout());
        frame.add(bezierCurvePanel, BorderLayout.CENTER);
        frame.add(label,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}