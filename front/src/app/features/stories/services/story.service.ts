import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Story } from "../models/story.model";
import { MessageResponse } from "../../../core/models/messageResponse.model";

@Injectable({ providedIn: 'root' })
export class StoryService {

    private http = inject(HttpClient);
    private pathService = 'http://localhost:8080/api/stories';

    public getAllStories(): Observable<Story[]> {
        return this.http.get<Story[]>(`${this.pathService}`);
    }

    public getStoryById(storyId: string): Observable<Story> {
        return this.http.get<Story>(`${this.pathService}/${storyId}`);
    }

    public createStory(story: Story): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${this.pathService}`, story);
    }
}
