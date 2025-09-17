```
event boot camp /from 22/09/2025 /to 24/09/2025
```
Other supported features include:
* **List Tasks** :
```
* list
```
* **Track completion status of tasks** : `mark/unmark <task_number>`
```
mark 1
```
* **Delete Task **: `delete <task_number>`
* **Find Task**: `find <keyword>`
```
find book
```
* **Undo command**: `undo`
* **Exit**: `bye`

## Date Formats
The application supports the following date format: `dd/MM/yyyy` (e.g. 25/12/2025)

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   ├── nailong/
│   │   │   ├── Nailong.java          # Main application controller
│   │   │   ├── Parser.java           # Command parsing and validation
│   │   │   ├── TaskList.java         # Task collection management
│   │   │   ├── Storage.java          # File I/O operations
│   │   │   ├── Ui.java               # User interface messages
│   │   │   ├── CommandHistory.java   # Undo functionality
│   │   │   └── task/
│   │   │       ├── Task.java         # Abstract base task class
│   │   │       ├── Todo.java         # Simple task implementation
│   │   │       ├── Deadline.java     # Task with due date
│   │   │       └── Event.java        # Task with time period
│   │   └── ui/
│   │       ├── Main.java             # JavaFX application entry
│   │       ├── Launcher.java         # Application launcher
│   │       ├── MainWindow.java       # Primary GUI controller
│   │       └── DialogBox.java        # Chat bubble components
│   └── resources/
│       ├── view/
│       │   ├── MainWindow.fxml       # Main UI layout
│       │   └── DialogBox.fxml        # Chat bubble layout
│       ├── images/
│       │   ├── User.png              # User avatar
│       │   ├── Nailong.png           # Bot avatar
│       │   └── background.jpg        # Background image
│       └── styles/
│           ├── main.css              # Main application styles
│           └── dialog-box.css        # Chat bubble styles

data/
└── Nailong.txt                       # Task storage file (created at runtime)
```

## Development
### Building
```
./gradlew build
```
### Testing
```
./gradlew testing
```
### Code Style
The project uses **Checkstyle** for code quality. Run checks with:
```
./gradlew checkstyleMain
```

## Storage
All tasks are saved automatically in `./data/nailong.txt` in a human-readable format.  
The folder and file will be created for you on the first run if they do not already exist.

