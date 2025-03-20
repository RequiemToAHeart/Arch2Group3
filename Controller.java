import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        // Add action listener to the start button
        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                startSimulation();
            }
        });
    }

    private void startSimulation() {
        // Get user input
        int memoryBlocks = Integer.parseInt(view.getMemoryBlocks());
        String testCase = view.getTestCase();
    
        // Validate input
        if (memoryBlocks < 1024) {
            JOptionPane.showMessageDialog(view, "Number of memory blocks must be at least 1024.");
            return;
        }
    
        // Run simulation
        model.simulate(testCase, memoryBlocks);
    
        // Update View
        view.setLog("Simulation started with " + memoryBlocks + " memory blocks and test case: " + testCase);
        view.setCacheMemory(model.getCacheMemory());
        view.updateStatistics(model); // Update statistics with new metrics
    }
}
