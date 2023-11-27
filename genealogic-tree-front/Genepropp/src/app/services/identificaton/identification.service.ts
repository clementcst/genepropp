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

  registerResquest(user: any):Observable<any> {
    const data = { lastName: user.lastName, firstName: user.firstName, gender: user.sexe, dateOfBirth: user.birthDate, countryOfBirth: user.countryofbirth, cityOfBirth: user.cityofbirth, email: user.email, password: user.password, noSecu: user.ssn, noPhone: user.phn, nationality: user.nationality, adress: user.adress, postalCode: user.postalCode }
    console.log(data)
    return this.http.post<any[]>("http://localhost:8080/registration", data, { responseType: 'json'});
  }
}
