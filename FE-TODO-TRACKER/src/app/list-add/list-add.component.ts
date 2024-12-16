import { Component, TemplateRef, ViewChild, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../services/user.service';
import { TodolistService } from '../services/todolist.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { v4 as uuidv4 } from 'uuid';


export interface Task {
  todoListId?: string; // Make todoListId optional
  task: string;
  dueDate: string;
  category: string;
  priority: string;
  isEditMode?: boolean;
}



@Component({
  selector: 'app-list-add',
  templateUrl: './list-add.component.html',
  styleUrls: ['./list-add.component.css']
})
export class ListAddComponent implements OnInit {
  @ViewChild('dateDialog') dateDialog!: TemplateRef<any>;
  @ViewChild('categoryDialog') categoryDialog!: TemplateRef<any>;
  @ViewChild('priorityDialog') priorityDialog!: TemplateRef<any>;

  dialogRef!: MatDialogRef<any>;

  show: boolean = false;
  newTask: Task = {
    todoListId: '', // Set an initial value for todoListId
    task: '',
    dueDate: '',
    category: '',
    priority: ''
  };
  
  
  tasks: Task[] = [];
  searchTerm: string = '';
  filteredTasks: Task[] = [];
  userName: string = '';
  showClearIcon: boolean = false;
  loading: boolean = true; // Add loading state

  constructor(
    private userService: UserService,
    private todolistService: TodolistService,
    private MatSnackBar: MatSnackBar,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    const userEmail = localStorage.getItem('userEmailId');
    if (userEmail) {
      this.todolistService.getUserByEmail(userEmail).subscribe((data: any) => {
        this.userName = data.name;
        localStorage.setItem('userData', JSON.stringify(data));
      });
    }
    
    // Fetch tasks from the server on initialization
    this.fetchTasks();
  }

  fetchTasks() {
    this.todolistService.getAllTodoList().subscribe(
      (tasks: Task[]) => {
        this.tasks = tasks;
        this.filteredTasks = [...this.tasks];
        this.loading = false; // Set loading to false once tasks are fetched
      },
      (error) => {
        console.error('Error fetching tasks:', error);
        this.MatSnackBar.open('Error fetching tasks: ' + (error.error?.message || error.message), 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom'
        });
        this.loading = false; // Set loading to false even if there's an error
      }
    );
  }toggleClearIcon() {
    this.showClearIcon = this.searchTerm.length > 0;
  }

  clearSearch() {
    this.searchTerm = '';
    this.showClearIcon = false;
    this.filterTasks();
  }

  onSearchInput() {
    this.toggleClearIcon();
    this.filterTasks();
  }

  openpopup() {
    this.show = true;
  }

  openDateDialog() {
    this.dialogRef = this.dialog.open(this.dateDialog);
  }

  openCategoryDialog() {
    this.dialogRef = this.dialog.open(this.categoryDialog);
  }

  openPriorityDialog() {
    this.dialogRef = this.dialog.open(this.priorityDialog);
  }

  addTask() {
    if (this.newTask.task && this.newTask.dueDate && this.newTask.category && this.newTask.priority) {
      // Generate unique todoListId
      const todoListId = uuidv4();
  
      const taskToSave: Task = {
        ...this.newTask,
        todoListId: todoListId // Assign generated todoListId to the task
      };
  
      this.todolistService.saveTodolist(taskToSave).subscribe(
        response => {
          // Update tasks array with the new task received from the backend
          this.tasks.push(response);
          // Reset the newTask object
          this.newTask = { task: '', dueDate: '', category: '', priority: '' };
          // Update filteredTasks as well
          this.filteredTasks = [...this.tasks];
          // Hide the add task form
          this.show = false;
          // Show success message
          this.MatSnackBar.open('Task added successfully', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
          // Fetch tasks again to update the list immediately
          this.fetchTasks(); // Add this line
        },
        error => {
          console.error('Error saving task:', error);
          this.MatSnackBar.open('Error saving task: ' + (error.error?.message || error.message), 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
        }
      );
    } else {
      this.MatSnackBar.open('All fields are required.', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'bottom'
      });
    }
  }
  cancelEdit(task: Task) {
    task.isEditMode = false;
  }
  
  
  deleteTask(task: Task) {
    if (task.todoListId) {
      this.todolistService.deleteTodoList(task.todoListId).subscribe(
        () => {
          // Filter out the deleted task from the local array
          this.tasks = this.tasks.filter(t => t !== task);
          // Update filtered tasks as well
          this.filterTasks();
          // Show success message
          this.MatSnackBar.open('Task deleted successfully', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
        },
        error => {
          if (error.status === 200 && error.error instanceof ProgressEvent) {
            // This is not an actual error, it's likely a parsing error
            console.error('Parsing error:', error);
          } else {
            // This is an actual error
            console.error('Error deleting task:', error);
            this.MatSnackBar.open('Error deleting task: ' + (error.error?.message || error.message), 'Close', {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'bottom'
            });
          }
          // If there's an error, still remove the task from the UI
          this.tasks = this.tasks.filter(t => t !== task);
          // Update filtered tasks as well
          this.filterTasks();
        }
      );
    } else {
      console.error('TodoListId is undefined');
    }
  }
  toggleEditMode(task: Task) {
    task.isEditMode = !task.isEditMode;
  }

  updateTodo(task: Task) {
    if (task.todoListId) {
      this.todolistService.updateTodoList(task.todoListId, task).subscribe(
        () => {
          // Task updated successfully
          this.toggleEditMode(task); // Exit edit mode
          this.MatSnackBar.open('Task updated successfully', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
        },
        error => {
          console.error('Error updating task:', error);
          this.MatSnackBar.open('Error updating task: ' + (error.error?.message || error.message), 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
        }
      );
    }
  }
  

  filterTasks() {
    if (this.searchTerm.trim() === '') {
      this.filteredTasks = [...this.tasks];
    } else
    {
      this.filteredTasks = this.tasks.filter(task =>
        task.task.toLowerCase().includes(this.searchTerm.trim().toLowerCase())
      );
    }
 
  }
  saveTask(task: Task) {
    if (task.todoListId) {
      // Call the service method to update the task
      this.todolistService.updateTodoList(task.todoListId, task).subscribe(
        () => {
          // Update the task in the local array
          const index = this.tasks.findIndex(t => t.todoListId === task.todoListId);
          if (index !== -1) {
            this.tasks[index] = task;
            this.filteredTasks = [...this.tasks];
          }
          // Close the edit mode
          task.isEditMode = false;
          // Show success message
          this.MatSnackBar.open('Task updated successfully', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
        },
        error => {
          console.error('Error updating task:', error);
          this.MatSnackBar.open('Error updating task: ' + (error.error?.message || error.message), 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom'
          });
        }
      );
    } else {
      console.error('TodoListId is undefined');
    }
  }
}