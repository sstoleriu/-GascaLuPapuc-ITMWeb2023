import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Message, MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService]
})
export class LoginComponent {
  constructor(private messageService: MessageService) {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  submited = false;

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }

  onSubmit() {
    this.submited = true;

    this.messageService.clear();

    if (this.loginForm.valid) {
      console.log('Register payload: ', this.loginForm.value);
      // this.loginForm.disable();

      // TODO Request
      
      //this.messageService.add({severity: 'success', summary: 'S-a produs înregistrare'})
      this.messageService.add({severity: 'error', summary: 'A aparut o eroare'})
    }
  }
}
