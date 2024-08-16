import { Injectable } from '@angular/core';
import { SignUpComponent } from '../../sign-up/sign-up.component';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  used: boolean = false;

  constructor() { }


  signUp(datas: any){
    console.log(datas);
    this.used = true
  }
}
