# Toy Language Interpreter

A functional programming language interpreter built from scratch using **Java**. This project simulates the execution of a custom "Toy Language," featuring its own memory management system, concurrent execution capabilities, and a graphical interface for state monitoring.

## 🌟 Key Features

* **Execution Stack & Tables**: Implements core architectural components including an Execution Stack, Symbol Table, and Output List.
* **Concurrency Support**: Advanced multi-threading capabilities allowing the interpreter to manage a shared global heap across multiple concurrent threads while ensuring memory integrity.
* **Memory Management**: Features a custom **Heap** and a **Garbage Collector** mechanism that automatically cleans up unreferenced variables.
* **File Operations**: Support for file handles (open, read, close) within the toy language scripts.
* **Advanced ADTs**: Built using Java Generics and design patterns like **Command** and **Repository** for high performance and type safety.
* **Asynchronous Logging**: Includes an asynchronous logger to track `PrgState` transitions and execution steps into external log files without blocking the main execution thread.

## 🛠️ Technical Stack

* **Language**: Java
* **GUI Framework**: JavaFX (for the visual interpreter dashboard)
* **Design Patterns**: Model-View-Controller (MVC), Command, Repository, Strategy.
* **Concepts**: Multi-threading, Garbage Collection, Generics, Synchronization Primitives.

## 📐 Architecture & Logic

The interpreter processes programs through several stages of abstraction:

1.  **Statement Execution**: Each statement (e.g., `If`, `While`, `Fork`, `Assign`) modifies the Program State.
2.  **Concurrency (`Fork`)**: The `fork` statement creates a new thread with its own stack but shared access to the Heap and File Table.
3.  **Type Checking**: A static type checker ensures program validity before execution begins to prevent runtime errors.



## 🖥️ Graphical User Interface

The application includes a **JavaFX dashboard** that provides a real-time view of the interpreter's internal state:
* List of all active Program Identifiers.
* Visual representation of the **Heap Table** and **Symbol Table**.
* The current **Execution Stack** for the selected thread.
* The **File Table** and **Output** console.
