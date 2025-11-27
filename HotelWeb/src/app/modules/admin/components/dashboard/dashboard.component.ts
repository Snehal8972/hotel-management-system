// import { Component } from '@angular/core';
// import { AdminService } from '../../admin-services/admin.service';
// import { NzMessageService } from 'ng-zorro-antd/message';

// @Component({
//   selector: 'app-dashboard',
//   templateUrl: './dashboard.component.html',
//   styleUrl: './dashboard.component.scss'
// })
// export class DashboardComponent {
// currentPage=1;
// rooms=[];
// constructor(private adminService:AdminService,
//   private message:NzMessageService,
// ){
//   this.getRooms();
// }

// getRooms(){
//   this.adminService.getRooms(this.currentPage -1).subscribe(res=>{
//     console.log(res);
//     this.rooms=res;
//   })
// }


// }
import { Component } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  currentPage = 1;
  rooms: any[] = [];
  totalPages = 0;
  total:any;
loading=false;
  constructor(
    private adminService: AdminService,
    private message: NzMessageService,
    private modalService:NzModalService
  ) {
    this.getRooms();
  }

  getRooms() {
    this.adminService.getRooms(this.currentPage - 1).subscribe(res => {
      console.log(res);
      this.rooms = res.roomDtoList;   // <-- FIXED
      this.totalPages = res.totalPages;
      this.total=res.totalPages*1;
    });

  }
  pageIndexChange(value:any){
    this.currentPage=value;
    this.getRooms();
  }

  showConfirm(roomId:number){
    this.modalService.confirm({
      nzTitle:'Comfirm',
      nzContent:'Do you want to delete this room?',
      nzOkText:'Delete',
      nzCancelText:'Cancel',
      nzOnOk:()=>this.deleteRoom(roomId)
    })
  }


deleteRoom(roomId:number){
  this.adminService.deleteRoom(roomId).subscribe(res=>{
    this.message.success(`Room Deleted Successfully`,
      {nzDuration:5000}
    );
    this.getRooms();
  },
error=>{
  this.message.error(`${error.error}`,{nzDuration:5000});
})
}

}
