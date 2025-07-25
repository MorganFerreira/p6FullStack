import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { HeaderComponent } from '../../../header/header.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { flatMap, mergeMap, Observable } from 'rxjs';
import { Theme } from '../../../theme/models/theme.model';
import { ThemesService } from '../../../theme/services/themes.services';
import { SessionService } from '../../../../core/services/session.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { StoryService } from '../../services/story.service';
import { Story } from '../../models/story.model';
import { MessageResponse } from '../../../../core/models/messageResponse.model';
import { UserService } from '../../../../core/services/user.service';
import { User } from '../../../../core/models/user.model';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-story-creation',
    imports: [CommonModule, RouterModule, MatIconModule, HeaderComponent, ReactiveFormsModule, MatSnackBarModule, MatButtonModule],
    templateUrl: './story-creation.component.html',
    styleUrl: './story-creation.component.scss'
})
export class StoryCreationComponent {

    public themes$!: Observable<Theme[]>;
	public onError: boolean = false;
    
	private formBuilder = inject(FormBuilder);
	private themesService = inject(ThemesService);
	private sessionService = inject(SessionService);
	private userService = inject(UserService);
	private router = inject(Router);
	private matSnackBar = inject(MatSnackBar);
	private storyService = inject(StoryService);
	private user: User = new User();
	private associatedTheme: Theme = new Theme();
    
	public storyForm = this.formBuilder.group({
        theme_id: ['', [Validators.required]],
        title: ['',[Validators.required,Validators.minLength(3)]],
        content: ['', [Validators.required,Validators.minLength(5)]]
    });

    constructor() {
		this.userService.getUserById((this.sessionService.userSession?.id?.toString())!).subscribe(user => {
			this.user = user;
		});
    }

    ngOnInit() {
        this.themes$ = this.themesService.getAllThemes();
    }

    public onCreatePost() {
		this.themes$.subscribe(themes => {
            const foundTheme = themes.find(theme => theme.id.toString() === this.storyForm.value.theme_id);
            this.associatedTheme = foundTheme ? foundTheme : new Theme();
        	const newStory = this.storyForm?.value as Story;
        	newStory.associatedUser = this.user;
			newStory.associatedTheme = this.associatedTheme;
        	this.storyService.createStory(newStory).subscribe({
            	next: (response: MessageResponse) => {
                	this.onError = false;
               		this.matSnackBar.open(response.message, 'Close', { duration: 3000 });
            		this.router.navigate(['/post']);
            	},
            	error: () => this.onError = true,
        	});
		});
    }

    public back() {
        window.history.back();
    }
}
