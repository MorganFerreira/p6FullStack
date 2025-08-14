import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { HeaderComponent } from '../../../header/header.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { Observable } from 'rxjs';
import { Story } from '../../models/story.model';
import { Comments } from '../../models/comments.model';
import { Component, inject } from '@angular/core';
import { SessionService } from '../../../../core/services/session.service';
import { UserService } from '../../../../core/services/user.service';
import { StoryService } from '../../services/story.service';
import { ActivatedRoute } from '@angular/router';
import { CommentService } from '../../services/comment.service';
import { User } from '../../../../core/models/user.model';
import { MessageResponse } from '../../../../core/models/messageResponse.model';

@Component({
    selector: 'app-story',
    imports: [CommonModule, MatIconModule, HeaderComponent, ReactiveFormsModule, MatSnackBarModule, MatButtonModule],
    templateUrl: './story.component.html',
    styleUrl: './story.component.scss'
})
export class StoryComponent {

    public story$!: Observable<Story>;
    public commentList$!: Observable<Comments[]>;
    public onError = false;
    
    private user: User = new User();
    private storyId!: string | undefined;
    private formBuilder = inject(FormBuilder);
    private sessionService = inject(SessionService);
    private userService = inject(UserService);
	private matSnackBar = inject(MatSnackBar);
	private storyService = inject(StoryService);
    private commentService = inject(CommentService);
    private route = inject(ActivatedRoute)

    public commentForm = this.formBuilder.group({
        content: ['', [Validators.required]]
    });

    constructor() {
		this.userService.getUserById((this.sessionService.userSession?.id?.toString())!).subscribe(user => {
			this.user = user;
		});
    }

    ngOnInit() {
        this.storyId = this.route.snapshot.paramMap.get('storyId')!;
        this.story$ = this.storyService.getStoryById(this.storyId);
        this.commentList$ = this.commentService.getAllComments()
    }

    onCreateComment() {
        this.story$.subscribe(story => {
            const newComment = this.commentForm?.value as Comments;
            newComment.associatedUser = this.user;
            newComment.associatedStory = story;
            this.commentService.createComment(newComment).subscribe({
                next: (response: MessageResponse) => {
                    this.onError = false;
                    this.matSnackBar.open(response.message, 'Close', { duration: 3000 });
                    this.ngOnInit();
                    this.commentForm?.reset();
                },
                error: () => this.onError = true,
            });
        });
    }
    
    public back() {
        window.history.back();
    }
}
