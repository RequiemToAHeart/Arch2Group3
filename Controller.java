import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class Controller {
    private Model model;
    private View view;
    private boolean isStepByStepAnimation = false;
    private int currentStep = 0;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        view.addToggleAnimationButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAnimation();
            }
        });
    }

    private void startSimulation() {
        int memoryBlocks;
        try {
            memoryBlocks = Integer.parseInt(view.getMemoryBlocks());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid number format for memory blocks!");
            return;
        }

        // Enforce minimum memoryBlocks of 1024
        if (memoryBlocks < 1024) {
            JOptionPane.showMessageDialog(view, "Number of memory blocks must be at least 1024.");
            return;
        }

        String testCase = view.getTestCase();

        model.simulate(testCase, memoryBlocks);

        view.updateStatistics(model);
        view.setLog("Simulation started with " + memoryBlocks + " memory blocks (" + testCase + ")");
        
        if (isStepByStepAnimation) {
            currentStep = 0;
            showStepByStep();
        } else {
            showFinalSnapshot();
        }
    }

    private void toggleAnimation() {
        isStepByStepAnimation = !isStepByStepAnimation;
        view.setLog("Step-by-step animation " + (isStepByStepAnimation ? "enabled" : "disabled"));
    }

    private void showStepByStep() {
        List<String[][]> snapshots = model.getCacheMemorySnapshots();
        if (currentStep < snapshots.size()) {
            view.setCacheMemory(snapshots.get(currentStep));
            view.setLog(model.getCacheMemoryTraceLog().get(currentStep));
            currentStep++;
        } else {
            view.setLog("End of simulation.");
        }
    }
    
    private void showFinalSnapshot() {
        // Ensure cacheMemory is synchronized with lruCache
        model.synchronizeCacheMemory();
        
        // Display the final snapshot
        view.setCacheMemory(model.getCacheMemory());
        view.setLog("Final cache memory snapshot.");
        for (String logEntry : model.getCacheMemoryTraceLog()) {
            view.setLog(logEntry);
        }
    }
}