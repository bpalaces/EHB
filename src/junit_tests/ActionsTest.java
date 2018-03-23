import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import junit.framework.TestCase;
import org.junit.Test;

import ehb.ActionTypes;
import ehb.Actions;
import simulation.engine.Engine;

import java.util.concurrent.atomic.AtomicBoolean;

public class ActionsTest extends TestCase
{
  // The sole responsibility of this class is to ensure that the
  // engine is initialized in a thread-safe way
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

    public RunTest() {
      _isComplete.set(true);
    }

    public void execute(BasicTest test) {
      _isComplete.set(false);
      Platform.runLater(() ->
      {
        test.executeTest();
        // Test has now executed on the JFX thread successfully
        _isComplete.set(true);
      });
    }

    public boolean isComplete()
    {
      return _isComplete.get();
    }
  }

  @Override
  protected void setUp() throws Exception {
    InitEngine init = new InitEngine();
    init.init();
    // Stall until the engine has finished starting up
    while (!init.isInitialized())
      ;
  }

  @Test
  public void testExecute()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      Actions actions = new Actions();
      new Thread(() -> Engine.main(new String[0])).start();
      actions.execute(ActionTypes.DISENGAGE_EHB);
      actions.execute(ActionTypes.FULLY_ENGAGE_EHB);
      actions.execute(ActionTypes.NO_OP);
      actions.execute(ActionTypes.PLAY_CONTINUOUS_ALERT);
      actions.execute(ActionTypes.PLAY_DISENGAGED_SOUND);
      actions.execute(ActionTypes.PLAY_ENGAGED_SOUND);
      actions.execute(ActionTypes.SET_COLOR_BLUE);
      actions.execute(ActionTypes.SET_COLOR_ORANGE);
      actions.execute(ActionTypes.SET_COLOR_RED);
      actions.execute(ActionTypes.UPDATE_APPLIED_FORCE);
    });
    // Stall until the test is complete
    while (!test.isComplete())
      ;
  }

}
