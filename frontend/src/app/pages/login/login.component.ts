import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Message, MessageService } from 'primeng/api';
import { TokenDTO, UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService]
})
export class LoginComponent {
  constructor(private messageService: MessageService, private userService: UserService, private router: Router) {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  submited = false;

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }

  onSubmit() {
    this.submited = true;

    this.messageService.clear();

    if (this.loginForm.valid && this.email?.value && this.password?.value) {
      console.log('Register payload: ', this.loginForm.value);
      
      this.userService.login(this.email.value, this.password.value).subscribe({
        next: (data: TokenDTO) => {
          localStorage.setItem("JWT", data.token);
          this.userService.loadJWT();
          this.router.navigate(['reports'])
          //this.userService.secondCall()
        },
        error: (e) => this.messageService.add({severity: 'error', summary: e}),
        complete: () => console.info('login complete') 
    });

    }
  }
}
