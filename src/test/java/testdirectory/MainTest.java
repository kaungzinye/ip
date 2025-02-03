package testdirectory;

import gojosatoru.GojoSatoru;
import gojosatoru.handlers.TaskHandler;
import gojosatoru.parser.Parser;
import gojosatoru.storage.Storage;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
  private static final String FILE_PATH = "./src/main/data/cursedEnergy.txt";
  private static final Ui UI = new Ui();

  private String inputDateFormat = "dd/MM/yyyy HHmm";
  private String outputDateFormat = "MMM dd yyyy HHmm";
  private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
  private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputDateFormat);
  private TaskHandler taskHandler = new TaskHandler(inputFormatter, outputFormatter, inputDateFormat);
  private Storage storage;
  private TaskList taskList;

  @BeforeEach
  void setUp() throws Exception {
    storage = new Storage(FILE_PATH, taskHandler, inputFormatter, outputFormatter);
    taskList = storage.load();
    taskList.clear();
    storage.save(taskList);
  }

  @Test
  void testAddTask() throws Exception {
    String input = "todo Buy groceries\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(1, taskList.size());
    assertEquals("Buy groceries", taskList.getTask(0).getTaskDescription());
  }

  @Test
  void testAddDeadline() throws Exception {
    String input = "deadline Project submission /by 12/12/2025 1800\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(1, taskList.size());
    assertEquals("Project submission", taskList.getTask(0).getTaskDescription());
  }

  @Test
  void testAddEvent() throws Exception {
    String input = "event Concert /from 10/10/2025 1900 /to 10/10/2025 2200\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(1, taskList.size());
    assertEquals("Concert", taskList.getTask(0).getTaskDescription());
  }

  @Test
  void testListTasks() throws Exception {
    String input = "todo Buy groceries\ndeadline Project submission /by 12/12/2025 1800\nevent Concert /from 10/10/2025 1900 /to 10/10/2025 2200\nlist\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(3, taskList.size());
  }

  @Test
  void testMarkTask() throws Exception {
    String input = "todo Buy groceries\nmark 1\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertTrue(taskList.getTask(0).isCompleted());
  }

  @Test
  void testUnmarkTask() throws Exception {
    String input = "todo Buy groceries\nmark 1\nunmark 1\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertFalse(taskList.getTask(0).isCompleted());
  }

  @Test
  void testDeleteTask() throws Exception {
    String input = "todo Buy groceries\ndeadline Project submission /by 12/12/2025 1800\ndelete 2\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(1, taskList.size());
    assertEquals("Buy groceries", taskList.getTask(0).getTaskDescription());
  }

  @Test
  void testInvalidTodo() throws Exception {
    String input = "todo\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(0, taskList.size());
  }

  @Test
  void testInvalidDeadline() throws Exception {
    String input = "deadline Submit report\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(0, taskList.size());
  }

  @Test
  void testInvalidEvent() throws Exception {
    String input = "event Birthday Party /to 10/10/2025 2200\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(0, taskList.size());
  }

  @Test
  void testMarkNonExistentTask() throws Exception {
    String input = "todo Buy groceries\nmark 10\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertFalse(taskList.getTask(0).isCompleted());
  }

  @Test
  void testDestroyWorld() throws Exception {
    String input = "destroy world\nbye";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    GojoSatoru.main(new String[]{});
    taskList = storage.load();
    assertEquals(0, taskList.size());
  }
}
