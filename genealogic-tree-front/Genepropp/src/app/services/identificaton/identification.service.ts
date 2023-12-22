import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

interface inputsResponse {
  value: any[];
  message: string | null;
  success: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class IdentificationService {

  constructor(private http: HttpClient) { }

  loginattempt(inputs: any):Observable<any> {
    const params = {
      privateCode : inputs.privatecode,
      password : inputs.password
    }
    return this.http.get<inputsResponse>("http://localhost:8080/account/login", {params, responseType: 'json'});
  }

  registerResquest(inputs: any, step: number, userResponse: number):Observable<any> {
    const params = new HttpParams()
      .set('step', step)
      .set('userResponse', userResponse);
    const data = { lastName: inputs.lastName, firstName: inputs.firstName, gender: inputs.sexe, dateOfBirth: inputs.birthDate, countryOfBirth: inputs.countryofbirth, cityOfBirth: inputs.cityofbirth, email: inputs.email, password: inputs.password, noSecu: inputs.ssn, noPhone: inputs.phn, nationality: inputs.nationality, adress: inputs.adress, postalCode: inputs.postalCode }
    return this.http.post<any[]>("http://localhost:8080/account/registration", data, {params, responseType: 'json'});
  }
}
