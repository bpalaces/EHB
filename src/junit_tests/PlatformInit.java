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
    // Stall until initialized
    while (!isInitialized())
      ;
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
  private String _errorMessage;
  private StackTraceElement[] _errorStack;

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
      // Error class is specific to JUnit it seems
      catch (Error | Exception e) {
        // If this happens then the JUnit test failed
        _encounteredError = true;
        _errorMessage = e.getMessage();
        _errorStack = e.getStackTrace();
      }
      finally {
        _isComplete.set(true);
      }
    });
    // Stall until complete
    while (!isComplete())
      ;
    if (_encounteredError) {
      Error e = new Error(_errorMessage);
      e.setStackTrace(_errorStack);
      throw e;
    }
  }

  public boolean isComplete()
  {
    return _isComplete.get();
  }
}
