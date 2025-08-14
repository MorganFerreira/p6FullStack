import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MessageResponse } from "../../../core/models/messageResponse.model";
import { Comments } from "../models/comments.model";

@Injectable({ providedIn: 'root' })
export class CommentService {

    private http = inject(HttpClient);
    private pathService = 'http://localhost:8080/api/comments';

    public getAllComments(): Observable<Comments[]> {
        return this.http.get<Comments[]>(`${this.pathService}`);
    }

    public createComment(comment: Comments): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${this.pathService}`, comment);
    }
}
