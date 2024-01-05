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

  getConversation(convId: string):Observable<conversationResponse> {
    let params = { conversationId: convId.toString() };
    return this.http.get<conversationResponse>("http://localhost:8080/conversation", {params, responseType: 'json'});
  }

  validateUser(endpoints: string):Observable<any> {
    return this.http.post<any[]>("http://localhost:8080" + endpoints, {responseType: 'json'});
  }

  newConversation(userId1: string, userId2: string):Observable<any> {
    let params = new HttpParams()
      .set('userId1', userId1)
      .set('userId2', userId2);
    return this.http.post<any[]>("http://localhost:8080/conversation/new", null, {params, responseType: 'json'});
  }

  newMessage(senderId: number, receiverId: number, content: string):Observable<any> {
    let params = new HttpParams()
      .set('senderId', senderId)
      .set('receiverId', receiverId)
      .set('content', content);
    return this.http.post<conversationResponse>("http://localhost:8080/conversation/message/new", null, {params, responseType: 'json'});
  }
}
