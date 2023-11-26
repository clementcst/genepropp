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
export class IdentificationService {

  constructor(private http: HttpClient) { }

  loginattempt(privatecode: string, password: string):Observable<any> {
    const params = {
      privatecode : privatecode,
      password : password
    }
    return this.http.get<UserResponse>("http://localhost:8080/login", {params, responseType: 'json'});
  }

  registerResquest(username: string, password: string):Observable<any> {
    const data = { username, password }
    return this.http.get<any[]>("http://localhost:8080/register",{responseType: 'json'});
  }
}
