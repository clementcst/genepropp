import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface TreeResponce {
  value: any[];
  message: string | null;
  success: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class TreeService {

  constructor(private http: HttpClient) { }

  getTree(id: string):Observable<TreeResponce> {
    return this.http.get<TreeResponce>(`http://localhost:8080/tree?treeId=${id}`,{responseType: 'json'});
  }
}
