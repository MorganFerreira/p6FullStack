import { Component, inject, OnInit } from '@angular/core';
import { Story } from '../../models/story.model';
import { StoryService } from '../../services/story.service';
import { SessionService } from '../../../../core/services/session.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { HeaderComponent } from '../../../header/header.component';
import { Theme } from '../../../theme/models/theme.model';
import { ThemesService } from '../../../theme/services/themes.services';

@Component({
	selector: 'app-stories',
	imports: [CommonModule, RouterModule, MatIconModule, HeaderComponent],
	templateUrl: './stories.component.html',
	styleUrl: './stories.component.scss'
})
export class StoriesComponent implements OnInit {
		
	public storiesFromSubscriptions: Story[] = [];
	public allStories: Story[] = [];
	public noStoriesMessage: string = '';
	public iconSortDown: boolean = true;

	private storyService = inject(StoryService);
	private sessionService = inject(SessionService);
	private themesServices = inject(ThemesService);
	private router = inject(Router);
	private userId: string | undefined;
	
	constructor() {
		this.userId = this.sessionService.userSession?.id?.toString();
	}

	ngOnInit() {
		this.storyService.getAllStories().subscribe(stories => {
			this.allStories = stories;
			this.themesServices.getAllSubscribedThemesByUserId(this.userId!).subscribe(themes => {
				if (themes.length === 0) {
					this.noStoriesMessage = "Aucun abonnement détecté, veuillez vous abonner à un thème pour accéder aux articles";
				} else {
					themes.forEach(theme => {
						this.filterStoriesByTheme(theme);
					});
					this.sortBy();
				}
			});
		});
	}
		
	public sortBy() {
		if (this.iconSortDown) this.storiesFromSubscriptions.sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
		else this.storiesFromSubscriptions.sort((a,b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
		this.iconSortDown = !this.iconSortDown;
	}
	
	private filterStoriesByTheme(theme: Theme) {
		this.allStories.forEach(story => {
			story.associatedTheme.id === theme.id ? this.storiesFromSubscriptions.push(story) : null;
		})
	}

	public storyById(storyId: number) {
		this.router.navigateByUrl(`storyDetail/${storyId}`);
	}
}
