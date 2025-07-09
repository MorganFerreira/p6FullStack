import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.services';
import { SessionService } from '../../../../core/services/session.service';
import { LoginRequest } from '../../models/loginRequest.model';
import { UserSession } from '../../../../core/models/userSession.model';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
    imports: [CommonModule, ReactiveFormsModule, MatIconModule]
})
export class LoginComponent implements OnInit {
	
    private authService = inject(AuthService);
    private sessionService = inject(SessionService);
    private router = inject(Router);
	public onError = false;
	private formBuilder = inject(FormBuilder);
    public form = this.formBuilder.group({
        email: ['', [Validators.required, Validators.minLength(3)]],
        password: ['', [Validators.required, Validators.minLength(3)]]
    });

    ngOnInit(): void { }

    onLogin(): void {
        const loginRequest = this.form.value as LoginRequest;
        this.authService.login(loginRequest).subscribe({
            next: (messageResponse: UserSession) => {
                this.onError = false;
                this.sessionService.logIn(messageResponse)
                this.router.navigate(['/post']);
            },
            error: () => this.onError = true,
        });
    }

    public home() {
        this.router.navigate(['/']);
    }

}
