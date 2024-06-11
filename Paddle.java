import biuoop.DrawSurface;
import java.awt.Color;
import biuoop.KeyboardSensor;

public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle paddleShape;
    private final Color color;
    private int gameWidth;
    private int gameHeight;
    public static final double EPSILON = 0.001;

    public static boolean threshold(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    public Paddle(Rectangle paddleShape, Color color, KeyboardSensor keyboard,int gameWidth, int gameHeight) {
        this.paddleShape = paddleShape;
        this.color = color;
        this.keyboard = keyboard;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    public void moveLeft() {
        // Move the paddle left
        double newX = this.paddleShape.getUpperLeft().getX() - 5; // Move left by 5 pixels
        double newY = this.paddleShape.getUpperLeft().getY(); // Y coordinate remains the same
        if (newX < 10) {
            newX = gameWidth - 10 - paddleShape.getWidth(); // Set newX to the rightmost position
        }
        this.paddleShape = new Rectangle(new Point(newX, newY), this.paddleShape.getWidth(), this.paddleShape.getHeight());
    }

    public void moveRight() {
        // Move the paddle right
        double newX = this.paddleShape.getUpperLeft().getX() + 5; // Move right by 5 pixels
        double newY = this.paddleShape.getUpperLeft().getY(); // Y coordinate remains the same
        if (newX > gameWidth - 10 - paddleShape.getWidth()) {
            newX = 10; // Set newX to the leftmost position
        }
        this.paddleShape = new Rectangle(new Point(newX, newY), this.paddleShape.getWidth(), this.paddleShape.getHeight());
    }


    // Sprite
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddleShape.getUpperLeft().getX(),
                (int) this.paddleShape.getUpperLeft().getY(),
                (int) this.paddleShape.getWidth(),
                (int) this.paddleShape.getHeight());

        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.paddleShape.getUpperLeft().getX(),
                (int) this.paddleShape.getUpperLeft().getY(),
                (int) this.paddleShape.getWidth(),
                (int) this.paddleShape.getHeight());
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleShape;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double sectionWidth = this.paddleShape.getWidth() / 5;
        double paddleX = this.paddleShape.getUpperLeft().getX();
        double paddleY = this.paddleShape.getUpperLeft().getY();
        double paddleWidth = this.paddleShape.getWidth();
        double paddleHeight = this.paddleShape.getHeight();
        double hitX = collisionPoint.getX();
        double hitY = collisionPoint.getY();

        // Calculate which section of the paddle was hit
        int section = -1; // Default value if no section is found
        double currentSpeed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx() + currentVelocity.getDy() * currentVelocity.getDy());

        // Check if the collision point is within the y-range of the paddle
        if (hitY >= paddleY && hitY <= paddleY + paddleHeight) {
            section = (int) ((hitX - paddleX) / sectionWidth);
        }

        switch (section) {
            case 0:
                if (threshold(hitX, paddleX, EPSILON)) {
                    return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
                } else {
                    return Velocity.fromAngleAndSpeed(300, currentSpeed);
                }

            case 1:
                return Velocity.fromAngleAndSpeed(330, currentSpeed);


            case 2:
                if (threshold(paddleY, hitY, EPSILON)) {
                    return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
                }


            case 3:
                return Velocity.fromAngleAndSpeed(30, currentSpeed);


            case 4:
                if (threshold(paddleX + paddleWidth, hitX, EPSILON)) {
                    return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
                } else {
                    return Velocity.fromAngleAndSpeed(60, currentSpeed);
                }


            default:
                return currentVelocity;

        }


    }



    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
