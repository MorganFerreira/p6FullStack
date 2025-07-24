import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BehaviorSubject } from 'rxjs';
import { Theme } from '../../models/theme.model';
import { ThemesService } from '../../services/themes.services';
import { SessionService } from '../../../../core/services/session.service';
import { UserService } from '../../../../core/services/user.service';
import { HeaderComponent } from '../../../header/header.component';

@Component({
  selector: 'app-themes',
  imports: [CommonModule, MatButtonModule, HeaderComponent],
  templateUrl: './themes.component.html',
  styleUrl: './themes.component.scss'
})
export class ThemesComponent implements OnInit {

    public themesNotSubscribed = new BehaviorSubject<Theme[]>([]);
    public themesSubscribed = new BehaviorSubject<Theme[]>([]);
    private sessionService = inject(SessionService);
    private themesService = inject(ThemesService);
    private userService = inject(UserService);
    private userId: string | undefined;
    
    constructor() {
        this.userId = this.sessionService.userSession?.id?.toString();
    }

    ngOnInit() {
        this.refreshThemes();
    }

    public subscribe(idTheme: number) {
        this.userService.subscribe(this.userId!, idTheme.toString()).subscribe(_ => {
            this.refreshThemes();
        });
    }

    private refreshThemes() {
        this.themesService.getAllNotSubscribedThemesByUserId(this.userId!).subscribe(themes => {
            this.themesNotSubscribed.next(themes);
        });
        this.themesService.getAllSubscribedThemesByUserId(this.userId!).subscribe(themes => {
            this.themesSubscribed.next(themes);
        });
    }
}
