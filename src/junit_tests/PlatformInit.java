package junit_tests;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import simulation.engine.Engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class InitEngine {
  private AtomicBoolean _isInitialized = new AtomicBoolean();

  public InitEngine() {
    _isInitialized.set(false);
   }

  public void init() {
    // This forces the JavaFX runtime to initialize itself - it's a hack
    JFXPanel panel = new JFXPanel();
    // Platform sets it up to run on the JavaFX event dispatch thread
    Platform.runLater(() ->
    {
      new Engine().start(new Stage());
      _isInitialized.set(true);
    });
  }

  public boolean isInitialized() {
    return _isInitialized.get();
  }
}

// This interface only requires that one simple method be implemented
interface BasicTest {
  void executeTest();
}

// Forces a test to run on the JavaFX thread and provides a thread-safe
// means of checking if it is done
class RunTest {
  private AtomicBoolean _isComplete = new AtomicBoolean();
  private boolean _encounteredError;

  public RunTest() {
    _isComplete.set(true);
  }

  public void execute(BasicTest test) {
    _isComplete.set(false);
    _encounteredError = false;
    Platform.runLater(() ->
    {
      try {
        test.executeTest();
        // Test has now executed on the JFX thread successfully
      }
      catch (Error e) {
        // If this happens then the JUnit test failed
        _encounteredError = true;
      }
      finally {
        _isComplete.set(true);
      }
    });
    // Stall until complete
    while (!isComplete())
      ;
    assertEquals(_encounteredError, false);
  }

  public boolean isComplete()
  {
    return _isComplete.get();
  }
}
