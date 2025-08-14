import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterLinkActive, RouterLinkWithHref } from '@angular/router';
import { SessionService } from '../../core/services/session.service';

@Component({
	selector: 'app-header',
	imports: [CommonModule, RouterLinkActive, RouterLinkWithHref],
	templateUrl: './header.component.html',
	styleUrl: './header.component.scss'
})
export class HeaderComponent {
	
	private sessionService = inject(SessionService);
	private router = inject(Router);
	public isShowMenuMobile = false;
	
	logOut() {
		this.sessionService.logOut();
		this.router.navigate(['/']);
	}

	public showMenuMobile() {
        this.isShowMenuMobile = !this.isShowMenuMobile;
    }
}
