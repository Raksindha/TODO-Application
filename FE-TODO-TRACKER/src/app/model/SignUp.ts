import { TodoList } from "./TodoList";

export interface SignUp {
    email: string;
    name: string;
    password: string;
    dateOfBirth?: string;
    todoLists?: TodoList[];
}
