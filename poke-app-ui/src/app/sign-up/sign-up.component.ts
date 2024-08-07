import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { from } from 'rxjs';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  signUpForm!: FormGroup;

  ngOnInit(): void {
    this.signUpForm = new FormGroup({
      email: new FormControl<string|null>(null, [
        Validators.required, 
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!?\/:\-_]).{8,20}$/)
      ]),
      confirmPassword: new FormControl('', [
        Validators.required
      ])
    }, {
      validators: this.passwordMatchValidator('password', 'confirmPassword')
    }
  ) 
  }

  signUp(){
    if(this.signUpForm.valid){
      console.log(this.signUpForm.value);
    } else {
      console.log("wrong datas");
    }
  }

  public validateInput(field: string, error: string) {
    return this.signUpForm.get(field)?.hasError(error) && (this.signUpForm.get(field)?.dirty || this.signUpForm.get(field)?.touched);
  }

  public validateForm() {
    return this.signUpForm.hasError('passwordMismatch') && (this.signUpForm.get('confirmPassword')?.dirty || this.signUpForm.get('confirmPassword')?.touched);
  }

  private passwordMatchValidator(password: string, confirmPassword: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const passwordValue = control.get(password)?.value;
      const confirmPasswordValue = control.get(confirmPassword)?.value;
  
      if (passwordValue && confirmPasswordValue && passwordValue !== confirmPasswordValue) {
        return { passwordMismatch: true };
      }
      return null;
    };
  }
}
