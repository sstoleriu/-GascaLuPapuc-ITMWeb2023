import { HttpClient } from '@angular/common/http';
import { Injectable,  } from '@angular/core';
import { JWTPayload } from '../model/JWTPayload';



export interface TokenDTO {token: string}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  jwtPayload?: JWTPayload = undefined
  isLoggedIn = false;

  constructor(private http: HttpClient) {
    this.loadJWT()
  }

  login(email:string, password:string) {
    return this.http.post<TokenDTO>('http://192.168.1.149:8081/api/v1/auth/login', {email, password})
  }

  logout() {
    this.isLoggedIn = false;
    this.jwtPayload = undefined;
    
    localStorage.removeItem("JWT");
  }

  loadJWT() {
    var JWTString = localStorage.getItem("JWT")

    if (JWTString == null)
      return;

    var payloadBase64String = localStorage.getItem("JWT")?.split(".")[1];

    if (payloadBase64String == undefined)
      return;

    try {
      var payload = JSON.parse(this.b64_to_utf8(payloadBase64String))

      this.jwtPayload = new JWTPayload(payload)

      this.isLoggedIn = true;
    } catch(e) {console.error(e); console.log("JWT Invalid")}

    console.log("JWT:", this.jwtPayload)
  }

  hasRole(role: string) {
    return this.jwtPayload?.roles?.indexOf(role) != -1;
  }

  b64_to_utf8(token: string) {
    return decodeURIComponent(window.atob(token));
  }

}
