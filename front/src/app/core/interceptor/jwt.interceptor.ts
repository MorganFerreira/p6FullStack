import { HttpHandlerFn, HttpHeaders, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { SessionService } from "../services/session.service";
import { Router } from "@angular/router";
import { jwtDecode } from 'jwt-decode';

export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
    
    const sessionService = inject(SessionService);
    const router = inject(Router)

    if (!req.url.includes('/auth')) {
        const jwt = localStorage.getItem('jwt');
        if (jwt) {
            const decodedToken = jwtDecode(jwt);
            if (decodedToken.exp && Date.now() > decodedToken.exp * 1000) {
                sessionService.logOut();
                router.navigateByUrl("/home")
            }
        } else {
            sessionService.logOut();
            router.navigateByUrl("/home")
        }
        const headers = new HttpHeaders().append('Authorization', `Bearer ${jwt}`);
        req = req.clone({ headers });
    }
    return next(req);
}