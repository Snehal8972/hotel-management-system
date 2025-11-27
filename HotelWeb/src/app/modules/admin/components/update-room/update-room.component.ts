import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-room',
  templateUrl: './update-room.component.html',
  styleUrl: './update-room.component.scss'
})
export class UpdateRoomComponent {
updateRoomForm:FormGroup;

id=this.activatedroute.snapshot.params['id'];

 constructor(private fb:FormBuilder,
  private message:NzMessageService,
  private router:Router,
  private adminService:AdminService,
  private activatedroute:ActivatedRoute
 ){
  this.updateRoomForm=this.fb.group({
    name:['',Validators.required],
    type:['',Validators.required],
    price:['',Validators.required]
  })
  this.getRoomId();
 }


 submitForm(){
this.adminService.updateRoomDetails(this.id,this.updateRoomForm.value).subscribe(res=>{
  this.message.success(`Room updated Successfully`,
    {nzDuration:5000}
  );
  this.router.navigateByUrl("/admin/dashboard")
},error=>{
  this.message.error(
    `${error.error}`,
    {nzDuration:5000}
  )
});


 }

 getRoomId(){
  this.adminService.getRoomById(this.id).subscribe(res=>{
    this.updateRoomForm.patchValue(res);
  },error=>{
    this.message.error(`${error.error}`,{nzDuration:5000})
  })
 }



}
