package com.example.todoapi.todo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController // This means that this class is a Controller
@RequestMapping("/api/v1") // This means URL's start with /api/v1 (after Application path)
public class todoController {

    private static List<todo> todolist; // List of todos

    // Constructor
    public todoController() { // Initialize the list of todos
        todolist = new ArrayList<>();
        todolist.add(new todo(1, 1, "Todo 1", false));
        todolist.add(new todo(2, 2, "Todo 2", true));
    }
    
    // Get all todos
    @GetMapping("/todos") // Map ONLY GET Requests
    public List<todo> getAllTodos() {
        return todolist; // Return the list of todos
    }

    // add a todo
    @PostMapping("/createTodo") // Map ONLY POST Requests
    @ResponseStatus(HttpStatus.CREATED) // Return 201 status code
    public todo createTodo(@RequestBody todo newTodo) { 
        todolist.add(newTodo); // Add the new todo to the list
        return newTodo; // Return the new todo
    }

    // get a todo by id     
    @GetMapping("/todos/{id}") // Map ONLY GET Requests
    public todo getTodoById(@PathVariable long id) { // Get the todo by id
        for (todo t : todolist) {
            if (t.getId() == id) { // If the id matches the id of the todo in the list 
                return t; // Return the todo
            }
        }
        return null; // Return null if the todo is not found
    }
    
    // delete a todo by id..!
    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable long id) {
        boolean removed = todolist.removeIf(todo -> todo.getId() == id);
        if (removed) {
            return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
        }
    }

    // get a todo by request parameter
    @GetMapping("/todo") // Map ONLY GET Requests
    public ResponseEntity<List<todo>> getTodoByRequestParam(@RequestParam(required = false) boolean isCompleted) {
        System.out.println("Incoming query parameter: " + isCompleted);
        return ResponseEntity.ok(todolist);
    }

}
