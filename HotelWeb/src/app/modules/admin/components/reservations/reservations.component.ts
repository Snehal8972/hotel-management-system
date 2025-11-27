







import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-reservations',
   templateUrl: './reservations.component.html',
 styleUrls: ['./reservations.component.scss']
 })
 export class ReservationsComponent implements OnInit {

  currentPage: number = 1;
  total: number = 0;
  reservations: any[] = [];
  pageSize: number = 2; // Show 2 reservations per page

  constructor(private adminService: AdminService, private message: NzMessageService) {}

  ngOnInit() {
    this.getReservations();
  }

  getReservations() {
    this.adminService.getReservations(this.currentPage - 1, this.pageSize).subscribe(res => {
      this.reservations = res.reservationDtoList.map((r: any) => ({
        ...r,
        reservationStatus: r.reservationStatus?.toUpperCase() || 'PENDING',
        paymentStatus: r.paymentStatus?.toUpperCase() || (r.reservationStatus==='APPROVED'?'WAIT':'NOT_AVAILABLE')
      }));
      this.total = res.totalPages * this.pageSize;
    });
  }

  pageIndexChange(page: number) {
    this.currentPage = page;
    this.getReservations();
  }

  changeReservationStatus(reservationId: number, status: string) {
    this.adminService.changeReservationStatus(reservationId, status).subscribe({
      next: () => {
        this.message.success('Reservation status updated successfully', { nzDuration: 3000 });
        this.getReservations();
      },
      error: (err) => {
        this.message.error(err.error || 'Error updating status', { nzDuration: 3000 });
      }
    });
  }
}
