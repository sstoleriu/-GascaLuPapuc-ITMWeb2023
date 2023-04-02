import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable } from 'rxjs';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor() {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("Da frate")
        var jwtToken = localStorage.getItem("JWT")
        if(jwtToken == null) {
            return next.handle(req);
        }

        const authReq = req.clone({
            headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
        });

        return next.handle(authReq);
    }
}