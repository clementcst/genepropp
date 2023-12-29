import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface UserResponse {
  value: any[];
  message: string | null;
  success: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  

  constructor(private http: HttpClient) { }

  getUsers():Observable<UserResponse> {
    return this.http.get<UserResponse>('http://localhost:8080/user/all', {responseType: 'json'});
  }

  getUser(id: string):Observable<UserResponse> {
    const params = { userId: id.toString() };
    return this.http.get<UserResponse>(`http://localhost:8080/user`, {params, responseType: 'json'});
  }

  updateUser(id: string, inputs: any):Observable<UserResponse> {
    const params = { userId: id.toString() };
    return this.http.post<UserResponse>(`http://localhost:8080/user/profil/update`, inputs, {params, responseType: 'json'});
  }
}