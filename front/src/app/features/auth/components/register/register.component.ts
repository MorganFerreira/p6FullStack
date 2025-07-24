import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.services';
import { RegisterRequest } from '../../models/registerRequest.model';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss',
    imports: [CommonModule, MatIconModule, ReactiveFormsModule, MatButtonModule]

})
export class RegisterComponent implements OnInit {

    private authService = inject(AuthService);
    private matSnackBar = inject(MatSnackBar);
    private router = inject(Router);
    public onError = false;
    //Minimum 8 caractères, au - 1 lettre majuscule, 1 lettre minuscule, un chiffre et un caractère spécial:
    private passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#^?&])[A-Za-z\d@$!%*#^?&]{8,}$/;
    private formBuilder = inject(FormBuilder);
    
    public form = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(this.passwordRegex)]]
    });

    ngOnInit(): void { }

    onRegister(): void {
        const registerRequest = this.form.value as RegisterRequest;
        this.authService.register(registerRequest).subscribe({
            next: () => {
                this.onError = false;
                this.matSnackBar.open("utilisateur créé", 'Close', { duration: 2000 });
                this.router.navigate(['/login']);
            },
            error: () => this.onError = true,
        });
    }

    public home() {
        this.router.navigate(['/']);
    }

}
