import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { HeaderComponent } from '../header/header.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SessionService } from '../../core/services/session.service';
import { BehaviorSubject } from 'rxjs';
import { Theme } from '../theme/models/theme.model';
import { ThemesService } from '../theme/services/themes.services';
import { User } from '../../core/models/user.model';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-profil',
  imports: [CommonModule, MatButtonModule, HeaderComponent, MatSnackBarModule, ReactiveFormsModule],
  templateUrl: './profil.component.html',
  styleUrl: './profil.component.scss'
})
export class ProfilComponent {

    public noSubscriptionMessage: String = '';
    public themesSubscribed = new BehaviorSubject<Theme[]>([]);
    //Minimum 8 caractères, au - 1 lettre majuscule, 1 lettre minuscule, un chiffre et un caractère spécial:
    private passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#^?&])[A-Za-z\d@$!%*#^?&]{8,}$/;
    private formBuilder = inject(FormBuilder);
    private matSnackBar = inject(MatSnackBar);
    private themesService = inject(ThemesService);
    private sessionService = inject(SessionService);
    private userService = inject(UserService);
    private userId: string | undefined;
    
    public profilForm = this.formBuilder.group({
        name: [this.sessionService.userSession!.name, [Validators.required, Validators.minLength(3)]],
        email: [this.sessionService.userSession!.email, [Validators.required, Validators.email]],
        password: [this.sessionService.userSession!.password, [Validators.required, Validators.minLength(8), Validators.pattern(this.passwordRegex)]]
    });

    constructor() {
        this.userId = this.sessionService.userSession?.id?.toString();
    }

    ngOnInit() {
        this.refreshThemes();
    }

    onUpdateProfil() {
        const userToUpdate = this.profilForm.value as User;
        this.userService.updateUser(this.userId!, userToUpdate).subscribe({
            next: () => {
                this.sessionService.userSession!.name = userToUpdate.name;
                this.sessionService.userSession!.email = userToUpdate.email;
                this.sessionService.userSession!.password = userToUpdate.password;
                this.matSnackBar.open("Profil mise à jour", 'Close', {duration: 3000});
            },
            error: () => this.matSnackBar.open("Erreur lors de la mise à jour du profil", 'Close', {duration: 3000})
        });
    }
    
    public unSubscribe(idTheme: number) {
        this.userService.unsubscribe(this.userId!, idTheme.toString()).subscribe(_ => {
            this.refreshThemes();
        });
    }
    
    private refreshThemes() {
        this.themesService.getAllSubscribedThemesByUserId(this.userId!).subscribe(themes => {
            this.themesSubscribed.next(themes);
            if (themes.length === 0) {
                this.noSubscriptionMessage = "Aucun abonnement détecté";
            } else {
                this.noSubscriptionMessage = '';
            }
        });
    }
}
