import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../models/user.model";
import { MessageResponse } from "../models/messageResponse.model";

@Injectable({ providedIn: 'root' })
export class UserService {

    private http = inject(HttpClient);
    private pathService = 'http://localhost:8080/api/user';

    public getUserById(userId: string): Observable<User> {
        return this.http.get<User>(`${this.pathService}/${userId}`);
    }

    public updateUser(userId: string, user: User): Observable<MessageResponse> {
        return this.http.put<MessageResponse>(`${this.pathService}/${userId}`, user);
    }

    public subscribe(userId: string, themeId: string): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${this.pathService}/${userId}/subscribe/${themeId}`, null);
    }

    public unsubscribe(userId: string, themeId: string): Observable<MessageResponse> {
        return this.http.delete<MessageResponse>(`${this.pathService}/${userId}/unsubscribe/${themeId}`);
    }
}
