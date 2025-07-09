import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginRequest } from "../models/loginRequest.model";
import { RegisterRequest } from "../models/registerRequest.model";
import { Observable } from "rxjs";
import { UserSession } from "../../../core/models/userSession.model";
import { MessageResponse } from "../../../core/models/messageResponse.model";

@Injectable({ providedIn: 'root' })
export class AuthService {
    
    private pathService = 'http://localhost:8080/api/auth';

    constructor(private http: HttpClient) { }

    public login(loginRequest: LoginRequest): Observable<any> {
        return this.http.post<UserSession>(`${this.pathService}/login`, loginRequest);
    }

    public register(registerRequest: RegisterRequest): Observable<any> {
        return this.http.post<MessageResponse>(`${this.pathService}/register`, registerRequest)
    }

}