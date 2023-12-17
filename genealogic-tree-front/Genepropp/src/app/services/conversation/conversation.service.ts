import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

interface conversationResponse {
  value: any[];
  message: string | null;
  success: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  constructor(private http: HttpClient) { }

  getConversation(id: string):Observable<conversationResponse> {
    const params = { userId: id.toString() };
    return this.http.get<conversationResponse>("http://localhost:8080/conversation", {params, responseType: 'json'});
  }

  newConversation(userId1: string, userId2: string):Observable<any> {
    const params = new HttpParams()
      .set('userId1', userId1)
      .set('userId2', userId2);
    return this.http.post<any[]>("http://localhost:8080/conversation/new", {params, responseType: 'json'});
  }

  newMessage(senderId: any, receiverId: any, content: string):Observable<any> {
    const params = new HttpParams()
      .set('userId1', senderId)
      .set('userId2', receiverId)
      .set('userId1', content);
    return this.http.post<any[]>("http://localhost:8080/conversation/message/new", {params, responseType: 'json'});
  }
}
