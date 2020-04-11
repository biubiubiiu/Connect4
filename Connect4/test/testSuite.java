import core.GameControlTest;
import core.JudgeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utils.ArchiveManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({GameControlTest.class, JudgeTest.class, ArchiveManagerTest.class})
public class testSuite {
}
