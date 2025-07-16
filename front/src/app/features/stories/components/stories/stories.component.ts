import { Component, inject, OnInit } from '@angular/core';
import { Story } from '../../models/story.model';
import { StoryService } from '../../services/story.service';
import { SessionService } from '../../../../core/services/session.service';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../../../core/services/user.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
	selector: 'app-stories',
	imports: [CommonModule, RouterModule, MatIconModule],
	templateUrl: './stories.component.html',
	styleUrl: './stories.component.scss'
})
export class StoriesComponent implements OnInit {
		
	public allStories: Story[] = [];
	public storiesFromSubscriptions: Story[] = [];
	public noStoriesMessage: string = '';
	public iconSortDown: boolean = true;

	public storyService = inject(StoryService);
	public userService = inject(UserService);
	public sessionService = inject(SessionService);
	public router = inject(Router);

	ngOnInit() {
		this.storyService.getAllStories().subscribe(stories => {
			this.allStories = stories;
		});
		this.userService.getUserById(this.sessionService.userSession!.id.toString()).subscribe(user => {
			if (user.subscriptions.length === 0) {
				this.noStoriesMessage = "Aucun abonnement détecté, veuillez vous abonner à un thème pour accéder aux articles";
			} else {
				user.subscriptions.forEach(theme => {
					this.storiesFromSubscriptions.push(...this.allStories.filter(story => story.associatedTheme === theme.id));
				});
				this.sortBy();
			}
		});
	}
		
	public sortBy(): void {
		if (this.iconSortDown) this.storiesFromSubscriptions.sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
		else this.storiesFromSubscriptions.sort((a,b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
		this.iconSortDown = !this.iconSortDown;
	}

	public storyById(storyId: number) {
		this.router.navigateByUrl(`stories/story/${storyId}`);
	}
}
