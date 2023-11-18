import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IdentificationService {

  constructor(private http: HttpClient) { }

  loginattempt(privatecode: string, password: string):Observable<any> {
    const data = { privatecode, password };
    return this.http.post<any[]>("http://localhost:8080/login", data, {responseType: 'json'});
  }

  registerResquest(username: string, password: string):Observable<any> {
    const data = { username, password }
    return this.http.post<any[]>("http://localhost:8080/register",{responseType: 'json'});
  }
}
