import {inject} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { ActivatedRouteSnapshot } from '@angular/router';
import { RouterStateSnapshot } from '@angular/router';
import { ROLE } from '../enums/ROLE';

export const authGuard = (next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot) => {
    const userService = inject(UserService);
    const router = inject(Router);

    console.log("ROLES:", next.data)
    if (next.data != undefined) {
        var authorized_roles = next.data['authorized_roles'];

        for(var i = 0; i < authorized_roles.length; i++) {
            if(userService.hasRole(authorized_roles[i])) {
                console.log(authorized_roles[i], userService.hasRole(authorized_roles[i]))
                return true;
            }
        }
    }

    //return router.parseUrl('/login');
    return router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
};

