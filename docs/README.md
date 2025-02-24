# GojoSatoru Task Manager

GojoSatoru is a task management application that helps you keep track of your tasks, deadlines, and events. It provides a command-line interface for adding, listing, marking, unmarking, deleting, and finding tasks.

## Features

- Add tasks (ToDo, Deadline, Event)
- List all tasks
- Mark tasks as completed
- Unmark tasks as not completed
- Delete tasks
- Find tasks by keyword
- Handle duplicate tasks

## Getting Started

### Prerequisites

- Java 17 Azul
- Gradle

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/kaungzinye/gojosatoru.git
   cd gojosatoru
   ```

2. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```

### Usage

1. Run the application:
   ```sh
   ./gradlew run
   ```

2. Use the following commands to interact with the application:

    - Add a ToDo task:
      ```sh
      todo <task description>
      ```

    - Add a Deadline task:
      ```sh
      deadline <task description> /by <dd/MM/yyyy HHmm>
      ```

    - Add an Event task:
      ```sh
      event <task description> /from <dd/MM/yyyy HHmm> /to <dd/MM/yyyy HHmm>
      ```

    - List all tasks:
      ```sh
      list
      ```

    - Mark a task as completed:
      ```sh
      mark <task number>
      ```

    - Unmark a task as not completed:
      ```sh
      unmark <task number>
      ```

    - Delete a task:
      ```sh
      delete <task number>
      ```

    - Find tasks by keyword:
      ```sh
      find <keyword>
      ```

    - Exit the application:
      ```sh
      bye
      ```

### Example

```sh
todo read book
deadline submit assignment /by 25/12/2023 2359
event project meeting /from 01/12/2023 1000 /to 01/12/2023 1200
list
mark 1
unmark 1
delete 2
deadline change codebase /by 25/12/2001 2059
event extra /from  23/1/2000 1000 /to 01/12/2010 1301
find book
bye
```

## Acknowledgements

This project was developed with the assistance of GitHub Copilot, which was used to auto-complete functions and write documentation.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.