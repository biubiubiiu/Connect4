import core.GameControlTest;
import core.JudgeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utils.ArchiveManager;

@RunWith(Suite.class)
@Suite.SuiteClasses({GameControlTest.class, JudgeTest.class, ArchiveManager.class})
public class testSuite {
}
