import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { LinkedHashMap } from '../../components/tree-folder/linked-hashmap/linked-hashmap.component';

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

  postTree(treeMergeForDB: any, linkedHashMap: any, myID: number, treeID: number,): Observable<any[]> {
    // Utilisez les variables nécessaires ici, par exemple :
        const params = new HttpParams()
          .set('userId', myID)
          .set('treeId', treeID);

        const requestData = {
          data1: treeMergeForDB,
          data2: linkedHashMap
        };


        console.log(requestData.data1)
        console.log(requestData.data2)

    return this.http.post<any[]>('http://localhost:8080/updateTree', requestData, { params, responseType: 'json' }); 

  }


}
