// import { Component , OnInit} from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { AuthService } from '../../services/auth/auth.service';
// import { NzMessageService } from 'ng-zorro-antd/message';
// import { Router } from '@angular/router';
// @Component({
//   selector: 'app-register',
//   templateUrl: './register.component.html',
//   styleUrl: './register.component.scss'
// })
// export class RegisterComponent   {
// registerForm!: FormGroup;

//   constructor(private fb: FormBuilder,
//     private authService:AuthService,
//     private message:NzMessageService,
//     private router:Router
//   ) {}

//   ngOnInit(): void {
//     this.registerForm = this.fb.group({
//       name: [null, Validators.required],
//       email: [null, [Validators.required, Validators.email]],
//       password: [null, Validators.required]
//     });
//   }

//   submitForm(){
//     this.authService.register(this.registerForm.value).subscribe(res=>{
//       if(res.id !=null){
//         this.message.success("Signup Succesfully",{nzDuration:5000});
//         this.router.navigateByUrl("/");
//       }else{
//         this.message.error(`${res.message}`,{nzDuration:5000})
//       }
//     })
//   }

// }
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });
  }

  submitForm() {
    if (this.registerForm.invalid) return;

    this.loading = true;

    this.authService.register(this.registerForm.value).subscribe({
      next: (res: any) => {
        this.loading = false;

        if (res.id) {
          this.message.success('Signup Successfully', { nzDuration: 5000 });
          this.router.navigateByUrl('/');
        } else if (res.message === 'User already exists') {
          this.message.warning('You are already registered, Login now for booking ', { nzDuration: 5000 });
        } else {
          this.message.error(`${res.message}`, { nzDuration: 5000 });
        }
      },
      error: (err) => {
        this.loading = false;
        this.message.error('Something went wrong. Please try again!', { nzDuration: 5000 });
      }
    });
  }
}
