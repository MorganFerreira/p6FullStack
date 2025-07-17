import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserSession } from '../models/userSession.model';

@Injectable({ providedIn: 'root' })
export class SessionService {

  public isLogged = false;
  public userSession: UserSession | undefined;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(userSession: UserSession): void {
    this.userSession = userSession;
    localStorage.setItem('token', userSession.token);
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    this.userSession = undefined;
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}