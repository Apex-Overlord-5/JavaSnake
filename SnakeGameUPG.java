import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SnakeGame extends JFrame
{
    private Snake snake;
    private Timer timer;
    private int score;

    public SnakeGame()
    {
        setTitle("Java Project Work 12115752");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);


        setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Java Mini Project (Rubesh)");

        label1.setFont(new Font("Stencil", Font.PLAIN, 16));

        label1.setHorizontalAlignment(JLabel.CENTER);
        add(label1, BorderLayout.NORTH);

        snake = new Snake();
        add(snake);

        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                snake.processKey(e.getKeyCode());
            }
        });
        setFocusable(true);
        requestFocus();
        timer = new Timer(75, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                snake.move();
                if (snake.checkCollision())
                {
                    timer.stop();
                    JOptionPane.showMessageDialog(SnakeGame.this, "Game Over! Your Score is: " + score, "Game Over",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }

    private class Snake extends JPanel
    {
        private int[] x;
        private int[] y;
        private int length;
        private int direction;
        private int foodX;
        private int foodY;

        public Snake()
        {
            setPreferredSize(new Dimension(600, 400));
            setBackground(Color.BLACK);

            x = new int[400];
            y = new int[400];
            length = 3;
            direction = KeyEvent.VK_RIGHT;
            score = 0;

            for (int i = 0; i < length; i++)
            {
                x[i] = 200 - i * 10;
                y[i] = 200;
            }

            spawnFood();
        }

        public void move()
        {
            for (int i = length; i > 0; i--)
            {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction)
            {
                case KeyEvent.VK_UP:
                    y[0] -= 10;
                    break;
                case KeyEvent.VK_DOWN:
                    y[0] += 10;
                    break;
                case KeyEvent.VK_LEFT:
                    x[0] -= 10;
                    break;
                case KeyEvent.VK_RIGHT:
                    x[0] += 10;
                    break;
            }

            if (x[0] == foodX && y[0] == foodY)
            {
                length++;
                score++;
                spawnFood();
            }
            repaint();
        }

        public void processKey(int keyCode)
        {
            switch (keyCode)
            {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    direction = keyCode;
                    break;
            }
        }

        public boolean checkCollision()
        {
            for (int i = 1; i < length; i++)
            {
                if (x[i] == x[0] && y[i] == y[0])
                {
                    return true;
                }
            }

            if (x[0] < 0 || x[0] >= 600 || y[0] < 0 || y[0] >= 400)
            {
                return true;
            }

            return false;
        }

        private void spawnFood()
        {
            foodX = (int) (Math.random() * 59) * 10;
            foodY = (int) (Math.random() * 39) * 10;
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            g.setColor(Color.GREEN);
            g.fillRoundRect(foodX, foodY, 10, 10, 10, 10);

            g.setColor(Color.WHITE);
            for (int i = 0; i < length; i++)
            {
                g.fillRoundRect(x[i], y[i], 10, 10, 10, 10);
            }

            g.setColor(Color.RED);
            g.fillRoundRect(x[0], y[0], 10, 10, 10, 10);

            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 10, 20);
        }
    }
}
