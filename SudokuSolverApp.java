import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SudokuSolverApp {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton;
    private JButton resetButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverApp::new);
    }

    public SudokuSolverApp() {
        JFrame frame = new JFrame("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = createValidatedTextField();
                gridPanel.add(cells[i][j]);
            }
        }

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Solve button
        solveButton = new JButton("Solve");
        styleButton(solveButton);
        solveButton.addActionListener(e -> solveBoard());

        // Reset button
        resetButton = new JButton("Reset");
        styleButton(resetButton);
        resetButton.addActionListener(e -> resetBoard());

        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(500, 550);
        frame.setMinimumSize(new Dimension(350, 400));

        // Dynamically adjust fonts and button sizes on resize
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int cellSize = Math.min(frame.getWidth(), frame.getHeight()) / SIZE;
                int fontSize = Math.max(12, cellSize / 3);
                Font cellFont = new Font("SansSerif", Font.BOLD, fontSize);
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        cells[i][j].setFont(cellFont);
                    }
                }

                int buttonFontSize = Math.max(16, frame.getWidth() / 30);
                Font buttonFont = new Font("SansSerif", Font.BOLD, buttonFontSize);
                solveButton.setFont(buttonFont);
                resetButton.setFont(buttonFont);

                int buttonWidth = frame.getWidth() / 4;
                int buttonHeight = 60;
                solveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                resetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                buttonPanel.revalidate();
            }
        });

        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 128, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
    }

    private JTextField createValidatedTextField() {
        JTextField field = new JTextField();
        field.setHorizontalAlignment(JTextField.CENTER);

        // Allow only one digit (1–9)
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text != null && text.matches("[1-9]?")) {
                    fb.replace(offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string != null && string.matches("[1-9]?")) {
                    fb.insertString(offset, string, attr);
                }
            }
        });

        return field;
    }

    private void solveBoard() {
        int[][] board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String text = cells[i][j].getText();
                board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        if (solve(board)) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No solution exists!");
        }
    }

    private void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j].setText("");
            }
        }
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) return false;
            }
        }
        return true;
    }
}
