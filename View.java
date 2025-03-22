import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JTextField memoryBlocksField;
    private JComboBox<String> testCaseComboBox;
    private JButton startButton;
    private JButton toggleAnimationButton;
    private JTextArea logArea;
    private JTable cacheTable;
    private JLabel statsLabel;

    public View() {
        setTitle("Cache Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Number of Memory Blocks:"));
        memoryBlocksField = new JTextField();
        inputPanel.add(memoryBlocksField);
        inputPanel.add(new JLabel("Test Case:"));
        testCaseComboBox = new JComboBox<>(new String[]{"Sequential Sequence", "Random Sequence", "Mid-Repeat Blocks"});
        inputPanel.add(testCaseComboBox);
        startButton = new JButton("Start Simulation");
        inputPanel.add(startButton);
        toggleAnimationButton = new JButton("Toggle Animation");
        inputPanel.add(toggleAnimationButton);
        add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea();
        logArea.setEditable(false);
        outputPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        String[] columnNames = {"Block", "Data"};
        Object[][] data = new Object[32][2]; 
        cacheTable = new JTable(data, columnNames);
        outputPanel.add(new JScrollPane(cacheTable), BorderLayout.WEST);
    

        statsLabel = new JLabel("Statistics will appear here.");
        outputPanel.add(statsLabel, BorderLayout.SOUTH);

        add(outputPanel, BorderLayout.CENTER);
    }

    public void setLog(String log) {
        logArea.append(log + "\n");
    }

    public void setCacheMemory(String[][] cacheMemory) {
        for (int i = 0; i < cacheMemory.length; i++) {
            cacheTable.setValueAt(cacheMemory[i][0], i, 0);
            cacheTable.setValueAt(cacheMemory[i][1], i, 1);
        }
    }

    public void setStatistics(String stats) {
        statsLabel.setText(stats);
    }

    public String getMemoryBlocks() {
        return memoryBlocksField.getText();
    }

    public String getTestCase() {
        return (String) testCaseComboBox.getSelectedItem();
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addToggleAnimationButtonListener(ActionListener listener) {
        toggleAnimationButton.addActionListener(listener);
    }

    public void updateStatistics(Model model) {
        String stats = "<html>"
                + "Memory Access Count: " + model.getMemoryAccessCount() + "<br>"
                + "Cache Hit Count: " + model.getCacheHits() + "<br>"
                + "Cache Miss Count: " + model.getCacheMisses() + "<br>"
                + "Cache Hit Rate: " + String.format("%.2f", model.getCacheHitRate() * 100) + "%<br>"
                + "Cache Miss Rate: " + String.format("%.2f", model.getCacheMissRate() * 100) + "%<br>"
                + "Average Memory Access Time: " + String.format("%.2f", model.getAverageMemoryAccessTime()) + " ns<br>"
                + "Total Memory Access Time: " + String.format("%.2f", model.getTotalMemoryAccessTime()) + " ns<br>"
                + "</html>";
        setStatistics(stats);
    }
}