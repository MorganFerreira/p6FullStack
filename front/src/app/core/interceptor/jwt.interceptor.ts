import { HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { SessionService } from "../services/session.service";
import { Router } from "@angular/router";

export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
    
    const sessionService = inject(SessionService);
    const router = inject(Router)

    if (!req.url.includes('/auth')) {
        const jwt = localStorage["token"];
        if (!jwt) {
            sessionService.logOut();
            router.navigateByUrl("/login")
        }
        req = req.clone({
            setHeaders: {
                Authorization: `Bearer ${jwt}`,
            },
        });
    }
    return next(req);
}