import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Theme } from "../models/theme.model";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class ThemesService {

    private http = inject(HttpClient);
    private pathService = 'http://localhost:8080/api/themes';

    public getAllThemes(): Observable<Theme[]> {
        return this.http.get<Theme[]>(`${this.pathService}`);
    }

    public getAllSubscribedThemesByUserId(userId: String): Observable<Theme[]> {
        return this.http.get<Theme[]>(`${this.pathService}/subscribed/${userId}`);
    }

    public getAllNotSubscribedThemesByUserId(userId: String): Observable<Theme[]> {
        return this.http.get<Theme[]>(`${this.pathService}/notsubscribed/${userId}`);
    }
}
