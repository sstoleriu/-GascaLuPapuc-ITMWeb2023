import { HttpClient } from '@angular/common/http';
import { Injectable,  } from '@angular/core';
import { JWT } from '../model/JWT';



export interface TokenDTO {token: string}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  roles!: string[]
  expirationTime?: number
  jwt?: JWT = undefined

  constructor(private http: HttpClient) {
    this.loadJWT()
  }

  login(email:string, password:string ) {
    return this.http.post<TokenDTO>('http://192.168.1.149:8081/api/v1/auth/login', {email, password})
      .subscribe({
        next: (data: TokenDTO) => {
          localStorage.setItem("JWT", data.token);
        },
        error: (e) => console.error(e),
        complete: () => console.info('login complete') 
    });
  }

  loadJWT() {
    var payloadBase64String = localStorage.getItem("JWT")?.split(".")[1];
    if (payloadBase64String == undefined)
      return;

    var payload = JSON.parse(this.b64_to_utf8(payloadBase64String))

    this.jwt = new JWT(payload)

    console.log(this.jwt)

    return true;
  }

  isLoggedIn(): boolean {
    return true;
  }

  hasRole(role: string) {
    return this.roles.indexOf(role) != -1;
  }

  b64_to_utf8(token: string) {
    return decodeURIComponent(window.atob(token));
  }

  logOut() {
    localStorage.removeItem("JWT");
  }

}
